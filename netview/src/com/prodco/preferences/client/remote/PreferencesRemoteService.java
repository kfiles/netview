package com.prodco.preferences.client.remote;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.prodco.preferences.client.model.AppTag;

public interface PreferencesRemoteService extends RemoteService {

  public List<AppTag> findAppTagsByCustomer(int custId)
      throws PreferencesRemoteException;

  public void saveAppTagForCustomer(int custId, AppTag tag)
  throws PreferencesRemoteException;

}
