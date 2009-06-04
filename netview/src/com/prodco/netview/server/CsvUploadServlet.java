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
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.prodco.netview.domain.FlowRecord;

public class CsvUploadServlet extends HttpServlet
  {

  private static final long serialVersionUID = 1L;

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
  @SuppressWarnings("unchecked")
  public void doPost ( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException
    {
    BufferedReader in = request.getReader();
    ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
    strat.setType(FlowRecord.class);
    String[] columns = new String[] {"timecode", "timestampStart", "vlanName", 
      "vlanTag", "appName", "srcIp", "destIp", "srcPort", "destPort",
      "ipProto", "ipTos", "tcpFlags", "packetCount", "byteCount"}; 
    strat.setColumnMapping(columns);

    CsvToBean csv = new CsvToBean();
    List<FlowRecord> list = csv.parse(strat, in);

    response.setContentType( "text/html" );
    PersistenceManager pm = PersistenceManagerHelper.getInstance().getPersistenceManager();
    try {
      for (FlowRecord rec : list) {
        rec.setSiteId( 1l );
        pm.makePersistent(rec);
      }
    } finally {
        pm.close();
    }
//    PrintWriter out = response.getWriter();
//    out
//      .println( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" );
//    out.println( "<HTML>" );
//    out.flush();
//    out.close();
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
