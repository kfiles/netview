
package com.prodco.preferences.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.model.Site;

public interface PreferencesRemoteService extends RemoteService
  {

  public List<AppTag> findAppTagsByCustomer ( int custId )
    throws PreferencesRemoteException;

  public void saveAppTagForCustomer ( int custId, AppTag tag )
    throws PreferencesRemoteException;

  public void deleteAppTagForCustomer ( int custId, AppTag tag )
    throws PreferencesRemoteException;

  public List<Site> findSitesByCustomer ( int custId )
    throws PreferencesRemoteException;

  public void saveSite ( int custId, Site site )
    throws PreferencesRemoteException;

  public void deleteSite ( int custId, Site site )
    throws PreferencesRemoteException;

  }
