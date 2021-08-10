import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {

    Map<String, Map<Double, Integer>> mapOfItemsAndPrices = new HashMap<>();
    Map<String, Integer> numberOfItems = new HashMap<>();

    public Matcher matchName(String keyToMatch, String textToParse) {
        Pattern pattern = Pattern.compile(new StringBuilder()
                .append("(?i)")
                .append(keyToMatch)
                .append("[:, @, ^, *, %]\\w+")
                .toString());
        Matcher matcher = pattern.matcher(textToParse);
        return matcher;
    }

    public Matcher matchPrice(String keyToMatch, String textToParse) {
        Pattern pattern = Pattern.compile(new StringBuilder()
                .append("(?i)")
                .append(keyToMatch)
                .append("[:, @, ^, *, %]\\d+.\\d*")
                .toString());
        Matcher matcher = pattern.matcher(textToParse);
        return matcher;
    }

    public Matcher matchJerkSONObject(String textToParse) {
        Pattern pattern = Pattern.compile(new StringBuilder()
                .append("(?<=\\#{2})")
                .append("[^#]+?")
                .append("(?=\\#{2})")
                .toString());
        Matcher matcher = pattern.matcher(textToParse);
        return matcher;
    }

    public Map<String, Map<Double, Integer>> populateMap(String textToParse) {
        Map<String, Map<Double, Integer>> itemMap = new HashMap<>();
        Matcher matcher = matchJerkSONObject(textToParse);
        while (matcher.find()) {
            if (!itemMap.containsKey(getName(matcher.group()))) {
                Map<Double, Integer> priceMap = new HashMap<>();
                priceMap.put(getPrice(matcher.group()), 1);
                itemMap.put(getName(matcher.group()), priceMap);
                numberOfItems.put(getName(matcher.group()), 1);
            }
            else {
                if (!itemMap.get(getName(matcher.group())).containsKey(getPrice(matcher.group()))) {
                    itemMap.get(getName(matcher.group())).put(getPrice(matcher.group()), 1);
                }
                else {
                    Integer numberOfItems = itemMap.get(getName(matcher.group())).get(getPrice(matcher.group()));
                    numberOfItems++;
                    itemMap.get(getName(matcher.group())).replace(getPrice(matcher.group()), numberOfItems);
                }
                Integer amountOfItems = numberOfItems.get(getName(matcher.group()));
                amountOfItems++;
                numberOfItems.replace(getName(matcher.group()), amountOfItems);
            }
        }
        return itemMap;
    }

    public String getName(String textToParse) {
        try {
            Pattern pattern = Pattern.compile(new StringBuilder()
                    .append("(?<=[Nn][Aa][Mm][Ee][:, @, ^, *, %])")
                    .append("\\w+")
                    .toString());
            Matcher matcher = pattern.matcher(textToParse);
            matcher.find();
            String name = matcher.group();
            if (name.contains("0")) {
//              String integerAsString = Integer.toString(name.charAt(i));
//              integerAsString = integerAsString.toLowerCase();
                name = name.replace('0', 'o');
            }
            name = name.toLowerCase();
            char firstLetter = Character.toUpperCase(name.charAt(0));
            name = name.replace(name.charAt(0), firstLetter);
            return name;
        } catch (Exception e) {
            //e.printStackTrace();
            return "error";
        }
    }

    public Double getPrice(String textToParse) {
        try {
            Pattern pattern = Pattern.compile(new StringBuilder()
                    .append("(?<=[Pp][Rr][Ii][Cc][Ee][:, @, ^, *, %])")
                    .append("\\d*.\\d*")
                    .toString());
            Matcher matcher = pattern.matcher(textToParse);
            matcher.find();
            return Double.parseDouble(matcher.group());
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            return 0.0;
        }
    }

    public String getType(String textToParse) {
        try {
            Pattern pattern = Pattern.compile(new StringBuilder()
                    .append("(?<=[Tt][Yy][Pp][Ee][:, @, ^, *, %])")
                    .append("\\w+")
                    .toString());
            Matcher matcher = pattern.matcher(textToParse);
            matcher.find();
            return matcher.group().toLowerCase();
        } catch (Exception e) {
            //e.printStackTrace();
            return "error";
        }
    }

    public String getExpiration(String textToParse) {
        try {
            Pattern pattern = Pattern.compile(new StringBuilder()
                    .append("(?<=[Ee][Xx][Pp][Ii][Rr][Aa][Tt][Ii][Oo][Nn]")
                    .append("[:, @, ^, *, %])")
                    .append("\\d+/\\d+/\\d+")
                    .toString());
            Matcher matcher = pattern.matcher(textToParse);
            matcher.find();
            return matcher.group().toLowerCase();
        } catch (Exception e) {
            //e.printStackTrace();
            return "error";
        }
    }

    public Map<String, Integer> getNumberOfItems() {
        return numberOfItems;
    }
}
