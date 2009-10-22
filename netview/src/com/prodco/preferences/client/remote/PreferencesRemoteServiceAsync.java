
package com.prodco.preferences.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.model.Site;

public interface PreferencesRemoteServiceAsync
  {

  public void findAppTagsByCustomer ( int custId,
    AsyncCallback<List<AppTag>> callback );

  public void saveAppTagForCustomer ( int custId, AppTag tag,
    AsyncCallback<Void> callback );

  public void deleteAppTagForCustomer ( int custId, AppTag tag,
    AsyncCallback<Void> callback );

  public void findSitesByCustomer ( int custId,
    AsyncCallback<List<Site>> callback );

  public void saveSite ( int custId, Site site, AsyncCallback<Void> callback );

  public void deleteSite ( int custId, Site site, AsyncCallback<Void> callback );

  }
