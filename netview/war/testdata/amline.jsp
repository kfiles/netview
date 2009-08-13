<%@page import="com.prodco.netview.server.*,com.prodco.netview.server.query.*,java.util.*,java.text.*" %><%response.setContentType("text/xml"); %><chart>
<%
	int siteId = 1;
int startTime = 1236211200;
int endTime = 1236296700;
DecimalFormat bwFormat = new DecimalFormat("0.#");

XYDataSet data = new FlowDataDAO().getTopResult(siteId, startTime, endTime, TopQueryType.SRC_PORT, 4);
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
</chart>
