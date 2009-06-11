/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.google.apphosting.api.DeadlineExceededException;
import com.prodco.netview.domain.FlowRecord;
import com.prodco.netview.server.PersistenceManagerHelper;

public class CsvUploadServlet extends HttpServlet
  {

  private static final long serialVersionUID = 1L;

  private final Logger log = Logger.getLogger( this.getClass().getName() );
  
  SimpleDateFormat tcFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ssZ");
  
  /**
   * Constructor of the object.
   */
  public CsvUploadServlet ()
    {
    super();
    }

  /**
   * Destruction of the servlet. <br>
   */
  public void destroy ()
    {
    super.destroy(); // Just puts "destroy" string in log
    // Put your code here
    }

  /**
   * The doGet method of the servlet. <br>
   * 
   * This method is called when a form has its tag value method equals to get.
   * 
   * @param request
   *          the request send by the client to the server
   * @param response
   *          the response send by the server to the client
   * @throws ServletException
   *           if an error occurred
   * @throws IOException
   *           if an error occurred
   */
  public void doGet ( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException
    {
    System.err.println( "Got HEAD request" );

    response.setContentType( "text/html" );
    PrintWriter out = response.getWriter();
    out
      .println( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" );
    out.println( "<HTML>" );
    out.println( "  <HEAD><TITLE>A Servlet</TITLE></HEAD>" );
    out.println( "  <BODY>" );
    out.print( "    This is " );
    out.print( this.getClass() );
    out.println( ", using the GET method" );
    out.println( "  </BODY>" );
    out.println( "</HTML>" );
    out.flush();
    out.close();
    }

  /**
   * The doPost method of the servlet. <br>
   * 
   * This method is called when a form has its tag value method equals to post.
   * 
   * @param request
   *          the request send by the client to the server
   * @param response
   *          the response send by the server to the client
   * @throws ServletException
   *           if an error occurred
   * @throws IOException
   *           if an error occurred
   */
  @SuppressWarnings ( "unchecked" )
  public void doPost ( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException
    {
    BufferedReader in = request.getReader();
    ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
    strat.setType( FlowRecord.class );
    String[] columns = new String[]{"timecode", "timestampStart", "vlanName",
      "vlanTag", "appName", "srcIp", "destIp", "srcPort", "destPort",
      "ipProto", "ipTos", "tcpFlags", "packetCount", "byteCount"};
    strat.setColumnMapping( columns );

    CsvToBean csv = new CsvToBean();
    PersistenceManager pm = PersistenceManagerHelper.getInstance()
      .getPersistenceManager();
    int count = 0;
    try
      {
      List<FlowRecord> list = csv.parse( strat, in );
      int listSize = list.size();
      log.info( "CSV Parsed with " + listSize + " records" );

      response.setContentType( "text/plain" );
      for ( FlowRecord rec : list )
        {
        rec.setSiteId( 1l );
        rec.setVlanName( "LAN" );
        try {
          rec.setTimecode( (int)(tcFormat.parse( rec.getTimestampStart() + "-0000" ).getTime()/30000l) );
        } catch (ParseException e) {
         //leave old timecode
        }

        pm.makePersistent( rec );
        count++;
        }
      log.info( "Done storing " + listSize + " rows");

      }
    catch ( DeadlineExceededException e ) {
      //Let the sender know how many records were processed
      response.getWriter().println( count );
      response.getWriter().flush();
    }
    catch ( RuntimeException e )
      {
      log.severe( "Error parsing CSV file: " + e.getClass().getName() + ": " + e.getMessage() );
      }
    finally
      {
      pm.close();
      log.info( "Transaction ended");
      }
    // PrintWriter out = response.getWriter();
    // out
    // .println( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01
    // Transitional//EN\">" );
    // out.println( "<HTML>" );
    // out.flush();
    // out.close();
    }

  /**
   * Initialization of the servlet. <br>
   * 
   * @throws ServletException
   *           if an error occurs
   */
  public void init () throws ServletException
    {
    // Put your code here
    }

  }
