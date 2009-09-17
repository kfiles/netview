
package com.prodco.preferences.client;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.prodco.preferences.client.model.AppTag;

/**
 * SOF Bundles Entry
 * 
 * @author: Robert Parlato
 * @since 1.5
 */
public class TagEditor extends LayoutContainer implements WindowResizeListener
  {

  private ScrollPanel scrollPanel = new ScrollPanel();

  private ListStore<BeanModel> store;

  private ContentPanel gridPanel = new ContentPanel();

  private Grid<BeanModel> grid = null;

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

    store.addStoreListener( new StoreListener<BeanModel>() {
      @Override
      public void storeUpdate ( StoreEvent<BeanModel> se )
        {
        super.storeUpdate( se );
        }
    } );

    grid = createGrid();

    createDetailPanel();
    
    Window.addWindowResizeListener( this );
    Window.enableScrolling( false );
    Window.setMargin( "0px" );
    DeferredCommand.addCommand( new Command() {
      public void execute ()
        {
        onWindowResized( Window.getClientWidth(), Window.getClientHeight() );
        }
    } );

    resizeWindow();
    }

  private void createDetailPanel ()
    {
    // TODO Auto-generated method stub
    
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
    gridPanel.setWidth( 800 );
    gridPanel.add( grid );

    LayoutContainer panel = new LayoutContainer();
    panel.setLayout( new TableLayout() );
//    panel.add( new Image( "images/spacer.gif", 0, 0, 1, 10 ) );
    panel.add( gridPanel );
    panel.add( new Image( "images/spacer.gif", 0, 0, 1, 5 ) );
    panel.add( new TagDetailPanel(new ArrayList<FormBinding>()) );

    ContentPanel cp = new ContentPanel();
//    cp.setHeading( "Tag Editor" );
    cp.setHeaderVisible( false );
    cp.setFrame( true );
    cp.setLayout( new FlowLayout() );

    scrollPanel.add( panel );
    cp.add( scrollPanel );
    
    add( cp );
//    add( new TagDetailPanel(new ArrayList<FormBinding>()) );
    layout();

    }

  @Override
  protected void onLoad ()
    {
    updateStore();
    }

  @Override
  protected void onShow ()
    {
    updateStore();
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
    grid.setAutoExpandColumn( "tagName" );
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
          AppTag tag = (AppTag) model.getBean();
          if ( tag != null )
            Window.alert( "Row-"
              + rowNum + " Selected." +"tagName="
              + tag.getTagName() + ": " + tag.getTagRule() );
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
    List<AppTag> tags = new ArrayList<AppTag>();
    AppTag tag = new AppTag( 1, "HTTP", "port=80" );
    tags.add( tag );
    tag = new AppTag( 2, "FTP", "port=23" );
    tags.add( tag );

    BeanModelFactory factory = BeanModelLookup.get().getFactory( AppTag.class );
    List<BeanModel> models = factory.createModel( tags );

    grid.getSelectionModel().deselectAll();
    store.removeAll();
    store.add( models );

    }

  }
