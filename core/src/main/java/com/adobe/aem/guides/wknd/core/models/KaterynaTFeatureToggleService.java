package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.ResourceResolver;

public interface KaterynaTFeatureToggleService {
    boolean isFeatureToggleEnabled(ResourceResolver resourceResolver);
}
