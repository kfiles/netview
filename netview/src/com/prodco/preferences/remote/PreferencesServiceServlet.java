
package com.prodco.preferences.remote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.prodco.netview.client.util.ExceptionUtil;
import com.prodco.preferences.client.model.AppTag;
import com.prodco.preferences.client.remote.PreferencesRemoteException;
import com.prodco.preferences.client.remote.PreferencesRemoteService;

public class PreferencesServiceServlet extends RemoteServiceServlet
  implements
    PreferencesRemoteService
  {

  private static final long serialVersionUID = 1L;
  private static Connection conn = null;

  public PreferencesServiceServlet ()
    {
    System.out.println( "Creating PreferencesServiceServlet" );
    connect();
    }

  protected void finalize () throws Throwable
    {
    if ( conn != null )
      {
      try
        {
        conn.close();
        System.out.println( "Database connection terminated" );
        }
      catch ( Exception e )
        { /* ignore close errors */
        }
      }
    }

  private static void connect ()
    {
    try
      {
      String userName = "netview";
      String password = "prodco";
      String url = "jdbc:mysql://localhost:3306/netview";
      Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
      conn = (Connection) DriverManager.getConnection( url, userName, password );
      System.out.println( "Database connection established" );

      }
    catch ( Exception e )
      {
      System.err.println( "Cannot connect to database server" );
      e.printStackTrace();
      }
    // finally
    // {
    // if ( conn != null )
    // {
    // try
    // {
    // conn.close();
    // System.out.println( "Database connection terminated" );
    // }
    // catch ( Exception e )
    // { /* ignore close errors */
    // }
    // }
    // }
    }

  public List<AppTag> findAppTagsByCustomer ( int custId )
    throws PreferencesRemoteException
    {
    System.out.println( "Entering findAppTagsByCustomer" );
    List<AppTag> tags = new ArrayList<AppTag>();

    try
      {
      String sql = "Select a.id,a.priority,a.name,a.descr,a.rule " +
                    "from appl_tag a, appl_prof b " +
                     "where a.app_prof_id=b.id and " +
                    "b.state = 1 and b.cust_id="+custId;

      ResultSet rs = getConnection().createStatement().executeQuery( sql );

      while ( rs.next() )
        {
        AppTag tag = new AppTag();
        tag.setTagId( rs.getInt( "id" ) );
        tag.setTagPref( rs.getInt( "priority" ) );
        tag.setTagName( rs.getString( "name" ) );
        tag.setTagRule( rs.getString( "rule" ) );
        tags.add( tag );
        }
      }
    catch ( SQLException e )
      {
      System.out.println( e.getClass().getSimpleName()
        + " in retrieving flows: " + ExceptionUtil.getMessageOrType( e )
        + " at\n" + ExceptionUtil.getTrace( e ) );
      throw new PreferencesRemoteException( ExceptionUtil.getMessageOrType( e ) );
      }
    catch ( Exception ex )
      {
      throw new PreferencesRemoteException( ExceptionUtil.getMessageOrType( ex ) );

      }

    return tags;
    }

  private static Connection getConnection ()
    {
    return conn;
    }

  public List<AppTag> findAppTags ()
    {
    return null;
    }

  public void saveAppTagForCustomer ( int custId, AppTag tag )
    throws PreferencesRemoteException
    {
    System.out.println( "Entering saveAppTagForCustomer" );
    try
      {
      String sql = "";
      if ( tag.getTagId() != null )
        {
        sql = "update appl_tag set priority="
          + tag.getTagPref() + ", name='" + tag.getTagName()
          + "', rule='" + tag.getTagRule() + "' where id=" + tag.getTagId();
        }
      else
        {
        sql = "insert into appl_tag (app_prof_id,priority,name,descr,rule) values ("
          + "1"
          + ","
          + tag.getTagPref()
          + ",'"
          + tag.getTagName()
          + "','"
          + tag.getTagName()
          + "','"
          + tag.getTagRule() + "')";
        }
      System.out.println( "sql="
        + sql );
      int num_rows = getConnection().createStatement().executeUpdate( sql );
      System.out.println( "total rows updated/inserted = "
        + num_rows );
      }
    catch ( SQLException e )
      {
      System.out.println( e.getClass().getSimpleName()
        + " in retrieving flows: " + ExceptionUtil.getMessageOrType( e )
        + " at\n" + ExceptionUtil.getTrace( e ) );
      throw new PreferencesRemoteException( ExceptionUtil.getMessageOrType( e ) );
      }
    catch ( Exception ex )
      {
      throw new PreferencesRemoteException( ExceptionUtil.getMessageOrType( ex ) );

      }

    }

  public void deleteAppTagForCustomer ( int custId, AppTag tag )
    throws PreferencesRemoteException
    {
    System.out.println( "Entering saveAppTagForCustomer" );
    try
      {
      String sql = "";
      if ( tag.getTagId() != null )
        {
        sql = "delete from appl_tag where id=" + tag.getTagId();
        System.out.println( "sql="
          + sql );
        int num_rows = getConnection().createStatement().executeUpdate( sql );
        System.out.println( "total rows deleted = "
          + num_rows );
        }
      }
    catch ( SQLException e )
      {
      System.out.println( e.getClass().getSimpleName()
        + " in retrieving flows: " + ExceptionUtil.getMessageOrType( e )
        + " at\n" + ExceptionUtil.getTrace( e ) );
      throw new PreferencesRemoteException( ExceptionUtil.getMessageOrType( e ) );
      }
    catch ( Exception ex )
      {
      throw new PreferencesRemoteException( ExceptionUtil.getMessageOrType( ex ) );

      }

    }

  }
