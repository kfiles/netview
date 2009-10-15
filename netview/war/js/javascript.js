/* Javascript */

/* Swap button image mouse hover or rollover */
function navbar_swapImgRestore() { //v3.0
  var i,x,a=document.navbar_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function navbar_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.navbar_p) d.navbar_p=new Array();
    var i,j=d.navbar_p.length,a=navbar_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.navbar_p[j]=new Image; d.navbar_p[j++].src=a[i];}}
}

function navbar_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=navbar_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function navbar_swapImage() { //v3.0
  var i,j=0,x,a=navbar_swapImage.arguments; document.navbar_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=navbar_findObj(a[i]))!=null){document.navbar_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

/* Toggle a table row's color */
var prevRow = null;
function toggle(it) {
  if (it.className.substring(0, 3) == "sel")
    {it.className = it.className.substring(3, 14);
     prevRow = null;}
  else
    {it.className = "sel" + it.className;
     if (prevRow != null)
       {prevRow.className = prevRow.className.substring(3, 14);}
     prevRow = it;}
}

/* Horizontal Sub-Navigation - show/hide sub-navigation bar */
/* <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script> */
/*
$(document).ready(function() {
	
$("ul#navbar li").hover(function() { //Hover over event on list item
	$(this).css({ 'background' : '#222 url(../images/navbar_bg_trans.png) repeat-x'}); //Add background color + image on hovered list item
	$(this).find("span").show(); //Show the subnav
} , function() { //on hover out...
	$(this).css({ 'background' : 'none'}); //Ditch the background
	$(this).find("span").hide(); //Hide the subnav
});
});
*/

/* Called from Set Site Dynamic Pull-Down Menu Bar when a site is selected. */
function setSite(siteId) {
	/* Call server-side component to set site id */
}

/* Called from Set Profile Dynamic Pull-Down Menu Bar when a profile is selected. */
function setProfile(profileId) {
	/* Call server-side component to set profile id */
}

function selectSubNavBarItem(token) {
	   $("ul#subnavlist li a").removeClass('selected');
	   $("ul#subnavlist li a[href*='#" + token + "']").addClass('selected');
	 }
