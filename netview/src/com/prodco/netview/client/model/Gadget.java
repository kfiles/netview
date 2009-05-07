/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.client.model;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.prodco.netview.client.view.DesktopViewListener;

public abstract class Gadget extends Composite
  {

  private GadgetClass gadgetclass = null;
  private HashMap userprefs = new HashMap();
  private DesktopViewListener listener;

  public Gadget ( GadgetClass gc )
    {
    gadgetclass = gc;
    // set default preferences
    for ( int i = 0; i < gc.getUserPrefsCount(); ++i )
      {
      UserPref up = gc.getUserPref( i );
      userprefs.put( up, up.getDefaultValue() );
      }
    }

  public void setGadgetListener ( DesktopViewListener listener )
    {
    this.listener = listener;
    }

  public DesktopViewListener getGadgetListener ()
    {
    return listener;
    }

  public GadgetClass getGadgetClass ()
    {
    return gadgetclass;
    }

  public String getTitle ()
    {
    return gadgetclass.getName();
    }

  public void refresh ()
    {
    }

  public Object getUserPrefValue ( UserPref up )
    {
    return userprefs.get( up );
    }

  public void setUserPrefValue ( UserPref up, Object value )
    {
    userprefs.put( up, value );
    }

  public void toXML ( Element element )
    {
    // add the gadget name as an attribute
    element.setAttribute( "name", gadgetclass.getName() );

    // add each user preference
    for ( int i = 0; i < gadgetclass.getUserPrefsCount(); i++ )
      {
      UserPref up = gadgetclass.getUserPref( i );
      Object pref = getUserPrefValue( up );
      Element prefElement = element.getOwnerDocument().createElement(
        "UserPref" );
      prefElement.setAttribute( "name", up.getName() );
      Text prefText = element.getOwnerDocument().createTextNode(
        pref.toString() );
      prefElement.appendChild( prefText );
      element.appendChild( prefElement );
      }
    }

  public void fromXML ( Element element )
    {
    // ensure the name of the class is correct
    String name = element.getAttribute( "name" );
    if ( name.compareTo( gadgetclass.getName() ) == 0 )
      {
      // load each user preference
      NodeList userPrefs = element.getElementsByTagName( "UserPref" );
      for ( int i = 0; i < userPrefs.getLength(); i++ )
        {
        Element prefElement = (Element) userPrefs.item( i );
        String prefName = prefElement.getAttribute( "name" );
        Text prefValue = (Text) prefElement.getChildNodes().item( 0 );
        for ( int j = 0; j < gadgetclass.getUserPrefsCount(); j++ )
          {
          UserPref up = gadgetclass.getUserPref( i );
          if ( prefName.compareTo( up.getName() ) == 0 )
            {
            setUserPrefValue( up, prefValue.getNodeValue() );
            j = gadgetclass.getUserPrefsCount();
            }
          }
        }
      }
    }
  }
