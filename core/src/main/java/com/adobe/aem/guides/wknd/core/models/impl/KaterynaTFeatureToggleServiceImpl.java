package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.CountryFeatureToggle;
import com.adobe.aem.guides.wknd.core.models.KaterynaTFeatureToggleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.osgi.service.component.annotations.Component;


@Component(service = KaterynaTFeatureToggleService.class)
public class KaterynaTFeatureToggleServiceImpl implements KaterynaTFeatureToggleService {

    @Override
    public boolean isFeatureToggleEnabled(String currentPage, ResourceResolver resourceResolver) {
        String currentPath = StringUtils.isNotBlank(currentPage) ? currentPage : StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if (contentResource != null) {
            CountryFeatureToggle config = contentResource
                    .adaptTo(ConfigurationBuilder.class)
                    .as(CountryFeatureToggle.class);
            return config.featureToggle();
        }
        return false;
    }
}
