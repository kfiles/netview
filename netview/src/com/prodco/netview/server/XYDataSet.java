package com.prodco.netview.server;

import java.util.ArrayList;
import java.util.List;

public class XYDataSet
  {
  List<XYSeries> dataSets = new ArrayList<XYSeries>();
  List<String> categories =null;
  String label;
  
  public void addDataSet(XYSeries dataset) {
    dataSets.add( dataset );
    if (null == categories)
      categories = dataset.getXVals();
//    else {
//      if (categories.size() != dataset.getXVals().size())
//        throw new ArrayIndexOutOfBoundsException("series has more XVals than this dataset");
//    }
  }

  public List<XYSeries> getDataSets ()
    {
    return dataSets;
    }

  public List<String> getCategories ()
    {
    return categories;
    }

  
  }

