package com.adobe.aem.guides.wknd.core.models;

public interface EmaContact {

	String getTitle();

	String getFirstName();

	String getLastName();

	String getFullName();

	String getPhoneNumber();

	String getEmailAddress();

	boolean isValidEmail();

	String getCountry();

}