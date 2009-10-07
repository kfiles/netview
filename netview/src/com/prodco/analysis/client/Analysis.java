
package com.prodco.analysis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.prodco.analysis.client.model.FlowCollector;

public class Analysis implements EntryPoint, ValueChangeHandler<String>
  {
  private static final String INIT_STATE = "apps";
  VerticalPanel mainpanel = new VerticalPanel();
  TreeMap treemap = new TreeMap( "treemap" );
  private final int TREEMAP_HEIGHT = 360;
  protected GraphType graphType = GraphType.PORTS;
  protected TimeRange range = new TimeRange( 41201280, 41201350 );
  protected DoubleGraphDetail graphs;
  private static final int SITEID = 1;

  @Override
  /*
   * * This is the entry point method.
   */
  public void onModuleLoad ()
    {
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
    graphs = new DoubleGraphDetail( range, SITEID, graphType );
    mainpanel.add( graphs );
    RootPanel.get( "chartslot" ).add( mainpanel );
    initHistorySupport();
    }

  public void initHistorySupport ()
    {
    // add the MainPanel as a history listener
    History.addValueChangeHandler( this );

    // Now that we've setup our listener, fire the initial history state.
    History.fireCurrentHistoryState();
    }

  /**
   * this method is called when the MainPanel's contents must be changed due to
   * a state change. in the context of this app, a state change represents a new
   * URL being loaded for the RSS reader.
   */
  public void updateState ( String token )
    {
    if ( !graphType.getDescr().equalsIgnoreCase( token ) )
      {
      graphType = GraphType.fromString( token );
      if ( null != graphType )
        {
        mainpanel.remove( graphs );
        graphs = new DoubleGraphDetail( range, SITEID, graphType );
        mainpanel.add( graphs );
        }
      setSelectedUrl( token );
      }
    }

  native void setSelectedUrl ( String token ) /*-{
    $wnd.selectSubNavBarItem( token );
  }-*/;

  @Override
  public void onValueChange ( ValueChangeEvent<String> event )
    {
    updateState( event.getValue() );
    }
  }
