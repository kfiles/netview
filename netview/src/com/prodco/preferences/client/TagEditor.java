
package com.prodco.preferences.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.extjs.gxt.ui.client.widget.grid.GridViewConfig;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.prodco.preferences.client.model.AppTag;

public class TagEditor extends LayoutContainer implements WindowResizeListener
  {

  private ScrollPanel scrollPanel = new ScrollPanel();

  private ListStore<BeanModel> store;

  private List<FormBinding> formBindings = new ArrayList<FormBinding>();

  private ContentPanel gridPanel = new ContentPanel();

  private Grid<BeanModel> grid = null;

  private TagDetailPanel detailPanel = null;

  private class GVC extends GridViewConfig
    {
    @SuppressWarnings ( "unchecked" )
    public String getRowStyle ( ModelData md, int rowIndex, ListStore ds )
      {
      if ( md != null )
        {
        Boolean valid = (Boolean) md.get( "valid" );
        if ( null != valid
          && false == valid )
          return ( "grid-invalid-model" );
        }
      return "";
      }
    }

  public TagEditor ( String header )
    {
    store = new ListStore<BeanModel>();
    store.setMonitorChanges( true );

    detailPanel = new TagDetailPanel( formBindings );
    detailPanel.setWidth( 600 );
    detailPanel.setHeight( 300 );
    detailPanel.setBodyBorder( true );
    detailPanel.setBorders( true );

    for ( FormBinding fb : formBindings )
      {
      fb.setStore( store );
      }

    grid = createGrid();

    store.addStoreListener( new StoreListener<BeanModel>() {
      @Override
      public void storeUpdate ( StoreEvent<BeanModel> se )
        {
        Window.alert( "StoreUpdate called" );
        super.storeUpdate( se );
        }
    } );

    Window.addWindowResizeListener( this );
    Window.enableScrolling( false );
    Window.setMargin( "10px" );
    DeferredCommand.addCommand( new Command() {
      public void execute ()
        {
        onWindowResized( Window.getClientWidth(), Window.getClientHeight() );
        }
    } );

    resizeWindow();
    }

  @Override
  protected void onRender ( Element parent, int index )
    {
    super.onRender( parent, index );

    gridPanel.setLayout( new FlowLayout() );
    gridPanel.setHeading( "App Tags" );
    gridPanel.setHeaderVisible( true );
    gridPanel.setFrame( true );
    gridPanel.setCollapsible( true );
    gridPanel.setWidth( 600 );
    gridPanel.setHeight( 125 );
    gridPanel.add( grid );

    LayoutContainer panel = new LayoutContainer();
    panel.setLayout( new TableLayout() );
    panel.add( gridPanel );
    panel.add( new Image( "images/spacer.gif", 0, 0, 1, 5 ) );
    panel.add( detailPanel );
    panel.setBorders( true );
    panel.setWidth( 650 );
    panel.setHeight( 500 );

    ContentPanel cp = new ContentPanel();
    cp.setHeaderVisible( false );
    cp.setFrame( true );
    cp.setLayout( new FlowLayout() );
    cp.setHeight( 675 );

    scrollPanel.add( panel );
    scrollPanel.setHeight( "300px" );
    scrollPanel.setWidth( "300px" );
    scrollPanel.setAlwaysShowScrollBars( true );

    cp.add( scrollPanel );
    cp.setBorders( true );

    add( cp );
    setWidth( 700 );
    setHeight( 700 );
    layout();

    }

  @Override
  protected void onLoad ()
    {
    updateStore();
    layout();

    }

  @Override
  protected void onShow ()
    {
    super.onShow();
    detailPanel = new TagDetailPanel( formBindings );
    Window.alert( "I am in TagEditor.onShow()" );
    }

  private void loadDetailPanel ( AppTag tag )
    {
    detailPanel.redraw_panel( true );
    BeanModelFactory factory = BeanModelLookup.get().getFactory( AppTag.class );
    BeanModel model = factory.createModel( tag );
    if ( model != null )
      {
      for ( FieldBinding b : detailPanel.getFieldBindings() )
        {
        b.bind( model );
        }

      }
    else
      {
      for ( FormBinding fb : formBindings )
        {
        fb.unbind();
        for ( FieldBinding b : fb.getBindings() )
          b.getField().clearInvalid();
        }
      }
    }

  private Grid<BeanModel> createGrid ()
    {
    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

    ColumnConfig column = new ColumnConfig( "tagPref", "Id", 50 );
    configs.add( column );

    column = new ColumnConfig( "tagName", "Tag Name", 200 );
    configs.add( column );

    column = new ColumnConfig( "tagRule", "Tag Rule", 400 );
    configs.add( column );

    ColumnModel cm = new ColumnModel( configs );

    Grid<BeanModel> grid = new Grid<BeanModel>( store, cm );
    GridView gv = grid.getView();
    gv.setEmptyText( "No rows returned" );
    gv.setViewConfig( new GVC() );
    grid.setAutoHeight( true );
    grid.setAutoExpandColumn( "tagRule" );
    grid.setStripeRows( true );
    grid.setBorders( true );
    grid.setWidth( 700 );

    // Listen for selection events,
    grid.addListener( Events.CellClick, new Listener<GridEvent>() {
      public void handleEvent ( GridEvent ge )
        {

        if ( null != ge )
          {
          int rowNum = ge.getRowIndex() + 1;
          BeanModel model = (BeanModel) ge.getGrid().getStore().getAt(
            ge.getRowIndex() );
          if ( null != model )
            {
            AppTag tag = (AppTag) model.getBean();
            if ( tag != null )
              loadDetailPanel( tag );
            }
          }
        }
    } );

    return grid;
    }

  public ContentPanel getGridPanel ()
    {
    return ( gridPanel );
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
    scrollPanel.setHeight( ( height - 171 )
      + "px" );
    scrollPanel.setWidth( ( width - 76 )
      + "px" );
    }

  private void updateStore ()
    {
    // Create a sample AppTag

//    GetTagData();
    List<AppTag> tags = new ArrayList<AppTag>();
    AppTag tag = new AppTag( 1, "HTTP", "port=80" );
    tags.add( tag );
    tag = new AppTag( 2, "FTP", "port=23" );
    tags.add( tag );
    tag = new AppTag( 3, "SFTP", "port=23" );
    tags.add( tag );
    tag = new AppTag( 4, "ORACLE", "port=23" );
    tags.add( tag );
    tag = new AppTag( 5, "SCP", "port=23" );
    tags.add( tag );

    BeanModelFactory factory = BeanModelLookup.get().getFactory( AppTag.class );
    List<BeanModel> models = factory.createModel( tags );

    grid.getSelectionModel().deselectAll();
    store.removeAll();
    store.add( models );

    }

  private void GetTagData ()
    {
    Services.PREFERENCES.findAppTagsByCustomer( 1,
      new AsyncCallback<List<AppTag>>() {
        public void onSuccess ( List<AppTag> tags )
          {
          Window.alert( "Found AppTags" );
          }

        public void onFailure ( Throwable caught )
          {
          Window.alert( "Found Error" );
          }
      } );

    }

  }
