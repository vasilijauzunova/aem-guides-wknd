package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(
        label = "Feature Toggle - Contact Component",
        description = "Feature toggle to enable or disable the Contact component"
)
public @interface ContactComponentFeatureToggle {
    @Property(label = "Enable Contact Component")
    boolean featureToggle() default false;
}
