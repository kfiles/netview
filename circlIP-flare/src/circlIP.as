package {
	import br.com.stimuli.string.printf;
	
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
	import flare.vis.legend.Legend;
	import flare.vis.operator.encoder.PropertyEncoder;
	import flare.vis.operator.label.RadialLabeler;
	import flare.vis.operator.layout.CircleLayout;
	
	import flash.display.Sprite;
	import flash.geom.Rectangle;
	import flash.text.TextFormat;

	
	[SWF(width="800", height="600", backgroundColor="#000000", frameRate="30")]
	public class circlIP extends Sprite
	{
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
		  {srcip:"10.100.2.42", destip:"10.1.33.95", bytes:257550},
  {srcip:"10.100.2.70", destip:"10.1.33.92", bytes:653},
  {srcip:"172.15.16.2", destip:"172.18.128.84", bytes:108757},
  {srcip:"172.15.17.2", destip:"172.18.128.84", bytes:5460},
  {srcip:"172.15.19.2", destip:"172.18.128.84", bytes:7644},
  {srcip:"172.15.21.2", destip:"172.18.128.84", bytes:26670},
  {srcip:"172.15.22.2", destip:"172.18.128.84", bytes:8064},
  {srcip:"172.15.23.2", destip:"172.18.128.84", bytes:7728},
  {srcip:"172.15.25.2", destip:"172.18.128.84", bytes:7056},
  {srcip:"172.15.27.3", destip:"172.18.128.84", bytes:7644},
  {srcip:"172.16.128.10", destip:"172.18.128.15", bytes:20443},
  {srcip:"172.16.128.81", destip:"172.18.128.84", bytes:10956},
  {srcip:"172.17.128.25", destip:"172.18.128.84", bytes:17121},
  {srcip:"172.19.128.34", destip:"172.18.128.84", bytes:6598},
  {srcip:"172.21.0.10", destip:"172.18.128.15", bytes:10482},
  {srcip:"172.21.0.12", destip:"172.18.128.15", bytes:8438},
  {srcip:"172.21.0.12", destip:"172.18.129.13", bytes:360},
  {srcip:"172.21.0.12", destip:"172.18.129.42", bytes:360},
  {srcip:"172.21.0.18", destip:"172.18.128.15", bytes:5610},
  {srcip:"172.21.0.186", destip:"172.18.129.24", bytes:6852},
  {srcip:"172.21.0.200", destip:"172.18.128.84", bytes:8276},
  {srcip:"172.21.0.26", destip:"172.18.128.84", bytes:3500},
  {srcip:"172.22.128.11", destip:"172.18.128.84", bytes:3638},
  {srcip:"172.22.128.13", destip:"172.18.128.84", bytes:6313},
  {srcip:"172.23.128.14", destip:"172.18.128.84", bytes:12810},
  {srcip:"172.25.128.23", destip:"172.18.128.84", bytes:7788},
  {srcip:"172.27.0.131", destip:"172.18.128.248", bytes:4693},
  {srcip:"172.27.0.131", destip:"172.18.128.249", bytes:660},
  {srcip:"172.27.0.140", destip:"172.18.128.248", bytes:1290},
  {srcip:"172.27.0.140", destip:"172.18.128.5", bytes:28745},
  {srcip:"172.27.0.140", destip:"172.18.128.6", bytes:23965},
  {srcip:"172.27.0.140", destip:"172.18.128.7", bytes:27255},
  {srcip:"172.27.0.140", destip:"172.18.128.8", bytes:24885},
  {srcip:"172.27.0.140", destip:"172.18.128.83", bytes:876},
  {srcip:"172.27.0.140", destip:"172.18.128.84", bytes:1776},
  {srcip:"172.27.0.140", destip:"172.18.129.46", bytes:1019},
  {srcip:"172.27.0.140", destip:"172.18.129.60", bytes:1971},
  {srcip:"172.27.0.140", destip:"172.18.129.72", bytes:1022},
  {srcip:"172.27.0.140", destip:"172.31.5.17", bytes:20898},
  {srcip:"172.27.0.186", destip:"172.18.129.24", bytes:7932},
  {srcip:"172.27.0.186", destip:"172.18.129.69", bytes:25904},
  {srcip:"172.27.0.186", destip:"172.18.129.79", bytes:18212},
  {srcip:"172.27.0.62", destip:"172.18.128.34", bytes:32397},
  {srcip:"172.27.0.97", destip:"172.18.128.9", bytes:3527},
  {srcip:"192.168.200.4", destip:"172.18.128.15", bytes:1358},
  {srcip:"216.141.33.102", destip:"172.18.128.20", bytes:1608},
  {srcip:"216.141.33.104", destip:"10.18.0.1", bytes:1624},
  {srcip:"216.141.33.104", destip:"172.18.128.1", bytes:2132},
  {srcip:"216.141.33.104", destip:"172.18.128.3", bytes:16054},
  {srcip:"216.141.33.104", destip:"172.18.128.4", bytes:16644},
  {srcip:"216.141.33.109", destip:"172.18.128.2", bytes:18708},
  {srcip:"216.141.33.26", destip:"172.18.128.2", bytes:2800},
  {srcip:"63.115.138.131", destip:"10.1.33.110", bytes:6830}
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
 */		public function circlIP() {
			init();
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
  		
  		private function visualize(ips:Array):void
		{
			var data:Data = buildData(ips);
			// place shorter names at the end of the data list
			// that way they will the easiest to mouse over later
			//data.nodes.sortBy("-data.srcip");
			
			// prepare data with default settings
  			data.nodes.setProperties({
				/*shape: null, */                 // no shape, use labels instead
				visible: function(d:DataSprite):Boolean {
					//trace(d.data.name);
					return ("flare" !=  d.data.name);
				}, // don't show root node
				buttonMode: true              // show hand cursor
			});
						
			// define the visualization
			_vis = new Visualization(data);
			// place around circle by tree structure, radius mapped to depth
			// make a large inner radius so labels are closer to circumference
			_vis.operators.add(new CircleLayout());//"depth", null, true));
			CircleLayout(_vis.operators.last).startRadiusFraction = 3/5;
			// bundle edges to route along the tree structure
			_vis.operators.add(new GroupedEdgeRouter(1));
			// set the edge alpha values
			// longer edge, lighter alpha: 1/(2*numCtrlPoints)
			_vis.operators.add(new PropertyEncoder(
				{alpha:1}, Data.EDGES));
			
			// add labels	
			_vis.operators.add(new RadialLabeler(
				// custom label function removes package names
				function(d:DataSprite):String {
					return  d.data.name;
				}, true, _fmt)); // leaf nodes only
			_vis.operators.last.textMode = TextSprite.EMBED; // embed fonts!
			_vis.operators.last.radiusOffset += 10;
			
			// update and add
			_vis.update();
			trace("are we going to die?");
			data.edges.setProperties({
				lineWidth: function(d:DataSprite):Number {
				  return (Math.log (d.data.bytes / 500));
				},
				lineColor: function(d:EdgeSprite):uint{ //Color edges based upon source index
					var i:Number = d.source.data.index / data.nodes.length;
					var color:uint =Colors.hsv(i, .8,.9); 
					d.source.props.label.color = color;
					d.target.props.label.color = color;
					return color;
					//return color;
				},
				//0xff0055cc,
				mouseEnabled: true,          // non-interactive edges
				visible: neq("source.parentNode","target.parentNode")
			});
			addChild(_vis);
			
			// add the legend and detail pane
			addDetail();
			
			// show all dependencies on single-click
			var linkType:int = NodeSprite.OUT_LINKS;
			_vis.controls.add(new ClickControl(NodeSprite, 1,
				function(evt:SelectionEvent):void {
					if (_focus && _focus != evt.node) {
						unhighlight(_focus);
						linkType = NodeSprite.OUT_LINKS;
					}
					_focus = evt.node;
					highlight(evt);
					showAllDeps(evt, linkType);
//					_vis.controls.remove(hov);
					linkType = (linkType==NodeSprite.OUT_LINKS ?
						NodeSprite.IN_LINKS : NodeSprite.OUT_LINKS);
				},
				// show all edges and nodes as normal
				function(evt:SelectionEvent):void {
					if (_focus) unhighlight(_focus);
					_focus = null;
					_vis.data.edges["visible"] = 
						neq("source.parentNode","target.parentNode");
					_vis.data.nodes["alpha"] = 1;
//					_vis.controls.add(hov);
					linkType = NodeSprite.OUT_LINKS;
				}
			));
			
			// add mouse-over details
// 			_vis.controls.add(new HoverControl(NodeSprite,
//				HoverControl.DONT_MOVE,
//				function(evt:SelectionEvent):void {
//					_detail.text = evt.node.data.name;
//				},
//				function(evt:SelectionEvent):void {
//					_detail.text = _vis.data.nodes.length + " ips";
//				}
//			));
 			
 			_vis.controls.add(new HoverControl(EdgeSprite,
				HoverControl.DONT_MOVE,
				function(evt:SelectionEvent):void {
					_detail.text = evt.edge.data.bytes + " bytes";
				},
				function(evt:SelectionEvent):void {
					_detail.text = "";
				}
			));

			// add mouse-over highlight
 			var hov:HoverControl = new HoverControl(NodeSprite,
				HoverControl.DONT_MOVE, highlight, unhighlight);
			_vis.controls.add(hov);
 			
			// compute the layout
			if (_bounds) resize(_bounds);
		}
		
		/** Add highlight to a node and connected edges/nodes */
		private function highlight(evt:SelectionEvent):void
		{
			//Save the original color
			evt.node.data.labelColor =evt.node.props.label.color; 
			// highlight mouse-over node
 			evt.node.props.label.color = 0x0000cc;
			evt.node.props.label.bold = true;
 			// highlight links for classes that depend on the focus in green
			evt.node.visitEdges(function(e:EdgeSprite):void {
				e.alpha = 0.5;
				e.data.lineColor = e.lineColor;
				e.lineColor = 0xff00ff00;
				e.source.data.labelColor = e.source.props.label.color;
				e.source.props.label.color = 0x00cc00;
				_vis.marks.setChildIndex(e, _vis.marks.numChildren-1);
			}, NodeSprite.IN_LINKS);
			// highlight links the focus depends on in red
			evt.node.visitEdges(function(e:EdgeSprite):void {
				e.alpha = 0.5;
				e.data.lineColor = e.lineColor;
				e.lineColor = 0xffff0000;
				e.target.data.labelColor = e.target.props.label.color;
				e.target.props.label.color = 0xff0000;
				_vis.marks.setChildIndex(e, _vis.marks.numChildren-1);
			}, NodeSprite.OUT_LINKS);
		}
		
		/** Remove highlight from a node and connected edges/nodes */
		private function unhighlight(n:*):void
		{
			var node:NodeSprite = n is NodeSprite ?
				NodeSprite(n) : SelectionEvent(n).node;
			// set everything back to normal
			node.props.label.color = node.data.labelColor;
			node.props.label.bold = false;
			node.visitEdges(function(e:EdgeSprite):void {
				e.alpha = 1;
				e.lineColor = e.data.lineColor;
				e.source.props.label.color = e.source.data.labelColor;
				e.target.props.label.color = e.target.data.labelColor;
			}, NodeSprite.GRAPH_LINKS);
/* 			node.setEdgeProperties({
				alpha: .5,
				lineColor: 0xff0055cc,
				"source.props.label.color": 0,
				"target.props.label.color": 0
			}, NodeSprite.GRAPH_LINKS);
 */		}
		
		/** Traverse all dependencies for a given class */
		private function showAllDeps(evt:SelectionEvent, linkType:int):void
		{
			// first, do a breadth-first-search to compute closure
			var q:Array = evt.items.slice();
				var u:NodeSprite = q.shift();
			// now highlight nodes and edges in the closure
			_vis.data.edges.visit(function(e:EdgeSprite):void {
				e.visible = (e.source == u);
			});
//			_vis.data.nodes.visit(function(n:NodeSprite):void {
//				n.alpha = map[n] ? 1 : 0.4;
//			});
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
		
		public function resize(bounds:Rectangle):void
		{
			_bounds = bounds;
			if (_bar) {
				_bar.x = _bounds.width/2 - _bar.width/2;
				_bar.y = _bounds.height/2 - _bar.height/2;
			}
			if (_vis) {
				// automatically size labels based on bounds
				var d:Number = Math.min(_bounds.width, _bounds.height);
				_vis.data.nodes.setProperty("props.label.size",
					(d <= 650 ? 7 : d <= 725 ? 8 : 9),
					null, eq("childDegree",0));
				
				// compute the visualization bounds
				_vis.bounds.x = _bounds.x;
				_vis.bounds.y = _bounds.y + (0.06 * _bounds.height);
				_vis.bounds.width = _bounds.width;
				_vis.bounds.height = _bounds.height - (0.05 * _bounds.height);
				// update
				_vis.update();
				
				// layout legend and details
				_legend.x = _bounds.width  - _legend.width;
				_legend.y = _bounds.height - _legend.border.height - 5;
				//_detail.y = _bounds.height - _detail.height - 5;
				_detail.y = _bounds.height - _detail.height - 5;
				_detail.x = 5;
				// forcibly render to eliminate partial update bug, as
				// the standard RENDER event routing can get delayed.
				// remove this line for faster but unsynchronized resizes
				DirtySprite.renderDirty();
			}
		}
		
		// --------------------------------------------------------------------
		
		/**
		 * Creates the visualized data.
		 */
		public static function buildData(ips:Array):Data
		{
			var data:Data = new Data();
			var tree:Tree = new Tree();
			var map:Object = {};
			
			tree.root = data.addNode({name:"flare", size:0});
			map.flare = tree.root;
			
			var row:Object, u:NodeSprite, v:NodeSprite;
			var path:Array, p:String, pp:String, i:uint;
			p = "";
			// build data set and tree edges
			ips.sortOn("bytes");
			var nn:Number = 0;
			//First add nodes for all source and dest ips
 			for each (row in ips) {
					p = row.srcip;
					if (!map[p]) {
						map[p] = data.addNode({name:p, size:1, index:nn++});
					 	tree.addChild(tree.root, map[p]);
					}
					if (!map[row.destip]) {
						t = data.addNode({name:row.destip, size:1, index:nn++});
				 	    tree.addChild(map[p], t);
					}
			}

			//Now create edges from src->dest
			for each (row in ips) {
					p = row.srcip;
					var t:NodeSprite = map[row.destip];
 					if (!t) {
						t = data.addNode({name:row.destip, size:1, index:nn++});
				 	}
				 	    tree.addChild(map[p], t);
 				 	//tree.addChild(map[p], t);
				 	data.addEdgeFor(map[p], t, true, row);
			}
			
			// sort the list of children alphabetically by name
/* 			for each (u in tree.nodes) {
				u.sortEdgesBy(NodeSprite.CHILD_LINKS, "target.data.name");
			}
 */			

			data.tree = tree;
			return data;
		}
		
	} // end of class 
}