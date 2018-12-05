package TaskB;

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
    String source = ""; //add for URL source shown in figure
    double minValue = 0.0;
    String[] keys = {};
    double groupNumber;
    double[] values = {};
}

// Defining abstract base class for Task B
public abstract class HistogramBase {
    Canvas c;
    Formats f;
    HistogramData d;
    double[] xValue; // MIN, MAX
    double[] yValue; // MIN, MAX
    double[] xScale; // MIN, MAX
    double[] yScale; // MIN, MAX
    int rulerGrade;
    double rulerStep;

    public HistogramBase(Canvas c, Formats f, HistogramData d) {
        this.c = c;
        this.f = f;
        this.d = d;
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

    public abstract void draw();

    protected abstract void setCanvas();

    protected abstract void setHistogramScale(int nBars);
    
    protected abstract void setOriginalScale();

    protected abstract void plotBars();

    protected abstract void plotRuler();

    protected abstract String numberForRuler(double x);

    private int maxMarkLength(String[] sa) {
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
