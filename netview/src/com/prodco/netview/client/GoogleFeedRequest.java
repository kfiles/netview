/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client;

public class GoogleFeedRequest
  {
  static int id = 0;

  static public void get ( String url, GoogleFeedRequestHandler handler )
    {
    createCallbackFunction( handler, id++, url );
    }

  private native static void createCallbackFunction (
    GoogleFeedRequestHandler obj, int id, String url )/*-{
    var _id = id;
    if( $wnd.gfeed == null )
      $wnd.gfeed = new Array();
    $wnd.gfeed[id] = new $wnd.google.feeds.Feed(url);
    $wnd.gfeed[id].setNumEntries(10);
    $wnd.gfeed[id].load( function( result ) {
        if(!result.error) {
    	obj.@com.prodco.netview.client.GoogleFeedRequestHandler::onRequestComplete(Ljava/lang/String;)( result.feed.toJSONString() );
        }
        else {
    	obj.@com.prodco.netview.client.GoogleFeedRequestHandler::onRequestFailed(Ljava/lang/String;)( result.error.message);
        }
    });
  }-*/;
  }
