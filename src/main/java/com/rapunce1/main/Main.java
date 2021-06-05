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
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            MyHandler myHandler = new MyHandler();
            parser.parse(new File("address.xml"), myHandler);

            String filename = System.getProperty("user.home") + "\\Desktop\\Result.txt";
            File file = new File(filename);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);

            HashMap<String, City> model = myHandler.getModel();
            List<Struct> repeated = myHandler.getRepeatedLines();

            writer.write("Doubled items:\n");
            for (Struct item :
                    repeated) {
                writer.write("<item city=\"" + item.data[0] + "\" street=\"" + item.data[1] +
                        "\" house=\"" + item.data[2] + "\" floor=\"" + item.data[3] + "\" /> x" + item.count + "\n\n");
            }
            writer.write("Total cities: " + model.size() + "\n\n"); // не нужно

            Set<String> cityNames = model.keySet();

            for (String cityName :
                    cityNames) {
                City city = model.get(cityName);
                String result = cityName + '\n';
                for (int i = 0; i < 5; ++i) {
                    result += i + 1 + " floors: " + city.floors[i] + '\n';
                }
                result += '\n';
                writer.write(result);
            }
            writer.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
