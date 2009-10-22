
package com.prodco.preferences.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.model.Site;

public class SiteDetailPanel extends CommonPanel
  {

  private ContentPanel panel = new ContentPanel();

  private String title = "Site Detail";

  private NumberField tfId = new NumberField();

  private TextField<String> tfSiteName = new TextField<String>();

  private TextField<String> tfAddress = new TextField<String>();

  private TextField<String> tfCity = new TextField<String>();

  private TextField<String> tfState = new TextField<String>();

  private TextField<String> tfCountry = new TextField<String>();

  private TextField<String> tfZip = new TextField<String>();

  private List<FieldBinding> bindings = new ArrayList<FieldBinding>();

  private FormPanel detailPanel = makeFormColumn();

  Button btnSave = null;
  Button btnAddIntf = null;

  private SiteEditor parent;

  public String getTitle ()
    {
    return title;
    }

  /**
   * 
   */
  public SiteDetailPanel ( SiteEditor parent, List<FormBinding> formBindings )
    {

    this.parent = parent;
    this.formBindings = formBindings;
    this.setWidth( 590 );
    this.setHeight( 250 );
    this.setLayout( new FlowLayout() );
    this.setFrame( true );
    this.setHeading( getTitle() );
    add( createTabPanel() );
    layout();
    }

  @Override
  protected void onRender ( Element parent, int index )
    {
    super.onRender( parent, index );
    this.layout();
    }

  public void redraw_panel ( boolean readOnly )
    {
    this.layout();
    }

  public void clearPanel ()
    {
    detailPanel.reset();
    tfId.reset();
    this.layout();
    }

  @SuppressWarnings ( "unchecked" )
  private LayoutContainer createTabPanel ()
    {
    FormData fieldWidth = new FormData( "95%" );

    tfSiteName.setName( "name" );
    tfSiteName.setFieldLabel( "Site Name" );
    detailPanel.add( tfSiteName, fieldWidth );

    tfAddress.setName( "address" );
    tfAddress.setFieldLabel( "Address" );
    detailPanel.add( tfAddress, fieldWidth );

    tfCity.setName( "city" );
    tfCity.setFieldLabel( "City" );
    detailPanel.add( tfCity, fieldWidth );

    tfState.setName( "state" );
    tfState.setFieldLabel( "State" );
    detailPanel.add( tfState, fieldWidth );

    tfCountry.setName( "country" );
    tfCountry.setFieldLabel( "Country" );
    detailPanel.add( tfCountry, fieldWidth );

    tfZip.setName( "zip" );
    tfZip.setFieldLabel( "Zip/Postal" );
    detailPanel.add( tfZip, fieldWidth );

    tfId.setPropertyEditorType( Integer.class );
    tfId.setName( "siteId" );
    tfId.setVisible( false );

    bindings.addAll( new FormBinding( detailPanel, true ).getBindings() );
    bindings.add( new FieldBinding( tfId, "siteId" ) );

    // detailPanel.setBorders( true );
    detailPanel.setSize( 590, 200 );
    detailPanel.setHeaderVisible( false );
    panel.add( detailPanel );

    HorizontalAlignment align = HorizontalAlignment.CENTER;
    btnSave = new Button( "Save" );
    btnSave.setIconStyle( "gwt-Button" );
    btnSave.addListener( Events.OnClick, new Listener<ComponentEvent>() {
      public void handleEvent ( ComponentEvent ce )
        {
        if ( ce.getType() == Events.OnClick )
          {
          updateSite();
          }
        }
    } );

    Button btnReset = new Button( "Add" );
    btnReset.setIconStyle( "gwt-Button" );
    btnReset.addListener( Events.OnClick, new Listener<ComponentEvent>() {
      public void handleEvent ( ComponentEvent ce )
        {
        if ( ce.getType() == Events.OnClick )
          {
          clearPanel();
          btnSave.setText( "Save" );
          btnAddIntf.disable();
          }
        }
    } );

    Button btnDelete = new Button( "Delete" );
    btnDelete.setIconStyle( "gwt-Button" );
    btnDelete.addListener( Events.OnClick, new Listener<ComponentEvent>() {
      public void handleEvent ( ComponentEvent ce )
        {

        if ( ce.getType() == Events.OnClick )
          {
          deleteSite();
          btnAddIntf.disable();
          }
        }

    } );

    btnAddIntf = new Button( "Flow Collector" );
    btnDelete.setIconStyle( "gwt-Button" );
    btnDelete.addListener( Events.OnClick, new Listener<ComponentEvent>() {
      public void handleEvent ( ComponentEvent ce )
        {

        if ( ce.getType() == Events.OnClick )
          {
//          addFlowCollector();
          }
        }

    } );
    btnAddIntf.disable();

    panel.addButton( btnSave );
    panel.addButton( btnReset );
    panel.addButton( btnDelete );
    panel.addButton( btnAddIntf );
    // panel.setBorders( true );
    panel.setButtonAlign( align );
    panel.setSize( 600, 220 );
    panel.setLayout( new FitLayout() );
    panel.setHeaderVisible( false );
    panel.layout();

    return panel;
    }

  public void updateSite ()
    {
    if ( tfId.getValue() != null )
      {
      int id = tfId.getValue().intValue();
      String siteName = tfSiteName.getValue();
      String address = tfAddress.getValue();
      String city = tfCity.getValue();
      String state = tfState.getValue();
      String country = tfCountry.getValue();
      String zip = tfZip.getValue();

      Site site = new Site( id, siteName, address, city, state, country, zip );
      Services.PREFERENCES.saveSite( 1, site,
        new AsyncCallback<Void>() {
          public void onSuccess ( Void args0 )
            {
            // Window.alert("Successfully updated Site for customer");
            parent.updateStore();
            }

          public void onFailure ( Throwable caught )
            {
            Window.alert( "Failed in updating tag:"
              + caught.getMessage() );
            }
        } );
      }
    else
      {
      String siteName = tfSiteName.getValue();
      String address = tfAddress.getValue();
      String city = tfCity.getValue();
      String state = tfState.getValue();
      String country = tfCountry.getValue();
      String zip = tfZip.getValue();

      Site site = new Site( null, siteName, address, city, state, country, zip );
      Services.PREFERENCES.saveSite( 1, site,
        new AsyncCallback<Void>() {
          public void onSuccess ( Void args0 )
            {
            Window.alert( "Successfully Added Site for customer" );
            parent.updateStore();
            clearPanel();
            }

          public void onFailure ( Throwable caught )
            {
            Window.alert( "Failed in Adding site:"
              + caught.getMessage() );
            }
        } );

      }

    }

  private void deleteSite ()
    {
    int id = tfId.getValue().intValue();

    Site site = new Site( );
    site.setSiteId( id );
    Services.PREFERENCES.deleteSite( 1, site,
      new AsyncCallback<Void>() {
        public void onSuccess ( Void args0 )
          {
          Window.alert( "Successfully deleted Site for customer" );
          parent.updateStore();
          clearPanel();
          }

        public void onFailure ( Throwable caught )
          {
          Window.alert( "Failed in Deleting tag:"
            + caught.getMessage() );
          }
      } );

    }

  public List<FieldBinding> getFieldBindings ()
    {
    return bindings;
    }

  public Button getSaveBtn ()
    {
    return btnSave;
    }

  public Button getAddIntfBtn ()
    {
    return btnAddIntf;
    }

  }
