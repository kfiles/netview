/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.gadgets;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class AmLineGraphGadget extends Gadget
  {

  private static int divId = 0;
  final String divName = "graphGadgetDiv"
    + divId++;

  public static class Class extends GadgetClass
    {
    public Class ()
      {
      super( "IP Graph", false );
      }

    public Gadget newGadget ()
      {
      return new AmLineGraphGadget( this );
      }
    }

  protected AmLineGraphGadget ( Class c )
    {
    super( c );

    final HTML graph = new HTML( "<div id='"
      + divName + "' align='center'>Chart Missing</div>" );

    HorizontalPanel mpanel = new HorizontalPanel();
    initWidget( mpanel );
    mpanel.setWidth( "100%" );
    mpanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
    mpanel.add( graph );
    DeferredCommand.addCommand( new Command() {

      public void execute ()
        {
        addGraph( "New Graph", divName );

        }
    } );
    }

  @Override
  public void refresh ()
    {
    super.refresh();
    addGraph( "New Graph", divName );
    }

  native void addGraph ( String title, String divName ) /*-{
    var so = new $wnd.SWFObject("amline/amline.swf", "amline", "300", "150", "8", "#000000");
    so.addVariable("path", "amline/");
    so.addVariable("settings_file", escape("amline/smalllinechartsettings.xml"));  // you can set two or more different settings files here (separated by commas)
    so.addVariable("data_file", escape("testdata/amline.jsp"));
    // so.addVariable("chart_data", "");                                       // you can pass chart data as a string directly from this file
    // so.addVariable("additional_chart_settings", "<settings><graphs><graph gid=\"1\"><title>10.0.1.1</title></graph><graph gid=\"2\"><title>10.0.2.1</title></graph></graphs></settings>");                                   // you can pass chart settings as a string directly from this file
    // so.addVariable("chart_settings", "");                        // you can append some chart settings to the loaded ones
    // so.addVariable("loading_settings", "LOADING SETTINGS");                 // you can set custom "loading settings" text here
    // so.addVariable("loading_data", "LOADING DATA");                         // you can set custom "loading data" text here
    so.addVariable("preloader_color", "#000000");
    so.write(divName);
  }-*/;

  }
