
package com.prodco.preferences.client.model;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BeanModelTag;

public class AppTag implements BeanModelTag,Serializable
  {
  private int tagId;
  private int tagPref;
  private String tagName;
  private String tagRule;

  public AppTag() {}
  
  public AppTag ( int tagId, int tagPref, String tagName, String tagRule )
    {
    this.tagId = tagId;
    this.tagPref = tagPref;
    this.tagName = tagName;
    this.tagRule = tagRule;
    }

  public Integer getTagId ()
    {
    return this.tagId;

    }

  public void setTagId ( int tagId )
    {
    this.tagId = tagId;
    }

  public Integer getTagPref ()
    {
    return this.tagPref;

    }

  public void setTagPref ( int tagPref )
    {
    this.tagPref = tagPref;
    }

  public String getTagName ()
    {
    return this.tagName;
    }

  public void setTagName ( String tagName )
    {
    this.tagName = tagName;
    }

  public String getTagRule ()
    {
    return this.tagRule;
    }

  public void setTagRule ( String tagRule )
    {
    this.tagRule = tagRule;
    }

  }
