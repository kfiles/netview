
package com.prodco.netview.server;

import java.util.ArrayList;
import java.util.List;

import com.prodco.netview.domain.FlowRecord;

/**
 * @author kfiles
 * 
 * @param
 * <P>
 * The type of the property being compared (e.g. Integer for top port)
 */
public class TopQueryResult<P> implements Comparable<TopQueryResult<P>>
  {
  private P topProp;
  private Long totalPackets = 0l;
  private Long totalBytes = 0l;
  private List<FlowRecord> records = new ArrayList<FlowRecord>();

  public TopQueryResult ( P topProp )
    {
    this.topProp = topProp;
    }

  public P getTopProp ()
    {
    return topProp;
    }

  public long getTotalPackets ()
    {
    synchronized ( this.totalPackets )
      {
      return totalPackets;
      }
    }

  public void setTotalPackets ( long totalPackets )
    {
    synchronized ( this.totalPackets )
      {
      this.totalPackets = totalPackets;
      }
    }

  public void addPackets ( long packets )
    {
    synchronized ( this.totalPackets )
      {
      this.totalPackets = new Long( totalPackets
        + packets );
      }
    }

  public long getTotalBytes ()
    {
    synchronized ( this.totalBytes )
      {
      return totalBytes;
      }
    }

  public void setTotalBytes ( long totalBytes )
    {
    synchronized ( this.totalBytes )
      {
      this.totalBytes = totalBytes;
      }
    }

  public void addBytes ( long bytes )
    {
    synchronized ( this.totalBytes )
      {
      this.totalBytes = new Long( totalBytes
        + bytes );
      }
    }

  public List<FlowRecord> getRecords ()
    {
    synchronized ( this.records )
      {
      return records;
      }
    }

  public void setRecords ( List<FlowRecord> records )
    {
    synchronized ( this.records )
      {
      this.records = records;
      }
    }

  public void addRecord ( FlowRecord rec )
    {
    synchronized ( this.records )
      {
      this.records.add( rec );
      }
    }

  @Override
  public boolean equals ( Object obj )
    {
    if ( this == obj )
      return true;
    if ( obj == null )
      return false;
    if ( getClass() != obj.getClass() )
      return false;
    final TopQueryResult other = (TopQueryResult) obj;
    if ( topProp == null )
      {
      if ( other.topProp != null )
        return false;
      }
    else if ( !topProp.equals( other.topProp ) )
      return false;

    return true;
    }

  public int compareTo ( TopQueryResult<P> obj )
    {
    if ( this == obj )
      return 0;
    if ( obj == null )
      return -1;
    if ( getClass() != obj.getClass() )
      throw new ClassCastException ("Object of type " + obj.getClass().getName() + 
        " cannot be compared to this " + this.getClass().getName());
    final TopQueryResult<P> other = (TopQueryResult<P>) obj;
    return totalBytes.compareTo( other.totalBytes );
    }

  @Override
  public int hashCode ()
    {
    final int prime = 31;
    int result = 1;
    result = prime
      * result + ( ( topProp == null ) ? 0 : topProp.hashCode() );
    return result;
    }

  }
