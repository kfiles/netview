
package com.prodco.preferences.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.prodco.preferences.client.model.AppTag;

public interface PreferencesRemoteService extends RemoteService
  {

  public List<AppTag> findAppTagsByCustomer ( int custId )
    throws PreferencesRemoteException;

  public List<AppTag> findAppTags () throws PreferencesRemoteException;

  public void saveAppTagForCustomer ( int custId, AppTag tag )
    throws PreferencesRemoteException;

  }
