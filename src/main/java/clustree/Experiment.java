package clustree;

import com.clust4j.algo.KMedoids;
import com.yahoo.labs.samoa.instances.Instance;

import java.time.Instant;
import java.util.ArrayList;

public class Experiment {
    private ExperimentConfig config;
    private ArrayList<Report> reports;
    public Clusterer learner = new ClusTree();

    public Experiment(ExperimentConfig config) {

        this.config = config;
        this.reports = new ArrayList<>();
    }

    public void run() {
        // may not work out so well with the InputHandler thread
        int i = config.nPoints;

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue(config.dataPath);
        stream.prepareForUse();

        // Add arguments to function
        learner.resetLearning();
        learner.setConfig(config);
        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();


        Instant begin = Instant.now();

        while (stream.hasMoreInstances() && i > 0) {
            Instance inst = stream.nextInstance().instance;
            int n = inst.numAttributes();
            learner.trainOnInstance(inst);
            i--;
        }

        performExperiment();

        // Join PAMService thread instead of this once that is done
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {}



    }

    private void performExperiment() {
        for (int k: config.kVals) {
            Report report = new Report(k);
            KMedoids km = learner.getKMedoids(k);
            ArrayList<double[]> kmedoids = km.getCentroids();

            report.setAbsoluteError(Metrics.absoluteError(kmedoids, config.dataPath));
            // add other metrics

            this.reports.add(report);
        }

    }

    public void printReports() {
        System.out.println(config.toString());
        for (Report report: this.reports) {
            System.out.println(report.toString());
        }
    }


}
