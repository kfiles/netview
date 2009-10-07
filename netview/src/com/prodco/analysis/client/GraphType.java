
package com.prodco.analysis.client;

public enum GraphType {
  APP( "apps" ), ADDRESSES( "addresses" ), IP_PROTO( "proto" ), PORTS( "ports" ), TOS(
    "tos" ), SUBNETS( "subnets" ), HOSTS( "hosts" ), TOTAL( "total" ), TALKERS(
    "talker" ), SRC_IPS( "srcips" ), DEST_IPS( "destips" ), SRC_PORTS(
    "srcports" ), DEST_PORTS( "destports" );

  private String text;

  private GraphType ( String text )
    {
    this.text = text;
    }

  public String getDescr ()
    {
    return text;
    }

  public static GraphType fromString ( String token )
    {
    for ( GraphType t : GraphType.values() )
      {
      if ( t.getDescr().equalsIgnoreCase( token ) )
        return t;
      }
    return null;
    }

}
