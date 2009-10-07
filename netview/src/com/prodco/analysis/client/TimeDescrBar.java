
package com.prodco.analysis.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class TimeDescrBar extends Composite
  {
  protected TimeRange range;
  protected SimplePanel mainPanel = new SimplePanel();

  public TimeDescrBar ( TimeRange range )
    {
    super();
    this.range = range;
    initWidget( mainPanel );
    }

  @Override
  protected void onAttach ()
    {
    mainPanel.setStyleName( "timerangebar_container" );
    mainPanel.add( new HTML( "<p>"
      + range.getDateLong() + "</p>" ) );
    super.onAttach();

    }

  }
