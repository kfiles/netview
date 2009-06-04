
package com.prodco.netview.server;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.prodco.netview.domain.FlowRecord;

public class FlowDataDAO
  {

  SimpleDateFormat format = new SimpleDateFormat("HH:mm");
  
  public XYDataSet getTopPorts ( int siteId, int startTime, int endTime )
    {
    PersistenceManager pm = PersistenceManagerHelper.getInstance()
      .getPersistenceManager();
    String query = "select from "
      + FlowRecord.class.getName() + " where siteId = " + siteId
      + " and timecode>=" + startTime + " and timecode<=" + endTime;
    List<FlowRecord> recs = (List<FlowRecord>) pm.newQuery( query ).execute();

    XYDataSet rv = new XYDataSet();
    return rv;
    }
  }
