package com.adobe.aem.guides.wknd.core.models.impl;


import com.adobe.aem.guides.wknd.core.models.KaterynaTContact;
import com.adobe.aem.guides.wknd.core.models.KaterynaTCountryLookupService;
import com.adobe.aem.guides.wknd.core.models.KaterynaTFeatureToggleService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
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
    @OSGiService
    private KaterynaTFeatureToggleService featureToggleService;
    @Inject
    private SlingHttpServletRequest request;

    private ResourceResolver resourceResolver;

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

    @PostConstruct
    private void init() {
        if (request != null) {
            resourceResolver = request.getResourceResolver();
        }
    }
    @Override
    public String getData() throws CredentialException {
        if ((phoneNumber == null || phoneNumber.isEmpty()) &&
                (firstName == null || firstName.isEmpty()) &&
                (lastName == null || lastName.isEmpty())) {
            throw new CredentialException("Missing required contact information.");
        }

        String country = (featureToggleService.isFeatureToggleEnabled(resourceResolver) && countryLookupService != null)
                ? countryLookupService.getCountryLabel(phoneNumber) : "";

        StringBuilder result = new StringBuilder();
        result.append("The title is: ").append(title)
                .append("<br/> The First Name is: ").append(firstName)
                .append("<br/> The Last Name is: ").append(lastName)
                .append("<br/> The Phonenumber is: ").append(phoneNumber);

        if (!country.isEmpty()) {
            result.append(" (").append(country).append(")");
        }

        result.append("<br/> The email is: ").append(emailAddress);
        return result.toString();
    }
}

