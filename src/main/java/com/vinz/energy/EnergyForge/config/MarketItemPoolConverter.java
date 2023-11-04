package com.vinz.energy.EnergyForge.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinz.energy.EnergyForge.dto.MarketItemPool;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MarketItemPoolConverter implements AttributeConverter<MarketItemPool, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(MarketItemPool marketItemPool) {
        try {
            return objectMapper.writeValueAsString(marketItemPool);
        } catch (JsonProcessingException e) {
            // Handle error
            return null;
            // or throw a more specific error indicating the serialization problem
        }
    }

    @Override
    public MarketItemPool convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, MarketItemPool.class);
        } catch (JsonProcessingException e) {
            // Handle error
            return null;
            // or throw a more specific error indicating the deserialization problem
        }
    }
}
