package TaskB;

public class HistogramData {
    String header = "";
    String footer = "";
    String source = ""; //add for URL source shown in figure
    String[] groupMembers = {}; 
    double minValue = 0.0;
    String[] keys = {};
    int groupNumber = 0;
    double[] values = {};
    RankingMap map = new RankingMap();
    
    public void generateMap() {
        map.generateMap(values.clone());
    }
}

class RankingMap {
    // A bidirectional map for rankings and indexes.
    // Note that the ranking starts from 0.
    private int[] rankings = {};
    private int[] indexes = {};
    
    public void generateMap(double[] values) {
        // Initialization.
        int size = values.length;
        rankings = new int[size];
        indexes = new int[size];
        for (int i = 0; i < size; ++i) {
            rankings[i] = indexes[i] = i;
        }
        
        sort(values, 0, values.length);
    }
    
    public int getRanking(int index) {
        return rankings[index];
    }
    
    public int getIndex(int ranking) {
        return indexes[ranking];
    }
    
    // Using quick sort algorithm.
    // Range: [lIndex, rIndex).
    // Note that the result is in descending order.
    private void sort(double[] values, int lBound, int rBound) {
        // Boundary condition: the size of the range is less than 1, 
        // then the range is sorted.
        if (lBound >= rBound - 1) {
            return;
        }

        // Choose the pivot using Median-of-three method.
        // The pivot is to split the range to 
        // `greater`(left) part and `less`(right) part.
        double pivot = median(values[lBound], values[rBound-1], values[(lBound+rBound)/2]);
        int lIndex = lBound, rIndex = rBound;
        while (true) {
            while (values[lIndex] > pivot) {
                ++lIndex;
            }
            --rIndex;
            while (values[rIndex] < pivot) {
                --rIndex;
            }
            if (lIndex >= rIndex) {
                // Recursive to solve sub-problem.
                sort(values, lBound, lIndex);
                sort(values, lIndex, rBound);
            }
            else {
                double t = values[lIndex];
                values[lIndex] = values[rIndex];
                values[rIndex] = t;
                swapRankings(lIndex, rIndex);
                ++lIndex;
            }
        }
    }
    
    private double median(double a, double b, double c) {
        if (a >= b) {
            double t = a;
            a = b;
            b = t;
        }
        // Now that a < b.
        if (c < a) {
            return a;
        }
        else if (c > b) {
            return b;
        }
        else {
            return c;
        }
    }
    
    private void swapRankings(int lIndex, int rIndex) {
        int tRanking = rankings[lIndex];
        rankings[lIndex] = rankings[rIndex];
        rankings[rIndex] = tRanking;
        indexes[rankings[lIndex]] = lIndex;
        indexes[rankings[rIndex]] = rIndex;
    }
}