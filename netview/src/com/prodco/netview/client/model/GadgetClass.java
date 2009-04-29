package com.prodco.netview.client.model;

import java.util.ArrayList;
import java.util.List;


public abstract class GadgetClass {
	private String name;
	private boolean refreshable;
	private ArrayList userprefs = new ArrayList();

	private static List classes = new ArrayList();
	public static void addClass( GadgetClass gclass ){
		classes.add( gclass );
	}
	
	public static List getClasses(){
		return classes;
	}
	
	public GadgetClass( String n, boolean r ){ 
		name=n; 
		refreshable = r;
	}
	
	public abstract Gadget newGadget();
	public boolean isRefreshable(){ 
		return refreshable; 
	}
	public String getName(){
		return name;
	}
	
	public int getUserPrefsCount(){
		return userprefs.size();
	}
	public UserPref getUserPref( int pos ){
		return (UserPref)userprefs.get(pos);
	}
	protected void addUserPref( UserPref up ){
		userprefs.add( up );
	}
}
