
package com.prodco.preferences.client;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;

public class ProfileEditor extends LayoutContainer
  {
  @Override
  protected void onRender ( Element parent, int index )
    {
    super.onRender( parent, index );
    setStyleName( "prefContent" );
    add( new HTML( "<p>Profile Editor</p>" ) );
    setWidth( "100%" );
    }

  }
