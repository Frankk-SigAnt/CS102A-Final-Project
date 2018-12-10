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
    public boolean hasSource = false;
    public Color sourceColor = Color.BLACK;
    
    public Font rulerFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    public Font keysFont = new Font("Comic Sans MS", Font.BOLD, 15);
    public Font headerFont = new Font("Bodoni MT",Font.BOLD,24);
    public Font footerFont = new Font("Candara", Font.BOLD,16);
    public Font legendsFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    public Font sourceFont = new Font("Candara", Font.PLAIN,10);
    
    public double[] headerLocation;
    public double[] footerLocation;
    
	Color[] groupedColor = null;
}

