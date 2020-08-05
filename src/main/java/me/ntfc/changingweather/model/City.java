package me.ntfc.changingweather.model;

public class City {
    private final String name;

    private final String countryCode;

    public City(String name, String countryCode) {
        this.name = name;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
