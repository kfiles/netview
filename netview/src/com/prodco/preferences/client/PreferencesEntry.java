
package com.prodco.preferences.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class PreferencesEntry extends LayoutContainer
  implements
    EntryPoint,
    ValueChangeHandler<String>
  {
  private ContentPanel prefPanel = new ContentPanel();

  private PrefEditorType editorType = PrefEditorType.APPS;

  private Widget editor;

  private static FlexibleTabPanel tabs = null;

  //
  public void onModuleLoad ()
    {
    init();

    RootPanel.get( "prefslot" ).add( prefPanel );

    }

  private void init ()
    {

    Window.enableScrolling( false );
    Window.setMargin( "0px" );

    // prefPanel.setBodyStyle( "background-color:#6e7e99;" );
    // prefPanel.setHeading( "Preferences" );
    prefPanel.setHeaderVisible( false );
    prefPanel.setStyleName( "content" );
    editor = editorType.newInstance();
    prefPanel.add( editor );

    setStyleName( "content" );
    add( prefPanel );
    layout();

    resizeWindow();
    initHistorySupport();
    }

  // private void reload ()
  // {
  // Window.alert( "reload" );
  //
  // // panel.add( tabPanel );
  // // panel.setVisible( true );
  //
  // }

  /**
   * Force an update of the component positions to the Window size
   */
  private void resizeWindow ()
    {
    onWindowResized( 800, Window.getClientHeight() );
    }

  /**
   * Update of the component positions to the Window size
   */
  public void onWindowResized ( int width, int height )
    {
    height = height - 20;
    prefPanel.setWidth( width
      - 25 + "px" );
    }

  public void initHistorySupport ()
    {
    // add the MainPanel as a history listener
    History.addValueChangeHandler( this );

    // Now that we've setup our listener, fire the initial history state.
    History.fireCurrentHistoryState();
    }

  /**
   * this method is called when the content must be changed due to a state
   * change. in the context of this app, a state change represents a new URL
   * being loaded.
   */
  public void updateState ( String token )
    {
    if ( !editorType.getDescr().equalsIgnoreCase( token ) )
      {
      PrefEditorType t = PrefEditorType.fromString( token );
      if ( null != t )
        {
        editorType = t;
        if ( null != editor )
          prefPanel.removeAll();
        editor = editorType.newInstance();
        prefPanel.add( editor );
        setSelectedUrl( token );
        }
      }
    }

  native void setSelectedUrl ( String token ) /*-{
                $wnd.selectSubNavBarItem( token );
              }-*/;

  @Override
  public void onValueChange ( ValueChangeEvent<String> event )
    {
    updateState( event.getValue() );
    }

  }