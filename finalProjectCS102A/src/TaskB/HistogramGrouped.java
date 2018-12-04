package TaskB;

import java.awt.Color;

import TaskA.StdDraw;

public class HistogramGrouped extends HistogramBase {
	public HistogramGrouped(Canvas c, Formats f, HistogramData d) {
		super(c, f, d);
		c.x = 768;
		f.hasBorder = false;
		f.hasFooter = false;
	}
	
	
	@Override
	protected void plotRuler() {

	}

	@Override
	protected void plotBars() {
		double[] a = d.values;
		int n = a.length;
		setHistogramScale(n);

		if (f.isBarFilled) {
			if (f.barFillColor != null) {
				StdDraw.setPenColor(f.barFillColor);
				for (int i = 0; i < n; i++) {
					StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
					// (x, y, halfWidth, halfHeight)
				}
			} else {
				for (int i = 0; i < n; i++) {
					StdDraw.setPenColor(g.groupedColor[0]);
					StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
				}
			}
		}

		if (f.hasBarFrame) {
			StdDraw.setPenColor(f.barFrameColor);
			for (int i = 0; i < n; i++) {
				StdDraw.rectangle(i, a[i] / 2, 0.25, a[i] / 2);
				// (x, y, halfWidth, halfHeight)
			}
		}

	}

	@Override
	protected void plotLegends() {
		// TODO Auto-generated method stub
	}
	
	GroupedColor g = new GroupedColor(); // groupedcolor
}

class GroupedColor {
	Color[] groupedColor = new Color[3];
	public GroupedColor(){
		this.groupedColor[0] = new Color(153, 51, 255);
		this.groupedColor[1] = new Color(153, 0, 76);
		this.groupedColor[2] = new Color(160, 82, 45);
	}
}
