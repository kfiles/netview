<%@page import="com.prodco.netview.server.*,com.prodco.netview.server.query.*,java.util.*,java.text.*" %><%response.setContentType("text/xml"); %><chart>
<%
//	int siteId = 1;
//int startTime = 1236211200;
//int startTime =41201280;
//int endTime = 41201350;
//int endTime = 1236211320;
int siteId = Integer.parseInt( request.getParameter("siteId") );
int startTime = Integer.parseInt( request.getParameter("startTc") );
int endTime = Integer.parseInt( request.getParameter("endTc") );
TopQueryType type = TopQueryType.fromString( request.getParameter("type") );
TrafficDir dir = TrafficDir.fromString( request.getParameter("dir") );

DecimalFormat bwFormat = new DecimalFormat("0.#");

XYDataSet data = new FlowDataDAO().getTopResult(siteId, startTime, endTime, type, dir, 4);
if (null == data)
	out.println("No XYDataSet");
else { 
List<String> categories = data.getCategories(); 
if (null == categories || categories.size() == 0)
	out.println("No categories");
else {
%>
<series>
<% int xid=0;
for (String label : categories) {%>
<value xid='<%= xid++ %>'><%= label %></value>
<% } //for %>
</series>
<graphs>
<% int gid=1;
for (XYSeries series : data.getDataSets()) { %>
<graph gid='<%= gid++ %>' title='<%= series.getLabel() %>' text_size="8">
<%   xid=0;
for (Number num : series.getYVals()) { %>
<value xid='<%= xid++ %>'><%= bwFormat.format(num) %></value>
<% } //vals
%></graph>
<% 
  } //series
  } //else
%></graphs>
<%
  } //else %>
  <labels>
    <label lid="0">
      <text><![CDATA[<b>Top <%= type.getDescr() %>, <%= dir.getDescr() %> from site</b>]]></text>
      <y>7</y>
      <text_size>13</text_size>
      <align>center</align>
    </label>
  </labels>
  
</chart>
