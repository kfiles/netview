/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.gadgets;

import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class TreeMapGadget2 extends Gadget
  {
  private String divName;

  public static class Class extends GadgetClass
    {
    private static int divId = 0;
    public Class ()
      {
      super( "Treemap", false );
      }

    public Gadget newGadget ()
      {
      return new TreeMapGadget2( this, "TreeMapDiv"+ divId++);
      }
    }
  
  protected TreeMapGadget2 ( Class c, String div )
    {
    super( c );
    this.divName = div;
    TreeMap widget = new TreeMap( divName);
    widget.setWidth( "100%" );
//    HorizontalPanel mpanel = new HorizontalPanel();
    initWidget( widget);
//    mpanel.setWidth( "100%" );
//    mpanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
    }


  }
