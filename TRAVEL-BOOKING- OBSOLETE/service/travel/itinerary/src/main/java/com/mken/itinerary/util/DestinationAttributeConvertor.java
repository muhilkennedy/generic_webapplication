package com.mken.itinerary.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mken.itinerary.model.DestinationAttributes;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Muhil
 *
 */
@Converter(autoApply = true)
public class DestinationAttributeConvertor implements AttributeConverter<DestinationAttributes, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(DestinationAttributes attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	@Override
	public DestinationAttributes convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, DestinationAttributes.class);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

}
