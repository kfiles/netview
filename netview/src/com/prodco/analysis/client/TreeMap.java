
package com.prodco.analysis.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class TreeMap extends Widget
  {
  private Element div;
  private String name;
  private boolean initted = false;

  public TreeMap ( String divName )
    {
    super();
    this.name = divName;
    div = DOM.createDiv();
    div.setId( divName );
    div.setAttribute( "align", "center" );
    div.setAttribute( "class", "treemap_container" );
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
    var so = new $wnd.SWFObject("/birdeye/Treemap.swf", "/birdeye", "810", "360", "8", "#000000");
    //    "width", "100%",
    //    "height", "100%",
    //    "align", "middle",
    //    "id", "Treemap",
    so.addParam("wmode" ,"opaque" );
    so.addVariable("quality", "high");
    //    so.addVariable("bgcolor", "#869ca7",
    so.addVariable("preloader_color", "#000000"); 
    //    $wnd.alert(divName);
    so.write(divName);
  }-*/;

  }
