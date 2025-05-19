package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.RitaCountryLookupService;
import com.google.i18n.phonenumbers.*;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

@Component(service = RitaCountryLookupService.class )
public class RitaCountryLookupServiceImpl implements RitaCountryLookupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RitaCountryLookupService.class);

    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    @Override
    public String getCountry(String phoneNumber) {
        LOGGER.info("Looking for country for phone number: " + phoneNumber);
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
            String regionCode = phoneUtil.getRegionCodeForNumber(numberProto);
            LOGGER.info("Country code: " + numberProto.getCountryCode());
            Locale locale = new Locale("", regionCode);
            return locale.getDisplayCountry(new Locale("", regionCode));
        } catch (NumberParseException e) {
            LOGGER.error("NumberParseException was thrown: " + e);
            return "";
        }
    }
}
