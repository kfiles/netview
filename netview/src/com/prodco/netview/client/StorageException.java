/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client;

public class StorageException extends Exception
  {
  public StorageException ()
    {
    }

  public StorageException ( String msg )
    {
    super( msg );
    }

  public StorageException ( String msg, Throwable cause )
    {
    super( msg, cause );
    }

  public StorageException ( Throwable cause )
    {
    super( cause );
    }
  }
