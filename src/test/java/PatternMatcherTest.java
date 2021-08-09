import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class PatternMatcherTest {

    PatternMatcher patternMatcher = new PatternMatcher();

    @Test
    public void testRun() throws Exception {
        Main main = new Main();
        Map<String, Map<Double, Integer>> map = patternMatcher.populateMap(main.readRawDataToString());
        System.out.println(map.toString());
    }

    @Test
    public void matchName() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        String keyToMatch = "name";
        String expectedMatch1 = "naME:BreaD";
        String expectedMatch2 = "NAMe:BrEAD";
        String expectedMatch3 = "naMe:MiLK";
        String expectedMatch4 = "naMe:Cookies";

        // When
        Matcher matcher1 = patternMatcher.matchName(keyToMatch, example1);
        Matcher matcher2 = patternMatcher.matchName(keyToMatch, example2);
        Matcher matcher3 = patternMatcher.matchName(keyToMatch, example3);
        Matcher matcher4 = patternMatcher.matchName(keyToMatch, example4);
        matcher1.find(); matcher2.find(); matcher3.find(); matcher4.find();

        // Then
        Assert.assertEquals(expectedMatch1, matcher1.group());
        Assert.assertEquals(expectedMatch2, matcher2.group());
        Assert.assertEquals(expectedMatch3, matcher3.group());
        Assert.assertEquals(expectedMatch4, matcher4.group());
    }

    @Test
    public void matchPrice() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        String keyToMatch = "price";
        String expectedMatch1 = "price:1.23";
        String expectedMatch2 = "price:1.23";
        String expectedMatch3 = "price:3.23";
        String expectedMatch4 = "price:2.25";

        // When
        Matcher matcher1 = patternMatcher.matchPrice(keyToMatch, example1);
        Matcher matcher2 = patternMatcher.matchPrice(keyToMatch, example2);
        Matcher matcher3 = patternMatcher.matchPrice(keyToMatch, example3);
        Matcher matcher4 = patternMatcher.matchPrice(keyToMatch, example4);
        matcher1.find(); matcher2.find(); matcher3.find(); matcher4.find();

        // Then
        Assert.assertEquals(expectedMatch1, matcher1.group());
        Assert.assertEquals(expectedMatch2, matcher2.group());
        Assert.assertEquals(expectedMatch3, matcher3.group());
        Assert.assertEquals(expectedMatch4, matcher4.group());
    }

    @Test
    public void matchJerkSONObject() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        String expectedMatch1 = "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016";
        String expectedMatch2 = "NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016";
        String expectedMatch3 = "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016";
        String expectedMatch4 = "naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016";

        // When
        Matcher matcher1 = patternMatcher.matchJerkSONObject(example1);
        Matcher matcher2 = patternMatcher.matchJerkSONObject(example2);
        Matcher matcher3 = patternMatcher.matchJerkSONObject(example3);
        Matcher matcher4 = patternMatcher.matchJerkSONObject(example4);
        matcher1.find(); matcher2.find(); matcher3.find(); matcher4.find();

        // Then
        Assert.assertEquals(expectedMatch1, matcher1.group());
        Assert.assertEquals(expectedMatch2, matcher2.group());
        Assert.assertEquals(expectedMatch3, matcher3.group());
        Assert.assertEquals(expectedMatch4, matcher4.group());
    }

    @Test
    public void populateMap() {
        // Given
        String example = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##" +
                "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##" +
                "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##" +
                "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        Map<Double, Integer> breadPriceMap = new HashMap<>();
        breadPriceMap.put(1.23, 1);
        breadPriceMap.replace(1.23, 2);
        Map<Double, Integer> milkPriceMap = new HashMap<>();
        milkPriceMap.put(3.23, 1);
        Map<Double, Integer> cookiesPriceMap = new HashMap<>();
        cookiesPriceMap.put(2.25, 1);
        Map<String, Map<Double, Integer>> expectedMap = new HashMap<>();
        expectedMap.put("bread", breadPriceMap);
        expectedMap.put("milk", milkPriceMap);
        expectedMap.put("cookies", cookiesPriceMap);

        // When
        Map<String, Map<Double, Integer>> actualMap = patternMatcher.populateMap(example);

        // Then
        Assert.assertEquals(expectedMap, actualMap);
    }

    @Test
    public void getName() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        String expectedName1 = "bread";
        String expectedName2 = "bread";
        String expectedName3 = "milk";
        String expectedName4 = "cookies";

        // When
        String actualName1 = patternMatcher.getName(example1);
        String actualName2 = patternMatcher.getName(example2);
        String actualName3 = patternMatcher.getName(example3);
        String actualName4 = patternMatcher.getName(example4);

        // Then
        Assert.assertEquals(expectedName1, actualName1);
        Assert.assertEquals(expectedName2, actualName2);
        Assert.assertEquals(expectedName3, actualName3);
        Assert.assertEquals(expectedName4, actualName4);
    }

    @Test
    public void getPrice() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        Double expectedPrice1 = 1.23;
        Double expectedPrice2 = 1.23;
        Double expectedPrice3 = 3.23;
        Double expectedPrice4 = 2.25;

        // When
        Double actualPrice1 = patternMatcher.getPrice(example1);
        Double actualPrice2 = patternMatcher.getPrice(example2);
        Double actualPrice3 = patternMatcher.getPrice(example3);
        Double actualPrice4 = patternMatcher.getPrice(example4);

        // Then
        Assert.assertEquals(expectedPrice1, actualPrice1);
        Assert.assertEquals(expectedPrice2, actualPrice2);
        Assert.assertEquals(expectedPrice3, actualPrice3);
        Assert.assertEquals(expectedPrice4, actualPrice4);
    }

    @Test
    public void getType() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        String expectedType1 = "food";
        String expectedType2 = "food";
        String expectedType3 = "food";
        String expectedType4 = "food";

        // When
        String actualType1 = patternMatcher.getType(example1);
        String actualType2 = patternMatcher.getType(example2);
        String actualType3 = patternMatcher.getType(example3);
        String actualType4 = patternMatcher.getType(example4);

        // Then
        Assert.assertEquals(expectedType1, actualType1);
        Assert.assertEquals(expectedType2, actualType2);
        Assert.assertEquals(expectedType3, actualType3);
        Assert.assertEquals(expectedType4, actualType4);
    }

    @Test
    public void getExpiration() {
        // Given
        String example1 = "##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String example2 = "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        String example3 = "##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
        String example4 = "##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##";
        String expectedExpiration1 = "1/02/2016";
        String expectedExpiration2 = "2/25/2016";
        String expectedExpiration3 = "1/11/2016";
        String expectedExpiration4 = "1/25/2016";

        // When
        String actualExpiration1 = patternMatcher.getExpiration(example1);
        String actualExpiration2 = patternMatcher.getExpiration(example2);
        String actualExpiration3 = patternMatcher.getExpiration(example3);
        String actualExpiration4 = patternMatcher.getExpiration(example4);

        // Then
        Assert.assertEquals(expectedExpiration1, actualExpiration1);
        Assert.assertEquals(expectedExpiration2, actualExpiration2);
        Assert.assertEquals(expectedExpiration3, actualExpiration3);
        Assert.assertEquals(expectedExpiration4, actualExpiration4);
    }
}
