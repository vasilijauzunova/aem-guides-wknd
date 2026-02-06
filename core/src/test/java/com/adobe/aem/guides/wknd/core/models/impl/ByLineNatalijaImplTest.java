package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.BylineNatalija;
import com.adobe.cq.wcm.core.components.models.Image;
import com.google.common.collect.ImmutableList;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class ByLineNatalijaImplTest {

    private final AemContext context = new AemContext();

    @Mock
    private Image image;

    @Mock
    ModelFactory modelFactory;

    @BeforeEach
    public void setUp() {
        context.addModelsForClasses(ByLineNatalijaImpl.class);
        context.load().json("/com/adobe/aem/guides/wknd/core/models/impl/ByLineNatalijaImplTest.json","/content");

        lenient().when(modelFactory.getModelFromWrappedRequest(eq(context.request()),  any(Resource.class), eq(Image.class)))
                .thenReturn(image);

        context.registerService(ModelFactory.class, modelFactory, org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
    }

    @Test
    public void testGetName() {
        String expected = "Jane Doe";

        context.currentResource("/content/bylineNatalija");
        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        String actual = bylineNatalija.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetOccupations() {
        List<String> expected = new ImmutableList.Builder<String>()
                .add("Blogger")
                .add("Photographer")
                .add("YouTuber")
                .build();

        context.currentResource("/content/bylineNatalija");
        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        List<String> actual = bylineNatalija.getOccupations();
        assertEquals(expected, actual);

    }

    @Test
    public void testIsEmpty() {
        context.currentResource("/content/empty");
        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        assertTrue(bylineNatalija.isEmpty());
    }

    @Test
    public void testIsNotEmpty_withoutName() {
        context.currentResource("/content/without-name");
        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        assertTrue(bylineNatalija.isEmpty());
    }

    @Test
    public void testIsNotEmpty_withoutOccupations() {
        context.currentResource("/content/without-occupations");
        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        assertTrue(bylineNatalija.isEmpty());
    }

    @Test
    public void testIsNotEmpty_withoutImage() {
        context.currentResource("/content/bylineNatalija");
        lenient().when(modelFactory.getModelFromWrappedRequest(eq(context.request()),  any(Resource.class), eq(Image.class)))
                .thenReturn(null);

        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        assertTrue(bylineNatalija.isEmpty());
    }

    @Test
    public void testIsNotEmpty_withoutImageSrc() {
        context.currentResource("/content/bylineNatalija");
        when(image.getSrc()).thenReturn("");

        BylineNatalija bylineNatalija = context.request().adaptTo(BylineNatalija.class);

        assertTrue(bylineNatalija.isEmpty());
    }
}
