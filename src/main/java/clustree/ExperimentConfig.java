package clustree;

import scala.util.parsing.combinator.testing.Str;

import java.io.Serializable;

public class ExperimentConfig implements Serializable {
    public int[] kVals;
    public String dataPath;
    public String resultPath;
    public int nPoints;
    public int windowSize;
    public int maxHeight;
    public int updatePoints;

    public ExperimentConfig(int[] kVals, String dataPath, String resultPath, int nPoints, int maxHeight, int updatePoints, int windowSize) {
        this.kVals = kVals;
        this.dataPath = dataPath;
        this.resultPath = resultPath;
        this.nPoints = nPoints;
        this.maxHeight = maxHeight;
        this.updatePoints = updatePoints;
    }
}