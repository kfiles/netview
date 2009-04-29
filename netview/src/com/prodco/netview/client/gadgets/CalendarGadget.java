package com.prodco.netview.client.gadgets;

import java.util.Date;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.prodco.netview.client.model.Gadget;
import com.prodco.netview.client.model.GadgetClass;

public class CalendarGadget extends Gadget {
	
	public static class Class extends GadgetClass{
		public Class(){
			super("Calendar",false);
		}
		public Gadget newGadget(){
			return new CalendarGadget(this);
		}
	}
	
	private FlexTable calendar = new FlexTable();
	private static final int DAYS_IN_WEEK = 7;
	private static final int ONE_DAY_MS = 24*60*60*1000;
	private static final String[] DAYS = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	
	protected CalendarGadget( Class c ){
		super( c );
		HorizontalPanel mpanel = new HorizontalPanel();
		initWidget( mpanel );
		mpanel.setWidth("100%");
		mpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mpanel.add(calendar);
		calendar.setStyleName("Calendar");
		
		for( int i=0; i< DAYS_IN_WEEK; ++i ){
			calendar.setText(0,i,DAYS[i]);
			calendar.getCellFormatter().setStyleName(0,i,"Calendar-Label");
		}
		
		//get first day of the month
		Date now = new Date();
		Date firstOfMonth = new Date( now.getYear(), now.getMonth(), 1 );
		Date firstOfNextMonth = new Date( now.getYear(), now.getMonth()+1, 1 );
		Date lastOfMonth = new Date( firstOfNextMonth.getTime()-ONE_DAY_MS );
		Date startLastMonth = new Date( firstOfMonth.getTime()-ONE_DAY_MS*firstOfMonth.getDay() );
		
		//last month
		int row = 1;
		for( int day = 0; day<firstOfMonth.getDay(); ++day ){
			calendar.setText(row,day,Integer.toString(startLastMonth.getDate()+day));
			calendar.getCellFormatter().setStyleName(row,day,"Calendar-OtherMonth");
		}
		
		//this month
		int day = firstOfMonth.getDay();
		for( int date = 0; date < lastOfMonth.getDate(); ++date, ++day ){
			if( day == DAYS_IN_WEEK ){
				row++;
				day = 0;
			}
			calendar.setText(row,day,Integer.toString(date+1));
			calendar.getCellFormatter().setStyleName(row,day,"Calendar-CurrentMonth");
		}
		
		//next month
		for( int date=0; day < DAYS_IN_WEEK; ++day, ++date ){
			calendar.setText(row,day,Integer.toString(date+1));
			calendar.getCellFormatter().setStyleName(row,day,"Calendar-OtherMonth");
		}
		
		//set style for today
		calendar.getCellFormatter().addStyleName((firstOfMonth.getDay()+now.getDate())/7+1,now.getDay(),"Calendar-Today");
	}
}
