
package com.prodco.netview.server.query;

import java.util.List;

import com.prodco.netview.domain.FlowRecord;
import com.prodco.netview.server.XYDataSet;

public interface ResultHandler
  {
  public abstract XYDataSet getDataSet ( final List<FlowRecord> recs, final int num);
  }