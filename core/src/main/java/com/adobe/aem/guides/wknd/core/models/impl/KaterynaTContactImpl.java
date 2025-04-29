package com.adobe.aem.guides.wknd.core.models.impl;


import com.adobe.aem.guides.wknd.core.models.KaterynaTContact;
import com.adobe.aem.guides.wknd.core.models.KaterynaTCountryLookupService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.security.auth.login.CredentialException;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = KaterynaTContact.class,
        resourceType = KaterynaTContactImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class KaterynaTContactImpl implements KaterynaTContact {

    protected static final String RESOURCE_TYPE = "wknd/components/kateryna-tkachova-contact";

    @ValueMapValue
    private String title;
    @ValueMapValue
    private String firstName;
    @ValueMapValue
    private String lastName;

    @ValueMapValue
    private String emailAddress;

    @ValueMapValue
    private String phoneNumber;

    @OSGiService
    private KaterynaTCountryLookupService countryLookupService;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getEmail() {
        return emailAddress;
    }

    @Override
    public String getData() throws CredentialException {
        if ((phoneNumber == null || phoneNumber.isEmpty()) &&
                (firstName == null || firstName.isEmpty()) &&
                (lastName == null || lastName.isEmpty())) {
            throw new CredentialException("Missing required contact information.");
        }

        String country = countryLookupService != null ? countryLookupService.getCountryLabel(phoneNumber) : "Unknown";

        return "The title is: " + title +
                "<br/> The First Name is: " + firstName +
                "<br/> The Last Name is: " + lastName +
                "<br/> The Phonenumber is: " + phoneNumber + " (" + country + ")" +
                "<br/> The email is: " + emailAddress;
    }
}

