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
	
    public void draw() {
        setCanvas();
        plotBars();
        plotRuler();
        plotKeys();
        plotLegends();
        if (f.hasBorder)
            plotBorder();
        if (f.hasRightRuler)
            plotRightRuler();
        if (f.hasHeader)
            plotHeader();
        if (f.hasFooter)
            plotFooter();
    }

    protected void setCanvas() {
        StdDraw.setCanvasSize(c.x, c.y);
        setOriginalScale();
        StdDraw.clear(c.bgColor);
        StdDraw.setPenColor(c.color);
    }

    protected void setHistogramScale(int nBars) {
        double span = yValue[MAX] - yValue[MIN] + 1;
        double ySpacing = span / (1 - f.margins[NORTH] - f.margins[SOUTH]);
        yScale[MIN] = yValue[MIN] - f.margins[SOUTH] * ySpacing - 1;
        yScale[MAX] = yValue[MAX] + f.margins[NORTH] * ySpacing;
        StdDraw.setYscale(yScale[MIN], yScale[MAX]);

        double xSpacing = (nBars + 1) / (1 - f.margins[WEST] - f.margins[EAST]);
        xScale[MIN] = -f.margins[WEST] * xSpacing - 1;
        xScale[MAX] = nBars + f.margins[EAST] * xSpacing;
        StdDraw.setXscale(xScale[MIN], xScale[MAX]);
    };

    protected void setOriginalScale() {
        StdDraw.setXscale(c.xScale[MIN], c.xScale[MAX]);
        StdDraw.setYscale(c.yScale[MIN], c.yScale[MAX]);
    }


    protected void plotRuler() {
        StdDraw.setFont(f.rulerFont);
        StdDraw.setPenColor(f.rulerColor);
        final double x0 = xValue[MIN] - 0.05, x1 = xValue[MIN] + 0.05;
        String[] mark = new String[rulerGrade + 1];
        for (int i = 0; i <= rulerGrade; i++) {
            double y = yValue[MIN] + i * rulerStep;
            mark[i] = numberForRuler(y);
            StdDraw.line(x0, y, x1, y);
        }
        int len = maxMarkLength(mark);
        final double xs = xScale[MIN] + 0.7 * (xValue[MIN] - xScale[MIN]);
        for (int i = 0; i <= rulerGrade; i++) {
            double y = yValue[MIN] + i * rulerStep;
            StdDraw.text(xs, y, String.format("%" + len + "s", mark[i]));
        }
    }

    protected String numberForRuler(double x) { // TO BE Customized
        if (yValue[MAX] >= 5 && rulerStep > 1)
            return "" + (int) x;
        if (rulerStep > 0.1)
            return String.format("%.1f", x);
        if (rulerStep > 0.01)
            return String.format("%.2f", x);
        if (rulerStep > 0.001)
            return String.format("%.3f", x);
        if (rulerStep > 0.0001)
            return String.format("%.4f", x);
        if (rulerStep > 0.00001)
            return String.format("%.5f", x);
        return String.format("%g", x);
    }

    private int maxMarkLength(String[] sa) {
        int n = sa[0].length();
        for (String s : sa)
            if (n < s.length())
                n = s.length();
        return n;
    }

    protected void plotKeys() {
        StdDraw.setFont(f.keysFont);
        StdDraw.setPenColor(f.keyColor);
        final double y = yValue[MIN] - 0.5 * rulerStep;
        for (int i = 0; i < d.keys.length; i++) {
            if (d.keys[i].length() >= 1) {
                double x = xValue[MIN] + 1 + i;
                StdDraw.text(x, y, d.keys[i]);
            }
        }
    }
    
    protected void plotLegends() {
    	
    }

    protected void plotBorder() {
        double x = .5 * (xValue[MIN] + xValue[MAX]);
        double y = .5 * (yValue[MIN] + yValue[MAX]);
        double halfWidth = .5 * (xValue[MAX] - xValue[MIN]);
        double halfHeight = .5 * (yValue[MAX] - yValue[MIN]);
        StdDraw.setPenColor(f.borderColor);
        StdDraw.rectangle(x, y, halfWidth, halfHeight);
    }

    protected void plotRightRuler() {
        StdDraw.setPenColor(f.rulerColor);
        final double x0 = xValue[MAX] - 0.05, x1 = xValue[MAX] + 0.05;
        String[] mark = new String[rulerGrade + 1];
        for (int i = 0; i <= rulerGrade; i++) {
            double y = yValue[MIN] + i * rulerStep;
            mark[i] = numberForRuler(y);
            StdDraw.line(x0, y, x1, y);
        }
    } // DONE

    protected void plotHeader() {
        StdDraw.setFont(f.headerFont);
        StdDraw.setPenColor(f.headerColor);
        if(f.headerLocation == null) {
        	double x = .5 * (xScale[MIN] + xScale[MAX]);
        	double y = .5 * (yValue[MAX] + yScale[MAX]);
        	StdDraw.text(x, y, d.header);
        }
        else {
        	StdDraw.text(f.headerLocation[0], f.headerLocation[1], d.header);
        }
    }

    protected void plotFooter() {
        StdDraw.setFont(f.footerFont);
        StdDraw.setPenColor(f.footerColor);
        if(f.footerLocation == null) {
        	double x = .5 * (xScale[MIN] + xScale[MAX]);
        	double y = .5 * (yScale[MIN] + yValue[MIN]);
        	StdDraw.text(x, y, d.footer);
        }
        else {
        	StdDraw.text(f.footerLocation[0], f.footerLocation[1], d.footer);
        }
    }
	
	

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
