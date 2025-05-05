package com.adobe.aem.guides.wknd.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

public class RitaContactModel {
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
    @OSGiService(injectionStrategy = InjectionStrategy.REQUIRED)
    private RitaCountryLookupService countryLookupService;

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
        return this.countryLookupService.getCountry(this.phoneNumber);
    }

    public boolean hasContent() {
        return StringUtils.isNoneBlank(firstName, lastName, phoneNumber);
    }

}
