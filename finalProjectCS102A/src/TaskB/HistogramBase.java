package TaskB;

import java.awt.Color;
import java.awt.Font;

// Defining abstract base class for Task B
public abstract class HistogramBase {
    protected Canvas c;
    protected Formats f;
    protected HistogramData d;
    protected double[] xValue; // MIN, MAX
    protected double[] yValue; // MIN, MAX
    protected double[] xScale; // MIN, MAX
    protected double[] yScale; // MIN, MAX
    protected int rulerGrade;
    protected double rulerStep;

    public HistogramBase(Canvas c, Formats f, HistogramData d) {
        this.c = c;
        this.f = f;
        this.d = d;
        xValue = new double[2];
        yValue = new double[2];
        xScale = new double[2];
        yScale = new double[2];
    }
    public HistogramBase(Canvas c, Formats f) {
    	this.c = c;
    	this.f = f;
    	xValue = new double[2];
    	yValue = new double[2];
    	xScale = new double[2];
    	yScale = new double[2];
    }

    protected void setHistogramParameters() {
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
        if (span >= 1) {
            while (span >= 10) {
                span /= 10;
                factor *= 10;
            }
        } else {
            while (span < 1) {
                span *= 10;
                factor /= 10;
            }
        }
        int nSpan = (int) Math.floor(span);
        span = max - yValue[MIN];
        yValue[MAX] = yValue[MIN] + factor * nSpan;
        switch (nSpan) {
        case 1:
        	rulerGrade = (int) Math.floor(span / (factor / 5));
            rulerStep = factor / 5;
            break;
        case 2:
            rulerGrade = (int) Math.floor(span / (factor / 2));
            rulerStep = factor / 2;
            break;
        default:
            rulerGrade = nSpan;
            rulerStep = factor;
            break;
        }
    }

    public abstract void draw();

    protected abstract void setCanvas();

    protected abstract void setHistogramScale(int nBars);
    
    protected abstract void setOriginalScale();

    protected abstract void plotBars();

    protected abstract void plotRuler();

    protected abstract String numberForRuler(double x);

    protected int maxMarkLength(String[] sa) {
        int n = sa[0].length();
        for (String s : sa)
            if (n < s.length())
                n = s.length();
        return n;
    }

    protected abstract void plotKeys();
    
    protected abstract void plotLegends();

    protected abstract void plotBorder();

    protected abstract void plotRightRuler();

    protected abstract void plotHeader();

    protected abstract void plotFooter();

    protected final static int NORTH = 0;
    protected final static int SOUTH = 1;
    protected final static int WEST = 2;
    protected final static int EAST = 3;
    protected final static int MIN = 0;
    protected final static int MAX = 1;
}
