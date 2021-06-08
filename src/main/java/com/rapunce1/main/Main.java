package com.rapunce1.main;

import com.rapunce1.parser.MyHandler;
import com.rapunce1.struct.City;
import com.rapunce1.struct.Struct;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        long noRepeatedCounter = 0;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            MyHandler myHandler = new MyHandler();
            parser.parse(new File("address.xml"), myHandler);

            String filename = System.getProperty("user.home") + "\\Desktop\\XMLParser_Result.txt";
            File file = new File(filename);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);

            HashMap<String, City> model = myHandler.getModel();
            List<Struct> repeated = myHandler.getRepeatedLines();

            writer.write("Repeated strings:\n");
            for (Struct item :
                    repeated) {
                writer.write("<item city=\"" + item.data[0] + "\" street=\"" + item.data[1] +
                        "\" house=\"" + item.data[2] + "\" floor=\"" + item.data[3] + "\" /> x" + item.count + "\n\n");
            }

            Set<String> cityNames = model.keySet();

            for (String cityName :
                    cityNames) {
                City city = model.get(cityName);//StringBuilder
                String result = cityName + '\n';
                for (int i = 0; i < 5; ++i) {
                    result += i + 1 + " floors: " + city.floors[i] + '\n';
                    noRepeatedCounter += city.floors[i];
                }
                result += '\n';
                writer.write(result);
            }
            writer.write("No repeated strings: " + noRepeatedCounter + "\n\n");
            writer.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
