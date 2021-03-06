package TaskB;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import TaskA.StdDraw;

public class HistogramStacked extends HistogramBase {

    public HistogramStacked(Canvas c, Formats f, HistogramData d) {
        super(c, f, d);
        c.x = 768;
        f.hasBorder = false;
		f.hasFooter = false;
		f.hasRightRuler = false;
		f.margins[0] = 0.2;// NORTH
		f.margins[1] = 0.2;// SOUTH
		f.margins[2] = 0.14;// WEST
		f.margins[3] = 0.14;// EAST
		
		v.add((double)0);
		for (int i = 0; i < d.values.length / d.groupNumber; i++) {
			double add = 0;
			for (int j = 0; j < d.groupNumber; j++) {
				add += d.values[i + j];
			}
			v.add(add);
			v.add((double)0);
		}
		
		groups = new double[d.values.length / d.groupNumber][d.groupNumber];
		for (int i = 0; i < d.values.length / d.groupNumber; i++) {
			for (int j = 0; j < d.groupNumber; j++) {
				groups[i][j] = d.values[d.groupNumber * i + j];
			}
		}
		
		groupHeight = new double[groups.length];
		Arrays.fill(groupHeight, 0);
		for (int i = 0; i < groups.length; i++) {
			for (int j = 0; j < groups[i].length; j++) {
				groupHeight[i] += groups[i][j];
			}
		}
		
		this.setHistogramParameters();
    }
    
