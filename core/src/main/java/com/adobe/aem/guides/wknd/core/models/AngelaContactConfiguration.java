package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "Contact Configuration", description = "Configuration for Contact component")
public @interface AngelaContactConfiguration {
    @Property(label = "Enable Country")
    boolean isCountryEnabled() default false;
}