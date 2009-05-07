/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client;

public interface GoogleFeedRequestHandler
  {
  public void onRequestComplete ( String result );

  public void onRequestFailed ( String error );
  }
