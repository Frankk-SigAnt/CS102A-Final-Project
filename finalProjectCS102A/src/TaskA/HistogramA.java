package TaskA;

import java.awt.Color;
import java.awt.Font;

class Canvas {
    int x = 512, y = 512;
    double[] xScale = { 0, 1.0 }; // MIN, MAX
    double[] yScale = { 0, 1.0 }; // MIN, MAX
    Color bgColor = Color.WHITE;
    Color color = Color.BLACK;
}

class Formats {
    double[] margins = { 0.15, 0.15, 0.1, 0.05 }; // NORTH, SOUTH, WEST, EAST
    boolean isBarFilled = true;
    Color barFillColor = null;//null by default
    boolean hasBarFrame = false;
    Color barFrameColor = Color.BLACK;
    boolean hasBorder = true;
    Color borderColor = Color.BLACK;
    Color rulerColor = Color.BLACK;
    Color rulerMarkColor = Color.BLACK;
    boolean hasRightRuler = true;
    Color keyColor = Color.BLACK;
    boolean hasHeader = true;
    Color headerColor = Color.BLACK;
    boolean hasFooter = true;
    Color footerColor = Color.BLACK;
    
    DefaultBarColor dc = new DefaultBarColor();
    
    Font rulerFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    Font keysFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    Font headerFont = new Font("Bodoni MT",Font.BOLD,24);
    Font footerFont = new Font("Candara", Font.BOLD,16);
    
    double[] headerLocation;
    double[] footerLocation;
    
}

class DefaultBarColor{ // have a changing color when data increase,by wang
	Color sBarColor = new Color(0,153,76);
	Color mBarColor = new Color(128,255,0);
	Color lBarColor = new Color(204,255,153);
	Color xlBarColor = new Color(255,255,153);
}

class HistogramData {
    String header = "";
    String footer = "";
    double minValue = 0.0;
    String[] keys = {};
    double[] values = {};
}

public class HistogramA {
    Canvas c;
    Formats f;
    HistogramData d;
    double[] xValue; // MIN, MAX
    double[] yValue; // MIN, MAX
    double[] xScale; // MIN, MAX
    double[] yScale; // MIN, MAX
    int rulerGrade;
    double rulerStep;

    public HistogramA(Canvas c, Formats f, HistogramData d) {
        this.c = c;
        this.f = f;
        this.d = d;
        xValue = new double[2];
        yValue = new double[2];
        xScale = new double[2];
        yScale = new double[2];
        setHistogramParameters();
    }

    private void setHistogramParameters() {
        double[] a = d.values;
        xValue[MIN] = -1;
        xValue[MAX] = a.length;

        yValue[MIN] = d.minValue;

        double max = a[0];
        for (int i = 1; i < a.length; i++)
            if (max < a[i])
                max = a[i];

        double span = max - yValue[MIN];
        double factor = 1.0;
        if (span >= 1)
            while (span >= 10) {
                span /= 10;
                factor *= 10;
            }
        else
            while (span < 1) {
                span *= 10;
                factor /= 10;
            }
        int nSpan = (int) Math.ceil(span);
        yValue[MAX] = yValue[MIN] + factor * nSpan;
        switch (nSpan) {
        case 1:
            rulerGrade = 5;
            rulerStep = factor / 5;
            break;
        case 2:
        case 3:
            rulerGrade = nSpan * 2;
            rulerStep = factor / 2;
            break;
        default:
            rulerGrade = nSpan;
            rulerStep = factor;
            break;
        }
    }

    public void draw() {
        setCanvas();
        plotBars();
        plotRuler();
        plotKeys();
        if (f.hasBorder)
            plotBorder();
        if (f.hasRightRuler)
            plotRightRuler();
        if (f.hasHeader)
            plotHeader();
        if (f.hasFooter)
            plotFooter();
    }

    private void setCanvas() {
        StdDraw.setCanvasSize(c.x, c.y);
        setOriginalScale();
        StdDraw.clear(c.bgColor);
        StdDraw.setPenColor(c.color);
    }

    private void setHistogramScale(int nBars) {
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

    private void setOriginalScale() {
        StdDraw.setXscale(c.xScale[MIN], c.xScale[MAX]);
        StdDraw.setYscale(c.yScale[MIN], c.yScale[MAX]);
    }

    private void plotBars() {
        double[] a = d.values;
        int n = a.length;
        setHistogramScale(n);
        
        if (f.isBarFilled) {
        	if(f.barFillColor !=null) {
        		StdDraw.setPenColor(f.barFillColor);
        		for (int i = 0; i < n; i++) {
                StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
                // (x, y, halfWidth, halfHeight)
        		}
        	}
            else {
            	for (int i = 0; i < n; i++) {
            		if(a[i]/2 < a[n-1]/8) {
            			StdDraw.setPenColor(f.dc.sBarColor);
            			StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
            		}
            		
            		else if(a[i]/2 < a[n-1]/6) {
            			StdDraw.setPenColor(f.dc.mBarColor);
            			StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
            		}
            		
            		else if(a[i]/2 < a[n-1]/4) {
            			StdDraw.setPenColor(f.dc.lBarColor);
            			StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
            		}
            		
            		else{
            			StdDraw.setPenColor(f.dc.xlBarColor);
            			StdDraw.filledRectangle(i, a[i] / 2, 0.25, a[i] / 2);
            		}
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

    private void plotRuler() {
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

    private String numberForRuler(double x) { // TO BE Customized
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

    private void plotKeys() {
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

    private void plotBorder() {
        double x = .5 * (xValue[MIN] + xValue[MAX]);
        double y = .5 * (yValue[MIN] + yValue[MAX]);
        double halfWidth = .5 * (xValue[MAX] - xValue[MIN]);
        double halfHeight = .5 * (yValue[MAX] - yValue[MIN]);
        StdDraw.setPenColor(f.borderColor);
        StdDraw.rectangle(x, y, halfWidth, halfHeight);
    }

    private void plotRightRuler() {
        StdDraw.setPenColor(f.rulerColor);
        final double x0 = xValue[MAX] - 0.05, x1 = xValue[MAX] + 0.05;
        String[] mark = new String[rulerGrade + 1];
        for (int i = 0; i <= rulerGrade; i++) {
            double y = yValue[MIN] + i * rulerStep;
            mark[i] = numberForRuler(y);
            StdDraw.line(x0, y, x1, y);
        }
    } // DONE

    private void plotHeader() {
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

    private void plotFooter() {
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

    private final static int NORTH = 0;
    private final static int SOUTH = 1;
    private final static int WEST = 2;
    private final static int EAST = 3;
    private final static int MIN = 0;
    private final static int MAX = 1;
}
