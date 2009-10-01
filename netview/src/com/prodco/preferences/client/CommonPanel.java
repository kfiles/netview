package com.prodco.preferences.client;

import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;

public class CommonPanel extends ContentPanel {

  public List<FormBinding> formBindings;

  public LayoutContainer makeFormSection() {
    LayoutContainer main = new LayoutContainer();
    main.setLayout(new ColumnLayout());
    return main;
  }

  public FormPanel makeFormColumn() {
    FormPanel col = new FormPanel();
    FormLayout layout = new FormLayout();
    layout.setLabelAlign(LabelAlign.LEFT);
    col.setLayout(layout);
    col.setHeaderVisible(false);
    col.setFrame(false);
    return col;
  }

  public FormPanel makeFormRow() {
    FormPanel row = new FormPanel();
    row.setLayout(new RowLayout(Orientation.HORIZONTAL));
    row.setHeaderVisible(false);
    row.setFrame(false);
    return row;
  }

  public List<FormBinding> getFormBindings() {
    return formBindings;
  }

  public void setFormBindings(List<FormBinding> formBindings) {
    this.formBindings = formBindings;
  }

}
