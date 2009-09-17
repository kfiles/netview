package com.prodco.preferences.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * <h3>CSS Style Rules</h3> <ul class='css'> <li>.gwt-TabBar-horiz-sep { style
 * for horizontal separators }</li> <li>.gwt-TabBar-vert-sep { style for
 * vertical separators }</li> </ul>
 */
public class FancyFlexibleTabBar extends FlexibleTabBar {

  private static final String STYLE_SEP_HORIZ = "gwt-TabBar-horiz-sep";

  private static final String STYLE_SEP_VERT = "gwt-TabBar-vert-sep";

  @Override
  public void disableTab(int index) {
    super.disableTab(getRealIndex(index));
  }

  @Override
  public void enableTab(int index) {
    super.enableTab(getRealIndex(index));
  }

  @Override
  public void hideTab(int index) {
    super.hideTab(getRealIndex(index));
    super.hideTab(getSeparatorIndex(index));
  }

  @Override
  public void showTab(int index) {
    super.showTab(getRealIndex(index));
    int sepIndex = getSeparatorIndex(index);
    if (-1 == sepIndex)
      return;
    String style = STYLE_SEP_VERT;
    if (isHorizontal())
      style = STYLE_SEP_HORIZ;
    super.setLabelStyle(sepIndex, style);
  }

  public FancyFlexibleTabBar(TabOrientation orientation, boolean wrapLabels) {
    super(orientation, wrapLabels);
  }

  @Override
  public int getSelectedTab() {
    // TODO Auto-generated method stub
    return super.getSelectedTab();
  }

  @Override
  public int getTabCount() {
    // It's actually (getWidgetCount - 2 Headers) / 2 + 1 (to account for
    // separators)
    return panel.getWidgetCount();
  }

  @Override
  public String getTabHTML(int index) {
    return super.getTabHTML(getRealIndex(index));
  }

  @Override
  public void insertTab(Label item, int beforeIndex) {
    int realIndex = getRealIndex(beforeIndex);
    super.insertTab(item, (realIndex == 0) ? realIndex : realIndex - 1); // insert
                                                                         // before
                                                                         // separator
  }

  @Override
  protected void insertTabInternal(Label item, int beforeIndex) {
    // The superclass will use the modified indexing, so no need to adjust here
    super.insertTabInternal(item, beforeIndex);
    insertSeparator(beforeIndex);
  }

  private void insertSeparator(int beforeIndex) {
    Label sep = new Label("");
    if (beforeIndex > 0) {
      if (isHorizontal())
        sep.setStyleName(STYLE_SEP_HORIZ);
      else sep.setStyleName(STYLE_SEP_VERT);
      panel.insert(sep, beforeIndex + 1);
    }
  }

  @Override
  public void insertTab(String text, boolean asHTML, int beforeIndex) {
    int realIndex = getRealIndex(beforeIndex);
    super.insertTab(text, asHTML, (realIndex == 0) ? realIndex : realIndex - 1); // insert
                                                                                 // before
                                                                                 // separator
    // insertSeparator(beforeIndex);
  }

  @Override
  public void insertTab(String text, int beforeIndex) {
    int realIndex = getRealIndex(beforeIndex);
    super.insertTab(text, (realIndex == 0) ? realIndex : realIndex - 1); // insert
                                                                         // before
                                                                         // separator
    // insertSeparator(beforeIndex);
  }

  @Override
  public void onClick(Widget sender) {
    for (int i = 1; i < panel.getWidgetCount() - 1; ++i) {
      if (panel.getWidget(i) == sender) {
        super.selectTab(i - 1);
        return;
      }
    }
  }

  @Override
  public void removeTab(int index) {
    int realIndex = getRealIndex(index);
    super.removeTab(index);
    if (realIndex > 0)
      panel.remove(realIndex - 1);
  }

  @Override
  protected boolean fireBeforeTabSelected(SourcesTabEvents src, int index) {
    return super.fireBeforeTabSelected(src, index / 2);
  }

  @Override
  protected void fireTabSelected(SourcesTabEvents src, int index) {
    super.fireTabSelected(src, index / 2);
  }

  @Override
  public boolean selectTab(int index) {
    return super.selectTab(getRealIndex(index));
  }

  /**
   * Because we have separators between the real tabs, we have to adjust our
   * index accordingly to ignore these extra Widgets
   * 
   * @param index
   * @return
   */
  private int getRealIndex(int index) {
    return index * 2;
  }

  private int getSeparatorIndex(int index) {
    // No separator for last tab
    if (getTabCount() - 1 == index)
      return -1;
    else return index * 2 + 1;
  }
}