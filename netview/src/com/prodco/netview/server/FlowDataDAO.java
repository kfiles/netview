
package com.prodco.netview.server;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.prodco.netview.domain.FlowRecord;

public class FlowDataDAO
  {
  private final Logger log = Logger.getLogger( this.getClass().getName() );
  private static final int MAX_CATEGORIES = 10;
  
  SimpleDateFormat labelFormat = new SimpleDateFormat("HH:mm");
  
  @SuppressWarnings("unchecked")
  public XYDataSet getTopSrcPorts ( int siteId, int startTime, int endTime )
    {
    XYDataSet rv = new XYDataSet();
    try
      {
      PersistenceManager pm = PersistenceManagerHelper.getInstance()
        .getPersistenceManager();
      String query = "select from "
        + FlowRecord.class.getName() + " where siteId = " + siteId
        + " and timecode>=" + startTime + " and timecode<=" + endTime;
      List<FlowRecord> recs = (List<FlowRecord>) pm.newQuery( query ).execute();
      int rpb = recs.size() / MAX_CATEGORIES;
      Hashtable<Integer, TopQueryResult<Integer>>  top = new Hashtable<Integer, TopQueryResult<Integer>>();
      for (FlowRecord rec : recs) 
        {
        TopQueryResult<Integer> entry = top.get( rec.getSrcPort() );
        if (null == entry)
          entry = new TopQueryResult<Integer>( rec.getSrcPort() );
        entry.addBytes( rec.getByteCount() );
        entry.addPackets( rec.getPacketCount() );
        entry.addRecord( rec );
        
        }
      }
    catch ( RuntimeException e )
      {
      log.severe( "Error getting top ports: " + e.getClass().getName() + ": " + e.getMessage() );
      }

    return rv;
    }
  }
