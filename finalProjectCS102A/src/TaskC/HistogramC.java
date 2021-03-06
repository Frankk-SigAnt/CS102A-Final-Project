package TaskC;

import java.awt.Color;
import java.awt.Font;

import TaskB.Canvas;
import TaskB.Formats;
import TaskB.HistogramBase;

public class HistogramC extends HistogramBase {
	protected AnimationData a, aPrev, aNext;
    private static final int TOTAL_BARS = 16;
    private static final int FRAMES_PER_SEC = 20;

    public HistogramC(Canvas c, Formats f, AnimationData a) {
    	super(c,f);
    	this.a = a;
    	this.aPrev = a.clone();
    	this.aNext = a.clone();
        
        f.margins[NORTH] = 0.12;
        f.margins[SOUTH] = 0.12;
        f.margins[WEST] = 0.2;
        f.margins[EAST] = 0.15;
        
        // setHistogramParameters();
    }
    
    @Override
    protected void setHistogramParameters() {
        yValue[MIN] = -1;
        yValue[MAX] = a.values.length;

        xValue[MIN] = a.minValue;
        xValue[MAX] = a.values[a.mapIndex(0)];

        double max = xValue[MAX];
        double span = max - xValue[MIN];
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
        int nSpan = (int) Math.floor(span);
        span = max - yValue[MIN];
        switch (nSpan) {
        case 1:
            rulerStep = factor / 4;
        	rulerGrade = (int) Math.floor(span / rulerStep);
            break;
        case 2:
            rulerStep = factor / 2;
            rulerGrade = (int) Math.floor(span / rulerStep);
            break;
        default:
            rulerStep = factor;
            rulerGrade = nSpan;
            break;
        }
    }
    
    boolean isFirstFrame = true;

    @Override
    public void draw() {
        setHistogramParameters();

        if (isFirstFrame) {
            setCanvas();
            StdDraw.enableDoubleBuffering();
            isFirstFrame = false;
        }
        else
            StdDraw.clear();
        plotRuler();
        plotBars();
        plotKeys();
        plotYearNumber();
        if (f.hasHeader)
        	plotHeader();
        StdDraw.show();
        StdDraw.pause(1000 / FRAMES_PER_SEC);
    }

    @Override
    protected void setCanvas() {
        StdDraw.setCanvasSize(c.x, c.y);
        setOriginalScale();
        StdDraw.clear(c.bgColor);
        StdDraw.setPenColor(c.color);
    }

    @Override
    protected void setHistogramScale(int nBars) {
        double span = xValue[MAX] - xValue[MIN] + 1;
        double xSpacing = span / (1 - f.margins[WEST] - f.margins[EAST]);
        xScale[MIN] = xValue[MIN] - f.margins[WEST] * xSpacing - 1;
        xScale[MAX] = xValue[MAX] + f.margins[EAST] * xSpacing;
        StdDraw.setXscale(xScale[MIN], xScale[MAX]);

        double ySpacing = (nBars + 1) / (1 - f.margins[NORTH] - f.margins[SOUTH]);
        yScale[MIN] = -f.margins[SOUTH] * ySpacing - 1;
        yScale[MAX] = nBars + f.margins[NORTH] * ySpacing;
        StdDraw.setYscale(yScale[MIN], yScale[MAX]);
    }
    
    @Override
    protected void setOriginalScale() {
        StdDraw.setXscale(c.xScale[MIN], c.xScale[MAX]);
        StdDraw.setYscale(c.yScale[MIN], c.yScale[MAX]);
    }

    @Override
    protected void plotRuler() {
        setHistogramScale(TOTAL_BARS);

        StdDraw.setFont(f.rulerFont);
        StdDraw.setPenColor(f.rulerColor);
        final double y0 = yValue[MIN], y1 = yValue[MAX] * 0.52;
        String[] mark = new String[rulerGrade + 1];
        for (int i = 0; i <= rulerGrade; i++) {
            double x = xValue[MIN] + i * rulerStep;
            mark[i] = numberForRuler(x);
            StdDraw.line(x, y0, x, y1);
        }
        int len = maxMarkLength(mark);
        final double ys = yScale[MIN] + 0.7 * (yValue[MIN] - yScale[MIN]);
        for (int i = 0; i <= rulerGrade; i++) {
            double x = xValue[MIN] + i * rulerStep;
            StdDraw.text(x, ys, String.format("%" + len + "s", mark[i]));
        }
    }

