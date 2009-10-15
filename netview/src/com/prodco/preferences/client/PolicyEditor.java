
package com.prodco.preferences.client;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;

public class PolicyEditor extends LayoutContainer
  {
  @Override
  protected void onRender ( Element parent, int index )
    {
    super.onRender( parent, index );
    setStyleName( "prefContent" );
    add( new HTML( "<p>Policy Editor</p>" ) );
    setWidth( "100%" );
    }

  }
