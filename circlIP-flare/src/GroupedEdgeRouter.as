package 
{
	import flare.animate.Transitioner;
	import flare.util.Arrays;
	import flare.util.Shapes;
	import flare.vis.data.DataSprite;
	import flare.vis.data.EdgeSprite;
	import flare.vis.data.NodeSprite;
	import flare.vis.operator.layout.BundledEdgeRouter;

	/**
	 * Layout that routes edges in a graph so that they form groups, reducing
	 * clutter. This operator requires that a tree structure (for example, a
	 * computed spanning tree) be defined over the graph. The class also sets
	 * all edges' <code>shape</code> property to <code>Shapes.BSPLINE</code>
	 * and can optionally compute <code>alpha</code> values to improve edge
	 * visibility.
	 * 
	 * <p>The algorithm uses the tree path between two nodes to define control
	 * points for routing a b-spline curve. The technique is adapted from
	 * Danny Holten's work on
	 * <a href="http://www.win.tue.nl/~dholten/papers/bundles_infovis.pdf">
	 * Hierarchical Edge Bundles</a>, InfoVis 2006.</p>
	 */
	public class GroupedEdgeRouter extends BundledEdgeRouter
	{
		
		/**
		 * Creates a new GroupedEdgeRouter 
		 * @param bundling the tightness of edge bundles
		 */
		public function GroupedEdgeRouter(bundling:Number=0.85,
			removeSharedAncestor:Boolean=false)
		{
			this.bundling = bundling;
			this.removeSharedAncestor = removeSharedAncestor;
		}
		
		/** @inheritDoc */
		public override function operate(t:Transitioner=null):void
		{
			t = (t==null ? Transitioner.DEFAULT : t);
			
			var u:NodeSprite, v:NodeSprite, pu:NodeSprite, pv:NodeSprite;
			var p1:Array = [], p2:Array = [];
			var d1:int, d2:int, o:Object;
			var ux:Number, uy:Number, dx:Number, dy:Number;
			
			// compute edge bundles
			for each (var e:EdgeSprite in visualization.data.edges) {
				u = e.source; p1.push(pu=u); d1 = u.depth;
				v = e.target; p2.push(pv=v); d2 = v.depth;
				
				// collect b-spline control points
				var p:Array = t.$(e).points;
				if (p==null) { p = []; } else { Arrays.clear(p); }
				addPoint(p, e.source, t);
				// set the bundling strength by adjusting control points
				// Need to improve these forcing equations. 
				var b:Number = bundling, ib:Number = 1-b, N:int = p.length;
				if (b < 1) {
					o = t.$(u); ux = o.x; uy = o.y;
					o = t.$(v);	dx = o.x; dy = o.y;
					dx = (dx-ux)/(N+2);
					dy = (dy-uy)/(N+2);

					for (var i:int=0; i<N; i+=2) {
						p[i]   = b*p[i]   + ib*(ux + (i+2)*dx);
						p[i+1] = b*p[i+1] + ib*(uy + (i+2)*dy);
					}
				}
				
				o = t.$(e);
				o.points = p;
				e.shape = Shapes.BSPLINE;
				
				// clean-up
				Arrays.clear(p1);
				Arrays.clear(p2);
			}
		}
		
	/**
	 * Adds a control point to the spline point array. The point location is computed
	 * by following a line from the NodeSprite to the Origin 
	 * @param p The array of spline points
	 * @param u The source NodeSprite 
	 * @param t a Transitioner
	 */
	private static function addPoint(p:Array, u:DataSprite, t:Transitioner):void
		{
			var a:Object = t.$(u);
			var b:Object = t.$(u.origin);
			p.push(b.x - ((a.x - b.x) *.7));
			p.push(b.y - ((a.y - b.y) * .7));
		}

	} // end of class GroupedEdgeRouter
}