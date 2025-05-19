package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.impl.KaterynaTCountryLookupServiceImpl.CountryLookupConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KaterynaTCountryLookupServiceImplTest {

    private KaterynaTCountryLookupServiceImpl service;

    interface TestConfig extends CountryLookupConfig {}

    @BeforeEach
    void setUp() {
        service = new KaterynaTCountryLookupServiceImpl();
        service.activate(new TestConfig() {
            @Override
            public String[] getCountries() {
                return new String[]{
                        "+41=Switzerland",
                        "+49=Germany",
                        "+1=USA/Canada",
                        "+44=United Kingdom",
                        "+380=Ukraine"
                };
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return CountryLookupConfig.class;
            }
        });
    }

    @Test
    void testGetCountryLabel_knownPrefix() {
        assertEquals("Switzerland", service.getCountryLabel("+41791234567"));
        assertEquals("Germany", service.getCountryLabel("+4915123456789"));
        assertEquals("USA/Canada", service.getCountryLabel("+14155552671"));
        assertEquals("United Kingdom", service.getCountryLabel("+447911123456"));
        assertEquals("Ukraine", service.getCountryLabel("+380501234567"));
    }

    @Test
    void testGetCountryLabel_unknownPrefix() {
        assertEquals("Unknown", service.getCountryLabel("+999123456789"));
    }

    @Test
    void testGetCountryLabel_nullInput() {
        assertEquals("", service.getCountryLabel(null));
    }

    @Test
    void testGetCountryLabel_emptyInput() {
        assertEquals("Unknown", service.getCountryLabel(""));
    }
}
