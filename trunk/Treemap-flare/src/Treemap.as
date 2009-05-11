package {
/*  	import br.com.stimuli.string.printf;
	
	import flare.display.DirtySprite;
	import flare.display.TextSprite;
	import flare.query.methods.eq;
	import flare.query.methods.neq;
	import flare.util.Colors;
	import flare.vis.Visualization;
	import flare.vis.controls.ClickControl;
	import flare.vis.controls.HoverControl;
	import flare.vis.data.Data;
	import flare.vis.data.DataSprite;
	import flare.vis.data.EdgeSprite;
	import flare.vis.data.NodeSprite;
	import flare.vis.data.Tree;
	import flare.vis.events.SelectionEvent;
	import flare.vis.operator.encoder.PropertyEncoder;
	import flare.vis.operator.label.RadialLabeler;
	import flare.vis.operator.layout.CircleLayout;
	
	import flash.geom.Rectangle;
	import flash.text.TextFormat;
 */ 
	import flare.vis.legend.Legend;


	import flash.display.Sprite;
	import flare.display.TextSprite;
	import flare.query.methods.eq;
	import flare.query.methods.fn;
	import flare.util.Shapes;
	import flare.util.Strings;
	import flare.vis.Visualization;
	import flare.vis.controls.ClickControl;
	import flare.vis.controls.HoverControl;
	import flare.vis.controls.TooltipControl;
	import flare.vis.data.Data;
	import flare.vis.data.NodeSprite;
	import flare.vis.data.Tree;
	import flare.vis.events.SelectionEvent;
	import flare.vis.events.TooltipEvent;
	import flare.vis.operator.encoder.PropertyEncoder;
	import flare.vis.operator.label.Labeler;
	import flare.vis.operator.layout.TreeMapLayout;
/* 	import flare.widgets.ProgressBar;
 */	
	import flash.display.StageQuality;
	import flash.filters.DropShadowFilter;
	import flash.geom.Rectangle;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.text.TextFormat;

	import flash.events.Event;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;


	[SWF(width="800", height="600", backgroundColor="#000000", frameRate="30")]
	public class Treemap extends Sprite
	{
		
		private static var _tipText:String = "<b>{0}</b><br/>{1:,0} bytes";
		protected var _appBounds:Rectangle;

		/** We will be rotating text, so we embed the font. */
		[Embed(source="verdana.TTF", fontName="Verdana")]
		private static var _font:Class;
		
		private var _url:String = 
			"http://flare.prefuse.org/data/flare.json.txt";
			
		private var _vis:Visualization;
		private var _detail:TextSprite;
		private var _legend:Legend;
		private var _bar:ProgressBar;
		private var _bounds:Rectangle = new Rectangle(0, 0, 800, 600);
		
		private var _fmt:TextFormat = new TextFormat("Verdana", 7, "0xffffff");
		private var _focus:NodeSprite;
		
		private var _data:Array = [
  {"name":"172.15.16.2", app:"HTTP", "size":108758},
  {"name":"172.15.16.2", app:"SNMP", "size":108757},
  {"name":"172.15.17.2", app:"ORACLE", "size":5460},
  {"name":"172.15.19.2", app:"LDP", "size":7644},
  {"name":"172.15.21.2", app:"XIMB", "size":26670},
  {"name":"172.15.22.2", app:"FTP", "size":8064},
  {"name":"172.15.23.2", app:"SSH", "size":7728},
  {"name":"172.15.25.2", app:"CVS", "size":7056},
  {"name":"172.15.27.3", app:"HTTP", "size":7644},
  {"name":"172.16.128.10", app:"HTTP", "size":20443},
  {"name":"172.16.128.81", app:"MNA", "size":10956},
  {"name":"172.17.128.25", app:"XIMB", "size":17121},
  {"name":"172.19.128.34", app:"SOAP", "size":6598},
  {"name":"172.21.0.10", app:"FTP", "size":10482},
  {"name":"172.21.0.12", app:"HTTP", "size":8438},
  {"name":"172.21.0.12", app:"SNMP", "size":360},
  {"name":"172.21.0.12", app:"LDAP", "size":360},
  {"name":"172.21.0.18", app:"FTP", "size":5610},
  {"name":"172.21.0.186", app:"LDAP", "size":6852},
  {"name":"172.21.0.200", app:"SSH", "size":8276},
  {"name":"172.21.0.26", app:"CVS", "size":3500},
  {"name":"172.22.128.11", app:"LDAP", "size":3638},
  {"name":"172.22.128.13", app:"HTTP", "size":6313},
  {"name":"172.23.128.14", app:"ORACLE", "size":12810},
  {"name":"172.25.128.23", app:"MNA", "size":7788},
  {"name":"172.27.0.131", app:"HTTP", "size":4693},
  {"name":"172.27.0.131", app:"SNMP", "size":660},
  {"name":"172.27.0.140", app:"HTTP", "size":1290},
  {"name":"172.27.0.140", app:"SNMP", "size":28745},
  {"name":"172.27.0.140", app:"LDAP", "size":23965},
  {"name":"172.27.0.140", app:"CVS", "size":27255},
  {"name":"172.27.0.140", app:"FTP", "size":24885},
  {"name":"172.27.0.140", app:"XIMB", "size":876},
  {"name":"172.27.0.140", app:"SOAP", "size":1776},
  {"name":"172.27.0.140", app:"MNA", "size":1019},
  {"name":"172.27.0.140", app:"IM", "size":1971},
  {"name":"172.27.0.140", app:"DCAT", "size":1022},
  {"name":"172.23.0.140", app:"EJB", "size":20898},
  {"name":"172.27.0.186", app:"HTTP", "size":7932},
  {"name":"172.27.0.186", app:"SNMP", "size":25904},
  {"name":"172.27.0.186", app:"AUTHEN", "size":18212},
  {"name":"172.27.0.62", app:"SNMP", "size":32397},
  {"name":"172.27.0.97", app:"ORACLE", "size":3527}
  ];
  //These are the received packets. Having both messes with
  //the tree structure
/*   {srcip:"10.1.33.110", destip:"63.115.138.131", bytes:2320},
  {srcip:"10.1.33.92", destip:"10.100.2.70", bytes:1300},
  {srcip:"10.1.33.95", destip:"10.100.2.42", bytes:10304},
  {srcip:"10.1.33.95", destip:"10.100.2.76", bytes:1092},
  {srcip:"10.1.33.98", destip:"10.100.2.76", bytes:698},
  {srcip:"10.18.0.1", destip:"216.141.33.104", bytes:1848},
  {srcip:"172.18.128.1", destip:"216.141.33.104", bytes:2466},
  {srcip:"172.18.128.15", destip:"172.16.128.10", bytes:13388},
  {srcip:"172.18.128.15", destip:"172.21.0.10", bytes:9101},
  {srcip:"172.18.128.15", destip:"172.21.0.12", bytes:4310},
  {srcip:"172.18.128.15", destip:"172.21.0.18", bytes:1904},
  {srcip:"172.18.128.15", destip:"192.168.200.4", bytes:1808},
  {srcip:"172.18.128.2", destip:"216.141.33.109", bytes:22131},
  {srcip:"172.18.128.2", destip:"216.141.33.26", bytes:2872},
  {srcip:"172.18.128.20", destip:"216.141.33.102", bytes:2037},
  {srcip:"172.18.128.248", destip:"172.27.0.131", bytes:28905},
  {srcip:"172.18.128.248", destip:"172.27.0.140", bytes:1494},
  {srcip:"172.18.128.3", destip:"216.141.33.104", bytes:18270},
  {srcip:"172.18.128.34", destip:"172.27.0.62", bytes:30989},
  {srcip:"172.18.128.4", destip:"216.141.33.104", bytes:18968},
  {srcip:"172.18.128.5", destip:"172.27.0.140", bytes:31391},
  {srcip:"172.18.128.6", destip:"172.27.0.140", bytes:26317},
  {srcip:"172.18.128.7", destip:"172.27.0.140", bytes:29771},
  {srcip:"172.18.128.8", destip:"172.27.0.140", bytes:26739},
  {srcip:"172.18.128.83", destip:"172.27.0.140", bytes:1022},
  {srcip:"172.18.128.84", destip:"172.15.16.2", bytes:67984},
  {srcip:"172.18.128.84", destip:"172.15.17.2", bytes:5460},
  {srcip:"172.18.128.84", destip:"172.15.19.2", bytes:7644},
  {srcip:"172.18.128.84", destip:"172.15.21.2", bytes:17832},
  {srcip:"172.18.128.84", destip:"172.15.22.2", bytes:8064},
  {srcip:"172.18.128.84", destip:"172.15.23.2", bytes:7728},
  {srcip:"172.18.128.84", destip:"172.15.25.2", bytes:7056},
  {srcip:"172.18.128.84", destip:"172.15.27.3", bytes:7644},
  {srcip:"172.18.128.84", destip:"172.16.128.81", bytes:10380},
  {srcip:"172.18.128.84", destip:"172.17.128.25", bytes:17118},
  {srcip:"172.18.128.84", destip:"172.19.128.34", bytes:6513},
  {srcip:"172.18.128.84", destip:"172.21.0.200", bytes:7762},
  {srcip:"172.18.128.84", destip:"172.21.0.26", bytes:4042},
  {srcip:"172.18.128.84", destip:"172.22.128.11", bytes:4091},
  {srcip:"172.18.128.84", destip:"172.22.128.13", bytes:6513},
  {srcip:"172.18.128.84", destip:"172.23.128.14", bytes:14465},
  {srcip:"172.18.128.84", destip:"172.25.128.23", bytes:7654},
  {srcip:"172.18.128.84", destip:"172.27.0.140", bytes:1937},
  {srcip:"172.18.128.9", destip:"172.27.0.97", bytes:28506},
  {srcip:"172.18.129.11", destip:"172.27.0.186", bytes:424},
  {srcip:"172.18.129.13", destip:"172.21.0.12", bytes:384},
  {srcip:"172.18.129.200", destip:"172.27.0.86", bytes:7774},
  {srcip:"172.18.129.201", destip:"172.27.0.86", bytes:7787},
  {srcip:"172.18.129.202", destip:"172.27.0.86", bytes:13364},
  {srcip:"172.18.129.203", destip:"172.27.0.86", bytes:9698},
  {srcip:"172.18.129.204", destip:"172.27.0.86", bytes:7200},
  {srcip:"172.18.129.205", destip:"172.27.0.86", bytes:10920},
  {srcip:"172.18.129.22", destip:"172.27.0.186", bytes:1368},
  {srcip:"172.18.129.24", destip:"172.21.0.186", bytes:6936},
  {srcip:"172.18.129.24", destip:"172.27.0.186", bytes:9724},
  {srcip:"172.18.129.42", destip:"172.21.0.12", bytes:384},
  {srcip:"172.18.129.46", destip:"172.27.0.140", bytes:1118},
  {srcip:"172.18.129.48", destip:"172.27.0.186", bytes:1824},
  {srcip:"172.18.129.49", destip:"172.27.0.186", bytes:456},
  {srcip:"172.18.129.55", destip:"172.27.0.186", bytes:2736},
  {srcip:"172.18.129.60", destip:"172.27.0.140", bytes:2122},
  {srcip:"172.18.129.69", destip:"172.27.0.186", bytes:5968},
  {srcip:"172.18.129.72", destip:"172.27.0.140", bytes:1229},
  {srcip:"172.18.129.79", destip:"172.27.0.186", bytes:3236},
  {srcip:"172.31.5.17", destip:"172.27.0.140", bytes:22402}
 */		public function Treemap() {
 			addEventListener(Event.ADDED_TO_STAGE, onStageAdd);
/* 			init();
 */		}
		
		private function onStageAdd(evt:Event):void
		{
			removeEventListener(Event.ADDED_TO_STAGE, onStageAdd);
			initStage();
			onResize();
			init();
			stage.addEventListener(Event.RESIZE, onResize);
		}

		protected function initStage():void
		{
			if (!stage) {
				throw new Error(
					"Can't initialize Stage -- not yet added to stage");
			}
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
		}

		protected function init():void
		{
			// load data file
/* 			var ldr:URLLoader = new URLLoader(new URLRequest(_url));
			_bar.loadURL(ldr, function():void {
				var obj:Array = JSON.decode(ldr.data as String) as Array;
				var data:Data = buildData(obj);
 */	            visualize(_data);
/*			});
 */  		}
  		
 		private function onResize(evt:Event=null):void
		{
			_appBounds = new Rectangle(0, 0, stage.stageWidth, stage.stageHeight);
 			resize(_appBounds.clone());
 		}
				
		public function resize(b:Rectangle):void
		{
			if (_bar) {
				_bar.x = b.width/2 - _bar.width/2;
				_bar.y = b.height/2 - _bar.height/2;
			}
			if (_vis) {
				// make some extra room for the treemap border
				b.x += 1; b.y += 1; b.width -= 1; b.height -= 1;
				_vis.bounds = b;
				_vis.update();
			}
		}

  		private function visualize(ips:Array, srcIp:String=null):void
		{
			var data:Data = buildData(ips);
 			if (srcIp != null)
				data = buildDataForIp(ips, srcIp);
			else
 				data = buildData(ips);

			// we're only drawing rectangles, so no one should notice...
			stage.quality = StageQuality.HIGH;
			
			// create and add visualization
			addChild(_vis = new Visualization(data));
						
			// -- initialize visual items ----------------------
			
			// nodes are blocks, lower depths have thicker edges
			_vis.data.nodes.visit(function(n:NodeSprite):void {
				n.buttonMode = true;
				n.shape = Shapes.BLOCK;
				n.fillColor = 0xff4444ff;
				n.lineColor = 0xffcccccc;
				n.lineWidth = n.depth==1 ? 2 : n.childDegree ? 1 : 0;
				n.fillAlpha = n.depth / 25;
			});
			// no fill or mouse interaction for nodes with children
			_vis.data.nodes.setProperties({
				fillColor: 0,
				mouseEnabled: false
			}, null, "childDegree");
			
			// don't show any edges
			_vis.data.edges["visible"] = false;
			
			
			// -- define operators -----------------------------
			
			// perform a tree map layout
			_vis.operators.add(new TreeMapLayout("data.size"));

			// label top-level packages in new layer
			_vis.operators.add(new Labeler(
				// strip off the "flare." prefix
			    fn("substring","data.name",0),
				Data.NODES, new TextFormat("Arial", 14, 0, true),
				eq("depth",1), Labeler.LAYER));

			// add drop shadow to generated labels
			_vis.operators.add(new PropertyEncoder({
				"props.label.filters": [new DropShadowFilter(3,45,0x888888)]
			}, Data.NODES, eq("depth",1)));

			// run the operators
			_vis.update();
			
			
             // -- define interactive controls -----------------
			
			// highlight nodes on mouse over
			_vis.controls.add(new HoverControl(NodeSprite,
				// don't change drawing order of nodes
				HoverControl.MOVE_AND_RETURN,
				// highlight
				function(evt:SelectionEvent):void {
					evt.node.lineColor = 0xffFF0000;
					evt.node.fillColor = 0xffFFAAAA;
				},
				// unhighlight
				function(evt:SelectionEvent):void {
					var n:NodeSprite = evt.node;
					n.lineColor = 0xffcccccc;
					n.fillColor = 0xff4444FF;
					n.fillAlpha = n.depth / 25;
				}
			));
			
			// provide tooltip on mouse hover
			_vis.controls.add(new TooltipControl(NodeSprite, null,
				function(evt:TooltipEvent):void {
					TextSprite(evt.tooltip).htmlText = Strings.format(_tipText,
						evt.node.data.name, evt.node.data.size);
				}
			));
			
			// click to hyperlink to source code
			_vis.controls.add(new ClickControl(NodeSprite, 1,
				function(evt:SelectionEvent):void {
					
					// Build data gain
					
					// Visualize treemap again for given subnet.
					var cls:String = evt.node.data.name;
					trace("callAgain() for srcIp "+cls);
					callAgain(cls);
/*  					var url:String = _src + cls.split(".").join("/") + ".as";
					trace(cls+" : " +url);
					navigateToURL(new URLRequest(url), "_code");
 */ 				}
			));
			
			// perform layout
			resize(_appBounds);

		}
		
  		private function callAgain(srcIp:String):void 
  		{
  			_bar=null;
  			_vis.controls.clear();
  			_vis.labels=null;
  			_vis.data=null;
  			_vis=null;
  			
 			addChild(_bar = new ProgressBar());
 			_bar.bar.filters = [new DropShadowFilter(1)];
			
			// load data file

/*   			var ldr:URLLoader = new URLLoader(new URLRequest(_url));
			_bar.loadURL(ldr, function():void {
 */  				trace("visulizing data");
 	            visualize(_data,srcIp);
 	            _bar = null;
/* 			});
 */ 
  		}
		

		
		/** Show all reverse dependencies */
		
		
		private function addDetail():void
		{	
			var fmt:TextFormat = new TextFormat("Verdana",14, 0xffffff);
			
			_legend = Legend.fromValues(null, [
				{color: 0xffff0000, size: 0.75, label: "Inbound"},
				{color: 0xff00ff00, size: 0.75, label: "Outbound"}
			]);
			_legend.labelTextFormat = fmt;
			_legend.labelTextMode = TextSprite.EMBED;
			_legend.update();
			addChild(_legend);

			_detail = new TextSprite("", fmt, TextSprite.EMBED);
			_detail.textField.multiline = true;
			_detail.htmlText = "No selection";
			addChild(_detail);
		}
		


		// --------------------------------------------------------------------
		
		/**
		 * Creates the visualized data.
		 */
		public static function buildData(ips:Array):Data
		{
			var data:Data = new Data();
			var tree:Tree = new Tree();
			var ipmap:Object = {};
			var appmap:Object = {};

			tree.root = data.addNode({name:"flare", size:0});
			ipmap.flare = tree.root;
			
			var row:Object, u:NodeSprite, node:NodeSprite, v:NodeSprite;
			var path:Array, p:String, d:String, pp:String, i:uint, j:uint, s:uint, subnet:String;
			p = "";
			// build data set and tree edges
			ips.sortOn("name");
			var nn:Number = 0;
			//First add nodes for all source and dest ips
			i=0;
 			for each (row in ips) {
 					++i;
					p = row.name;
/*  					subnet=p.substr(0,p.indexOf(".",p.indexOf(".")+1));
					trace("subnet="+subnet);
					p=subnet;
 */ 					s = row.size;
					d = row.app;
/* 					trace("row-"+i+"="+row.name + " : "+row.app+" : "+row.size);
 */					if (!ipmap[p]) {
						ipmap[p] = data.addNode({name:p, app:d, size:s, index:nn++});
					 	tree.addChild(tree.root, ipmap[p]);
					} else {
 						var n:NodeSprite = ipmap[p];
/* 						trace("existing data="+n.data.name.toString()+ " : "+n.data.size);
 */ 						ipmap[p].data.size += s;
					}

 			}
 

			// sort the list of children alphabetically by name
 			for each (u in tree.nodes) {
				u.sortEdgesBy(NodeSprite.CHILD_LINKS, "target.data.name");
			}
 			

			data.tree = tree;
			return data;
		}
		
		public static function buildDataForIp(ips:Array, ip:String):Data
		{
			var data:Data = new Data();
			var tree:Tree = new Tree();
			var ipmap:Object = {};
			var appmap:Object = {};

			trace("building data for ip="+ip);
			tree.root = data.addNode({name:"flare2", size:0});
			ipmap.flare = tree.root;
			
			var row:Object, u:NodeSprite, v:NodeSprite, node:NodeSprite;
			var path:Array, p:String, d:String, pp:String, i:uint, j:uint, s:uint, subnet:String;
			p = "";
			// build data set and tree edges
			ips.sortOn("name");
			var nn:Number = 0;
			//First add nodes for all source and dest ips
			i=0;
 			for each (row in ips) {
 					++i;
/* 					p = row.name;
  					subnet=p.substr(0,p.indexOf(".",p.indexOf(".")+1));
					trace("subnet="+subnet);
					p=subnet;
 */ 					if (row.name.indexOf(ip) != 0)
						continue;

					p = row.app
 					s = row.size;
					d = row.app;
					trace("row-"+i+"="+row.app+" : "+row.size);
					if (!ipmap[p]) {
						ipmap[p] = data.addNode({name:p,  size:s, index:nn++});
					 	tree.addChild(tree.root, ipmap[p]);
					} else {
 						var n:NodeSprite = ipmap[p];
						trace("existing data="+n.data.app.toString()+ " : "+n.data.size);
 						ipmap[p].data.size += s;
					}

/* 					if (!ipmap[row.size]) {
						t = data.addNode({name:row.app, size:1, index:nn++});
				 	    tree.addChild(ipmap[p], t);
					}
 */			}

  			for each (node in tree.nodes)
 			{
 				++j;
 				trace("node-"+j+"="+node.data.name+":"+node.data.size);
 			}

 

			//Now create edges from src->dest
/* 			for each (row in ips) {
					p = row.srcip;
					var t:NodeSprite = ipmap[row.app];
 					if (!t) {
						t = data.addNode({name:row.app, size:1, index:nn++});
				 	}
				 	    tree.addChild(ipmap[p], t);
 				 	//tree.addChild(ipmap[p], t);
				 	data.addEdgeFor(ipmap[p], t, true, row);
			}
 */
			// sort the list of children alphabetically by name
 			for each (u in tree.nodes) {
				u.sortEdgesBy(NodeSprite.CHILD_LINKS, "target.data.name");
			}
 			

			data.tree = tree;
			return data;
		}

		
	} // end of class 
}