import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;
import java.util.Map;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
//        String output = (new Main()).readRawDataToString();
//        System.out.println(output);
        FileWriter fileWriter = new FileWriter();
        fileWriter.writeToFile(new FileOutputStream("ConvertedJerkSON.txt"));
    }
}
