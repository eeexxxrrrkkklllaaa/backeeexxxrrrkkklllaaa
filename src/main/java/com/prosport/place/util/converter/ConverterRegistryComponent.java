package com.prosport.place.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Vlad Milyutin.
 */

@Component("converterRegistryComponent")
public class ConverterRegistryComponent {

    private final ConversionServiceFactoryBean conversionServiceFactoryBean;
    private final Converter jsonNodeToPlaceConverter;
    private final Converter placeToJsonNodeConverter;

    @Autowired
    public ConverterRegistryComponent(
            @Qualifier("conversionService") ConversionServiceFactoryBean conversionServiceFactoryBean,
            @Qualifier("jsonNodeToPlaceConverter") Converter jsonNodeToPlaceConverter,
            @Qualifier("placeToJsonNodeConverter") Converter placeToJsonNodeConverter) {
        this.conversionServiceFactoryBean = conversionServiceFactoryBean;
        this.jsonNodeToPlaceConverter = jsonNodeToPlaceConverter;
        this.placeToJsonNodeConverter = placeToJsonNodeConverter;
    }

    @PostConstruct
    private void init() {

        ConversionService conversionService = conversionServiceFactoryBean.getObject();
        ConverterRegistry registry = (ConverterRegistry) conversionService;

        registry.addConverter(this.jsonNodeToPlaceConverter);
        registry.addConverter(this.placeToJsonNodeConverter);
    }

}
