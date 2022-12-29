package com.antonromanov.arnote.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO для добавления нового пользователя.
 */
@Data
public class UserDto {

    /**
     * ID пользователя.
     */
    private Long id;

    /**
     * Логин пользователя.
     */
    private String login;

    /**
     * Пароль пользователя не-шифрованный.
     */
    @JsonProperty(value = "pwd")
    private String unSecurePassword;

    /**
     * Режим шифрования на фронте.
     */
    private Boolean userCryptoMode;


    /**
     * Email-адрес пользователя.
     */
    private String email;

    /**
     * Полное имя пользователя.
     */
    @JsonProperty(value = "fullname")
    private String fullName; //todo: нормально переименовать
}
