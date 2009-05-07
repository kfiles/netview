/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client;

import java.util.HashMap;
import java.util.Map;

public abstract class Storage
  {

  private Map values = new HashMap();

  protected Map getValues ()
    {
    return values;
    }

  public Storage ()
    {
    try
      {
      load();
      }
    catch ( StorageException e )
      {
      // loading fails silently - empty values
      }
    }

  public String getValue ( String key )
    {
    return (String) values.get( key );
    }

  public void setValue ( String key, String value )
    {
    values.put( key, value );
    }

  public abstract void save () throws StorageException;

  public abstract void load () throws StorageException;

  }
