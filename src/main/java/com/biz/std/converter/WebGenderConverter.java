package com.biz.std.converter;

import com.biz.std.vo.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 *  性别转换器
 */
public class WebGenderConverter implements Converter<Integer, Gender> {
    @Nullable
    @Override
    public Gender convert(Integer integer) {
        if (integer != null){
            Gender[] genders = Gender.values();
            for (Gender gender : genders){
                if(integer.equals(gender.getValue())){
                    return gender;
                }
            }
        }
        return null;
    }
}
