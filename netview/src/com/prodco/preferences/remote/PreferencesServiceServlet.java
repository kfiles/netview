package com.prodco.preferences.remote;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.remote.PreferencesRemoteException;
import com.prodco.preferences.client.remote.PreferencesRemoteService;

public class PreferencesServiceServlet implements PreferencesRemoteService  {


  public PreferencesServiceServlet() {
	  System.out.println("Creating YaltaServiceServlet");
  }

  public void init(ServletConfig config) throws ServletException {
}

  public List<AppTag> findAppTagsByCustomer(int custId)
      throws PreferencesRemoteException {
      //Connect to mysql and get all the tags for this customer.

      return null;
  }

  public void saveAppTagForCustomer(int custId, AppTag tag)
      throws PreferencesRemoteException {
  }

}
