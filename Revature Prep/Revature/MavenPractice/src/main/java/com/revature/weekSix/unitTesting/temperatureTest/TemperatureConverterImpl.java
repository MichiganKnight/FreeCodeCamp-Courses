package com.revature.weekSix.unitTesting.temperatureTest;

public class TemperatureConverterImpl implements TemperatureConverter {

    /**
     * Simple Function to Convert a Fahrenheit Temperature to Celsius
     * @param temperature The Temperature
     * @return Boolean
     */
    @Override
    public double fahrenheitToCelsius(double temperature) {
        return (temperature - 32) *  5 / 9;
    }

    @Override
    public double celsiusToFahrenheit(double temperature) {
        if (temperature <= -273.15) {
            throw new RuntimeException("Temperature Was Below Absolute Zero");
        }

        return (9d / 5d * temperature) + 32;
    }

    @Override
    public double convertTemp(double temperature, String from, String to) {
        return 0;
    }
}
