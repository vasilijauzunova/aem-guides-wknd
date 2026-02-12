package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.EmaCountryLookupService;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@Component(service = EmaCountryLookupService.class)
@Designate(ocd = EmaCountryLookupServiceImpl.Config.class)
public class EmaCountryLookupServiceImpl implements EmaCountryLookupService {

	private Map<String, String> countryCodeMap;

	@ObjectClassDefinition
	public @interface Config {
		String[] countries() default {
				"+1:USA/Canada",
				"+44:United Kingdom",
				"+49:Germany",
				"+33:France",
				"+39:Italy",
				"+34:Spain",
				"+31:Netherlands",
				"+41:Switzerland",
				"+43:Austria",
				"+389:North Macedonia",
				"+381:Serbia",
				"+385:Croatia",
				"+387:Bosnia and Herzegovina",
				"+382:Montenegro",
				"+386:Slovenia"
		};
	}

	@Override
	public String getCountryByPhoneNumber(String phoneNumber) {
		if (StringUtils.isBlank(phoneNumber)) {
			return "";
		}

		String number = phoneNumber.trim().replaceAll("[\\s\\-\\.]", "");
		if (number.startsWith("00")) {
			number = "+" + number.substring(2);
		}
		if (!number.startsWith("+")) {
			return "";
		}

		for (int length = Math.min(5, number.length()); length >= 2; length--) {
			String prefix = number.substring(0, length);
			if (countryCodeMap.containsKey(prefix)) {
				return countryCodeMap.get(prefix);
			}
		}

		return "";
	}

	@Activate
	protected void activate(Config config) {
		this.countryCodeMap = Arrays.stream(config.countries())
		                            .map(entry -> entry.split(":", 2))
		                            .filter(entry -> entry.length == 2)
		                            .map(entry -> Pair.of(entry[0].trim(), entry[1].trim()))
		                            .collect(Collectors.toMap(
				                            Pair::getKey,
				                            Pair::getValue,
				                            (a, b) -> a));
	}
}
