package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.YuriiPhoneNumberLookup;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;

@Component(service = YuriiPhoneNumberLookup.class )
public class YuriiPhoneNumberLookupImpl implements YuriiPhoneNumberLookup {


    @Override
    public String findCountry(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = null;
        try {
            number = phoneUtil.parse(phoneNumber, null);
        } catch (NumberParseException e) {
            return "";
        }
        String regionCode = phoneUtil.getRegionCodeForNumber(number);  // → "UA"

        Locale locale = new Locale("", regionCode);
        return String.format("%s (%s)",phoneNumber,locale.getDisplayCountry() ) ;
    }
}
