
package com.prodco.preferences.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.prodco.preferences.client.model.AppTag;

public class TagDetailPanel extends CommonPanel
  {

  private String title = "Tag Detail";

  private NumberField tfId = new NumberField();

  private NumberField tfRank = new NumberField();

  private TextField<String> tfTagName = new TextField<String>();

  private TextField<String> tfTagRule = new TextField<String>();

  private List<FieldBinding> bindings = new ArrayList<FieldBinding>();

  private FormPanel detailPanel = makeFormColumn();
  Button btnSave = new Button();
  Button btnCancel = new Button( "&nbsp;Cancel" );

  private TagEditor parent;
  
  public String getTitle ()
    {
    return title;
    }

  /**
   * 
   */
  public TagDetailPanel ( TagEditor parent, List<FormBinding> formBindings )
    {

    this.parent=parent;
    this.formBindings = formBindings;
    this.setWidth( 600 );
    this.setHeight( 300 );
    this.setLayout( new FlowLayout() );
    this.setFrame( true );
    this.setHeading( getTitle() );
    setIconStyle( "icon-home" );
    add( createTabPanel() );
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
    this.layout();
    }

  @SuppressWarnings ( "unchecked" )
  private LayoutContainer createTabPanel ()
    {
    FormData fieldWidth = new FormData( "95%" );

    tfId.setPropertyEditorType( Integer.class );
    tfId.setName( "tagId" );

    tfRank.setPropertyEditorType( Integer.class );
    tfRank.setName( "tagPref" );
    tfRank.setFieldLabel( "Tag Priority" );
    tfRank.setLabelSeparator( ":" );
    detailPanel.add( tfRank, fieldWidth );

    tfTagName.setName( "tagName" );
    tfTagName.setFieldLabel( "Tag Name#" );
    detailPanel.add( tfTagName, fieldWidth );

    tfTagRule.setName( "tagRule" );
    tfTagRule.setFieldLabel( "Tag Rule" );
    detailPanel.add( tfTagRule, fieldWidth );

    // When the button is pressed, load new alarms with the date range
    btnSave.setText( "&nbsp;&nbsp;Save" );
    btnSave.setIconStyle( "gwt-Button" );
    btnSave.addListener( Events.OnClick, new Listener<ComponentEvent>() {
      public void handleEvent ( ComponentEvent ce )
        {
        if ( ce.getType() == Events.OnClick )
          {
          updateTag();
          }
        }
    } );

    btnCancel.setIconStyle( "gwt-Button" );
    btnCancel.setText( "&nbsp;&nbsp;Cancel" );
    btnCancel.addListener( Events.OnClick, new Listener<ComponentEvent>() {
      public void handleEvent ( ComponentEvent ce )
        {
        if ( ce.getType() == Events.OnClick )
          {
          clearPanel();
          }
        }
    } );

    detailPanel.add( btnSave );
    detailPanel.add( btnCancel );

    detailPanel.setButtonAlign( HorizontalAlignment.CENTER );

    bindings.addAll( new FormBinding( detailPanel, true ).getBindings() );
    bindings.add( new FieldBinding(tfId,"tagId") );

    return detailPanel;
    }

  public void updateTag ()
    {
    int id = tfId.getValue().intValue();
    int rank = tfRank.getValue().intValue();
    String tagName = tfTagName.getValue();
    String tagRule = tfTagRule.getValue();    
    AppTag tag = new AppTag(id,rank,tagName,tagRule);
    
    Services.PREFERENCES.saveAppTagForCustomer( 1, tag, 
      new AsyncCallback<Void>() {
        public void onSuccess ( Void args0 )
          {
//          Window.alert("Successfully changed Tag for customer");
          parent.updateStore();
          }

        public void onFailure ( Throwable caught )
          {
          Window.alert("Failed in updating tag:"+caught.getMessage() );
          }
      } );


    }

  public List<FieldBinding> getFieldBindings ()
    {
    return bindings;
    }

  }
