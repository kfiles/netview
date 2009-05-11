/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.prodco.netview.client.gadgets.AmLineGraphGadget;
import com.prodco.netview.client.gadgets.GraphGadget;
import com.prodco.netview.client.gadgets.RadialGraphGadget;
import com.prodco.netview.client.gadgets.TreeMapGadget;
import com.prodco.netview.client.gadgets.TreeMapGadget2;
import com.prodco.netview.client.model.GadgetClass;
import com.prodco.netview.client.view.DesktopView;
import com.prodco.netview.client.view.DesktopViewListener;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Netview
  implements
    EntryPoint,
    DesktopViewListener,
    ValueChangeHandler<String>
  {
  private static final String TOKEN_READONLY = "readonly";

  private static final String TOKEN_EDITABLE = "editable";

  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
    + "attempting to contact the server. Please check your network "
    + "connection and try again.";

  private static final String INITSTATE = TOKEN_EDITABLE;

  // private StackPanel slider = new StackPanel();
  private VerticalPanel panel = new VerticalPanel();

  private Storage storage;

  DesktopView view1;

  DesktopView view2;

  private boolean editable = true;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad ()
    {
    final HTML graph = new HTML(
      "<div id='ChartDiv' align='center'>Chart Missing</div>" );
    // \n" +
    // "<script type=\"text/javascript\">var chart_topIpaddr = new
    // FusionCharts('fusioncharts/MSLine.swf', 'Top IP Addresses',
    // '875', '350', '0', '1'); \n" +
    // "chart_topIpaddr.setDataURL('/testdata/testgraph.jsp');\n" +
    // "chart_topIpaddr.render('ChartDiv');</script><!-- END Script Block for
    // Chart SalesByCat -->");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element

    // slider.add(addRadialGraph(), "Network");
    // panel.add(addRadialGraph());
    // slider.add(graph, "Analysis");
    // add gadgets
    GadgetClass.addClass( new RadialGraphGadget.Class() );
    GadgetClass.addClass( new AmLineGraphGadget.Class() );
//    GadgetClass.addClass( new TreeMapGadget.Class() );
    GadgetClass.addClass( new TreeMapGadget2.Class() );
    RootPanel.get( "chartslot" ).add( panel );

    History.addValueChangeHandler( this );
    String token = History.getToken();
    if ( null == token
      || token.length() == 0 )
      // Create an initial event to load the page
      History.newItem( INITSTATE );
    else
    // We missed the initial event
    History.fireCurrentHistoryState();

    // reload();

    // addGraph("Top IP addresses");
    }

  private void reload ()
    {
    panel.clear();
    // create view

    String label = "Edit";
    String token = TOKEN_EDITABLE;
    if ( true == editable )
      {
      label = "Save Changes";
      token = TOKEN_READONLY;
      }
    panel.add( new Hyperlink( label, token ) );
    view1 = new DesktopView( this, editable );
    view2 = new DesktopView( this, editable );
    panel.add( view1 );
    panel.add( view2 );
    storage = new CookieStorage();
    String layout = storage.getValue( "layout1" );
    if ( layout != null )
      view1.setLayoutFromString( layout );

    layout = storage.getValue( "layout2" );
    if ( layout != null )
      view2.setLayoutFromString( layout );
    }

  native void addGraph ( String title ) /*-{
    var chart_topIpaddr = new $wnd.FusionCharts('fusioncharts/MSLine.swf', title, '875', '350', '0', '1'); 
        chart_topIpaddr.setDataURL('/testdata/testgraph.jsp');
        chart_topIpaddr.render('ChartDiv');
  }-*/;

  Widget addRadialGraph ()
    {
    HTML radial = new HTML(
      "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab'version=7,0,0,0' "
        + " width='600' height='600' id='circleIP' align='middle'>"
        + "<param name='allowScriptAccess' value='sameDomain' />"
        + "<param name='movie' value='flare/circlIP.swf' />"
        + "<param name='quality' value='high' />"
        + "<param name='bgcolor' value='#000000' />"
        + "<embed src='flare/circlIP.swf' quality='high' bgcolor='#000000' width='600' height='600' name='circlIP' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />"
        + "</object>" );
    return radial;
    // $wnd.AC_FL_RunContent(
    // "id", "circlIP",
    // "name", "circlIP",
    // "type", "application/x-shockwave-flash",
    // "pluginspage", "http://www.adobe.com/go/getflashplayer"
    // );
    }

  public void onInterfaceChange ()
    {
    // save layout to storage
    String layout = view1.getLayoutAsString();
    storage.setValue( "layout1", layout );
    layout = view2.getLayoutAsString();
    storage.setValue( "layout2", layout );
    try
      {
      storage.save();
      }
    catch ( StorageException e )
      {
      Window.alert( "unable to save the desktops state" );
      }
    }

  public void onValueChange ( ValueChangeEvent<String> event )
    {
    if ( null != event )
      {
      String token = event.getValue();
      if ( null != token
        && token.length() > 0 )
        {
        if ( token.contains( TOKEN_EDITABLE ) )
          {
          editable = true;
          reload();
          }
        else if ( token.contains( TOKEN_READONLY ) )
          {
          editable = false;
          reload();
          }
        }
      }
    }
  }
