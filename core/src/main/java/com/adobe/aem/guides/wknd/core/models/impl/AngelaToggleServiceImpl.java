package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.AngelaContactConfiguration;
import com.adobe.aem.guides.wknd.core.models.AngelaToggleService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = AngelaToggleService.class)
public class AngelaToggleServiceImpl implements AngelaToggleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AngelaToggleServiceImpl.class);

    @Override
    public boolean isCountryEnabled(ResourceResolver resourceResolver) {

        Resource configResource = resourceResolver.getResource("/conf");
        ConfigurationBuilder configBuilder = configResource != null
                ? configResource.adaptTo(ConfigurationBuilder.class)
                : null;

        boolean enabled = configBuilder != null
                && configBuilder.as(AngelaContactConfiguration.class).isCountryEnabled();

        LOGGER.info("Country feature toggle resolved as: {}", enabled);
        return enabled;
    }
}
