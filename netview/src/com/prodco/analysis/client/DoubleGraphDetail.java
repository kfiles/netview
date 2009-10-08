
package com.prodco.analysis.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DoubleGraphDetail extends Composite
  {
  VerticalPanel mainPanel = new VerticalPanel();
  HorizontalPanel graphPanel = new HorizontalPanel();
  VerticalPanel leftView = new VerticalPanel();
  VerticalPanel rightView = new VerticalPanel();

  public DoubleGraphDetail ( TimeRange range, int siteId, GraphType type )
    {
    mainPanel.add( new TimeDescrBar( range ) );
    GraphType leftType = type;
    GraphType rightType = type;
    if ( GraphType.ADDRESSES == type )
      {
      leftType = GraphType.SRC_IPS;
      rightType = GraphType.DEST_IPS;
      }
    if ( GraphType.PORTS == type )
      {
      leftType = GraphType.SRC_PORTS;
      rightType = GraphType.DEST_PORTS;
      }
    graphPanel.add( leftView );
    graphPanel.add( rightView );
    leftView.setStyleName( "left_graph_legend_container" );
    leftView.add( new AmLineGraph( "leftGraph", range, siteId, leftType,
      TrafficDir.INBOUND ) );
    leftView
      .add( new GraphLegend( range, siteId, leftType, TrafficDir.INBOUND ) );
    leftView.setStyleName( "right_graph_legend_container" );
    rightView.add( new AmLineGraph( "rightGraph", range, siteId, rightType,
      TrafficDir.OUTBOUND ) );
    rightView.add( new GraphLegend( range, siteId, rightType,
      TrafficDir.OUTBOUND ) );
    mainPanel.add( graphPanel );
    initWidget( mainPanel );
    }
  }
