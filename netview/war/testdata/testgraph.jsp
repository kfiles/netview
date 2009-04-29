<%response.setContentType("text/xml"); %><chart caption='Top Source Addresses, last 60 minutes' canvasBgColor='000000' bgColor='111111' XAxisName='' palette='2' animation='1' formatNumberScale='0' labelStep='2' labelDisplay='Rotate' slantLabels='1' showValues='0' YAxisName='Avg Rate in Kbps' seriesNameInToolTip='1' baseFontColor='66DD66'>
<categories>
<category label='04:00'/>
<category label='04:05'/>
<category label='04:10'/>
<category label='04:15'/>
<category label='04:20'/>
<category label='04:25'/>
<category label='04:30'/>
<category label='04:35'/>
<category label='04:40'/>
<category label='04:45'/>
<category label='04:50'/>
<category label='04:55'/>
<category label='05:00'/>
</categories>
<dataset seriesname='10.0.1.1'>
<set value='256' />
<set value='301' />
<set value='200' />
<set value='223' />
<set value='305' />
<set value='365' />
<set value='276' />
<set value='109' />
<set value='316' />
<set value='109' />
<set value='223' />
<set value='301' />
<set value='200' />
</dataset>
<dataset seriesName='10.2.30.2'>
<set value='174'/>
<set value='197'/>
<set value='55'/>
<set value='15'/>
<set value='66'/>
<set value='85'/>
<set value='37'/>
<set value='10'/>
<set value='44'/>
<set value='322'/>
<set value='197'/>
<set value='55'/>
<set value='37'/>
</dataset>
<styles><definition><style type='font' name='CaptionFont' size='15' color='66FF66' /><style type='font' name='SubCaptionFont' color='66EE66' bold='0' />
</definition>
<application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application>
</styles>
</chart>