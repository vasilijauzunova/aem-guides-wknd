package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.KaterynaTCountryLookupService;
import com.adobe.aem.guides.wknd.core.models.KaterynaTFeatureToggleService;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.CredentialException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class KaterynaTContactImplTest {

    private final AemContext context = new AemContext();

    @Mock
    private KaterynaTCountryLookupService countryLookupService;

    @Mock
    private KaterynaTFeatureToggleService featureToggleService;

    @Mock
    private Page currentPage;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private ResourceResolver resourceResolver;

    @BeforeEach
    void setUp() {
        context.registerService(KaterynaTFeatureToggleService.class, featureToggleService);
        context.registerService(KaterynaTCountryLookupService.class, countryLookupService);

        context.create().page("/content/wknd/us/en/article");
        context.currentPage(context.pageManager().getPage("/content/wknd/us/en/article"));

        context.create().resource("/content/wknd/us/en/article/jcr:content/contact",
                                  "title", "Ms.",
                                  "firstName", "Kateryna",
                                  "lastName", "Tkachova",
                                  "phoneNumber", "+123456789",
                                  "emailAddress", "kateryna@example.com"
        );
        context.request().setAttribute(SlingHttpServletRequest.class.getName(), request);
    }

    @Test
    void testGetData_FeatureEnabledWithCountry() throws CredentialException {
        context.currentResource("/content/wknd/us/en/article/jcr:content/contact");
        Mockito.when(featureToggleService.isFeatureToggleEnabled(Mockito.eq("/content/wknd/us/en/article"),
                                                                 Mockito.any(ResourceResolver.class)))
               .thenReturn(true);
        Mockito.when(countryLookupService.getCountryLabel("+123456789")).thenReturn("Ukraine");

        KaterynaTContactImpl contact = context.request().adaptTo(KaterynaTContactImpl.class);
        Assertions.assertNotNull(contact);
        String result = contact.getData();

        assertTrue(result.contains("Kateryna"));
        assertTrue(result.contains("Ukraine"));
    }

    @Test
    void testGetData_FeatureDisabled() throws CredentialException {
        context.currentResource("/content/wknd/us/en/article/jcr:content/contact");
        Mockito.when(featureToggleService.isFeatureToggleEnabled(Mockito.eq("/content/wknd/us/en/article"),
                                                                 Mockito.any(ResourceResolver.class)))
               .thenReturn(false);

        KaterynaTContactImpl contact = context.request().adaptTo(KaterynaTContactImpl.class);
        Assertions.assertNotNull(contact);
        String result = contact.getData();

        assertTrue(result.contains("Kateryna"));
        assertFalse(result.contains("Ukraine"));
    }

    @Test
    void testGetData_MissingValuesThrowsException() {
        context.create().resource("/content/wknd/us/en/article/jcr:content/contact-missing",
                                  "title", "Ms.",
                                  "firstName", "",
                                  "lastName", "",
                                  "phoneNumber", "",
                                  "emailAddress", "kateryna@example.com"
        );
        context.currentResource("/content/wknd/us/en/article/jcr:content/contact-missing");
        context.request().setAttribute(SlingHttpServletRequest.class.getName(), request);
        KaterynaTContactImpl contact = context.request().adaptTo(KaterynaTContactImpl.class);
        Assertions.assertNotNull(contact);
        assertThrows(CredentialException.class, contact::getData);
    }
}

