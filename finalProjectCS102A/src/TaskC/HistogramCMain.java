package TaskC;

public class HistogramCMain {
    
    static HistogramC h;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("JSON file name required in argument.");
        }
        StdDraw.enableDoubleBuffering();
        h = createHistogramFrom(args[0]);
        h.draw();
        StdDraw.show();
        // iterateHistogram();
    }

    private static HistogramC createHistogramFrom(String string) {
        // TODO Auto-generated method stub
        return null;
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
