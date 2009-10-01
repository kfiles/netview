
package com.prodco.preferences.remote;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.remote.PreferencesRemoteException;
import com.prodco.preferences.client.remote.PreferencesRemoteService;

public class PreferencesServiceServlet extends RemoteServiceServlet
  implements
    PreferencesRemoteService
  {

  public PreferencesServiceServlet ()
    {
    System.out.println( "Creating YaltaServiceServlet" );
    }

  public List<AppTag> findAppTagsByCustomer ( int custId )
    throws PreferencesRemoteException
    {

    System.out.println( "Entering findAppTagsByCustomer" );
    List<AppTag> tags = new ArrayList<AppTag>();
    AppTag tag = new AppTag( 1, "HTTP", "port=80" );
    tags.add( tag );
    tag = new AppTag( 2, "FTP", "port=23" );
    tags.add( tag );
    tag = new AppTag( 3, "SFTP", "port=23" );
    tags.add( tag );
    tag = new AppTag( 4, "ORACLE", "port=23" );
    tags.add( tag );
    tag = new AppTag( 5, "SCP", "port=23" );
    tags.add( tag );

    System.out.println( "Returning tags with "
      + tags.size() + " tags" );

    return tags;
    }

  public List<AppTag> findAppTags ()
    {
    return null;
    }

  public void saveAppTagForCustomer ( int custId, AppTag tag )
    throws PreferencesRemoteException
    {
    }

  }
