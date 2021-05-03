package com.example.countrylist;

public class Country {
    public String countryName, countryCode, countryPopulation;
    public Country (String name, String code, String pop) {
        countryName = name;
        countryCode = code;
        countryPopulation = pop;
    }
    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"geonames\":[{\"population\":\"");
        sb.append(countryPopulation);
        sb.append("\",\"countryCode\":\"");
        sb.append(countryCode);
        sb.append("\",\"countryName\":\"");
        sb.append(countryName);
        sb.append("\"}]}");
        return sb.toString();
    }
}
