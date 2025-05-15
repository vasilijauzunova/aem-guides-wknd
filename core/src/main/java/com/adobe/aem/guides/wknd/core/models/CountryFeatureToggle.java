package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(
        label = "Feature Toggle - Contact Component",
        description = "Feature toggle to enable or disable the country info"
)
public @interface CountryFeatureToggle {
    @Property(label = "Enable Country")
    boolean featureToggle() default false;
}
