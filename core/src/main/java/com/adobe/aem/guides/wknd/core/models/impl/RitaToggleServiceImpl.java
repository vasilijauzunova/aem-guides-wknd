package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.RitaContactConfiguration;
import com.adobe.aem.guides.wknd.core.models.RitaCountryLookupService;
import com.adobe.aem.guides.wknd.core.models.RitaToggleService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Component(service = RitaToggleService.class)
public class RitaToggleServiceImpl implements RitaToggleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RitaToggleServiceImpl.class);

    @Override
    public boolean isCountryEnabled(ResourceResolver resourceResolver) {
        LOGGER.info("resourceResolver.getResource(\"/conf\"): " + resourceResolver.getResource("/conf"));
        Resource contentResource = resourceResolver.getResource("/conf");
        if (contentResource == null) return false;

        RitaContactConfiguration config = Objects.requireNonNull(contentResource
                        .adaptTo(ConfigurationBuilder.class))
                .as(RitaContactConfiguration.class);
        LOGGER.info("config.isCountryEnabled(): " + config.isCountryEnabled());
        return config.isCountryEnabled();
    }
}
