package com.example.data.utils;

import com.example.data.entity.PostStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PostStatusConverter implements AttributeConverter<PostStatus, String> {

    @Override
    public String convertToDatabaseColumn(PostStatus postStatus) {
        if (postStatus == null) {
            return null;
        }

        return postStatus.name().toLowerCase();
    }

    @Override
    public PostStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return PostStatus.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("这是个未知的 PostStatus 常量" + dbData);
        }
    }
}
