package com.rapunce1.struct;

public class Struct {

    public final String[] data = new String[4];
    public Integer count;

    public Struct(String city, String street, String house, String floor) {
        data[0] = city;
        data[1] = street;
        data[2] = house;
        data[3] = floor;
        count = 2;
    }
}
