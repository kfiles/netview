
package com.prodco.netview.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
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
import com.prodco.netview.server.query.TopQueryResult;
import com.prodco.netview.server.query.TopQueryType;

public class FlowDataDAO
  {
  private final Logger log = Logger.getLogger( this.getClass().getName() );
  private static final int MAX_CATEGORIES = 10;

  SimpleDateFormat labelFormat = new SimpleDateFormat( "HH:mm" );

  @SuppressWarnings ( "unchecked" )
  public XYDataSet getTopResult( int siteId, int startTime, int endTime, TopQueryType type,
    int num )
    {
    XYDataSet rv = null;
    try
      {
      System.out.println("Getting data from database");
      PersistenceManager pm = PersistenceManagerHelper.getInstance()
        .getPersistenceManager();
      pm.currentTransaction().begin();
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
      rv = type.getHandler().getDataSet( recs, num );
      pm.currentTransaction().commit();
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
      Connection conn = null;
      List<FlowRecord> recs =  new ArrayList<FlowRecord>();

      try
      {
          String userName = "prodco";
          String password = "prodco";
          String url = "jdbc:mysql://localhost:3306/prodco";
          Class.forName ("com.mysql.jdbc.Driver").newInstance ();
          conn = (Connection) DriverManager.getConnection (url, userName, password);
          System.out.println ("Database connection established");
          
          String sql = "Select flow_id,app_id,app_name,src_ip,src_port,dest_ip,dest_port,"+
          "byte_count,packet_count,site_id from flow_sign where site_id="+siteId;
          ResultSet rs = conn.createStatement().executeQuery( sql );
          

          while (rs.next()) {
            FlowRecord flow = new FlowRecord();
            flow.setAppName(rs.getString( "app_name" ));
            flow.setSrcIp( rs.getString("src_ip"));
            flow.setSrcPort(rs.getInt( "src_port" ));
            flow.setDestIp( rs.getString( "dest_ip" ));
            flow.setDestPort(rs.getInt( "dest_port"));
            flow.setByteCount(rs.getLong( "byte_count"));
            flow.setPacketCount(rs.getInt( "packet_count"));
            flow.setAppId( (long)rs.getInt( "app_id"));
            flow.setId( (long)rs.getInt( "flow_id" ) );
            flow.setSiteId( (long)rs.getInt( "site_id" ) );
            recs.add( flow );
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
          System.err.println ("Cannot connect to database server");
      }
      finally
      {
          if (conn != null)
          {
              try
              {
                  conn.close ();
                  System.out.println ("Database connection terminated");
              }
              catch (Exception e) { /* ignore close errors */ }
          }
      }      
      
//      PersistenceManager pm = PersistenceManagerHelper.getInstance()
//        .getPersistenceManager();
////      String query = "select from " + FlowRecord.class.getName() + " where appName=='SNMP'";
////      List<FlowRecord> recs = (List<FlowRecord>) pm.newQuery(query).execute();
//      int packetCount=1;
//      String q = "siteId == " + siteId; 
////      + " && timecode > " + startTime + " && timecode < " + endTime;
//      Query query = pm.newQuery( FlowRecord.class, q);
//      query.setOrdering( "timecode asc" );
//      List<FlowRecord> recs = (List<FlowRecord>) query.execute();
      if (0 == recs.size()) {
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