    @Override
    protected String numberForRuler(double x) {
        if (xValue[MAX] >= 5 && rulerStep > 1)
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

    @Override
    protected void plotBars() {
        final double halfHeight = 0.25;
        StdDraw.setFont(f.keysFont);
        // Assume that the bars are filled and have no frames.
        for (int index = 0; index < a.values.length; ++index) {
            double yPosition = TOTAL_BARS - 1 - a.yPositions[index];
            double halfWidth = a.values[index] / 2 / a.values[a.mapIndex(0)] * (xValue[MAX] - xValue[MIN]);
            if (yPosition >= 0) {
            	int alpha = 255; 
            	if(yPosition == 0) {
            		alpha = (int)(1-yPosition)*255;
            	}
            	Color tempColor = new Color(
            			f.getGroupedColor(index).getRed(),
            			f.getGroupedColor(index).getGreen(),
                		f.getGroupedColor(index).getBlue(),
                		alpha);
                StdDraw.setPenColor(tempColor);
                StdDraw.filledRectangle(halfWidth, yPosition, halfWidth, halfHeight);
                final double push = 0.01 * (xValue[MAX] - xValue[MIN]);
                StdDraw.textLeft(halfWidth * 2 + push, yPosition, String.format("%.0f", a.values[index]));
            }
            else if (yPosition >= -1) {
            	Color tempColor = new Color(
            			f.getGroupedColor(index).getRed(),
            			f.getGroupedColor(index).getGreen(),
                		f.getGroupedColor(index).getBlue(),
                		(int) (255*(1+yPosition)));
                StdDraw.setPenColor(tempColor);
                StdDraw.filledRectangle(halfWidth, yPosition, halfWidth, halfHeight);
                final double push = 0.01 * (xValue[MAX] - xValue[MIN]);
                StdDraw.textLeft(halfWidth * 2 + push, yPosition, String.format("%.0f", a.values[index]));
                
            }
        }
    }
    
    @Override
    protected void plotKeys() {
        StdDraw.setFont(f.keysFont);
        double xPosition = xValue[MIN] - 0.01 * (xValue[MAX] - xValue[MIN]);
        for (int index = 0; index < a.values.length; ++index) {
            double yPosition = TOTAL_BARS - 1 - a.yPositions[index];
            if (yPosition >= 0) {
                StdDraw.setPenColor(f.getGroupedColor(index));
                StdDraw.textRight(xPosition, yPosition, a.keys[index]);
            }
            else if (yPosition >= -1) {
            	Color tempColor = new Color(
            			f.getGroupedColor(index).getRed(),
            			f.getGroupedColor(index).getGreen(),
                		f.getGroupedColor(index).getBlue(),
                		(int) (255*(1+yPosition)));
                StdDraw.setPenColor(tempColor);
            	
                StdDraw.textRight(xPosition, yPosition, a.keys[index]);
            }
        }
    }

    @Override
    protected void plotHeader() {
        StdDraw.setFont(f.headerFont);
        StdDraw.setPenColor(f.headerColor);
        if (f.headerLocation == null) {
            double x = .4 * (xScale[MAX] );
            double y = .55 * (yValue[MAX] );
            StdDraw.text(x, y, a.header);
        } else {
            StdDraw.text(f.headerLocation[0], f.headerLocation[1], a.header);
        }
    }

    private void plotYearNumber() {
        StdDraw.setFont(new Font("Candara", Font.BOLD, 40));
        StdDraw.setPenColor(Color.DARK_GRAY);
        final double x = xScale[MAX] * 0.87;
        final double y = yScale[MIN] + yScale[MAX] * 0.15;
        StdDraw.text(x, y, a.year);
    }

    // These methods will not be used in Task C.
    @Override
    protected void plotFooter() {
    }

    @Override
    protected void plotLegends() {
    }

    @Override
    protected void plotBorder() {
    }

    @Override
    protected void plotRightRuler() {
    }

}
