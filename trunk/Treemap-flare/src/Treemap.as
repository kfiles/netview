package {

	import com.adobe.serialization.json.JSON;
	
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
	
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageQuality;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.filters.DropShadowFilter;
	import flash.geom.Rectangle;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.text.TextFormat;


 	[SWF(backgroundColor="#ffffff", frameRate="30")]
	public class Treemap extends Sprite
	{
		
  		private static var _tipText:String = "<b>{0}</b><br/>{1:,0} bytes";
		protected var _appBounds:Rectangle;

		/** We will be rotating text, so we embed the font. */
		[Embed(source="verdana.TTF", fontName="Verdana")]
		private static var _font:Class;
		
		private var _url:String = "http://localhost:8080/testdata/treemap.jsp";
		private var _vis:Visualization;
		private var _detail:TextSprite;
		private var _bar:ProgressBar;

		private static var _fillColor:uint = 0xff447744;
		private static var _lineColor:uint = 0x111111FF;		
		private var _fmt:TextFormat = new TextFormat("Verdana", 7, "0xffffff");
		private var _focus:NodeSprite;
				
		private var _level:int=0;
		private var obj:Array;
 		public function Treemap() {
 			addEventListener(Event.ADDED_TO_STAGE, onStageAdd);
/* 			init();
 */		}
		
		private function onStageAdd(evt:Event):void
		{
			removeEventListener(Event.ADDED_TO_STAGE, onStageAdd);
			initStage();
			init();
			onResize();
			stage.addEventListener(Event.RESIZE, onResize);
		}

		protected function onResize(evt:Event=null):void
		{
			trace(stage.stageWidth+": "+stage.stageHeight);
 			_appBounds = new Rectangle(0, 0, stage.stageWidth, stage.stageHeight);
  			resize(_appBounds.clone());
 		}

		private function initStage():void
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
			trace("Starting treemap");
 			addChild(_bar = new ProgressBar());
 			_bar.bar.filters = [new DropShadowFilter(1)];

			// load data file
 			  var ldr:URLLoader = new URLLoader(new URLRequest(_url));
			_bar.loadURL(ldr, function():void {
   				trace("visulizing data");
   				trace("ldr.data="+ldr.data);
   				trace("Decoding data now");
   				obj = JSON.decode(ldr.data as String) as Array;
   				
   				trace ("Got response back from treemap.jsp");
   				

/* 				var _data:Data = buildData(obj);
 */
 				trace("total records="+obj.length);
 	            visualize(obj);
 	            _bar = null;
			});
/*   	            visualize(_data);
	            _bar=null;
 */  		}
  		
				
		public function resize(b:Rectangle):void
		{
/* 			trace(b.width+" :  "+b.height+" :  "+b.x+" : "+b.y);
 */			if (_bar) {
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
				n.fillColor = _fillColor;
				n.lineColor = _lineColor;
				n.lineWidth = n.depth==1 ? 2 : n.childDegree ? 1 : 0;
				n.fillAlpha = 1;/*n.depth / 25;*/
			});
			// no fill or mouse interaction for nodes with children
			_vis.data.nodes.setProperties({
				fillColor: 0x00ff0000,
				mouseEnabled: false
			}, null, "childDegree");
			
			// don't show any edges
			_vis.data.edges["visible"] = false;
			
			
			// -- define operators -----------------------------
			
			// perform a tree map layout
			_vis.operators.add(new TreeMapLayout("data.size"));

			// label top-level packages in new layer
 			_vis.operators.add(new Labeler(
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
					evt.node.lineColor = 0xff77dd77;
					evt.node.fillColor = 0xff92BA92;
				},
				// unhighlight
				function(evt:SelectionEvent):void {
					var n:NodeSprite = evt.node;
					n.lineColor = _lineColor;
					n.fillColor = _fillColor;
					n.fillAlpha = 1;/*n.depth / 25;*/
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
				}
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
  			
  			_level++;
  			
  			if (_level >1)
  			   _level=0;
  			
  			addChild(_bar = new ProgressBar());
 			_bar.bar.filters = [new DropShadowFilter(1)];
 			
			// load data file
   			var ldr:URLLoader = new URLLoader(new URLRequest(_url));
			_bar.loadURL(ldr, function():void {
  				trace("visulizing data");
   				
   				trace("obj.size="+obj.length);
    			if (_level==0)
 	            	visualize(obj);
 	            else 
 	            	visualize(obj, srcIp);
 	            _bar = null;
			});
 
/*   				trace("visulizing data");
 	            visualize(_data,srcIp);
 	            _bar = null;
 */ 
  		}
		
		
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
						trace("existing data="+n.data.name.toString()+ " : "+n.data.size);
 						ipmap[p].data.size += s;
					}

			}

  			for each (node in tree.nodes)
 			{
 				++j;
 				trace("node-"+j+"="+node.data.name+":"+node.data.size);
 			}

 			// sort the list of children alphabetically by name
 			for each (u in tree.nodes) {
				u.sortEdgesBy(NodeSprite.CHILD_LINKS, "target.data.name");
			}
 			

			data.tree = tree;
			return data;
		}

		
	} // end of class 
}