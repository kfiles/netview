package com.prodco.netview.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.*;
import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class DesktopView extends Composite implements DockableListener {
	
  private HorizontalPanel gadgetPanel = new HorizontalPanel();
  private final DesktopViewListener listener;
  public static boolean editable;
	
	public DesktopView( DesktopViewListener ls, boolean editable ){
		this.listener = ls;
		this.editable = editable;
		
		//initialize the main panel
		VerticalPanel mainPanel = new VerticalPanel();
		initWidget( mainPanel );
		mainPanel.setWidth("100%");
		mainPanel.setHeight("100%");

		//create the menu
		HorizontalPanel gadgetsMenu = new HorizontalPanel();
		gadgetsMenu.setStyleName("GadgetsMenu");
		mainPanel.add( gadgetsMenu );
		mainPanel.setCellHorizontalAlignment( 
				gadgetsMenu, HasHorizontalAlignment.ALIGN_CENTER );

		//add gadgets
		List classes = GadgetClass.getClasses();
		if (editable) {
      gadgetsMenu.add(new Label("Add Gadget: "));
      for (int i = 0; i < classes.size(); ++i) {
        final GadgetClass gadgetClass = (GadgetClass) classes.get(i);
        Hyperlink gadgetLink = new Hyperlink(gadgetClass.getName(), "");
        gadgetLink.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            insertGadget(gadgetClass.newGadget());
          }
        });

        gadgetsMenu.add(gadgetLink);
      }
    }
		//create the tab panel
		mainPanel.setCellHeight(gadgetPanel, "100%");
    gadgetPanel = createPage();
		gadgetPanel.setWidth("1000px");
		gadgetPanel.setHeight("100%");
    mainPanel.add( gadgetPanel);

	}
	public HorizontalPanel createPage(){
		HorizontalPanel page = new HorizontalPanel();
		page.setStyleName("GadgetPage");
		page.setWidth("100%");
		page.setHeight("100%");
		page.setVerticalAlignment( HasVerticalAlignment.ALIGN_TOP );
		createColumn(page,0);
		createColumn(page,1);
		createColumn(page,2);
		return page;
	}

	public void createColumn( HorizontalPanel page, int columnNumber ){
		//create the column with a FlowPanel 
		FlowPanel column = new FlowPanel();
	  	column.setStyleName("GadgetColumn");
		page.add( column );
		page.setCellWidth( column, "33%");
		page.setCellHeight( column, "100%");
		column.setHeight("100%");
		DockableWidget.addContainer( column );
	}

	public HorizontalPanel getCurrentPage(){
		return gadgetPanel;
	}
	
	public void insertGadget( Gadget gadget ){
		FlowPanel column = (FlowPanel)getCurrentPage().getWidget(0);
		insertGadget( column, gadget );	
		gadget.setGadgetListener(listener);
		listener.onInterfaceChange();
	}
	protected void insertGadget( FlowPanel column, Gadget gadget ){
		GadgetContainerView gadgetContainer = new GadgetContainerView( gadget );
		DockableWidget dw = new DockableWidget( gadgetContainer );
		column.add( dw  );	
		dw.addDockableListener( this );
	}
	public void setLayoutFromString(String layout) {
		List classes = GadgetClass.getClasses();
		Document document = XMLParser.parse( layout );
		Element element = document.getDocumentElement();
		NodeList pages = element.getElementsByTagName("Page");
		for( int i = 0; i < pages.getLength(); i++ ){
			HorizontalPanel page = getCurrentPage();
			Element pageElement = (Element)pages.item(i);
			NodeList columns = pageElement.getElementsByTagName("Column");
			for( int j = 0; j < columns.getLength(); j++ ){
				FlowPanel column = (FlowPanel)page.getWidget(j);
				Element columnElement = (Element)columns.item(j);
				NodeList gadgets = columnElement.getElementsByTagName("Gadget");
				for( int k=0; k < gadgets.getLength(); ++k ){
					Element gadgetElement = (Element)gadgets.item(k);
					String name = gadgetElement.getAttribute("name");
					for( int l=0; l< classes.size();++l){
						GadgetClass gc = (GadgetClass)classes.get(l);
						if( name.compareTo( gc.getName() ) == 0 ){
							Gadget gadget = gc.newGadget();
							gadget.fromXML( gadgetElement );
							insertGadget( column, gadget );
							l = classes.size();
						}
					}
				}
			}
		}		
	}
	
	public String getLayoutAsString() {
		Document document = XMLParser.createDocument();
		Element element = document.createElement("Desktop");
		document.appendChild( element );
			Element pageElement = element.getOwnerDocument().createElement("Page");
			element.appendChild(pageElement);
			HorizontalPanel page = getCurrentPage();
			int columnCount = page.getWidgetCount();
			for( int j=0; j < columnCount; j++ ){
				FlowPanel column = (FlowPanel)page.getWidget(j);
				Element columnElement = element.getOwnerDocument().createElement("Column");
				pageElement.appendChild(columnElement);
				int gadgetCount = column.getWidgetCount();
				for( int k=0; k < gadgetCount; ++k ){
					Element gadgetElement = element.getOwnerDocument().createElement("Gadget");
					columnElement.appendChild(gadgetElement);
					DockableWidget dockacbleWidget = (DockableWidget)column.getWidget(k);
					GadgetContainerView gadgetContainer = (GadgetContainerView)dockacbleWidget.getWidget();
					gadgetContainer.getGadget().toXML( gadgetElement );
				}
			}
		return document.toString();
	}
	public void onDocked(Widget widget) {
		listener.onInterfaceChange();
		
	}

}
