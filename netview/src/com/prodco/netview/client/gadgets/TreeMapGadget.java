package com.prodco.netview.client.gadgets;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class TreeMapGadget extends Gadget {
	
  HorizontalPanel mpanel = new HorizontalPanel();

  public static class Class extends GadgetClass{
		public Class(){
			super("TreeMap",false);
		}
		public Gadget newGadget(){
			return new TreeMapGadget(this);
		}
	}
	
	
	protected TreeMapGadget( Class c ){
		super( c );
		
		initWidget( mpanel );
		mpanel.setWidth("100%");
		mpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		mpanel.add(addRadialGraph());
		DeferredCommand.addCommand(new Command() {

      public void execute() {
        mpanel.add(buildTreeMap());
        
      }});
	}

//	@Override
//  public void refresh() {
//    super.refresh();
//    mpanel.clear();
//    mpanel.add(addRadialGraph());
//  }


  Widget buildTreeMap() {
    HTML radial = new HTML(
        "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab'version=7,0,0,0' " +
        " width='600' height='400' id='circleIP' align='middle'>" +
   "<param name='allowScriptAccess' value='sameDomain' />"+
   "<param name='movie' value='pkgmap/PackageMap.swf' />" +
   "<param name='quality' value='high' />" +
   "<param name='bgcolor' value='#000000' />" +
   "<embed src='pkgmap/PackageMap.swf' quality='high' bgcolor='#000000' width='600' height='400' name='circlIP' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />" +
   "</object>");
    return radial;
  }
}
