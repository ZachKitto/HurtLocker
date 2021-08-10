import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Formatter;
import java.util.Map;
import java.util.regex.Matcher;

public class FileWriter {

    PatternMatcher patternMatcher = new PatternMatcher();
    Map<String, Map<Double, Integer>> map = patternMatcher.populateMap(new Main().readRawDataToString());
    Integer numberOfErrors = 0;

    public FileWriter() throws Exception {
    }

    public String writeName() {
        return new StringBuilder()
                .append("\nname:%8s           seen: %d times\n")
                .append("=============           =============\n")
                .toString();
    }

    public String writePrice(int numberOfUniquePrices) {
        if (numberOfUniquePrices == 1) {
            return new StringBuilder()
                    .append("Price%8.2f           seen: %d times\n")
                    .append("-------------           -------------\n")
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

    public String writeToFile(FileOutputStream outputStream) throws Exception {
        Formatter formatter = new Formatter(outputStream);
        for (Map.Entry<String, Map<Double, Integer>> item : map.entrySet()) {
            if (item.getKey().equals("error")) {
                numberOfErrors++;
                continue;
            }
            formatter.format(writeName(), item.getKey(), patternMatcher.getNumberOfItems().get(item.getKey()));
            for (Map.Entry<Double, Integer> price : item.getValue().entrySet()) {
                if (price.getKey() == 0.0) {
                    numberOfErrors++;
                    continue;
                }
                else if (item.getValue().size() == 1) {
                    //formatter.format(writeName(), item.getKey(), patternMatcher.getNumberOfItems().get(item.getKey()));
                    formatter.format(writePrice(1), price.getKey(), price.getValue());
                }
                else {
                    //formatter.format(writeName(), item.getKey(), patternMatcher.getNumberOfItems().get(item.getKey()));
                    formatter.format(writePrice(1), price.getKey(), price.getValue());
                }
            }
        }
        calculateNumberOfErrors();
        formatter.format("\nErrors                  seen: %d times", numberOfErrors);
        formatter.flush();
        return formatter.toString();
    }

    public void calculateNumberOfErrors() throws Exception {
        Matcher matcher = patternMatcher.matchJerkSONObject(new Main().readRawDataToString());
        while (matcher.find()) {
            if (patternMatcher.getType(matcher.group()).equals("error") ||
            patternMatcher.getExpiration(matcher.group()).equals("error")) {
                numberOfErrors++;
            }
        }
    }
}
