package com.godtiner.api.domain.sharedroutines;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godtiner.api.domain.sharedroutines.dto.TagInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
@Converter
public class RoutineTagConverter implements AttributeConverter<List<TagInfo>,String> {
   private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Override
    public String convertToDatabaseColumn(List<TagInfo> attribute) {
        if(CollectionUtils.isEmpty(attribute)) {
            return new String();
        }
        try{
            //ObjectMapper objectMapper = CommonUtils.getMapper();
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TagInfo> convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        try{
            //ObjectMapper objectMapper = CommonUtils.getMapper();
                return Arrays.asList(objectMapper.readValue(dbData, TagInfo[].class));
            } catch (JsonProcessingException e) {
            log.info("failed to parse database. data to json.");
            return Collections.emptyList();
            }
        }
    }

