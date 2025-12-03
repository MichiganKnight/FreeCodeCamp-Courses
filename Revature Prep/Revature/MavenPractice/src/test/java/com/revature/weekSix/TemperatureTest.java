package com.revature.weekSix;

import com.revature.weekSix.unitTesting.temperatureTest.TemperatureConverter;
import com.revature.weekSix.unitTesting.temperatureTest.TemperatureConverterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TemperatureTest {
    TemperatureConverter converter = new TemperatureConverterImpl();

    @Test
    void celsiusToFahrenheit() {
        double result = this.converter.celsiusToFahrenheit(100);
        Assertions.assertEquals(212, result, 0.1);
    }

    @Test
    void fahrenheitToCelsius() {
        double result = this.converter.fahrenheitToCelsius(212);
        Assertions.assertEquals(100, result, 0.1);
    }

    @Test
    void absoluteZeroRaisesException() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            this.converter.celsiusToFahrenheit(-400);
        });
    }

    @Test
    void kelvinCelsius() {
        double result = this.converter.convertTemp(100, "kelvin", "celsius");
        Assertions.assertEquals(-273.15, result);
    }

    @Test
    void caseInsensitive() {
        double result = this.converter.convertTemp(100, "KeLVin", "cElsIus");
        Assertions.assertEquals(-273.15, result);
    }
}
