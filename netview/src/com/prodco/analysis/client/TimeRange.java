
package com.prodco.analysis.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class TimeRange
  {
  private int startTc;
  private int endTc;
  // private static final long OFFSET = 604800;
  private static final long TC_PER_DAY = 2880;
  private static final long MULTIPLIER = 30l * 1000l;

  public TimeRange ( int startTc, int endTc )
    {
    super();
    this.startTc = startTc;
    this.endTc = endTc;
    }

  String getDateLong ()
    {
    long curTc = new Date().getTime()
      / MULTIPLIER;
    curTc = curTc
      / TC_PER_DAY * TC_PER_DAY;
    long startDay = startTc
      / TC_PER_DAY * TC_PER_DAY;
    long endDay = endTc
      / TC_PER_DAY * TC_PER_DAY;
    Date startDate = new Date( ( curTc
      - startDay + (long) startTc )
      * MULTIPLIER );
    Date endDate = new Date( ( curTc
      - endDay + (long) endTc )
      * MULTIPLIER );
    DateTimeFormat fmt = DateTimeFormat.getFormat( "EEEE, MMMM dd, yyyy" );
    DateTimeFormat fmtTime = DateTimeFormat.getFormat( "HH:mm" );
    String time = fmtTime.format( startDate )
      + " - " + fmtTime.format( endDate );
    String dur = " ("
      + (long) ( endTc - startTc ) / 2 + " min)";
    return fmt.format( startDate )
      + " " + time + dur;
    }

  public int getStartTc ()
    {
    return startTc;
    }

  public void setStartTc ( int startTc )
    {
    this.startTc = startTc;
    }

  public int getEndTc ()
    {
    return endTc;
    }

  public void setEndTc ( int endTc )
    {
    this.endTc = endTc;
    }
  }
