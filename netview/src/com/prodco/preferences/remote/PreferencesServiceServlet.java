
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
      String userName = "prodco";
      String password = "prodco";
      String url = "jdbc:mysql://localhost:3306/prodco";
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
      String sql = "Select app_tag_id,tag_pref,tag_name,tag_rule "
        + " from customer_application_tag where cust_id=" + custId;
      ResultSet rs = getConnection().createStatement().executeQuery( sql );

      while ( rs.next() )
        {
        AppTag tag = new AppTag();
        tag.setTagId( rs.getInt( "app_tag_id" ) );
        tag.setTagPref( rs.getInt( "tag_pref" ) );
        tag.setTagName( rs.getString( "tag_name" ) );
        tag.setTagRule( rs.getString( "tag_rule" ) );
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
        sql = "update customer_application_tag set tag_pref="
          + tag.getTagPref() + ", tag_name='" + tag.getTagName()
          + "', tag_rule='" + tag.getTagRule() + "' where cust_id=" + custId
          + " and app_tag_id=" + tag.getTagId();
        }
      else
        {
        sql = "insert into customer_application_tag (cust_id,tag_pref,tag_name,tag_rule) values ("
          + custId
          + ","
          + tag.getTagPref()
          + ",'"
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
        sql = "delete from customer_application_tag where cust_id="
          + custId + " and app_tag_id=" + tag.getTagId();
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
