package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.ContactComponentFeatureToggle;
import com.adobe.aem.guides.wknd.core.models.KaterynaTFeatureToggleService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.osgi.service.component.annotations.Component;


@Component(service = KaterynaTFeatureToggleService.class)
public class KaterynaTFeatureToggleServiceImpl implements KaterynaTFeatureToggleService {

    @Override
    public boolean isFeatureToggleEnabled(ResourceResolver resourceResolver) {
        Resource contentResource = resourceResolver.getResource("/conf");
        if (contentResource != null) {
            ContactComponentFeatureToggle config = contentResource
                    .adaptTo(ConfigurationBuilder.class)
                    .as(ContactComponentFeatureToggle.class);
            return config.featureToggle();
        }
        return false;
    }
}
