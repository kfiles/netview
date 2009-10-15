
package com.prodco.preferences.client;

import com.google.gwt.user.client.ui.Widget;

public enum PrefEditorType
  {
  POLICIES( "policies", new PolicyEditor() ),
  APPS( "apps", new TagEditor() ),
  PROFILES( "profiles", new ProfileEditor() ),
  USERS( "users", new UserEditor() ),
  ALARMS( "alarms", new AlarmEditor() ),
  SITES( "sites", new SiteEditor() ),
  AGENTS( "agents", new AgentEditor() );

  private String text;

  private Widget editor;

  private PrefEditorType ( String text, Widget editor )
    {
    this.text = text;
    this.editor = editor;
    }

  public String getDescr ()
    {
    return text;
    }

  public static PrefEditorType fromString ( String token )
    {
    for ( PrefEditorType t : PrefEditorType.values() )
      {
      if ( t.getDescr().equalsIgnoreCase( token ) )
        return t;
      }
    return null;
    }

  public Widget newInstance ()
    {
    return editor;
    }

  }
