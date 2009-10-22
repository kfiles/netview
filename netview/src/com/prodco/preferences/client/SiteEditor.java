
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
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.model.Site;

public class SiteEditor extends LayoutContainer
  {
  
  private ScrollPanel scrollPanel = new ScrollPanel();

  private ListStore<BeanModel> store;

  private List<FormBinding> formBindings = new ArrayList<FormBinding>();

  private ContentPanel gridPanel = new ContentPanel();

  private Grid<BeanModel> grid = null;

  private SiteDetailPanel detailPanel = null;

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

  public SiteEditor ()
    {
    store = new ListStore<BeanModel>();
    store.setMonitorChanges( true );

    detailPanel = new SiteDetailPanel( this, formBindings );
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
        super.storeUpdate( se );
        }
    } );

    Window.enableScrolling( false );
    Window.setMargin( "10px" );

    resizeWindow();
    }

  @Override
  protected void onRender ( Element parent, int index )
    {
    super.onRender( parent, index );

  setStyleName( "prefContent" );
  add( new HTML( "<p>Site Editor</p>" ) );
  setWidth( "100%" );

    gridPanel.setLayout( new FlowLayout() );
    gridPanel.setHeading( "Site Editor" );
    gridPanel.setHeaderVisible( true );
    gridPanel.setFrame( true );
    gridPanel.setCollapsible( true );
    gridPanel.add( grid );

    LayoutContainer panel = new LayoutContainer();
    panel.setLayout( new TableLayout() );
    panel.add( gridPanel );
    panel.add( new Image( "/images/spacer.gif", 0, 0, 1, 5 ) );
    panel.add( detailPanel );
    detailPanel.layout();
    panel.setBorders( true );
    panel.setWidth( 600 );
    panel.setHeight( 480 );

    ContentPanel cp = new ContentPanel();
    cp.setHeaderVisible( false );
    cp.setFrame( true );
    cp.setLayout( new FlowLayout() );
    cp.add( panel );
    cp.setBorders( true );

    add( cp );
    setWidth( 710 );
    setHeight( 550 );
    layout();

    }

  @Override
  protected void onLoad ()
    {
    updateStore();
    layout();

    }

  private void loadDetailPanel ( Site site )
    {
    detailPanel.redraw_panel( true );
    BeanModelFactory factory = BeanModelLookup.get().getFactory( Site.class );
    BeanModel model = factory.createModel( site );
    if ( model != null )
      {
      for ( FieldBinding b : detailPanel.getFieldBindings() )
        {
        b.bind( model );
        }
      detailPanel.getSaveBtn().setText( "Update" );
      detailPanel.getAddIntfBtn().enable();
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

    ColumnConfig column = new ColumnConfig( "name", "Name", 150 );
    configs.add( column );

    column = new ColumnConfig( "address", "Address", 125 );
    configs.add( column );

    column = new ColumnConfig( "city", "City", 100 );
    configs.add( column );

    column = new ColumnConfig( "state", "State", 100 );
    configs.add( column );

    column = new ColumnConfig( "country", "Country", 100 );
    configs.add( column );

    ColumnModel cm = new ColumnModel( configs );

    Grid<BeanModel> grid = new Grid<BeanModel>( store, cm );
    GridView gv = grid.getView();
    gv.setEmptyText( "No rows returned" );
    gv.setViewConfig( new GVC() );
    grid.setAutoExpandColumn( "address" );
    grid.setStripeRows( true );
    grid.setBorders( true );
    grid.setWidth( 590 );
    grid.setHeight( 150 );

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
            Site site = (Site) model.getBean();
            if ( site != null )
              {
              loadDetailPanel( site );
              }
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
    onWindowResized( 800, Window.getClientHeight() );
    }

  /**
   * Update of the component positions to the Window size
   */
  public void onWindowResized ( int width, int height )
    {
    scrollPanel.setHeight( ( height - 50 )
      + "px" );
    scrollPanel.setWidth( ( width - 76 )
      + "px" );
    }

  public void updateStore ()
    {
    // Create a sample AppTag

    getSiteData();
    layout();
    }

  private void getSiteData ()
    {

    Services.PREFERENCES.findSitesByCustomer( 1,
      new AsyncCallback<List<Site>>() {
        public void onSuccess ( List<Site> sites )
          {
          if ( sites != null )
            {
            BeanModelFactory factory = BeanModelLookup.get().getFactory(
              Site.class );
            List<BeanModel> models = null;
            models = factory.createModel( sites );

            // grid.getSelectionModel().deselectAll();
            store.removeAll();
            store.add( models );

            }

          }

        public void onFailure ( Throwable caught )
          {
          System.out.println( caught.getMessage() );
          }
      } );

    }

//  @Override
//  protected void onRender ( Element parent, int index )
//    {
//    super.onRender( parent, index );
//    setStyleName( "prefContent" );
//    add( new HTML( "<p>Site Editor</p>" ) );
//    setWidth( "100%" );
//    }

  }
