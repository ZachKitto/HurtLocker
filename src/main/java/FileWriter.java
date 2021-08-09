import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Formatter;
import java.util.Map;

public class FileWriter {

    PatternMatcher patternMatcher = new PatternMatcher();
    Map<String, Map<Double, Integer>> map = patternMatcher.populateMap(new Main().readRawDataToString());

    public FileWriter() throws Exception {
    }

    public String writeName() {
        return new StringBuilder()
                .append("name:%8s           seen: %d times\n")
                .append("=============           =============\n")
                .toString();
    }

    public String writePrice(int numberOfUniquePrices) {
        if (numberOfUniquePrices == 1) {
            return new StringBuilder()
                    .append("Price%8.2f           seen: %d times\n")
                    .append("-------------           -------------\n\n")
                    .toString();
        }
        else {
            return new StringBuilder()
                    .append("Price:%8.2f           seen:  %d times\n")
                    .append("-------------           -------------\n")
                    .append("Price:%8.2f           seen:  %d times\n\n")
                    .toString();
        }
    }

    public String writeToFile(FileOutputStream outputStream) {
        Formatter formatter = new Formatter(outputStream);
        for (Map.Entry<String, Map<Double, Integer>> item : map.entrySet()) {
            formatter.format(writeName(), item.getKey(), patternMatcher.getNumberOfItems().get(item.getKey()));
            for (Map.Entry<Double, Integer> price : item.getValue().entrySet()) {
                if (item.getValue().size() == 1) {
                    //formatter.format(writeName(), item.getKey(), patternMatcher.getNumberOfItems().get(item.getKey()));
                    formatter.format(writePrice(1), price.getKey(), price.getValue());
                }
                else {
                    //formatter.format(writeName(), item.getKey(), patternMatcher.getNumberOfItems().get(item.getKey()));
                    formatter.format(writePrice(1), price.getKey(), price.getValue());

                }
            }
        }
        formatter.flush();
        return formatter.toString();
    }
}
