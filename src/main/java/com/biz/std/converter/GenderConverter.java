package com.biz.std.converter;

import com.biz.std.vo.Gender;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author 11785
 */
@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(Integer integer) {
        Gender[] genders = Gender.values();
        for(Gender gender : genders){
            if(gender.getValue().equals(integer)){
                return gender;
            }
        }
        return null;
    }
}
