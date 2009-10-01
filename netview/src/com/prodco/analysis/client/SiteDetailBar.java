
package com.prodco.analysis.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.prodco.analysis.client.model.FlowCollector;

public class SiteDetailBar extends SimplePanel
  {
  FlexTable row = new FlexTable();
  FlowCollector collector;
  ViewFilter filter = new PassthroughFilter();
  int col = 0;
  protected HandlerRegistration registration;

  public SiteDetailBar ( FlowCollector collector )
    {
    this.collector = collector;
    this.setStyleName( "sitedetailbar_container" );
    row.setWidth( "100%" );
    }

  @Override
  protected void onAttach ()
    {
    super.onAttach();
    init();
    add( row );
    }

  @Override
  protected void onDetach ()
    {
    super.onDetach();
    if ( null != registration )
      {
      registration.removeHandler();
      registration = null;
      }
    }

  public void addHandler ( ClickHandler handler )
    {
    registration = addDomHandler( handler, ClickEvent.getType() );
    }

  private void init ()
    {
    addSiteName();
    addSiteId();
    addBandwidth();
    addFilter();
    }

  private void addFilter ()
    {
    HTML widget = new HTML( "<p><a>Profile:</a><b> "
      + filter.getDescription() + "</b>" );
    widget.setWidth( "200px" );
    row.setWidget( 0, col++, widget );
    }

  private void addBandwidth ()
    {
    HTML widget = new HTML( "<p><a>Bandwidth:</a><b> "
      + collector.getWanBandwitch() + "</b>" );
    widget.setWidth( "200px" );
    row.setWidget( 0, col++, widget );
    }

  private void addSiteId ()
    {
    HTML widget = new HTML( "<p><a>SiteId:</a><b> "
      + collector.getSiteId() + "</b>" );
    widget.setWidth( "200px" );
    row.setWidget( 0, col++, widget );
    }

  private void addSiteName ()
    {
    HTML widget = new HTML( "<p><a>Site:</a><b> New York, NY</b>" );
    widget.setWidth( "200px" );
    row.setWidget( 0, col++, widget );
    }
  }
