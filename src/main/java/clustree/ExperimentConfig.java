package clustree;

import scala.util.parsing.combinator.testing.Str;

import java.io.Serializable;
import java.util.Arrays;

public class ExperimentConfig implements Serializable {
    public int[] kVals;
    public String dataPath;
    public String resultPath;
    public int nPoints;
    public int windowSize;
    public int maxHeight;
    public int updatePoints;
    public int numIters;

    public ExperimentConfig(int[] kVals, String dataPath, String resultPath, int nPoints, int maxHeight, int updatePoints, int windowSize, int numIters) {
        this.kVals = kVals;
        this.dataPath = dataPath;
        this.resultPath = resultPath;
        this.nPoints = nPoints;
        this.maxHeight = maxHeight;
        this.updatePoints = updatePoints;
        this.numIters = numIters;
    }

    @Override
    public String toString() {
        return "ExperimentConfig{" +
                "kVals=" + Arrays.toString(kVals) +
                ", dataPath='" + dataPath + '\'' +
                ", resultPath='" + resultPath + '\'' +
                ", nPoints=" + nPoints +
                ", windowSize=" + windowSize +
                ", maxHeight=" + maxHeight +
                ", updatePoints=" + updatePoints +
                ", numIters=" + numIters +
                '}';
    }
}
