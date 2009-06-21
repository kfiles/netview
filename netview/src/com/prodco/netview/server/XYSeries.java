package com.prodco.netview.server;

import java.util.ArrayList;
import java.util.List;

public class XYSeries
  {
  List<String> XVals= new ArrayList<String>();
  List<Number> YVals= new ArrayList<Number>();
  String label;
  
  public void addPoint(String x, Number y) {
    if (x != null && y != null) {
      XVals.add( x );
      YVals.add( y );
    }
  } 
  
  public List<String> getXVals ()
    {
    return XVals;
    }
  public List<Number> getYVals ()
    {
    return YVals;
    }
  public String getLabel ()
    {
    return label;
    }

  public void setLabel ( String label )
    {
    this.label = label;
    }

  
  }

