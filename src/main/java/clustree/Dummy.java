package clustree;


import java.util.ArrayList;

public class Dummy {
    public static void main(String[] args) {

        ArrayList<ExperimentConfig> experimentConfigs = new ArrayList<>();

        int[] kVals = new int[]{10, 25, 50};
        String dataPath = "covtype.csv";
        String resultPath = "results/";
        int nPoints = 580000;


        // Use ExperimentConfigBuilder the like I've done below. Refer to the builder class to see all parameters

        // Tree with maxHeight 8
        experimentConfigs.add(
                new ExperimentConfigBuilder().maxHeight(8).buildExperimentConfig());

        // Tree with maxHeight 12 where PAM is applied on Entries every 100 new points
        experimentConfigs.add(
                new ExperimentConfigBuilder().maxHeight(12).updatePoints(100).buildExperimentConfig()
        );

        experimentConfigs.add(
                new ExperimentConfigBuilder().updatePoints(50).buildExperimentConfig()
        );

        for (ExperimentConfig config: experimentConfigs) {
            Experiment experiment = new Experiment(config);
            experiment.run();
            experiment.printReports();
        }
    }

}
