
package com.prodco.preferences.client.model;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BeanModelTag;

public class Site implements BeanModelTag, Serializable
  {
  private Integer siteId;
  private String name;
  private String address;
  private String city;
  private String state;
  private String country;
  private String zip;

  public Site ()
    {
    }

  public Site ( Integer siteId, String name, String address, String city,
    String state, String country, String zip )
    {
    this.siteId = siteId;
    this.name = name;
    this.address = address;
    this.city = city;
    this.state = state;
    this.country = country;
    this.zip = zip;
    }

  public Integer getSiteId ()
    {
    return this.siteId;

    }

  public void setSiteId ( int siteId )
    {
    this.siteId = siteId;
    }

  public String getName ()
    {
    return this.name;
    }

  public void setName ( String name )
    {
    this.name = name;
    }

  public String getAddress ()
    {
    return this.address;
    }

  public void setAddress ( String address )
    {
    this.address = address;
    }

  public String getCity ()
    {
    return this.city;
    }

  public void setCity ( String city )
    {
    this.city = city;
    }

  public String getState ()
    {
    return state;
    }

  public void setState ( String state )
    {
    this.state = state;
    }

  public String getCountry ()
    {
    return this.country;
    }

  public void setCountry ( String country )
    {
    this.country = country;
    }

  public String getZip ()
    {
    return this.zip;
    }

  public void setZip ( String zip )
    {
    this.zip = zip;
    }

  }
