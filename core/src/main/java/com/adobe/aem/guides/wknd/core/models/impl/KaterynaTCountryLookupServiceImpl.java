package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.KaterynaTCountryLookupService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.HashMap;
import java.util.Map;

@Component(service = KaterynaTCountryLookupService.class, immediate = true)
public class KaterynaTCountryLookupServiceImpl implements KaterynaTCountryLookupService {
    private Map<String, String> countryMap = new HashMap<>();

    @ObjectClassDefinition
    public @interface CountryLookupConfig {
        String[] getCountries() default {
                "+41=Switzerland",
                "+49=Germany",
                "+1=USA/Canada",
                "+44=United Kingdom",
                "+380=Ukraine"
        };
    }

    @Activate
    @Modified
    protected void activate(CountryLookupConfig config) {
        countryMap.clear();
        for (String entry : config.getCountries()) {
            String[] split = entry.split("=");
            if (split.length == 2) {
                countryMap.put(split[0].trim(), split[1].trim());
            }
        }
    }

    @Override
    public String getCountryLabel(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        return countryMap.entrySet().stream()
                .filter(entry -> phoneNumber.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Unknown");
    }
}
