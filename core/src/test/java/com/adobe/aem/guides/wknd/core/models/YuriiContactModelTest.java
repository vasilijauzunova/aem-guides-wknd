package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.wcm.api.PageManager;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.apache.sling.caconfig.ConfigurationBuilder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class YuriiContactModelTest {

    private final AemContext context = new AemContext();


    @Mock
    YuriiPhoneNumberLookup lookup;

    @Mock
    YTContactConfig config;

    @Mock
    ConfigurationBuilder configBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        context.registerService(YuriiPhoneNumberLookup.class, lookup);

        // Create the current page
        context.create().page("/content/mysite/page1");
        context.currentPage(context.pageManager().getPage("/content/mysite/page1"));

        // Register mock ConfigurationBuilder adapter
        context.addModelsForClasses(YuriiContactModel.class);
        context.registerAdapter(Resource.class, ConfigurationBuilder.class, configBuilder);
        when(configBuilder.as(YTContactConfig.class)).thenReturn(config);
        when(config.isCountryEnabled()).thenReturn(true);

        // Create resource with properties
        context.create().resource("/content/user",
                "title", "Mr.",
                "firstName", "Yurii",
                "lastName", "Last",
                "phoneNumber", "123456789",
                "email", "yurii@example.com"
        );
        context.currentResource("/content/user");

        when(lookup.findCountry("123456789")).thenReturn("Ukraine");
    }

    @Test
    void testGetPhoneNumber_WithCountryEnabled() {
        YuriiContactModel model = context.request().adaptTo(YuriiContactModel.class);

        assertNotNull(model);
        assertEquals("Ukraine", model.getPhoneNumber());
        assertEquals("Mr.", model.getTitle());
        assertEquals("Yurii", model.getFirstName());
        assertEquals("yurii@example.com", model.getEmail());
    }
}