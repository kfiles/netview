
package com.prodco.preferences.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;

public class PreferencesEntry extends LayoutContainer
implements
WindowResizeListener, EntryPoint
  {
  private ContentPanel prefPanel = new ContentPanel();

  private static FlexibleTabPanel tabs = null;

//
  public void onModuleLoad ()
    {
    init();

    RootPanel.get( "chartslot" ).add( prefPanel );

    }

  private void init ()
    {

    Window.addWindowResizeListener( this );
    Window.enableScrolling( false );
    Window.setMargin( "0px" );
    DeferredCommand.addCommand( new Command() {
      public void execute ()
        {
        onWindowResized( Window.getClientWidth(), Window.getClientHeight() );
        }
    } );

    getVerticalTabInstance().add( new TagEditor("Tag"), "Tag" );
    getVerticalTabInstance().add( new TagEditor("Profile"), "Profile" );
    getVerticalTabInstance().add( new TagEditor("Policy"), "Policy" );
    getVerticalTabInstance().add( new TagEditor("Users"), "Users" );
    getVerticalTabInstance().add( new TagEditor("Alarms"), "Alarms" );
    getVerticalTabInstance().add( new TagEditor("Site"), "Site" );
    getVerticalTabInstance().add( new TagEditor("Agent"), "Agent" );

    getVerticalTabInstance().selectTab( 0 );

    prefPanel.setBodyStyle( "background-color:#6e7e99;" );
    prefPanel.setHeading( "Preferences" );
    prefPanel.setHeaderVisible( false );
    prefPanel.add( tabs );

    add( prefPanel );
    layout();

    resizeWindow();

    getVerticalTabInstance().addTabListener( new TabListener() {
      public boolean onBeforeTabSelected ( SourcesTabEvents arg0, int arg1 )
        {
        return true;
        }

      public void onTabSelected ( SourcesTabEvents arg0, int arg1 )
        {
//        Window.alert( "Vertical Tab Selected = "
//          + getVerticalTabInstance().getTabSelected() );
        // setOmsMenuItem(getVerticalTabInstance().getTabSelected());
        }
    } );

    }

//  private void reload ()
//    {
//    Window.alert( "reload" );
//
//    // panel.add( tabPanel );
//    // panel.setVisible( true );
//
//    }
  
  

  public static FlexibleTabPanel getVerticalTabInstance ()
    {
    if ( null == tabs )
      {
      tabs = new FlexibleTabPanel( TabOrientation.HORIZONTAL,
        TabStyle.FANCY_SEPARATORS, true );
      }

    return tabs;
    }

  /**
   * Force an update of the component positions to the Window size
   */
  private void resizeWindow ()
    {
    onWindowResized( Window.getClientWidth(), Window.getClientHeight() );
    }

  /**
   * Update of the component positions to the Window size
   */
  public void onWindowResized ( int width, int height )
    {
    height = height -20;
    prefPanel.setWidth( width
      - 25 + "px" );
    }

  }
