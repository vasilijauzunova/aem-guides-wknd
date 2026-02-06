package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.BylineNatalija;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.models.factory.ModelFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(
        adaptables= {SlingHttpServletRequest.class},
        adapters = {BylineNatalija.class},
        resourceType = {ByLineNatalijaImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ByLineNatalijaImpl implements BylineNatalija {
    protected static final String RESOURCE_TYPE = "wknd/components/bylineNatalija";

    @Self
    private SlingHttpServletRequest request;

    @OSGiService
    private ModelFactory modelFactory;

    @ValueMapValue
    private String name;

    @ValueMapValue
    private List<String> occupations;

    private Image image;

    @PostConstruct
    void init() {
        image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getOccupations() {
        if (occupations != null) {
            Collections.sort(occupations);
            return new ArrayList<>(occupations);
        }
        return Collections.emptyList();
    }

    private Image getImage() {
        return image;
    }

    @Override
    public boolean isEmpty() {
        final Image image = getImage();

        if (StringUtils.isBlank(name)){
            return true;
        } else if (occupations == null || occupations.isEmpty()){
            return true;
        } else if (image == null || StringUtils.isBlank(image.getSrc())){
            return true;
        }
        else return false;
    }
}
