
package com.prodco.netview.server;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.prodco.netview.client.util.ExceptionUtil;
import com.prodco.netview.domain.FlowRecord;

public class FlowDataDAO
  {
  private final Logger log = Logger.getLogger( this.getClass().getName() );
  private static final int MAX_CATEGORIES = 10;

  SimpleDateFormat labelFormat = new SimpleDateFormat( "HH:mm" );

  @SuppressWarnings ( "unchecked" )
  public XYDataSet getTopSrcPorts ( int siteId, int startTime, int endTime,
    int num )
    {
    XYDataSet rv = new XYDataSet();
    try
      {
      PersistenceManager pm = PersistenceManagerHelper.getInstance()
        .getPersistenceManager();
      String q = "siteId == " + siteId; 
//      + " && timecode > " + startTime + " && timecode < " + endTime;
      Query query = pm.newQuery( FlowRecord.class, q);
      query.setOrdering( "timecode asc" );
      List<FlowRecord> recs = (List<FlowRecord>) query.execute();
      if (null == recs) {
        log.warning( "No records matched the query for siteId " + siteId);
        return null;
      }
//      int rpb = recs.size() / MAX_CATEGORIES;
      Hashtable<Integer, TopQueryResult<Integer>> top = new Hashtable<Integer, TopQueryResult<Integer>>();
      for ( FlowRecord rec : recs )
        {
        TopQueryResult<Integer> entry = top.get( rec.getSrcPort() );
        if ( null == entry )
          entry = new TopQueryResult<Integer>( rec.getSrcPort() );
        entry.addBytes( rec.getByteCount() );
        entry.addPackets( rec.getPacketCount() );
        entry.addRecord( rec );
        top.put( entry.getTopProp(), entry );
        }
      TreeSet<TopQueryResult<Integer>> sorted = new TreeSet<TopQueryResult<Integer>>();
      Enumeration<TopQueryResult<Integer>> ii = top.elements();
      while ( ii.hasMoreElements() )
        {
        sorted.add( ii.nextElement() );
        }
      Iterator<TopQueryResult<Integer>> jj = sorted.iterator();
      TopQueryResult<Integer> entry;
      for ( int i = 0; i < num
        && jj.hasNext(); i++ )
        {
        entry = jj.next();
        XYSeries series = new XYSeries();
        for ( FlowRecord rec : entry.getRecords() )
          {
          series.addPoint( labelFormat.format( new Date( 30000l * rec
            .getTimecode() ) ), rec.getByteCount() );
          }
        rv.addDataSet( series );
        }
      }
    catch ( NullPointerException e) {
      log.severe( e.getClass().getSimpleName() + " in retrieving flows: " + 
        ExceptionUtil.getMessageOrType( e ) + " at\n" + ExceptionUtil.getTrace( e ));
    }
    catch ( RuntimeException e )
      {
      log.severe( "Error getting top ports: "
        + e.getClass().getName() + ": " + e.getMessage() );
      }

    return rv;
    }
  }
