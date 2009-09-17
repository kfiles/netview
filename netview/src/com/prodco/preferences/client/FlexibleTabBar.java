package com.prodco.preferences.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabListenerCollection;
import com.google.gwt.user.client.ui.Widget;

/**
 * A horizontal bar of folder-style tabs, most commonly used as part of a
 * {@link com.google.gwt.user.client.ui.TabPanel}. <h3>CSS Style Rules</h3> <ul
 * class='css'> <li>.mas-TabBar { the tab bar itself }</li> <li>.mas-TabBar
 * .mas-TabBarFirst { the left edge of the bar }</li> <li>.mas-TabBar
 * .mas-TabBarRest { the right edge of the bar }</li> <li>.mas-TabBar
 * .mas-TabBarItem { unselected tabs }</li> <li>.mas-TabBar
 * .mas-TabBarItem-selected { additional style for selected tabs }</li> </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.TabBarExample}
 * </p>
 */
public class FlexibleTabBar extends Composite implements SourcesTabEvents,
    ClickListener {

  private static final String CSS_NAMESPACE = "mas-";

  protected final IFlexibleIndexedPanel panel;

  private Widget selectedTab;

  private TabListenerCollection tabListeners;

  protected boolean wrapLabels;

  /**
   * Creates an empty tab bar.
   */
  public FlexibleTabBar(TabOrientation orientation, boolean wrapLabels) {
    this.wrapLabels = wrapLabels;
    panel = createTabPanel(orientation);
    initWidget((Widget) panel);
    sinkEvents(Event.ONCLICK);
    setStyleName(CSS_NAMESPACE + getPrefix(orientation) + "TabBar");
  }

  protected IFlexibleIndexedPanel getPanel() {
    return panel;
  }

  protected boolean isHorizontal() {
    return panel instanceof FlexibleHorizontalPanel;
  }

  protected String getPrefix() {
    TabOrientation horizontal = (isHorizontal() ? TabOrientation.HORIZONTAL
        : TabOrientation.VERTICAL);
    return getPrefix(horizontal);
  }

  protected String getPrefix(TabOrientation orientation) {
    return (TabOrientation.HORIZONTAL == orientation) ? "horizontal-"
        : "vertical-";
  }

  protected IFlexibleIndexedPanel createTabPanel(TabOrientation orientation) {
    if (TabOrientation.HORIZONTAL == orientation) {
      FlexibleHorizontalPanel panel = new FlexibleHorizontalPanel();

      HTML first = new HTML("&nbsp;", true);
      HTML rest = new HTML("&nbsp;", true);
      first
          .setStyleName(CSS_NAMESPACE + getPrefix(orientation) + "TabBarFirst");
      rest.setStyleName(CSS_NAMESPACE + getPrefix(orientation) + "TabBarRest");
      first.setHeight("100%");
      rest.setHeight("100%");

      panel.add(first);
      panel.add(rest);
      panel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
      panel.setCellHeight(first, "100%");
      panel.setCellWidth(rest, "100%");
      first.setHeight("100%");

      return panel;
    } else {
      FlexibleVerticalPanel panel = new FlexibleVerticalPanel();

      HTML first = new HTML("&nbsp;", true);
      HTML rest = new HTML("&nbsp;", true);
      first
          .setStyleName(CSS_NAMESPACE + getPrefix(orientation) + "TabBarFirst");
      rest.setStyleName(CSS_NAMESPACE + getPrefix(orientation) + "TabBarRest");
      first.setHeight("100%");
      rest.setHeight("100%");

      panel.add(first);
      panel.add(rest);

      return panel;
    }
  }

  /**
   * Adds a new tab with the specified text.
   * 
   * @param text
   *          the new tab's text
   */
  public void addTab(String text) {
    insertTab(text, getTabCount());
  }

  /**
   * Adds a new tab with the specified text.
   * 
   * @param text
   *          the new tab's text
   * @param asHTML
   *          <code>true</code> to treat the specified text as html
   */
  public void addTab(String text, boolean asHTML) {
    insertTab(text, asHTML, getTabCount());
  }

  public void addTabListener(TabListener listener) {
    if (tabListeners == null)
      tabListeners = new TabListenerCollection();
    tabListeners.add(listener);
  }

  /**
   * Gets the tab that is currently selected.
   * 
   * @return the selected tab
   */
  public int getSelectedTab() {
    if (selectedTab == null)
      return -1;
    return panel.getWidgetIndex(selectedTab) - 1;
  }

  /**
   * Gets the number of tabs present.
   * 
   * @return the tab count
   */
  public int getTabCount() {
    return panel.getWidgetCount() - 2;
  }

  /**
   * Gets the specified tab's HTML.
   * 
   * @param index
   *          the index of the tab whose HTML is to be retrieved
   * @return the tab's HTML
   */
  public String getTabHTML(int index) {
    if (index >= getTabCount())
      return null;
    return ((HTML) panel.getWidget(index - 1)).getHTML();
  }

  public void insertTab(String text, boolean asHTML, int beforeIndex) {
    Label item;
    if (asHTML)
      item = new HTML(text);
    else item = new Label(text);
    insertTabInternal(item, beforeIndex);
  }

  /**
   * Inserts a new tab at the specified index.
   * 
   * @param text
   *          the new tab's text
   * @param asHTML
   *          <code>true</code> to treat the specified text as HTML
   * @param beforeIndex
   *          the index before which this tab will be inserted
   */
  public void insertTab(Label item, int beforeIndex) {
    insertTabInternal(item, beforeIndex);
  }

  /**
   * Inserts a new tab at the specified index.
   * 
   * @param text
   *          the new tab's text
   * @param beforeIndex
   *          the index before which this tab will be inserted
   */
  public void insertTab(String text, int beforeIndex) {
    insertTab(text, false, beforeIndex);
  }

  public void onClick(Widget sender) {
    for (int i = 1; i < panel.getWidgetCount() - 1; ++i) {
      if (panel.getWidget(i) == sender) {
        selectTab(i - 1);
        return;
      }
    }
  }

  /**
   * Removes the tab at the specified index.
   * 
   * @param index
   *          the index of the tab to be removed
   */
  public void removeTab(int index) {
    checkTabIndex(index);

    // (index + 1) to account for 'first' placeholder widget.
    Widget toRemove = panel.getWidget(index + 1);
    if (toRemove == selectedTab)
      selectedTab = null;
    panel.remove(toRemove);
  }

  public void removeTabListener(TabListener listener) {
    if (tabListeners != null)
      tabListeners.remove(listener);
  }

  /**
   * Programmatically selects the specified tab.
   * 
   * @param index
   *          the index of the tab to be selected
   * @return <code>true</code> if successful, <code>false</code> if the change
   *         is denied by the {@link TabListener}.
   */
  public boolean selectTab(int index) {
    checkTabIndex(index);

    if (!fireBeforeTabSelected(this, index))
      return false;

    setSelectionStyle(selectedTab, false);
    selectedTab = panel.getWidget(index + 1);
    setSelectionStyle(selectedTab, true);
    fireTabSelected(this, index);
    return true;

  }

  protected void insertTabInternal(Label item, int beforeIndex) {
    if ((beforeIndex < 0) || (beforeIndex > getTabCount()))
      throw new IndexOutOfBoundsException();

    if (!wrapLabels)
      item.setWordWrap(false);
    item.addClickListener(this);
    item.setStyleName(CSS_NAMESPACE + getPrefix() + "TabBarItem");
    panel.insert(item, beforeIndex + 1);
  }

  protected boolean fireBeforeTabSelected(SourcesTabEvents src, int index) {
    if (tabListeners != null) {
      if (!tabListeners.fireBeforeTabSelected(src, index))
        return false;
    }
    return true;
  }

  protected void fireTabSelected(SourcesTabEvents src, int index) {
    if (tabListeners != null)
      tabListeners.fireTabSelected(src, index);
  }

  private void checkTabIndex(int index) {
    if ((index < 0) || (index >= getTabCount()))
      throw new IndexOutOfBoundsException();
  }

  private void setSelectionStyle(Widget item, boolean selected) {
    if (item != null) {
      if (selected)
        item.addStyleName(CSS_NAMESPACE + getPrefix() + "TabBarItem-selected");
      else item.removeStyleName(CSS_NAMESPACE
          + getPrefix()
          + "TabBarItem-selected");
    }
  }

  public void hideTab(int index) {
    checkTabIndex(index);
    setLabelStyle(index, CSS_NAMESPACE + getPrefix() + "TabBarItem-hidden");
  }

  public void showTab(int index) {
    checkTabIndex(index);
    setLabelStyle(index, CSS_NAMESPACE + getPrefix() + "TabBarItem");
  }

  /**
   * Sets the CSS style for the Label at the given index. If the item is
   * selected, the selection is cleared, to avoid problems with -selected
   * styles.
   * 
   * @param index
   * @param style
   */
  protected void setLabelStyle(int index, String style) {
    // (index + 1) to account for 'first' placeholder widget.
    Widget target = panel.getWidget(index + 1);
    if (target == selectedTab)
      selectedTab = null;
    target.setStyleName(style);
  }

  protected void disableEvents(int index) {
    // (index + 1) to account for 'first' placeholder widget.
    Label target = (Label) panel.getWidget(index + 1);
    target.removeClickListener(this);
  }

  protected void enableEvents(int index) {
    // (index + 1) to account for 'first' placeholder widget.
    Label target = (Label) panel.getWidget(index + 1);
    target.addClickListener(this);
  }

  public void disableTab(int index) {
    checkTabIndex(index);
    setLabelStyle(index, CSS_NAMESPACE + getPrefix() + "TabBarItem-disabled");
    disableEvents(index);
  }

  public void enableTab(int index) {
    checkTabIndex(index);
    setLabelStyle(index, CSS_NAMESPACE + getPrefix() + "TabBarItem");
    enableEvents(index);
  }
}
