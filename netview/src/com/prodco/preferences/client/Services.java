package com.prodco.preferences.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.prodco.preferences.client.remote.PreferencesRemoteService;
import com.prodco.preferences.client.remote.PreferencesRemoteServiceAsync;


public class Services {
  public static final PreferencesRemoteServiceAsync PREFERENCES = (PreferencesRemoteServiceAsync) GWT
      .create(PreferencesRemoteService.class);

  static {
    ((ServiceDefTarget) PREFERENCES).setServiceEntryPoint(GWT.getModuleBaseURL()
        + "PreferencesRemoteService");
  }
}
