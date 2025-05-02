package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.ArticleContent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        adapters = ArticleContent.class,
        resourceType = "wknd/components/article-content",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ArticleContentImpl implements ArticleContent {
    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String shortDescription;

    @Override
    public String getHeading() {
        return heading;
    }

    @Override
    public String getImage() {
        return image != null ? image : "/content/dam/wknd/not-found.jpg";
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }
}
