
package com.prodco.analysis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.prodco.analysis.client.model.FlowCollector;

public class Analysis implements EntryPoint
  {
  VerticalPanel mainpanel = new VerticalPanel();
  TreeMap treemap = new TreeMap( "treemap" );
  private final int TREEMAP_HEIGHT = 360;

  @Override
  /*
   * * This is the entry point method.
   */
  public void onModuleLoad ()
    {
    Window.alert( "alert" );
    FlowCollector c = new FlowCollector();
    c.setSiteId( 4l );
    c.setWanBandwitch( 6.0f );
    c.setSrcIp( "192.168.1.1" );
    SiteDetailBar bar = new SiteDetailBar( c );
    treemap.setHeight( TREEMAP_HEIGHT
      + "px" );
    bar.addHandler( new ClickHandler() {
      protected boolean hidden = false;
      protected int originalHeight = treemap.getOffsetHeight();

      @Override
      public void onClick ( ClickEvent event )
        {
        if ( !hidden )
          {
          new ResizeAnimation( treemap, TREEMAP_HEIGHT, 0 ).run( 1000 );
          hidden = true;
          }
        else
          {
          new ResizeAnimation( treemap, 0, TREEMAP_HEIGHT ).run( 1000 );
          hidden = false;
          }
        }
    } );
    mainpanel.add( bar );
    mainpanel.add( treemap );
    mainpanel.add( new DoubleGraphDetail() );
    RootPanel.get( "chartslot" ).add( mainpanel );
    }
  }
