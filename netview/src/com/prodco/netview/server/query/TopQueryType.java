package com.prodco.netview.server.query;

public enum TopQueryType {
  SRC_IP( new TopSrcIpHandler() ),
  DEST_IP( new TopDestIpHandler() ),
  IP_PROTO( new TopIpProtoHandler() ),
  SRC_PORT( new TopSrcPortHandler() ),
  DEST_PORT( new TopDestPortHandler() ),
  TOS( new TopTosHandler() );

  private ResultHandler handler;
  
  private TopQueryType (ResultHandler handler)
    {
    this.handler = handler;
    }
  
  public ResultHandler getHandler ()
    {
//    return (ResultHandler)handler.newInstance();
    return handler;
    }
}
