package com.adobe.aem.guides.wknd.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.adobe.cq.wcm.core.components.models.datalayer.builder.ComponentDataBuilder;
import com.adobe.cq.wcm.core.components.models.datalayer.builder.DataLayerBuilder;
import com.adobe.cq.wcm.core.components.util.ComponentUtils;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class },
        adapters = { YuriiContactModel.class, ComponentExporter.class, ComponentData.class },
        resourceType = YuriiContactModel.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class YuriiContactModel implements ComponentExporter, ComponentData {

    public static final String RESOURCE_TYPE = "wcm/components/yurii-contact";
    @SlingObject
    private Resource resource;

    @SlingObject
    ResourceResolver resourceResolver;

    @Inject
    Page currentPage;
    @OSGiService
    private YuriiPhoneNumberLookup lookup;
    @ValueMapValue
    private String title;
    @ValueMapValue
    private String firstName;
    @ValueMapValue
    private String lastName;
    @ValueMapValue
    private String phoneNumber;
    @ValueMapValue
    private String email;
    private String id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return getConfig(currentPage.getPath(), resourceResolver).isCountryEnabled() ? lookup.findCountry(phoneNumber) : "";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public YTContactConfig getConfig(String currentPage, ResourceResolver resourceResolver){
        String currentPath  = StringUtils.isNotBlank(currentPage) ? currentPage: StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if(contentResource != null){
            ConfigurationBuilder  configurationBuilder = contentResource.adaptTo(ConfigurationBuilder.class);
            if(configurationBuilder != null){
                return configurationBuilder.as(YTContactConfig.class);
            }
        }
        return null;
    }


    @PostConstruct
    protected void init() {
        // Use Adobe utility to generate a consistent ID
        this.id =  resource.getPath().replace("/", "-").substring(1);
    }

    // ComponentExporter interface
    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }

    // ComponentData interface
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return RESOURCE_TYPE;
    }

    public ComponentData getData() {
        if (ComponentUtils.isDataLayerEnabled(this.resource)) {
            return DataLayerBuilder.forComponent().withId(this::getId).withDescription(() -> title).withParentId(() -> "Parent").build();
        }
        return null;
    }
}
