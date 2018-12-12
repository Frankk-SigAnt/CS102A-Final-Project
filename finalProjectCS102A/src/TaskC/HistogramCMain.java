package TaskC;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import TaskB.Canvas;
import TaskB.Formats;
import TaskB.HistogramData;
import TaskB.HistogramGrouped;

public class HistogramCMain {
    
    static HistogramC h;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("JSON file name required in argument.");
        }
        StdDraw.enableDoubleBuffering();
        h = createHistogramCFrom(args[0]);
        h.draw();
        iterateHistogram();
    }

    private static void iterateHistogram() {
        for (int i = 1; i < h.a.allValues.length; ++i) {
            h.a.values = h.a.allValues[i];
            h.draw();
        }
    }
    
    private static HistogramC createHistogramCFrom(String fileName) {
    	HistogramC h = null;
        try (InputStream is = new FileInputStream(new File(fileName)); JsonReader rdr = Json.createReader(is)) {
            JsonObject obj = rdr.readObject().getJsonObject("histograma");
            Canvas canvas = getCanvasFrom(obj.getJsonObject("canvas"));
            Formats fmts = getFormatsFrom(obj.getJsonObject("formats"));
            AnimationData data = getDataFrom(obj.getJsonObject("data"));
            h = new HistogramC(canvas, fmts, data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ;
        return h;
    }

    private static Canvas getCanvasFrom(JsonObject obj) {
        Canvas canvas = new Canvas();

        JsonArray szArray = obj.getJsonArray("size");
        if (szArray != null) { // otherwise, use the default size
            int[] size = toIntArray(szArray);
            canvas.x = size[0];
            canvas.y = size[1];
        }

        JsonArray xsArray = obj.getJsonArray("xscale");
        if (xsArray != null) // otherwise, use the default xScale
            canvas.xScale = toDoubleArray(xsArray);

        JsonArray ysArray = obj.getJsonArray("yscale");
        if (ysArray != null) // otherwise, use the default yScale
            canvas.yScale = toDoubleArray(ysArray);

        JsonArray bgcArray = obj.getJsonArray("bgcolor");
        if (bgcArray != null) // otherwise, use the default bgColor
            canvas.bgColor = getColorFrom(bgcArray);

        JsonArray cArray = obj.getJsonArray("color");
        if (cArray != null) // otherwise, use the default color
            canvas.color = getColorFrom(cArray);

        return canvas;
    }

    private static int[] toIntArray(JsonArray jsa) {
        int[] a = new int[jsa.size()];
        for (int i = 0; i < jsa.size(); i++)
            a[i] = jsa.getInt(i);
        return a;
    }

    private static double[] toDoubleArray(JsonArray jsa) {
        double[] a = new double[jsa.size()];
        for (int i = 0; i < jsa.size(); i++)
            a[i] = jsa.getJsonNumber(i).doubleValue();
        return a;
    }

    private static String[] toStringArray(JsonArray jsa) {
        String[] s = new String[jsa.size()];
        for (int i = 0; i < jsa.size(); i++)
            s[i] = jsa.getString(i);
        return s;
    }

    private static Color[] toColorArray(JsonArray jsa) {
    	Color[] c = new Color[jsa.size()];
    	int[][] temp = new int[jsa.size()][];
    	for(int i = 0; i < jsa.size(); i++) {
    		temp[i] = toIntArray(jsa.getJsonArray(i));
    		c[i] = new Color(temp[i][0],temp[i][1],temp[i][2]);
    	}
    	return c;
    }
    
    private static Color getColorFrom(JsonArray jsa) {
        int[] c = toIntArray(jsa);
        return new Color(c[0], c[1], c[2]);
    }
    
    private static double[][] getAllValues(JsonArray jsa) {
    	double[][] temp = new double[jsa.size()][];
    	for(int i = 0; i < jsa.size(); i++) {
    		temp[i] = toDoubleArray(jsa.getJsonArray(i));
    	}
    	return temp;
    }
    //add for font getting
    private static Font getFontFrom(JsonArray jsa) {
    	String[] f = toStringArray(jsa);
    	switch (f[1]) {//for font style 
		case "BOLD":
			return new Font(f[0],Font.BOLD,Integer.parseInt(f[2]));
		case "PLAIN":
			return new Font(f[0],Font.PLAIN,Integer.parseInt(f[2]));
		case "ITALIC":
			return new Font(f[0],Font.ITALIC,Integer.parseInt(f[2]));
		case "BOLD + ITALIC":
			return new Font(f[0],Font.BOLD+Font.ITALIC,Integer.parseInt(f[2]));
		}
    	return null;
    }

    private static Formats getFormatsFrom(JsonObject obj) { // DONE for default values
        Formats fmts = new Formats();
        if (obj.containsKey("margins")) {
            fmts.margins = toDoubleArray(obj.getJsonArray("margins"));
        }
        if (obj.containsKey("isbarfilled")) {
            fmts.isBarFilled = obj.getBoolean("isbarfilled");
        }
        if (obj.containsKey("barfillcolor")) {
            fmts.barFillColor = getColorFrom(obj.getJsonArray("barfillcolor"));
        }
        if (obj.containsKey("hasbarframe")) {
            fmts.hasBarFrame = obj.getBoolean("hasbarframe");
        }
        if (obj.containsKey("barframecolor")) {
            fmts.barFrameColor = getColorFrom(obj.getJsonArray("barframecolor"));
        }
        if (obj.containsKey("hasborder")) {
            fmts.hasBorder = obj.getBoolean("hasborder");
        }
        if (obj.containsKey("bordercolor")) {
            fmts.borderColor = getColorFrom(obj.getJsonArray("bordercolor"));
        }
        if (obj.containsKey("rulercolor")) {
            fmts.rulerColor = getColorFrom(obj.getJsonArray("rulercolor"));
        }
        if (obj.containsKey("rulermarkcolor")) {
            fmts.rulerMarkColor = getColorFrom(obj.getJsonArray("rulermarkcolor"));
        }
        if (obj.containsKey("isbarfilled")) {
            fmts.hasRightRuler = obj.getBoolean("isbarfilled");
        }
        if (obj.containsKey("keycolor")) {
            fmts.keyColor = getColorFrom(obj.getJsonArray("keycolor"));
        }
        if (obj.containsKey("hasheader")) {
            fmts.hasHeader = obj.getBoolean("hasheader");
        }
        if (obj.containsKey("headercolor")) {
            fmts.headerColor = getColorFrom(obj.getJsonArray("headercolor"));
        }
        if (obj.containsKey("hasfooter")) {
            fmts.hasFooter = obj.getBoolean("hasfooter");
        }
        if (obj.containsKey("hasSource")) {
            fmts.hasSource = obj.getBoolean("hasSource");
        }
        if (obj.containsKey("footercolor")) {
            fmts.footerColor = getColorFrom(obj.getJsonArray("footercolor"));
        }
        //add for font getting judge
        if (obj.containsKey("rulerFont")) {
            fmts.rulerFont = getFontFrom(obj.getJsonArray("rulerFont"));
        }
        if (obj.containsKey("keysFont")) {
            fmts.keysFont = getFontFrom(obj.getJsonArray("keysFont"));
        }
        if (obj.containsKey("headerFont")) {
            fmts.headerFont = getFontFrom(obj.getJsonArray("headerFont"));
        }
        if (obj.containsKey("footerFont")) {
            fmts.footerFont = getFontFrom(obj.getJsonArray("footerFont"));
        }
        //add for getting text location
        if (obj.containsKey("footerLocation")) {
            fmts.footerLocation = toDoubleArray(obj.getJsonArray("footerLocation"));
        }
        if (obj.containsKey("headerLocation")) {
            fmts.headerLocation = toDoubleArray(obj.getJsonArray("headerLocation"));
        }
        if (obj.containsKey("barsColor")) {
            fmts.setGroupedColor(toColorArray(obj.getJsonArray("barsColor")));
        }
        
        return fmts;
    }

    private static AnimationData getDataFrom(JsonObject obj) {
    	
        AnimationData data = new AnimationData();
        data.header = obj.getString("header", "");
        data.footer = obj.getString("footer", "");
        data.source = obj.getString("source", "");
        if (obj.containsKey("minvalue")) {
            data.minValue = obj.getJsonNumber("minvalue").doubleValue(); // DONE for default value
        }
        data.years = toStringArray(obj.getJsonArray("years"));
        data.keys = toStringArray(obj.getJsonArray("keys"));
        double[][] allValuesTemp = getAllValues(obj.getJsonArray("values"));
        if(obj.containsKey("groupNumber"))
        	data.groupNumber = obj.getJsonNumber("groupNumber").intValue();
        if(obj.containsKey("groupMembers"))
        	data.groupMembers = toStringArray(obj.getJsonArray("groupMembers"));
        
        int x = allValuesTemp.length;
        int y = allValuesTemp[0].length;
        data.allValues =new double[y][x]; 
        
        
        try {
        for(int i = 0; i < x; i ++) {
        	for(int j = 0; j < y; j++) {
        		data.allValues[j][i] = allValuesTemp[i][j];
        	}
        }
        }catch(ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
        
        data.values = data.allValues[0];
        data.generateMap();
        return data;
    }

}
