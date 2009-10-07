
package com.prodco.analysis.client;

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
}
