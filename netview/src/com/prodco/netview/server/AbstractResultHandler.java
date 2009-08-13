package com.prodco.netview.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.prodco.netview.domain.FlowRecord;
import com.prodco.netview.server.query.TopQueryResult;

public abstract class AbstractResultHandler
  {
  SimpleDateFormat labelFormat = new SimpleDateFormat( "HH:mm" );

  public XYDataSet getDataSet ( final List<FlowRecord> recs, final int num)
    {
    XYDataSet rv = new XYDataSet();
    Hashtable<String, TopQueryResult<String>> top = new Hashtable<String, TopQueryResult<String>>();
    sumEntries( recs, top );
    TreeSet<TopQueryResult<String>> sorted = new TreeSet<TopQueryResult<String>>();
    Enumeration<TopQueryResult<String>> ii = top.elements();
    while ( ii.hasMoreElements() )
      {
      sorted.add( ii.nextElement() );
      }
    Iterator<TopQueryResult<String>> jj = sorted.iterator();
    for ( int i = 0; i < num
      && jj.hasNext(); i++ )
      {
      rv.addDataSet( createEntrySeries( jj.next() ) );
      }
    return rv;
    }

  protected abstract void sumEntries(List<FlowRecord> recs, Hashtable<String, TopQueryResult<String>> top);
  
  protected void updateTopEntry (
    Hashtable<String, TopQueryResult<String>> top, FlowRecord rec, String topProp )
    {
    TopQueryResult<String> entry = top.get( topProp );
    if ( null == entry )
      entry = new TopQueryResult<String>( topProp );
    entry.addBytes( rec.getByteCount() );
    entry.addPackets( rec.getPacketCount() );
    entry.addRecord( rec );
    top.put( topProp, entry );
    }

  protected XYSeries createEntrySeries ( TopQueryResult<String> entry )
    {
    XYSeries series = new XYSeries();
    series.setLabel( entry.getTopProp() );
    for ( FlowRecord rec : entry.getRecords() )
      {
      float kbps = rec.getByteCount() * 8 / 1000 / 30;
      series.addPoint( labelFormat.format( new Date( 30000l * rec
        .getTimecode() ) ), kbps);
      }
    return series;
    }

  }
