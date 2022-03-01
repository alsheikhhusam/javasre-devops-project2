package com.project.DTO;

import java.util.Arrays;

public class WeatherDaily {
    public int dt = 1645466400;
    public int sunrise = 1645448036;
    public int sunset = 1645488356;
    public int moonrise = 1645507440;
    public int moonset = 1645458540;
    public double moon_phase = 0.67;
    public WeatherTemp temp;
    public WeatherFeels feels_like;
    public double pressure = 1011;
    public double humidity = 88;
    public double dew_point = 64.09;
    public double wind_speed = 14.43;
    public int wind_deg = 174;
    public double wind_gust = 34.7;
    public WeatherRain[] weather;
    public int  clouds= 100;
    public double  pop= 0.94;
    public double  rain= 0.91;
    public double  uvi= 1.34;

    @Override
    public String toString() {
        return "WeatherDaily{" +
                "dt=" + dt +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", moonrise=" + moonrise +
                ", moonset=" + moonset +
                ", moon_phase=" + moon_phase +
                ", temp=" + temp +
                ", feels_like=" + feels_like +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dew_point=" + dew_point +
                ", wind_speed=" + wind_speed +
                ", wind_deg=" + wind_deg +
                ", wind_gust=" + wind_gust +
                ", weather=" + Arrays.toString(weather) +
                ", clouds=" + clouds +
                ", pop=" + pop +
                ", rain=" + rain +
                ", uvi=" + uvi +
                '}';
    }
}
