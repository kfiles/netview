
package com.prodco.netview.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.json.simple.JSONObject;


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
      System.out.println("Getting data from database");
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
      int rpb = recs.size(); // MAX_CATEGORIES;
      if (recs.size() == 0 ) {
        System.out.println("No records found for this query");
      } else {
        System.out.println("Got "+ rpb+" records back from database");
      }
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
  
  @SuppressWarnings ( "unchecked" )
  public List<JSONObject> getTreemapJsonSet ( int siteId, int startTime, int endTime,
    int num )
    {
    List<JSONObject> rv = new ArrayList<JSONObject>();
    try
      {
      System.out.println("Getting TreemapDataSet from database for site="+siteId);
      PersistenceManager pm = PersistenceManagerHelper.getInstance()
        .getPersistenceManager();
//      String query = "select from " + FlowRecord.class.getName() + " where appName=='SNMP'";
//      List<FlowRecord> recs = (List<FlowRecord>) pm.newQuery(query).execute();
      int packetCount=1;
      String q = "siteId == " + siteId; 
//      + " && timecode > " + startTime + " && timecode < " + endTime;
      Query query = pm.newQuery( FlowRecord.class, q);
      query.setOrdering( "timecode asc" );
      List<FlowRecord> recs = (List<FlowRecord>) query.execute();
      if (null == recs) {
        log.warning( "No records matched the query for siteId " + siteId);
        return null;
      }
    int rpb = recs.size(); // MAX_CATEGORIES;
    System.out.println("Got "+ rpb+" records back from database");
    Hashtable<String, TopQueryResult<String>> top = new Hashtable<String, TopQueryResult<String>>();
//    List<JSONObject> jsons = new ArrayList<JSONObject>();
    for ( FlowRecord rec : recs )
      {
      JSONObject obj = createjsonObject(rec);
      rv.add( obj );
      }
    System.out.println("Total entries="+rv.size());
    }

    catch ( NullPointerException e) {
      log.severe( e.getClass().getSimpleName() + " in retrieving flows: " + 
        ExceptionUtil.getMessageOrType( e ) + " at\n" + ExceptionUtil.getTrace( e ));
    }

    return rv;
    }

  
  @SuppressWarnings ( "unchecked" )
  public XYDataSet getTreemapDataSet ( int siteId, int startTime, int endTime,
    int num )
    {
    XYDataSet rv = new XYDataSet();
    try
      {
//      testSimpleJsonCode("172.15.16.2","HTTP",108758);
//      System.out.println("Getting TreemapDataSet from database for site="+siteId);
      PersistenceManager pm = PersistenceManagerHelper.getInstance()
        .getPersistenceManager();
//      String query = "select from " + FlowRecord.class.getName() + " where appName=='SNMP'";
//      List<FlowRecord> recs = (List<FlowRecord>) pm.newQuery(query).execute();
      int packetCount=1;
      String q = "siteId == " + siteId; 
//      + " && timecode > " + startTime + " && timecode < " + endTime;
      Query query = pm.newQuery( FlowRecord.class, q);
      query.setOrdering( "timecode asc" );
      List<FlowRecord> recs = (List<FlowRecord>) query.execute();
      if (null == recs) {
        log.warning( "No records matched the query for siteId " + siteId);
        return null;
      }
    int rpb = recs.size(); // MAX_CATEGORIES;
    System.out.println("Got "+ rpb+" records back from database");
    Hashtable<String, TopQueryResult<String>> top = new Hashtable<String, TopQueryResult<String>>();
    List<JSONObject> jsons = new ArrayList<JSONObject>();
    for ( FlowRecord rec : recs )
      {
      JSONObject obj = createjsonObject(rec);
      jsons.add( obj );
      TopQueryResult<String> entry = top.get( rec.getSrcIp());
      if ( null == entry ) {
        entry = new TopQueryResult<String>( rec.getSrcIp() );
      }
      entry.addBytes( rec.getByteCount() );
      entry.addPackets( rec.getPacketCount() );
      entry.addRecord( rec );
      top.put( entry.getTopProp(), entry );
      }
    System.out.println("Total entries="+top.size());
    TreeSet<TopQueryResult<String>> sorted = new TreeSet<TopQueryResult<String>>();
    Enumeration<TopQueryResult<String>> ii = top.elements();
    while ( ii.hasMoreElements() )
      {
      sorted.add( ii.nextElement() );
      }
    Iterator<TopQueryResult<String>> jj = sorted.iterator();
    TopQueryResult<String> entry;
    for ( int i = 0; i < num
      && jj.hasNext(); i++ )
      {
      entry = jj.next();
      XYSeries series = new XYSeries();
      series.setLabel( entry.getTopProp() );
      for ( FlowRecord rec : entry.getRecords() )
      {
        String x=labelFormat.format( new Date( 30000l * rec.getTimecode() ) );
        if (x == null)
          System.out.println("X is NULL");
        else {
            long y = rec.getByteCount();
            long t = 30000l * rec.getTimecode();
            Date d = new Date ( t );
            series.addPoint( labelFormat.format(d), y );
        }
      }
      System.out.println("adding series for property="+entry.getTopProp()+" with "+entry.getRecords().size()+" records");
      rv.addDataSet( series );
      }
    }

    catch ( NullPointerException e) {
      log.severe( e.getClass().getSimpleName() + " in retrieving flows: " + 
        ExceptionUtil.getMessageOrType( e ) + " at\n" + ExceptionUtil.getTrace( e ));
    }
    catch ( RuntimeException e )
      {
      log.severe( "Error getting top srcIp: "
        + e.getClass().getName() + ": " + e.getMessage() );
      }

    return rv;
    }

  private JSONObject createjsonObject ( FlowRecord rec )
    {
    JSONObject obj=new JSONObject();
    obj.put("name",rec.getSrcIp());
    obj.put("app",rec.getAppName());
    obj.put("size",new Long(rec.getByteCount()));
    return obj;
    }

  private void testSimpleJsonCode (String srcIp, String appName, int size)
    {
    JSONObject obj=new JSONObject();
    obj.put("name",srcIp);
    obj.put("app",appName);
    obj.put("size",new Integer(size));
    System.out.print(obj);    
    }

  }

