<%@page import="com.prodco.netview.server.*,java.util.*" %><%response.setContentType("text/xml"); %><chart canvasBgColor='000000' bgColor='111111' XAxisName='' palette='2' animation='1' formatNumberScale='0' labelStep='2' labelDisplay='Rotate' slantLabels='1' showValues='0' YAxisName='' seriesNameInToolTip='1' baseFontColor='66DD66' showLegend='0' shownames='0'>
<% 
int siteId = 1;
int startTime = 1236211200;
int endTime = 1236296700;
XYDataSet data = new FlowDataDAO().getTopSrcPorts(siteId, startTime, endTime, 4);
if (null == data)
	out.println("No XYDataSet");
else { 
List<String> categories = data.getCategories(); 
if (null == categories || categories.size() == 0)
	out.println("No categories");
else {
	%>
<categories fontSize='4'>
<% for (String label : categories) {%>
<category label='<%= label %>'/>
<% } //for %>
</categories>
<% for (XYSeries series : data.getDataSets()) { %>
<dataset seriesname='<%= series.getLabel() %>'>
<%   for (Number num : series.getYVals()) { %>
<set value='<%= num %>' />
</dataset>
<% } //vals
  } //series
  } //else
  } //else %>
<styles><definition><style type='font' name='CaptionFont' size='15' color='66FF66' /><style type='font' name='SubCaptionFont' color='66EE66' bold='0' />
</definition>
<application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application>
</styles>
</chart>