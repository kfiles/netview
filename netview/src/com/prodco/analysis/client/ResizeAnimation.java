
package com.prodco.analysis.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Widget;

public class ResizeAnimation extends Animation
  {
  protected int startHeight;
  protected int endHeight;
  protected Widget w;

  public ResizeAnimation ( Widget w, int startHeight, int endHeight )
    {
    this.startHeight = startHeight;
    this.endHeight = endHeight;
    this.w = w;
    }

  @Override
  protected void onUpdate ( double progress )
    {
    int newHeight = (int) ( ( endHeight - startHeight )
      * progress + startHeight );
    w.setHeight( newHeight
      + "px" );
    }

  }
