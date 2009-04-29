package com.prodco.netview.client.model;

public class UserPref 
{
	private Object defaultvalue;
	private String name;
	public UserPref( String n, Object d ){
		defaultvalue = d;
		name = n;
	}
	public String getName(){
		return name;
	}
	public Object getDefaultValue(){
		return defaultvalue;
	}
}
