
package com.prodco.netview.server.query;

import java.util.Hashtable;
import java.util.List;

import com.prodco.netview.domain.FlowRecord;
import com.prodco.netview.server.AbstractResultHandler;

public class TopSrcPortHandler extends AbstractResultHandler
  implements
    ResultHandler
  {
  @Override
  protected void sumEntries ( final List<FlowRecord> recs,
    Hashtable<String, TopQueryResult<String>> top )
    {
    for ( FlowRecord rec : recs )
      {
      updateTopEntry( top, rec, ""
        + rec.getSrcPort() );
      }
    }

  }
