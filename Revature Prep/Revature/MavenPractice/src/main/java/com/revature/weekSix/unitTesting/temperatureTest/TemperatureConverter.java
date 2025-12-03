package com.revature.weekSix.unitTesting.temperatureTest;

public interface TemperatureConverter {
    double fahrenheitToCelsius(double temperature);
    double celsiusToFahrenheit(double temperature);
    double convertTemp(double temperature, String from, String to);
}
