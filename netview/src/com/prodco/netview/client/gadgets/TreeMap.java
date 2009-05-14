
package com.prodco.netview.client.gadgets;

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
    var so = new $wnd.SWFObject("treemap/Treemap.swf", "amline", "650", "480", "8", "#000000");
    so.addVariable("preloader_color", "#000000"); 
//    $wnd.alert(divName);
    so.write(divName);
  }-*/;

  }
