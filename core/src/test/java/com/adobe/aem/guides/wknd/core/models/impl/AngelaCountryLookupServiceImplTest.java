package com.adobe.aem.guides.wknd.core.models.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AngelaCountryLookupServiceImplTest {

    private AngelaCountryLookupServiceImpl countryLookupService;

    @BeforeEach
    void setUp() {
        countryLookupService = new AngelaCountryLookupServiceImpl();
    }

    @Test
    void testValidPhoneNumber() {
        String phoneNumber = "+38978123123";
        String result = countryLookupService.getCountry(phoneNumber);
        assertTrue(result.contains("Macedonia"));
    }

    @Test
    void testInvalidPhoneNumber() {
        String phoneNumber = "1234";
        String result = countryLookupService.getCountry(phoneNumber);
        assertEquals(phoneNumber, result);
    }

    @Test
    void testNullPhoneNumber() {
        String result = countryLookupService.getCountry(null);
        assertNull(result);
    }

    @Test
    void testEmptyPhoneNumber() {
        String result = countryLookupService.getCountry("");
        assertEquals("", result);
    }
}
