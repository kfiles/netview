
package com.prodco.analysis.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class AmLineGraph extends Widget
  {
  private Element div;
  private String name;
  private boolean initted = false;
  private TimeRange range;
  private int siteId;
  private GraphType type;
  private TrafficDir dir;

  public AmLineGraph ( String divName, TimeRange range, int siteId,
    GraphType type, TrafficDir trafficDir )
    {
    super();
    this.name = divName;
    this.range = range;
    this.siteId = siteId;
    this.type = type;
    this.dir = trafficDir;
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
      addGraph( name, siteId, range.getStartTc(), range.getEndTc(), type
        .getDescr(), dir.getDescr() );
      initted = true;
      }
    }

  native void addGraph ( String divName, int siteId, int startTc, int endTc,
    String type, String dir ) /*-{
    //    var so = new $wnd.SWFObject("/amline/amline.swf", "amline", "300", "150", "8", "#000000");
       var so = new $wnd.SWFObject("/amline/amline.swf", "amline", "400", "250", "8", "#000000");
       so.addVariable("path", "/amline/");
       so.addVariable("settings_file", escape("/amline/linechartsettings.xml"));  // you can set two or more different settings files here (separated by commas)
    //    so.addVariable("settings_file", escape("/amline/smalllinechartsettings.xml"));  // you can set two or more different settings files here (separated by commas)
       so.addVariable("data_file", escape("/testdata/amline.jsp?siteId=" + siteId +
        "&startTc=" + startTc + "&endTc=" + endTc + "&type=" + type
        + "&dir=" + dir));
       //       so.addVariable("chart_data", "");                                       // you can pass chart data as a string directly from this file
       //       so.addVariable("additional_chart_settings", "<settings><graphs><graph gid=\"1\"><title>10.0.1.1</title></graph><graph gid=\"2\"><title>10.0.2.1</title></graph></graphs></settings>");                                   // you can pass chart settings as a string directly from this file
       //       so.addVariable("chart_settings", "");                        // you can append some chart settings to the loaded ones
       //       so.addVariable("loading_settings", "LOADING SETTINGS");                 // you can set custom "loading settings" text here
       //       so.addVariable("loading_data", "LOADING DATA");                         // you can set custom "loading data" text here
       so.addVariable("preloader_color", "#000000");
       //    $wnd.alert(divName);
       so.write(divName);
  }-*/;

  private TimeRange getRange ()
    {
    return range;
    }

  private int getSiteId ()
    {
    return siteId;
    }

  private GraphType getType ()
    {
    return type;
    }

  private TrafficDir getDir ()
    {
    return dir;
    }

  }
