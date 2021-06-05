package com.rapunce1.parser;

import com.rapunce1.struct.City;
import com.rapunce1.struct.Struct;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MyHandler extends DefaultHandler {

    private final HashMap<String, City> model = new HashMap<>();
    private final List<Struct> repeatedLines = new ArrayList<>();

    public HashMap<String, City> getModel() {
        return model;
    }

    public List<Struct> getRepeatedLines() {
        return repeatedLines;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!qName.equals("root")) {
            String cityName = attributes.getValue(0);
            String streetName = attributes.getValue(1);
            String house = attributes.getValue(2);
            String floor = attributes.getValue(3);

            if (!model.containsKey(cityName)) {
                model.put(cityName, new City());
            }

            City city = model.get(cityName);
            if (!city.streets.containsKey(streetName)) {
                city.streets.put(streetName, new ArrayList<>());
            }

            Collection<String> street = city.streets.get(streetName);
            if (!street.contains(house)) {
                street.add(house);
                city.floors[Integer.parseInt(floor) - 1]++;
            } else {
                if (repeatedLines.size() != 0) {
                    for (Struct item :
                            repeatedLines) {
                        if (item.data[0].equals(cityName)
                                && item.data[1].equals(streetName)
                                && item.data[2].equals(house)
                                && item.data[3].equals(floor)
                        ) item.count++;
                    }
                } else {
                    repeatedLines.add(new Struct(cityName, streetName, house, floor));
                }
            }
        }
    }
}
