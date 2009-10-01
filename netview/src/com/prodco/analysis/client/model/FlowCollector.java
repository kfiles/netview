/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.analysis.client.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class FlowCollector
  {
  // Flow sig fields; extract later
  @PrimaryKey
  @Persistent ( valueStrategy = IdGeneratorStrategy.IDENTITY )
  private Long id;
  @Persistent
  private Long siteId;
  @Persistent
  private String siteName;
  @Persistent
  private Float wanBandwitch;
  @Persistent
  private String srcIp;

  public FlowCollector ()
    {
    }

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

  public String getSiteName ()
    {
    return siteName;
    }

  public void setSiteName ( String siteName )
    {
    this.siteName = siteName;
    }

  public Float getWanBandwitch ()
    {
    return wanBandwitch;
    }

  public void setWanBandwitch ( Float wanBandwitch )
    {
    this.wanBandwitch = wanBandwitch;
    }

  public String getSrcIp ()
    {
    return srcIp;
    }

  public void setSrcIp ( String srcIp )
    {
    this.srcIp = srcIp;
    }

  }
