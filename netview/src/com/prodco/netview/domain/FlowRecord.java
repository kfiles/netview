/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.domain;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class FlowRecord
  {
  //Flow sig fields; extract later
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
  @Persistent
  private Long siteId;
  @Persistent
  private String srcIntf;
  @Persistent
  private String vlanName;
  @Persistent
  private int vlanTag;
  @Persistent
  private Long appId;
  @Persistent
  private String appName;
  @Persistent
  private String srcIp;
  @Persistent
  private String destIp;
  @Persistent
  private int ipProto;
  @Persistent
  private int srcPort;
  @Persistent
  private int destPort;
  @Persistent
  private int ipTos;
  
  //Currently all null
  @Persistent
  private int tcpFlags; //will be an enumset
  @Persistent
  private int destAS;
  @Persistent
  private int timecode;
  @Persistent
  private String timestampStart;
  @Persistent
  private String timestampEnd;
  //Should be longs, but that's not compatible with javascript
  @Persistent
  private long packetCount;
  @Persistent
  private long byteCount;
  
  
  public FlowRecord() {}


  public Long getId ()
    {
    return id;
    }


  public void setId ( Long id )
    {
    this.id = id;
    }


  public Long getSiteId ()
    {
    return siteId;
    }


  public void setSiteId ( Long siteId )
    {
    this.siteId = siteId;
    }


  public String getSrcIntf ()
    {
    return srcIntf;
    }


  public void setSrcIntf ( String srcIntf )
    {
    this.srcIntf = srcIntf;
    }


  public Long getAppId ()
    {
    return appId;
    }


  public void setAppId ( Long appId )
    {
    this.appId = appId;
    }


  public String getSrcIp ()
    {
    return srcIp;
    }


  public void setSrcIp ( String srcIp )
    {
    this.srcIp = srcIp;
    }


  public String getDestIp ()
    {
    return destIp;
    }


  public void setDestIp ( String destIp )
    {
    this.destIp = destIp;
    }


  public int getIpProto ()
    {
    return ipProto;
    }


  public void setIpProto ( int ipProto )
    {
    this.ipProto = ipProto;
    }


  public int getSrcPort ()
    {
    return srcPort;
    }


  public void setSrcPort ( int srcPort )
    {
    this.srcPort = srcPort;
    }


  public int getDestPort ()
    {
    return destPort;
    }


  public void setDestPort ( int destPort )
    {
    this.destPort = destPort;
    }


  public int getIpTos ()
    {
    return ipTos;
    }


  public void setIpTos ( int ipTos )
    {
    this.ipTos = ipTos;
    }


  public int getTcpFlags ()
    {
    return tcpFlags;
    }


  public void setTcpFlags ( int tcpFlags )
    {
    this.tcpFlags = tcpFlags;
    }


  public int getDestAS ()
    {
    return destAS;
    }


  public void setDestAS ( int destAS )
    {
    this.destAS = destAS;
    }


  public int getTimecode ()
    {
    return timecode;
    }


  public void setTimecode ( int timecode )
    {
    this.timecode = timecode;
    }


  public String getTimestampStart ()
    {
    return timestampStart;
    }


  public void setTimestampStart ( String timestampStart )
    {
    this.timestampStart = timestampStart;
    }


  public String getTimestampEnd ()
    {
    return timestampEnd;
    }


  public void setTimestampEnd ( String timestampEnd )
    {
    this.timestampEnd = timestampEnd;
    }


  public long getPacketCount ()
    {
    return packetCount;
    }


  public void setPacketCount ( long packetCount )
    {
    this.packetCount = packetCount;
    }


  public long getByteCount ()
    {
    return byteCount;
    }


  public void setByteCount ( long byteCount )
    {
    this.byteCount = byteCount;
    }


  public String getVlanName ()
    {
    return vlanName;
    }


  public void setVlanName ( String vlanName )
    {
    this.vlanName = vlanName;
    }


  public int getVlanTag ()
    {
    return vlanTag;
    }


  public void setVlanTag ( int vlanTag )
    {
    this.vlanTag = vlanTag;
    }


  public String getAppName ()
    {
    return appName;
    }


  public void setAppName ( String appName )
    {
    this.appName = appName;
    }
  
  
  }
