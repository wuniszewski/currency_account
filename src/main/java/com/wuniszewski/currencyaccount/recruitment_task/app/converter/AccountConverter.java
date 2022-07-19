package com.wuniszewski.currencyaccount.recruitment_task.app.converter;

import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements DTOConverter<AccountDTO, Account> {

    private final ModelMapper modelMapper;

    @Autowired
    public AccountConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDTO convertToDTO(Account entityToConvert) {
        return modelMapper.map(entityToConvert, AccountDTO.class);
    }
}
