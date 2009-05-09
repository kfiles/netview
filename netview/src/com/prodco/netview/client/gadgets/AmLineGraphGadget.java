/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.gadgets;

import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class AmLineGraphGadget extends Gadget
  {
  private String divName;

  public static class Class extends GadgetClass
    {
    private static int divId = 0;
    public Class ()
      {
      super( "IPGraph", false );
      }

    public Gadget newGadget ()
      {
      return new AmLineGraphGadget( this, "graphGadgetDiv"+ divId++);
      }
    }
  
  protected AmLineGraphGadget ( Class c, String div )
    {
    super( c );
    this.divName = div;
    AmLineGraph widget = new AmLineGraph( divName);
    widget.setWidth( "100%" );
//    HorizontalPanel mpanel = new HorizontalPanel();
    initWidget( widget);
//    mpanel.setWidth( "100%" );
//    mpanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
    }


  }
