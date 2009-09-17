package com.prodco.preferences.client;

import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Widget;

public interface IFlexibleIndexedPanel extends IndexedPanel {
  public void add(Widget w);

  public void insert(Widget w, int beforeIndex);

  public boolean remove(Widget index);
}
