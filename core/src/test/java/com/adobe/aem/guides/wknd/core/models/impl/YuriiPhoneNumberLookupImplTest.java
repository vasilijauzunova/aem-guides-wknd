package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.YuriiPhoneNumberLookup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YuriiPhoneNumberLookupImplTest {
    private YuriiPhoneNumberLookup lookup;

    @BeforeEach
    void setUp() {
        lookup = new YuriiPhoneNumberLookupImpl();
    }

    @Test
    void testFindCountry_withValidUANumber() {
        String result = lookup.findCountry("+380931234567");
        assertTrue(result.contains("Ukraine"), "Expected country to be Ukraine");
        assertTrue(result.startsWith("+380"), "Expected phone number to start with +380");
    }

    @Test
    void testFindCountry_withValidUSNumber() {
        String result = lookup.findCountry("+14155552671");
        assertTrue(result.contains("United States"), "Expected country to be United States");
    }

    @Test
    void testFindCountry_withInvalidNumber() {
        String result = lookup.findCountry("not-a-number");
        assertEquals("", result, "Expected empty result for invalid input");
    }

    @Test
    void testFindCountry_withNullInput() {
        String result = lookup.findCountry(null);
        assertEquals("", result, "Expected empty result for null input");
    }
}
