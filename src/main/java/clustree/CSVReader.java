package clustree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    public static ArrayList<ArrayList<Double>> read(String path) {

        ArrayList<ArrayList<Double>> points = new ArrayList<>();
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] points_ = line.split(csvSplitBy);
                ArrayList<Double> point_ = new ArrayList<>();

                for (String point: points_) {
                    point_.add(Double.parseDouble(point));
                }

                points.add(point_);
            }
        }
        catch (IOException e) {}

        return points;
    }
}
