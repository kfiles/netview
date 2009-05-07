/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.gadgets;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class GraphGadget extends Gadget
  {

  private static int divId = 0;

  public static class Class extends GadgetClass
    {
    public Class ()
      {
      super( "IP Graph", false );
      }

    public Gadget newGadget ()
      {
      return new GraphGadget( this );
      }
    }

  protected GraphGadget ( Class c )
    {
    super( c );
    final String divName = "graphGadgetDiv"
      + divId++;

    final HTML graph = new HTML( "<div id='"
      + divName + "' align='center'>Chart Missing</div>" );

    HorizontalPanel mpanel = new HorizontalPanel();
    initWidget( mpanel );
    mpanel.setWidth( "100%" );
    mpanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
    mpanel.add( graph );
    DeferredCommand.addCommand( new Command() {

      public void execute ()
        {
        addGraph( "New Graph", divName );

        }
    } );
    }

  native void addGraph ( String title, String divName ) /*-{
    var chart_topIpaddr = new $wnd.FusionCharts('fusioncharts/MSLine.swf', title, '200', '100', '0', '1'); 
        chart_topIpaddr.setDataURL('/testdata/smallgraph.jsp');
        chart_topIpaddr.render(divName);
  }-*/;

  }
