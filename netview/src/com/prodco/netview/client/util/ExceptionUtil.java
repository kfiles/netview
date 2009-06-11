package com.prodco.netview.client.util;


/**
 * An extended Exception implementation that adds severity codes, original
 * exceptions, and extra details. e.g. the SQL query causing the exception. *
 * 
 * This class behavior is implemented in a wrapped ResultMessage.
 * 
 * @author kfiles
 */
public class ExceptionUtil extends Exception
  {
  // Types are obsolete. Instead, use Level as defined in ResultMessage.
  public static final long serialVersionUID = 100100;

  public static String getTrace ( Throwable oException )
    {
    if ( null == oException )
      return "";
    StringBuffer rv = new StringBuffer();
    StackTraceElement[] stack = oException.getStackTrace();
    for ( int i = 0; i < stack.length; i++ )
      {
      rv.append( stack[i].toString()
        + "\n" );
      }
    return ( rv.toString() );
    }

  /**
   * Returns the Throwable message if there is one. Otherwise, returns the Class
   * name.
   * 
   * @param pException
   * @return String representing either the message or the type
   */
  public static String getMessageOrType ( Throwable pException )
    {
    if ( null == pException )
      return "(null Exception)";
    String msg = pException.getMessage();
    if ( null != msg
      && !( 0 == msg.length() ) )
      return msg;
    else return pException.getClass().getName();
    }

  }
