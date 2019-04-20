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
    private int _numIters = 1;
    private int _queueSize = 700;
    private int _threadK = 30;

    public ExperimentConfigBuilder() {}

    public ExperimentConfig buildExperimentConfig() {
        return new ExperimentConfig(this._kVals, this._dataPath, this._resultPath, this._nPoints, this._maxHeight, this._updatePoints, this._windowSize,this._numIters, this._queueSize, this._threadK);
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

    public ExperimentConfigBuilder numIters(int _numIters) {
        this._numIters = _numIters;
        return this;
    }

    public ExperimentConfigBuilder queueSize(int _queueSize) {
        this._queueSize = _queueSize;
        return this;
    }

    public ExperimentConfigBuilder threadK(int _threadK) {
        this._threadK = _threadK;
        return this;
    }

}
