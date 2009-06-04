package com.prodco.netview.server;

import java.util.List;

public class XYSeries
  {
  List<String> XVals;
  List<Number> YVals;
  String label;
  
  public void addPoint(String x, Number y) {
    XVals.add( x );
    YVals.add( y );
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

