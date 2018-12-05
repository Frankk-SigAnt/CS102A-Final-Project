package TaskB;

import java.awt.Color;
import java.awt.Font;

public class Formats {
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
    Font keysFont = new Font("Comic Sans MS", Font.BOLD, 15);
    Font headerFont = new Font("Bodoni MT",Font.BOLD,24);
    Font footerFont = new Font("Candara", Font.BOLD,16);
    Font legendsFont = new Font("Comic Sans MS", Font.PLAIN, 12);
    
    double[] headerLocation;
    double[] footerLocation;
}

class DefaultBarColor{ // have a changing color when data increase,by wang
	Color sBarColor = new Color(0,153,76);
	Color mBarColor = new Color(128,255,0);
	Color lBarColor = new Color(204,255,153);
	Color xlBarColor = new Color(255,255,153);
}
