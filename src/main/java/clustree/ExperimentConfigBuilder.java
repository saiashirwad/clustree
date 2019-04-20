package clustree;

public class ExperimentConfigBuilder {

    private ExperimentConfig config;

    private int[] _kVals = new int[]{10, 25, 50};
    private String _dataPath = "covtype.csv";
    private String _resultPath = "results/";
    private int _nPoints = 580000;
    private int _windowSize = 350000;
    private int _maxHeight = 10;
    private int _updatePoints = 50;

    public ExperimentConfigBuilder() {}

    public ExperimentConfig buildExperimentConfig() {
        return new ExperimentConfig(this._kVals, this._dataPath, this._resultPath, this._nPoints, this._maxHeight, this._updatePoints, this._windowSize);
    }

    public ExperimentConfigBuilder kVals(int[] _kVals) {
        this._kVals = _kVals;
        return  this;
    }

    public ExperimentConfigBuilder dataPath(String _dataPath) {
        this._dataPath = _dataPath;
        return this;
    }

    public ExperimentConfigBuilder resultPath(String _resultPath) {
        this._resultPath = _resultPath;
        return this;
    }

    public ExperimentConfigBuilder nPoints(int _nPoints) {
        this._nPoints = _nPoints;
        return this;
    }

    public ExperimentConfigBuilder windowSize(int _windowSize) {
        this._windowSize = _windowSize;
        return this;
    }

    public ExperimentConfigBuilder maxHeight(int _maxHeight) {
        this._maxHeight = _maxHeight;
        return this;
    }

    public ExperimentConfigBuilder updatePoints(int _updatePoints) {
        this._updatePoints = _updatePoints;
        return this;
    }

}
