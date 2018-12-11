package TaskC;

public class HistogramCMain {
    
    static HistogramC h;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("JSON file name required in argument.");
        }
        StdDraw.enableDoubleBuffering();
        createHistogramFrom(args[0]);
        h.draw();
        StdDraw.show();
        // iterateHistogram();
    }

    private static void createHistogramFrom(String string) {
        // TODO Auto-generated method stub
    }

    private static void iterateHistogram() {
        for (;;) {
            StdDraw.clear();
            // TODO Iteration
            h.draw();
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

}
