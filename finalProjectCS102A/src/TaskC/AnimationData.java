package TaskC;

import TaskB.HistogramData;

public class AnimationData extends HistogramData {
	public static double[][] allValues = {};
	public static String[] years = {};

    protected String year = "";
	protected double[] yPositions = {};
    RankingMap map = new RankingMap();
    
    public AnimationData clone() {
        AnimationData clonedObj = new AnimationData();
        clonedObj.year = this.year.toString();
        clonedObj.yPositions = this.yPositions.clone();
        clonedObj.map = this.map.clone();
        return clonedObj;
    }
    
    public void generateMap() {
        map.generateMap(ValuePair.getPairArray(values));
    }
    
    public void getPositions() {
        if (yPositions.length == 0) {
            yPositions = new double[values.length];
        }
        for (int i = 0; i < values.length; ++i) {
            yPositions[i] = map.getRanking(i);
        }
    }
    
    public int mapRanking(int index) {
        return map.getRanking(index);
    }
    
    public int mapIndex(int ranking) {
        return map.getIndex(ranking);
    }

}

class RankingMap {
    // A bidirectional map for rankings and indexes.
    // Note that the ranking starts from 0.
    private int[] rankings = {};
    private int[] indexes = {};
    
    public RankingMap clone() {
        RankingMap clonedObj = new RankingMap();
        clonedObj.rankings = this.rankings.clone();
        clonedObj.indexes = this.indexes.clone();
        return clonedObj;
    }
    
    public void generateMap(ValuePair[] values) {
        // Initialization.
        int size = values.length;
        rankings = new int[size];
        indexes = new int[size];
        for (int i = 0; i < size; ++i) {
            rankings[i] = i;
            indexes[i] = i;
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
    private void sort(ValuePair[] values, int lBound, int rBound) {
        // Boundary condition: the size of the range is less than 1, 
        // then the range is sorted.
        if (lBound >= (rBound - 1)) {
            return;
        }

        double pivot = median(values[lBound].value, values[rBound-1].value, values[(lBound+rBound)/2].value);
        int lIndex = lBound, rIndex = rBound - 1;
        while (lIndex < rIndex) {
            while (lIndex < rIndex && values[lIndex].value >= pivot) ++lIndex;
            while (lIndex < rIndex && values[rIndex].value < pivot) --rIndex;
            swap(values, lIndex, rIndex);
        }
        sort(values, lBound, lIndex);
        sort(values, lIndex, rBound);
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
    
    private void swap(ValuePair[] values, int lIndex, int rIndex) {
        ValuePair t = values[lIndex];
        values[lIndex] = values[rIndex];
        values[rIndex] = t;
        swapRankings(values, lIndex, rIndex);
    }
    
    private void swapRankings(ValuePair[] values, int lIndex, int rIndex) {
        int lhsIndex = values[lIndex].index;
        int rhsIndex = values[rIndex].index;
        int tRanking = rankings[lhsIndex];
        rankings[lhsIndex] = rankings[rhsIndex];
        rankings[rhsIndex] = tRanking;
        indexes[rankings[lhsIndex]] = lhsIndex;
        indexes[rankings[rhsIndex]] = rhsIndex;
    }
}

class ValuePair {
    double value;
    int index;
    
    public ValuePair(double value, int index) {
        this.value = value;
        this.index = index;
    }

    public static ValuePair[] getPairArray(double[] values) {
        int size = values.length;
        ValuePair[] resArray = new ValuePair[size];
        for (int i = 0; i < size; ++i) {
            resArray[i] = new ValuePair(values[i], i);
        }
        return resArray;
    }
}