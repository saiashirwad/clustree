package clustree;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;


/**
 * One report each for each value of k
 */

public class Report implements Serializable {
    private int k;

    private double absoluteError;

    public void setAbsoluteError(double absoluteError) {
        this.absoluteError = absoluteError;
    }



    public Report(int k) {
        this.k = k;
    }

    public void writeReport(String resultPath) throws IOException {
        FileWriter fileWriter = new FileWriter("./results.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
    }

    @Override
    public String toString() {
        return "Report{" +
                "k=" + k +
                ", absoluteError=" + absoluteError +
                '}';
    }
}
