
package com.prodco.analysis.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DoubleGraphDetail extends Composite
  {
  VerticalPanel mainPanel = new VerticalPanel();
  HorizontalPanel graphPanel = new HorizontalPanel();

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
    graphPanel.add( new AmLineGraph( "leftGraph", range, siteId, leftType,
      TrafficDir.INBOUND ) );
    graphPanel.add( new AmLineGraph( "rightGraph", range, siteId, rightType,
      TrafficDir.OUTBOUND ) );
    mainPanel.add( graphPanel );
    initWidget( mainPanel );
    }
  }
