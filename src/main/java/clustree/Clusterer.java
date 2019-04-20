package clustree;


import com.clust4j.algo.KMedoids;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import moa.MOAObject;
import moa.cluster.Clustering;
import moa.core.Measurement;
import moa.gui.AWTRenderable;
import moa.options.OptionHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface Clusterer extends MOAObject, OptionHandler, AWTRenderable {
    void setModelContext(InstancesHeader var1);

    InstancesHeader getModelContext();

    void traverse();
    Node getRealRoot();

//    Node[] traverse();

    ArrayList<Node> getLeaves(Node node);
    Node getRoot();

    boolean isRandomizable();

    double getNegLambda();

    void setRandomSeed(int var1);

    boolean trainingHasStarted();

    double trainingWeightSeenByModel();

    void resetLearning();

    void setConfig(ExperimentConfig config);
    long getTimeStamp();

    void trainOnInstance(Instance var1);

    void trainOnInstanceImpl(Instance varl);

//    void trainOnDoubleArray(ArrayList<Double> varl);

    ArrayList<double[]> collectLeafMedoids();
    KMedoids getKMedoids(int k);
    ArrayList<double[]> fakeMedoids(int k);
    ArrayList<double[]> collectResidualPoints();

    double[] getVotesForInstance(Instance var1);

    Measurement[] getModelMeasurements();

    moa.clusterers.Clusterer[] getSubClusterers();

    moa.clusterers.Clusterer copy();

    Clustering getClusteringResult();

    boolean implementsMicroClusterer();

    Clustering getMicroClusteringResult();

    boolean keepClassLabel();

    ArrayList<Node> collectLeafNodes(Node curr);

//    Node getRealRoot();
}