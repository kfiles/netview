
package com.prodco.preferences.client.model;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class AppTag implements BeanModelTag 
  {
  private int tagPref;
  private String tagName;
  private String tagRule;

  public AppTag() {}
  
  public AppTag ( int tagPref, String tagName, String tagRule )
    {
    this.tagPref = tagPref;
    this.tagName = tagName;
    this.tagRule = tagRule;
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
