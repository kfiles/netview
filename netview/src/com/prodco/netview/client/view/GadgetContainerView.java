/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetListener;
import com.prodco.netview.client.model.UserPref;
import com.google.gwt.user.client.ui.ImageBundle.Resource;

public class GadgetContainerView extends SimplePanel
  implements
    SourcesMouseEvents
  {

  private static final String COLOR_BORDER = "#999999";

  private static final String COLOR_TITLE_BG = "#666666";
  
  private static final String COLOR_BODY = "#000000";

  public static interface TitleBarImageBundle extends ImageBundle
    {
    @Resource ("com/prodco/netview/client/view/icons/stop-16.png")
    public AbstractImagePrototype closeIcon ();

    @Resource ("minimize1.gif")
    public AbstractImagePrototype minimize1Icon ();

    @Resource ("minimize2.gif")
    public AbstractImagePrototype minimize2Icon ();

    @Resource ("com/prodco/netview/client/view/icons/reload-16.png")
    public AbstractImagePrototype refreshIcon ();
    }

  public static TitleBarImageBundle titleBarImages = (TitleBarImageBundle) GWT
    .create( TitleBarImageBundle.class );

  private Gadget child;
  private HorizontalPanel titleBar = new HorizontalPanel();
  private VerticalPanel mainLayout = new VerticalPanel();
  private Label title = new Label();
  private Hyperlink edit = new Hyperlink( "edit", "" );
//  private ToggleButton minimizeButton = new ToggleButton( titleBarImages
//    .minimize1Icon().createImage(), titleBarImages.minimize2Icon()
//    .createImage() );
//  private PushButton refreshButton = new PushButton( titleBarImages
//    .refreshIcon().createImage() );
  private PushButton closeButton = new PushButton( titleBarImages.closeIcon()
    .createImage() );
  private FlexTable editPanel = new FlexTable();
  private SimplePanel childContainer = new SimplePanel();
  private boolean open = true;
  private boolean editopen = false;

  public GadgetContainerView ( Gadget c )
    {
    child = c;
    if ( DesktopView.editable )
      buildTitleBar();
    buildMainLayout();
    mainLayout.setWidth( "50%" );
    RoundedPanel round = new RoundedPanel( COLOR_BORDER, mainLayout,
      RoundedPanel.ROUND_TOP );
    round.setWidth( "50%" );
    setWidget( round );
    child.refresh();

    // set style names
    setStyleName( "gwtapps-GadgetContainer" );
    mainLayout.setStyleName( "gwtapps-GadgetContainerPanel" );
    titleBar.setStyleName( "gwtapps-GadgetContainerTitleBar" );
    editPanel.setStyleName( "gwtapps-GadgetContainerEditPanel" );
    title.setStyleName( "gwtapps-GadgetContainerTitle" );
    childContainer.setStyleName( "gwtapps-GadgetContainerBody" );
    }

  protected void buildMainLayout ()
    {
    String roundedColor = COLOR_TITLE_BG;
    if (false == DesktopView.editable)
      roundedColor = COLOR_BODY; 
    if (true == DesktopView.editable) {
    RoundedPanel roundTitle = new RoundedPanel( roundedColor, titleBar,
      RoundedPanel.ROUND_TOP );
    roundTitle.setWidth( "100%" );
    mainLayout.add( roundTitle );
    if ( child.getGadgetClass().getUserPrefsCount() > 0 )
      {
      mainLayout.add( editPanel );
      editPanel.setWidth( "50%" );
      buildEditPanel();
      }
    }
    childContainer.setWidget( child );
    mainLayout.add( childContainer );
    }

  protected void buildTitleBar ()
    {
    int column = 0;
//    titleBar.add( minimizeButton );
    titleBar.add( title );
//    titleBar.add( refreshButton );
    if ( child.getGadgetClass().getUserPrefsCount() > 0 )
      titleBar.add( edit );
    titleBar.add( closeButton );
    titleBar.setCellWidth( title, "100%" );
    titleBar.setWidth( "100%" );
    title.setWidth( "100%" );
    title.setText( child.getTitle() );
    buildClickListeners();
    }

  protected void buildClickListeners ()
    {
    edit.addClickListener( new ClickListener() {
      public void onClick ( Widget sender )
        {
        toggleEdit();
        }
    } );
//    refreshButton.addClickListener( new ClickListener() {
//      public void onClick ( Widget sender )
//        {
//        child.refresh();
//        }
//    } );
    closeButton.addClickListener( new ClickListener() {
      public void onClick ( Widget sender )
        {
        if ( Window.confirm( "Remove "
          + child.getGadgetClass().getName() + " from your page?" ) )
          {
          DesktopViewListener l = child.getGadgetListener(); 
          getParent().removeFromParent();
          if (null != l)
            l.onInterfaceChange();
          }
        }
    } );
//    minimizeButton.addClickListener( new ClickListener() {
//      public void onClick ( Widget sender )
//        {
//        if ( minimizeButton.isDown() )
//          child.setVisible( false );
//        else child.setVisible( true );
//        open = !open;
//        }
//    } );
    }

  protected void buildEditPanel ()
    {
    editPanel.setVisible( editopen );
    editPanel.setStyleName( "gwtapps-GadgetContainerEditPanel" );
    for ( int i = 0; i < child.getGadgetClass().getUserPrefsCount(); ++i )
      {
      final UserPref up = child.getGadgetClass().getUserPref( i );
      editPanel.setText( i, 0, up.getName()
        + ":" );
      Object value = child.getUserPrefValue( up );
      if ( value instanceof String )
        {
        final TextBox editbox = new TextBox();
        editbox.setText( (String) value );
        editbox.addChangeListener( new ChangeListener() {
          public void onChange ( Widget sender )
            {
            child.setUserPrefValue( up, editbox.getText() );
            }
        } );
        editPanel.setWidget( i, 1, editbox );
        editPanel.getCellFormatter().setWidth( i, 1, "100%" );
        editbox.setWidth( "100%" );
        }
      }
    }

  public void toggleEdit ()
    {
    editopen = !editopen;
    editPanel.setVisible( editopen );
    if ( !editopen )
      {
      edit.setText( "edit" );
      child.refresh();
      child.getGadgetListener().onInterfaceChange();
      }
    else
      {
      edit.setText( "done" );
      }
    }

  public void addMouseListener ( MouseListener listener )
    {
    title.addMouseListener( listener );
    }

  public void removeMouseListener ( MouseListener listener )
    {
    title.removeMouseListener( listener );
    }

  public Gadget getGadget ()
    {
    return child;
    }
  }
