package com.webnc.bt.mapper;

import com.webnc.bt.dto.response.LanguageResponse;
import com.webnc.bt.entity.Language;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    LanguageResponse toDto(Language language);
}