    @Override
    protected void setHistogramParameters() {
		double[] a = groupHeight;
		xValue[MIN] = -1;
		xValue[MAX] = this.v.size();

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
    
    @Override
    protected void plotBars() {
		int n = v.size();

		if (f.isBarFilled) {
			if (f.barFillColor != null) {
				for (int i = 0; i < n; i++) {
					int barNumber = -1;
					if (i % 2 == 1) {
						barNumber++;
						double wsqmFei = d.minValue;// wsqmFei means “我是起名废”，指新bar的底端
						for (int j = 0; j < d.groupNumber; j++) {
							StdDraw.setPenColor(f.barFillColor);
							StdDraw.filledRectangle(i, groups[barNumber][j] / 2 + wsqmFei, 0.4, groups[barNumber][j] / 2);
							if (f.hasBarFrame) {
								StdDraw.setPenColor(f.barFrameColor);
								StdDraw.rectangle(i, groups[barNumber][j] / 2 + wsqmFei, 0.4, groups[barNumber][j] / 2);
							}
							wsqmFei += groups[barNumber][j];
							// (x, y, halfWidth, halfHeight)
						}
					}
				}
			} else {
				int barNumber = -1;
				for (int i = 0; i < n; i++) {
					if (i % 2 == 1) {
						barNumber++;
						double wsqmFei = d.minValue;
						for (int j = 0; j < d.groupNumber; j++) {
							try {
								StdDraw.setPenColor(f.getGroupedColor()[j]);
							}catch(ArrayIndexOutOfBoundsException e) {}
							StdDraw.filledRectangle(i, groups[barNumber][j] / 2 + wsqmFei, 0.4, groups[barNumber][j] / 2);
							if (f.hasBarFrame) {
								StdDraw.setPenColor(f.barFrameColor);
								StdDraw.rectangle(i, groups[barNumber][j] / 2 + wsqmFei, 0.4, groups[barNumber][j] / 2);
							}
							wsqmFei += groups[barNumber][j];
							// (x, y, halfWidth, halfHeight)
						}
					}
				}
			}
		}

	}
    
    @Override
    protected void plotLegends() {
    	StdDraw.setFont(f.legendsFont);
    	double frameHeight = yValue[MAX] / 3;
    	double halfYPosition = yValue[MAX] - frameHeight / 3;
    	double bottom = halfYPosition - 0.5 * frameHeight;
    	double step = frameHeight / (d.groupNumber + 1);
    	double h = (yScale[MAX] - yScale[MIN]) / 100;
		double w = h * ((xScale[MAX] - xScale[MIN]) / (yScale[MAX] - yScale[MIN]));
		StdDraw.rectangle(xValue[MIN] + 0.2 + 1.05, halfYPosition, 1.05, 0.5 * frameHeight);
		for (int i = 1; i <= d.groupNumber; i++) {
			if (f.isBarFilled) {
			    final double push = 0.67;
				if (f.barFillColor != null) {
					StdDraw.setPenColor(f.barFillColor);
					StdDraw.filledRectangle(xValue[MIN] + 0.3 + 0.2, bottom + i * step, w, h);
					String text = d.groupMembers[i - 1];
					StdDraw.textLeft(xValue[MIN] + push, bottom + i * step, text);
				} else {
					try {
						StdDraw.setPenColor(this.f.getGroupedColor()[i - 1]);
					}catch(ArrayIndexOutOfBoundsException e) {}
					StdDraw.filledRectangle(xValue[MIN] + 0.3 + 0.2, bottom + i * step, w, h);
					String text = d.groupMembers[i - 1];
					StdDraw.textLeft(xValue[MIN] + push, bottom + i * step, text);
				}
			}
		}
	}

	@Override
	public void draw() {
		setCanvas();
		plotRuler();
		plotLeftRuler();
		plotBars();
		plotKeys();
		plotLegends();
		plotSource();
		if (f.hasBorder)
			plotBorder();
		if (f.hasRightRuler)
			plotRightRuler();
		if (f.hasHeader)
			plotHeader();
		if (f.hasFooter)
			plotFooter();
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

	@Override
	protected void setOriginalScale() {
		StdDraw.setXscale(c.xScale[MIN], c.xScale[MAX]);
		StdDraw.setYscale(c.yScale[MIN], c.yScale[MAX]);
	}
	
	/*StdDraw.setPenColor(f.rulerColor);
    final double x0 = xValue[MAX] - 0.05, x1 = xValue[MAX] + 0.05;
    String[] mark = new String[rulerGrade + 1];
    for (int i = 0; i <= rulerGrade; i++) {
        double y = yValue[MIN] + i * rulerStep;
        mark[i] = numberForRuler(y);
        StdDraw.line(x0, y, x1, y);
    }*/

	@Override
	protected void plotRuler() {
		int n = v.size();
		setHistogramScale(n);

		StdDraw.setFont(f.rulerFont);
		StdDraw.setPenColor(f.rulerColor);
		final double x0 = xValue[MIN] - 0.05, x1 = xValue[MAX], x2 = xValue[MIN];
		String[] mark = new String[rulerGrade + 1];
		for (int i = 0; i <= rulerGrade; i++) {
			double y = yValue[MIN] + i * rulerStep;
			mark[i] = numberForRuler(y);
			StdDraw.line(x0, y, x2, y);
		}
		StdDraw.line(x0, yValue[MIN], x1, yValue[MIN]);
		int len = maxMarkLength(mark);
		final double xs = xScale[MIN] + 0.7 * (xValue[MIN] - xScale[MIN]);
		for (int i = 0; i <= rulerGrade; i++) {
			double y = yValue[MIN] + i * rulerStep;
			StdDraw.text(xs, y, String.format("%" + len + "s", mark[i]));
		}
	}
	
	protected void plotLeftRuler() {
		final double x0 = xValue[MIN] - 0.05;
		final double y0 = yValue[MIN]		, y1 = yValue[MAX];
		StdDraw.line(x0, y0, x0, y1);
	}

	@Override
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

	@Override
	protected void plotKeys() {
		StdDraw.setFont(f.keysFont);
		StdDraw.setPenColor(f.keyColor);

		int n = v.size();
		final double y = yValue[MIN] - 0.5 * rulerStep;
		for (int i = 0; i < n; i++) {
			if (i % 2 == 1) {
				int k = i / 2 ;
				if (d.keys[k].length() >= 1) {
					double x = i;
					StdDraw.text(x, y, d.keys[k]);
				}
			}
		}
	}

	@Override
	protected void plotBorder() {
		double x = .5 * (xValue[MIN] + xValue[MAX]);
		double y = .5 * (yValue[MIN] + yValue[MAX]);
		double halfWidth = .5 * (xValue[MAX] - xValue[MIN]);
		double halfHeight = .5 * (yValue[MAX] - yValue[MIN]);
		StdDraw.setPenColor(f.borderColor);
		StdDraw.rectangle(x, y, halfWidth, halfHeight);
	}

	@Override
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

	@Override
	protected void plotHeader() {
		StdDraw.setFont(f.headerFont);
		StdDraw.setPenColor(f.headerColor);
		if (f.headerLocation == null) {
			double x = .5 * (xScale[MIN] + xScale[MAX]);
			double y = .5 * (yValue[MAX] + yScale[MAX]);
			StdDraw.text(x, y, d.header);
		} else {
			StdDraw.text(f.headerLocation[0], f.headerLocation[1], d.header);
		}
	}

	@Override
	protected void plotFooter() {
		StdDraw.setFont(f.footerFont);
		StdDraw.setPenColor(f.footerColor);
		if (f.footerLocation == null) {
			double x = .5 * (xScale[MIN] + xScale[MAX]);
			double y = .5 * (yScale[MIN] + yValue[MIN]);
			StdDraw.text(x, y, d.footer);
		} else {
			StdDraw.text(f.footerLocation[0], f.footerLocation[1], d.footer);
		}
	}
	
	protected void plotSource() {
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setFont(f.sourceFont);
		if (f.hasSource) {
			double x = 0.5 * (xScale[MIN] + xValue[MIN]) + 8;
			double y = 0.6 * (yValue[MIN] + yScale[MIN]);
			StdDraw.text(x, y, "Source: " + d.source);
		}
	}
	
	ArrayList<Double> v = new ArrayList<>();// values with Blanks
	double[][] groups;// group the data
	double[] groupHeight;// calculate each group's height
}
