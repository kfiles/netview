
package com.prodco.analysis.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

public class GraphLegend extends Composite
  {
  private TimeRange range;
  private int siteId;
  private GraphType type;
  private TrafficDir dir;
  protected Grid details = new Grid( 1, 5 );

  public GraphLegend ( TimeRange range, int siteId, GraphType type,
    TrafficDir trafficDir )
    {
    super();
    this.range = range;
    this.siteId = siteId;
    this.type = type;
    this.dir = trafficDir;
    init();
    }

  private void init ()
    {
    details.setStyleName( "graph_legend_table" );
    initWidget( details );
    String url = "/testdata/amline.jsp?siteId="
      + siteId + "&startTc=" + range.getStartTc() + "&endTc="
      + range.getEndTc() + "&type=" + type.getDescr() + "&dir="
      + dir.getDescr();
    getData( url );
    details.getRowFormatter().setStyleName( 0, "graph_legend_table_header" );

    details.setText( 0, 0, "Total" );
    details.setText( 0, 1, "Avg Kbps" );
    details.setText( 0, 2, "%" );
    details.setText( 0, 3, "Avg pps" );
    details.setText( 0, 4, "%" );

    details.resizeRows( 2 );
    details.getRowFormatter().setStyleName( 1, "graph_legend_table" );
    details.getColumnFormatter().setStyleName( 0, "graph_legend_table_header" );

    details.setText( 1, 0, "HTTP" );
    details.setText( 1, 1, "99.6" );
    details.setText( 1, 2, ".5" );
    details.setText( 1, 3, "55" );
    details.setText( 1, 4, "10" );
    }

  public void getData ( String url )
    {
    url = URL.encode( url );

    // Send request to server and catch any errors.
    RequestBuilder builder = new RequestBuilder( RequestBuilder.GET, url );

    try
      {
      @SuppressWarnings ( "unused" )
      Request request = builder.sendRequest( null, new RequestCallback() {
        public void onError ( Request request, Throwable exception )
          {
          displayError( "Couldn't retrieve JSON" );
          }

        public void onResponseReceived ( Request request, Response response )
          {
          if ( 200 == response.getStatusCode() )
            {
            updateTable( response.getText() );
            }
          else
            {
            displayError( "Couldn't retrieve JSON ("
              + response.getStatusText() + ")" );
            }
          }

        private void updateTable ( String text )
          {
          Document messageDom = XMLParser.parse( text );

          // find the sender's display name in an attribute of the <from> tag
          Node fromNode = messageDom.getElementsByTagName( "from" ).item( 0 );

          }

      } );

      }
    catch ( RequestException e )
      {
      displayError( "Couldn't retrieve JSON" );
      }
    }

  private void displayError ( String string )
    {
    Window.alert( string );

    }
  }
