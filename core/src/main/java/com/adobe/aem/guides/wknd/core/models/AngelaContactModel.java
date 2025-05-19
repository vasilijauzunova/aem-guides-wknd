package com.adobe.aem.guides.wknd.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AngelaContactModel {

    @ValueMapValue
    protected String title;

    @ValueMapValue
    protected String firstName;

    @ValueMapValue
    protected String lastName;

    @ValueMapValue
    protected String phoneNumber;

    @ValueMapValue
    protected String email;

    @ValueMapValue
    protected String country;

    @OSGiService(injectionStrategy = InjectionStrategy.REQUIRED)
    private AngelaCountryLookupService countryLookupService;

    @OSGiService(injectionStrategy = InjectionStrategy.REQUIRED)
    private AngelaToggleService toggleService;

    @SlingObject
    private ResourceResolver resource;

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        if (toggleService.isCountryEnabled(resource) && country == null)
            country = countryLookupService.getCountry(this.phoneNumber);
        return country;
    }

    public boolean hasContent() {
        return StringUtils.isNoneBlank(firstName, lastName, phoneNumber);
    }
}
