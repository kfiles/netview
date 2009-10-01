package com.prodco.preferences.client.remote;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PreferencesRemoteException extends Exception implements IsSerializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int errorCode;

  public PreferencesRemoteException() {
    super();
  }

  public PreferencesRemoteException(String message) {
    super(message);
  }

  public int getErrorCode() {
    return errorCode;
  }
}
