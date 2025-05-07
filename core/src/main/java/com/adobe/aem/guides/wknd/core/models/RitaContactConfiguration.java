package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "Contact Configuration", description = "Configuration for Contact component")
public @interface RitaContactConfiguration {
    @Property(label = "Enable Country", description = "Should the country field be enabled?")
    boolean isCountryEnabled() default false;
}
