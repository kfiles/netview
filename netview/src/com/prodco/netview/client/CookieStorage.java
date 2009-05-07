package com.prodco.netview.client;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;

public class CookieStorage extends Storage{
	private static final char DELIMITER = 255;
	
	private static class DelimitedStringSerializer{
		StringBuffer buffer = new StringBuffer();
		public void writeString( String value ){
			buffer.append(value);
			buffer.append(DELIMITER);
		}		
		public void writeInt( int value )
		{
			writeString(Integer.toString(value));
		}
		public String toString(){ return buffer.toString(); }
	}
	
	private static class DelimitedStringDeserializer{
		int position = 0;
		int nextPosition;
		String value;
		
		public DelimitedStringDeserializer( String value ){
			this.value = value;
			nextPosition = value.indexOf( DELIMITER, position );
		}
		
		public String readString(){
			String stringValue = value.substring(position,nextPosition);
			position = nextPosition + 1;
			nextPosition = value.indexOf( DELIMITER, position );
			return stringValue;
		}
		
		public int readInt(){
			String string = readString();
			return Integer.parseInt( string );
		}
		
		public boolean hasMore(){ return nextPosition != -1; }
	}	
	
	public CookieStorage(){
		super();
	}
	
	protected String getValuesAsString(){
		//serialize the values to a string
		DelimitedStringSerializer serializer = new DelimitedStringSerializer();
		serializer.writeInt( getValues().size() );

		Iterator iter = getValues().keySet().iterator();
		Map values = getValues(); 
		while( iter.hasNext() ){
			String key = (String) iter.next();
			serializer.writeString( key );
			serializer.writeString( (String)values.get(key));
		}
		return serializer.toString();
	}
	
	protected void setValuesFromString( String valueString ){
		getValues().clear();
		if( valueString.length() > 0 ){
			DelimitedStringDeserializer desrializer = new DelimitedStringDeserializer(valueString);
			if( desrializer.hasMore() ){
				int size = desrializer.readInt();
				for( int i = 0; i < size && desrializer.hasMore(); ++i ){
					getValues().put(desrializer.readString(), desrializer.readString());
				}
			}
		}
	}
	
	private static class CookieConstants{
		public String cookiePrefix(){ return "_cs"; }
		public int maxCookies(){ return 20; }
		public int maxCookieLength(){ return 4096; }
		public int maxTotalStorage(){ return maxCookies()*maxCookieLength(); }
	}
	
	private static class CookieConstantsIE6 extends CookieConstants{
		public int maxCookies(){ return 1; }
	}
	
	private static CookieConstants constants = (CookieConstants)GWT.create( CookieConstants.class );
	
	public int getTotalStorage(){ return constants.maxTotalStorage(); }
	
	public void save() throws StorageException{
		//clear cookies 
		Date now = new Date();
		Date expires = new Date( now.getYear(), now.getMonth()-1, now.getDate() );
		for( int i=0; i<constants.maxCookies(); ++i )
			Cookies.setCookie( constants.cookiePrefix()+i, "", expires );
		
		//get the values as a string and check their length
		String valueString = getValuesAsString();
		if( valueString.length() > constants.maxTotalStorage() ){
			throw new StorageException( "Out of storage space");
		}
		
		//set the expire date to 30 days
		expires = new Date( now.getYear(), now.getMonth()+1, now.getDate() );
		
		//set the cookies in chunks
		int cookiesRequired = ((int)valueString.length()/constants.maxCookieLength())+1;
		for( int cookieNum = 0; cookieNum < cookiesRequired; ++ cookieNum ){
			int begin = cookieNum*constants.maxCookieLength();
			int end = Math.min( begin + constants.maxCookieLength(), valueString.length() );
			String cookieValue = valueString.substring( begin, end );
			Cookies.setCookie( constants.cookiePrefix()+cookieNum, cookieValue, expires );
		}
	}
	
	public void load(){	
		//read the cookies to build the string
		String valueString = "";
		String cookieValue = Cookies.getCookie( constants.cookiePrefix()+"0" );
		for( int cookieNum = 0; cookieValue != null && cookieValue.length() > 0;  ){
			valueString += cookieValue;
			++cookieNum;
			cookieValue = Cookies.getCookie( constants.cookiePrefix()+cookieNum );
		}
		
		//set up the hash map from the string
		setValuesFromString( valueString );
	}

}
