/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Command;

import java.util.HashSet;
import java.util.Iterator;

public class DockableWidget extends SimplePanel implements MouseListener
  {

  private class DragInfo
    {
    public Widget dragWidget = null;
    public int dragStartX, dragStartY;
    public int centerX, centerY;
    }

  private DragInfo dragInfo = null;
  private DockableWidget switchWith = null;
  private static HashSet dockableContainers = new HashSet();
  private HashSet dockableListeners = new HashSet();

  public DockableWidget ( Widget w )
    {
    setWidget( w );
    setStyleName( "gwtapps-DockableWidget" );
    if ( DesktopView.editable == true )
      {
      if ( w instanceof SourcesMouseEvents )
        ( (SourcesMouseEvents) w ).addMouseListener( this );
      }
    }

  public static void addContainer ( Widget widget )
    {
    dockableContainers.add( widget );
    }

  public void onAttach ()
    {
    super.onAttach();
    dockableContainers.add( getParent() );
    }

  public void onMouseDown ( Widget sender, int x, int y )
    {
    endDrag( sender );
    dragInfo = new DragInfo();
    dragInfo.dragWidget = getWidget();

    int left = dragInfo.dragWidget.getAbsoluteLeft() - 1;
    int top = dragInfo.dragWidget.getAbsoluteTop() - 1;
    int width = dragInfo.dragWidget.getOffsetWidth();
    int height = dragInfo.dragWidget.getOffsetHeight();
    dragInfo.dragWidget.setWidth( width
      + "px" );

    HTML placeholder = new HTML( " " );
    placeholder.setStyleName( "gwtapps-Placeholder" );
    placeholder.setHeight( ( height + 2 )
      + "px" );
    setWidget( placeholder );
    RootPanel.get().add( dragInfo.dragWidget );

    Element elem = dragInfo.dragWidget.getElement();
    DOM.setStyleAttribute( elem, "position", "absolute" );
    DOM.setStyleAttribute( elem, "left", left
      + "px" );
    DOM.setStyleAttribute( elem, "top", top
      + "px" );
    DOM.setCapture( sender.getElement() );
    dragInfo.dragStartX = x;
    dragInfo.dragStartY = y;
    dragInfo.centerX = width / 2;
    dragInfo.centerY = height / 2;
    }

  public void onMouseEnter ( Widget sender )
    {
    }

  public void onMouseLeave ( Widget sender )
    {
    }

  public void onMouseMove ( Widget sender, int x, int y )
    {
    if ( dragInfo != null
      && switchWith == null )
      {
      int left = x
        + dragInfo.dragWidget.getAbsoluteLeft() - dragInfo.dragStartX;
      int top = y
        + dragInfo.dragWidget.getAbsoluteTop() - dragInfo.dragStartY;

      Element elem = dragInfo.dragWidget.getElement();
      DOM.setStyleAttribute( elem, "left", left
        + "px" );
      DOM.setStyleAttribute( elem, "top", top
        + "px" );

      findDropContainer( dragInfo.centerX
        + left, dragInfo.centerY
        + top );
      }
    }

  protected void findDropContainer ( int absCenterX, int absCenterY )
    {
    Widget parentWidget = getParent();
    for ( Iterator itContainers = dockableContainers.iterator(); itContainers
      .hasNext()
      && switchWith == null; )
      {
      Widget containerWidget = (Widget) itContainers.next();
      if ( containerWidget instanceof HasWidgets
        && absCenterX >= containerWidget.getAbsoluteLeft()
        && absCenterX <= containerWidget.getAbsoluteLeft()
          + containerWidget.getOffsetWidth()
        && absCenterY >= containerWidget.getAbsoluteTop()
        && absCenterY <= containerWidget.getAbsoluteTop()
          + containerWidget.getOffsetHeight() )
        {
        if ( containerWidget != parentWidget
          && containerWidget instanceof HasWidgets )
          ( (HasWidgets) containerWidget ).add( this );
        findDropWidget( absCenterX, absCenterY, (HasWidgets) containerWidget );
        }
      }
    }

  protected void findDropWidget ( int absCenterX, int absCenterY,
    HasWidgets container )
    {
    int width = dragInfo.dragWidget.getOffsetWidth();
    int height = dragInfo.dragWidget.getOffsetHeight();
    for ( Iterator it = container.iterator(); it.hasNext()
      && switchWith == null; )
      {
      Widget sibling = (Widget) it.next();
      if ( sibling instanceof DockableWidget
        && sibling != this && absCenterX >= sibling.getAbsoluteLeft()
        && absCenterX <= sibling.getAbsoluteLeft()
          + width && absCenterY >= sibling.getAbsoluteTop()
        && absCenterY <= sibling.getAbsoluteTop()
          + height )
        {
        switchWith = (DockableWidget) sibling;
        DeferredCommand.addCommand( new Command() {
          public void execute ()
            {
            swapChild();
            }
        } );
        }
      }

    }

  public void onMouseUp ( Widget sender, int x, int y )
    {
    endDrag( sender );
    }

  public void endDrag ( Widget sender )
    {
    if ( dragInfo != null )
      {
      switchWith = null;
      DOM.setStyleAttribute( dragInfo.dragWidget.getElement(), "position", "" );
      DOM.setStyleAttribute( dragInfo.dragWidget.getElement(), "left", "" );
      DOM.setStyleAttribute( dragInfo.dragWidget.getElement(), "top", "" );;
      dragInfo.dragWidget.setWidth( "100%" );
      setWidget( dragInfo.dragWidget );
      DOM.releaseCapture( sender.getElement() );
      dragInfo = null;
      doOnDocked( getWidget() );
      }
    }

  public void addDockableListener ( DockableListener listener )
    {
    dockableListeners.add( listener );
    }

  protected void doOnDocked ( Widget widget )
    {
    for ( Iterator it = dockableListeners.iterator(); it.hasNext(); )
      {
      DockableListener listener = (DockableListener) it.next();
      listener.onDocked( widget );
      }
    }

  protected void swapChild ()
    {
    if ( switchWith != null )
      {
      Widget childOther = switchWith.getWidget();
      Widget placeholder = getWidget();
      switchWith.setWidget( placeholder );
      setWidget( childOther );
      switchWith.dragInfo = dragInfo;
      Widget childThis = dragInfo.dragWidget;
      dragInfo = null;
      if ( childOther instanceof SourcesMouseEvents )
        {
        ( (SourcesMouseEvents) childOther ).removeMouseListener( switchWith );
        ( (SourcesMouseEvents) childOther ).addMouseListener( this );
        ( (SourcesMouseEvents) childThis ).removeMouseListener( this );
        ( (SourcesMouseEvents) childThis ).addMouseListener( switchWith );
        }
      switchWith = null;
      }
    }
  }
