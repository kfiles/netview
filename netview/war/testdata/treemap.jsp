<%@page
	import="com.prodco.netview.server.*,java.util.*,org.json.simple.JSONObject"%>
<%
	response.setContentType("text/json");
%>
[
<%
	int siteId = 1;
	int startTime = 1236211200;
	int endTime = 1236296700;
	List<JSONObject> data = new FlowDataDAO().getTreemapJsonSet(siteId,
			startTime, endTime, 4);
	if (null == data)
		out.println("No XYDataSet");
	else {
		int totalRecords = data.size();
		System.out.println(totalRecords	+ " records found :");
		if (totalRecords == 0)
			out.println("No categories");
		else {

	for (JSONObject obj : data) {
	System.out.println(obj);
%>
<%=obj%>,
<%
	} //for
	}  //else
	} //else
%>
]


