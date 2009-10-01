
package com.prodco.analysis.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class DoubleGraphDetail extends Composite
  {
  HorizontalPanel mainpanel = new HorizontalPanel();

  public DoubleGraphDetail ()
    {
    mainpanel.add( new AmLineGraph( "leftGraph" ) );
    mainpanel.add( new AmLineGraph( "rightGraph" ) );
    initWidget( mainpanel );
    }
  }
