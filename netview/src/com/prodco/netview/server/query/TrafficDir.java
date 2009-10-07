
package com.prodco.netview.server.query;

public enum TrafficDir {
  INBOUND( "inbound" ), OUTBOUND( "outbound" );

  private String descr;

  private TrafficDir ( String descr )
    {
    this.descr = descr;
    }

  public String getDescr ()
    {
    return descr;
    }

  public static TrafficDir fromString ( String token )
    {
    for ( TrafficDir t : TrafficDir.values() )
      {
      if ( t.getDescr().equalsIgnoreCase( token ) )
        return t;
      }
    return null;
    }

}
