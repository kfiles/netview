
package com.prodco.preferences.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.prodco.preferences.client.model.AppTag;

public class TagDetailPanelOrig extends CommonPanel
  {

  private FormPanel formPanel = makeFormColumn();

  private boolean dataChange=false;
  private List<FieldBinding> bindings = new ArrayList<FieldBinding>();

  NumberField tfRank = new NumberField();
  TextField<String> tfTagName = new TextField<String>();
  TextField<String> tfTagRule = new TextField<String>();

  public TagDetailPanelOrig ( List<FormBinding> formBindings )
    {
    this.formBindings = formBindings;

    this.setWidth(375);
    this.setLayout(new FlowLayout());
    setIconStyle("icon-home");

//    setLayout(new TableLayout());
    setFrame(true);
    setHeaderVisible( true );
    setHeading( "Tag Detail" );
    add( createPanel() );
    
    setHeight(300);

    }

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    this.layout();
  }

  private LayoutContainer createPanel ()
    {
    
    FormData fieldWidth = new FormData( "95%" );

    tfRank.setPropertyEditorType( Integer.class );
    tfRank.setName( "tagPref" );
    tfRank.setFieldLabel( "Tag Priority" );
    tfRank.setLabelSeparator(":");
    formPanel.add( tfRank, fieldWidth );


    tfTagName.setName( "tagName" );
    tfTagName.setFieldLabel( "Tag Name" );
    tfTagName.setLabelSeparator(":");
    formPanel.add( tfTagName, fieldWidth );

    tfTagRule.setName( "tagRule" );
    tfTagRule.setFieldLabel( "Tag Rule" );
    tfTagRule.setLabelSeparator(":");    
    formPanel.add( tfTagRule, fieldWidth );

    tfTagRule.addListener( Events.OnKeyDown, new Listener<FieldEvent>() {
      public void handleEvent ( FieldEvent be )
        {
        setDataChange( true );
        }

    } );

    tfRank.addListener( Events.OnKeyDown, new Listener<FieldEvent>() {
    public void handleEvent ( FieldEvent be )
      {
      setDataChange( true );
      }
  } );

    tfTagName.addListener( Events.OnKeyDown, new Listener<FieldEvent>() {
      public void handleEvent ( FieldEvent be )
        {
        setDataChange( true );
        }

    } );

    bindings.addAll(new FormBinding(formPanel, true).getBindings());

    return formPanel;
    }

  private void setDataChange ( boolean b )
    {
    this.dataChange = b;
    }

  public void clearPanel ()
    {
    Window.alert("ClearPanel called");
    if ( formPanel != null )
      formPanel.clear();
    }

  public void populatePanel(AppTag tag)
    {     
     String val = tfTagName.getValue();
     if (val != null)
     Window.alert("tagName is set to "+tfTagName.getValue());
     else
       Window.alert("tagName is null");
     
     tfTagName.setValue( "Custom" );
     layout();
    }
  
  public List<FieldBinding> getFieldBindings() {
  return bindings;
}

  }
