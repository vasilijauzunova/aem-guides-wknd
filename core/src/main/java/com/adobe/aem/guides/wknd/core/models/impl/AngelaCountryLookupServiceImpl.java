package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.AngelaCountryLookupService;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

@Component(service = AngelaCountryLookupService.class)
public class AngelaCountryLookupServiceImpl implements AngelaCountryLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AngelaCountryLookupServiceImpl.class);

    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    @Override
    public String getCountry(String phoneNumber) {
        LOGGER.info("Attempting country lookup for phone number: {}", phoneNumber);

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            LOGGER.warn("Phone number input is null or empty");
            return phoneNumber;
        }

        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
            String regionCode = phoneUtil.getRegionCodeForNumber(numberProto);
            LOGGER.info("Extracted country code: {}, region: {}", numberProto.getCountryCode(), regionCode);
            Locale countryLocale = new Locale("", regionCode);
            return phoneNumber + " (" + countryLocale.getDisplayCountry() + ")";

        } catch (NumberParseException e) {
            LOGGER.error("Failed to parse phone number '{}'. Reason: {}", phoneNumber, e.getMessage());
        }

        return phoneNumber;
    }
}
