package com.godtiner.api.domain.sharedroutines;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godtiner.api.domain.sharedroutines.dto.TagInfo;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

public class RoutineTagConverter implements AttributeConverter<List<TagInfo>,String> {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Override
    public String convertToDatabaseColumn(List<TagInfo> attribute) {
        try{
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TagInfo> convertToEntityAttribute(String dbData) {
        try{

                return Arrays.asList(objectMapper.readValue(dbData, TagInfo[].class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

