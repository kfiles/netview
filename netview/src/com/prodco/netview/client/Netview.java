package com.prodco.netview.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.prodco.netview.client.gadgets.GraphGadget;
import com.prodco.netview.client.model.GadgetClass;
import com.prodco.netview.client.view.DesktopView;
import com.prodco.netview.client.view.DesktopViewListener;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Netview implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT
      .create(GreetingService.class);

//  private StackPanel slider = new StackPanel();
  private VerticalPanel panel = new VerticalPanel();
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final HTML graph = new HTML(
        "<div id='ChartDiv' align='center'>Chart Missing</div>");
    // \n" +
    // "<script type=\"text/javascript\">var chart_topIpaddr = new
    // FusionCharts('fusioncharts/MSLine.swf', 'Top IP Addresses',
    // '875', '350', '0', '1'); \n" +
    // "chart_topIpaddr.setDataURL('/testdata/testgraph.jsp');\n" +
    // "chart_topIpaddr.render('ChartDiv');</script><!-- END Script Block for
    // Chart SalesByCat -->");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    
//    slider.add(addRadialGraph(), "Network");
    panel.add(addRadialGraph());
//    slider.add(graph, "Analysis");
    //add gadgets
    GadgetClass.addClass( new GraphGadget.Class() );
    
    //create view
    panel.add( new DesktopView(new DesktopViewListener() {

      public void onInterfaceChange() {
        // TODO Auto-generated method stub
        
      }}));

    RootPanel.get("chartslot").add(panel);
//    addGraph("Top IP addresses");
  }

  native void addGraph(String title) /*-{
    var chart_topIpaddr = new $wnd.FusionCharts('fusioncharts/MSLine.swf', title, '875', '350', '0', '1'); 
        chart_topIpaddr.setDataURL('/testdata/testgraph.jsp');
        chart_topIpaddr.render('ChartDiv');
  }-*/;

  Widget addRadialGraph() {
    HTML radial = new HTML(
        "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab'version=7,0,0,0' " +
        " width='800' height='600' id='circleIP' align='middle'>" +
   "<param name='allowScriptAccess' value='sameDomain' />"+
   "<param name='movie' value='flare/circlIP.swf' />" +
   "<param name='quality' value='high' />" +
   "<param name='bgcolor' value='#000000' />" +
   "<embed src='flare/circlIP.swf' quality='high' bgcolor='#000000' width='800' height='600' name='circlIP' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />" +
   "</object>");
    return radial;
//    $wnd.AC_FL_RunContent(
//        "id", "circlIP",
//        "name", "circlIP",
//        "type", "application/x-shockwave-flash",
//        "pluginspage", "http://www.adobe.com/go/getflashplayer"
//    );
  }
}
