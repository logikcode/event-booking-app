package com.bw.reference.modelfactory;

import com.bw.enums.GenderConstant;
import com.bw.reference.domain.account.UserRegistrationDto;
import com.bw.reference.domain.enumeration.UserTypeConstant;
import com.bw.reference.entity.State;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.UUID;

public class UserRegistrationDtoFactory implements FactoryHelper<UserRegistrationDto> {
    @Override
    public Class<UserRegistrationDto> getEntity() {
        return UserRegistrationDto.class;
    }

    @Override
    public UserRegistrationDto apply(Faker faker, ModelFactory factory) {
        UserRegistrationDto dto = new UserRegistrationDto();
        State state = factory.create(State.class);
        dto.setCountry(state.getCountry().getId());
        dto.setEmail(UUID.randomUUID().toString());
        dto.setFirstName(faker.name().firstName());
        dto.setLastName(faker.name().lastName());
        dto.setGender(GenderConstant.MALE);
        dto.setMobileNumber(faker.phoneNumber().phoneNumber());
        dto.setPassword("password");
        dto.setUserType(UserTypeConstant.STAFF);
        return dto;
    }
}
