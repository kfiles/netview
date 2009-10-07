
package com.prodco.netview.server.query;


public enum TopQueryType {
  SRC_IP( "srcips", new TopSrcIpHandler() ), DEST_IP( "destips",
    new TopDestIpHandler() ), IP_PROTO( "proto", new TopIpProtoHandler() ), SRC_PORT(
    "srcports", new TopSrcPortHandler() ), DEST_PORT( "destports",
    new TopDestPortHandler() ), TOS( "tos", new TopTosHandler() );
  // total, talkers, hosts, subnets

  private String text;

  private ResultHandler handler;
  private String descr;

  private TopQueryType ( String descr, ResultHandler handler )
    {
    this.descr = descr;
    this.handler = handler;
    }

  public ResultHandler getHandler ()
    {
    // return (ResultHandler)handler.newInstance();
    return handler;
    }

  public static TopQueryType fromString ( String token )
    {
    for ( TopQueryType t : TopQueryType.values() )
      {
      if ( t.getDescr().equalsIgnoreCase( token ) )
        return t;
      }
    return null;
    }

  public String getDescr ()
    {
    return descr;
    }

}
