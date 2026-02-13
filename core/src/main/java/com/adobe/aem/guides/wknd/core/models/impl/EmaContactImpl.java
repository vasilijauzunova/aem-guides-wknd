package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.caconfig.EmaContactConfig;
import com.adobe.aem.guides.wknd.core.models.EmaContact;
import com.adobe.aem.guides.wknd.core.services.EmaCountryLookupService;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},
		adapters = EmaContact.class,
		resourceType = "wknd/components/ema-contact",
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EmaContactImpl implements EmaContact {

	private static final Pattern EMAIL_PATTERN = Pattern.compile(
			"^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
	);

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
	@ValueMapValue
	private String country;
	@OSGiService
	private EmaCountryLookupService countryLookupService;
	@SlingObject
	private Resource resource;
	private EmaContactConfig config;

	private boolean isEmailValid;

	@PostConstruct
	protected void init() {
		isEmailValid = StringUtils.isNotBlank(emailAddress) &&
		               EMAIL_PATTERN.matcher(emailAddress.trim()).matches();
		if (resource != null) {
			ConfigurationBuilder configBuilder = resource.adaptTo(ConfigurationBuilder.class);
			if (configBuilder != null) {
				config = configBuilder.as(EmaContactConfig.class);
			}
		}
	}

	@Override
	public String getTitle() {
		return StringUtils.defaultString(title);
	}

	@Override
	public String getFirstName() {
		return StringUtils.defaultString(firstName);
	}

	@Override
	public String getLastName() {
		return StringUtils.defaultString(lastName);
	}

	@Override
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();

		if (StringUtils.isNotBlank(firstName)) {
			fullName.append(firstName).append(" ");
		}
		if (StringUtils.isNotBlank(lastName)) {
			fullName.append(lastName);
		}

		return fullName.toString().trim();
	}

	@Override
	public String getPhoneNumber() {
		return StringUtils.defaultString(phoneNumber).replaceAll("\\s+", "");
	}

	@Override
	public String getEmailAddress() {
		return StringUtils.defaultString(emailAddress).trim();
	}

	@Override
	public String getCountry() {
		if (countryLookupService != null && StringUtils.isNotBlank(phoneNumber)) {
			return countryLookupService.getCountryByPhoneNumber(phoneNumber);
		}
		return "";
	}

	@Override
	public boolean isValidEmail() {
		return isEmailValid;
	}

	@Override
	public boolean isShowCountry() {
		return config != null && config.showCountry();
	}
}

