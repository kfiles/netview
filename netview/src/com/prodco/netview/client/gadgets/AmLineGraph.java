
package com.prodco.netview.client.gadgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class AmLineGraph extends Widget
  {
  private Element div;
  private String name;
  private boolean initted = false;

  public AmLineGraph ( String divName )
    {
    super();
    this.name = divName;
    div = DOM.createDiv();
    div.setId( divName );
    div.setAttribute( "align", "center" );
    setElement( div );

    }

  @Override
  protected void onLoad ()
    {
    super.onLoad();
    if ( !initted )
      {
      addGraph( name );
      initted = true;
      }
    }

  native void addGraph ( String divName ) /*-{
    var so = new $wnd.SWFObject("/amline/amline.swf", "amline", "300", "150", "8", "#000000");
    so.addVariable("path", "/amline/");
    so.addVariable("settings_file", escape("/amline/smalllinechartsettings.xml"));  // you can set two or more different settings files here (separated by commas)
    so.addVariable("data_file", escape("/testdata/amline.jsp"));
    //       so.addVariable("chart_data", "");                                       // you can pass chart data as a string directly from this file
    //       so.addVariable("additional_chart_settings", "<settings><graphs><graph gid=\"1\"><title>10.0.1.1</title></graph><graph gid=\"2\"><title>10.0.2.1</title></graph></graphs></settings>");                                   // you can pass chart settings as a string directly from this file
    //       so.addVariable("chart_settings", "");                        // you can append some chart settings to the loaded ones
    //       so.addVariable("loading_settings", "LOADING SETTINGS");                 // you can set custom "loading settings" text here
    //       so.addVariable("loading_data", "LOADING DATA");                         // you can set custom "loading data" text here
    so.addVariable("preloader_color", "#000000");
    //    $wnd.alert(divName);
    so.write(divName);
  }-*/;

  }
