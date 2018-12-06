package TaskB;

import java.awt.Color;
import java.awt.Font;

public class Formats {
    public double[] margins = { 0.15, 0.15, 0.1, 0.05 }; // NORTH, SOUTH, WEST, EAST
    public boolean isBarFilled = true;
    public Color barFillColor = null;//null by default
    public boolean hasBarFrame = false;
    public Color barFrameColor = Color.BLACK;
    public boolean hasBorder = true;
    public Color borderColor = Color.BLACK;
    public Color rulerColor = Color.BLACK;
    public Color rulerMarkColor = Color.BLACK;
    public boolean hasRightRuler = true;
    public Color keyColor = Color.BLACK;
    public boolean hasHeader = true;
    public Color headerColor = Color.BLACK;
    public boolean hasFooter = true;
    public Color footerColor = Color.BLACK;
    
    public DefaultBarColor dc = new DefaultBarColor();
    
    public Font rulerFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    public Font keysFont = new Font("Comic Sans MS", Font.BOLD, 15);
    public Font headerFont = new Font("Bodoni MT",Font.BOLD,24);
    public Font footerFont = new Font("Candara", Font.BOLD,16);
    public Font legendsFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    
    public double[] headerLocation;
    public double[] footerLocation;
}

class DefaultBarColor{ // have a changing color when data increase,by wang
	Color sBarColor = new Color(0,153,76);
	Color mBarColor = new Color(128,255,0);
	Color lBarColor = new Color(204,255,153);
	Color xlBarColor = new Color(255,255,153);
}
