package com.adobe.aem.guides.wknd.core.caconfig;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "Contact Configuration")
public @interface EmaContactConfif {
	@Property(label = "Show Country")
	boolean showCountry() default false;
}
