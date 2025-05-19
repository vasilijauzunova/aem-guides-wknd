package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.ResourceResolver;

public interface RitaToggleService {
    boolean isCountryEnabled(ResourceResolver resourceResolver);
}