package clustree;


import java.util.ArrayList;

public class Dummy {
    public static void main(String[] args) {

        ArrayList<ExperimentConfig> experimentConfigs = new ArrayList<>();

        int[] kVals = new int[]{10, 25, 50};
        String dataPath = "covtype.csv";
        String resultPath = "results/";
        int nPoints = 580000;

        experimentConfigs.add(
                new ExperimentConfigBuilder().maxHeight(10).updatePoints(200).numIters(3).buildExperimentConfig()
        );

        experimentConfigs.add(
                new ExperimentConfigBuilder().maxHeight(10).updatePoints(150).numIters(3).buildExperimentConfig()
        );

        experimentConfigs.add(
                new ExperimentConfigBuilder().maxHeight(10).updatePoints(250).numIters(3).buildExperimentConfig()
        );

        experimentConfigs.add(
                new ExperimentConfigBuilder().maxHeight(10).updatePoints(300).numIters(3).buildExperimentConfig()
        );

        for (ExperimentConfig config: experimentConfigs) {
            Experiment experiment = new Experiment(config);
            experiment.run();
            experiment.printReports();
        }
    }

}
