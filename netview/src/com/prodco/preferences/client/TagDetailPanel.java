
package com.prodco.preferences.client;

import java.util.List;

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
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

public class TagDetailPanel extends ContentPanel
  {

  private FormPanel formPanel = makeFormColumn();

  private boolean dataChange=false;

  public List<FormBinding> formBindings;

  public TagDetailPanel ( List<FormBinding> formBindings )
    {
    this.formBindings = formBindings;

    setLayout( new FitLayout() );
    setFrame( true );
    setHeaderVisible( false );
    setHeading( "Tag Detail" );
    add( createPanel() );

    }

  public FormPanel makeFormColumn ()
    {
    FormPanel col = new FormPanel();
    FormLayout layout = new FormLayout();
    layout.setLabelAlign( LabelAlign.LEFT );
    col.setLayout( layout );
    col.setHeaderVisible( false );
    col.setFrame( false );
    return col;
    }

  public LayoutContainer makeFormSection ()
    {
    LayoutContainer main = new LayoutContainer();
    main.setLayout( new ColumnLayout() );
    return main;
    }

  private LayoutContainer createPanel ()
    {
    FormData fieldWidth = new FormData( "95%%" );

    LayoutContainer panel = new LayoutContainer();
    panel.setLayout( new TableLayout() );

    LayoutContainer siteForm = makeFormSection();

    NumberField tfRank = new NumberField();
    tfRank.setPropertyEditorType( Integer.class );
    tfRank.setName( "tagPriority" );
    tfRank.setFieldLabel( "Tag Priority" );
    formPanel.add( tfRank, fieldWidth );

    tfRank.addListener( Events.OnKeyDown, new Listener<FieldEvent>() {
      public void handleEvent ( FieldEvent be )
        {
        setDataChange( true );
        }
    } );

    TextField<String> tfTagName = new TextField<String>();
    tfTagName.setName( "tagName" );
    tfTagName.setFieldLabel( "Tag Name" );
    formPanel.add( tfTagName, fieldWidth );

    tfTagName.addListener( Events.OnKeyDown, new Listener<FieldEvent>() {
      public void handleEvent ( FieldEvent be )
        {
        setDataChange( true );
        }

    } );

    TextField<String> tfTagRule = new TextField<String>();
    tfTagRule.setName( "tagRule" );
    tfTagRule.setFieldLabel( "Tag Rule" );
    formPanel.add( tfTagRule, fieldWidth );

    tfTagRule.addListener( Events.OnKeyDown, new Listener<FieldEvent>() {
      public void handleEvent ( FieldEvent be )
        {
        setDataChange( true );
        }

    } );

    siteForm.add( formPanel, new ColumnData( 700 ) );

    formBindings.add( new FormBinding( formPanel, true ) );

    panel.add( siteForm );

    return panel;
    }

  private void setDataChange ( boolean b )
    {
    this.dataChange = b;
    }

  public void clearPanel ()
    {
    if ( formPanel != null )
      formPanel.clear();
    }

  public void populatePanel()
    {
    
    }
  
  }
