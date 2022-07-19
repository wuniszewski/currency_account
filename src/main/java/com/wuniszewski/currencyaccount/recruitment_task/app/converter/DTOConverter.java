package com.wuniszewski.currencyaccount.recruitment_task.app.converter;

public interface DTOConverter<T, V> {

    T convertToDTO(V entityToConvert);
}
