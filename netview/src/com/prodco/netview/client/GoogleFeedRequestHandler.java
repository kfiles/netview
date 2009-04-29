package com.prodco.netview.client;


public interface GoogleFeedRequestHandler {
	public void onRequestComplete( String result );
	public void onRequestFailed( String error );
}
