package com.pulsar.habitica.validation;

import com.pulsar.habitica.annotation.ValidationTest;
import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.LoginUserDto;
import com.pulsar.habitica.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class LoginValidatorTest {

    private static final LoginUserDto USER_1 = LoginUserDto.builder()
            .identifier("pulsarmn@yandex.ru")
            .password("123456789aA")
            .isEmail(true)
            .build();
    private static final LoginUserDto USER_2 = LoginUserDto.builder()
            .identifier("pulsarmn")
            .password("123456789")
            .build();

    private UserDao userDao;
    private Validator<LoginUserDto> validator;

    @BeforeEach
    void prepare() {
        userDao = Mockito.mock(UserDao.class);
        validator = new LoginValidator(userDao);
    }

    @ParameterizedTest
    @MethodSource("correctDtoProvider")
    @Tag("validation")
    void successIfUserDtoIsValid(LoginUserDto dto) {
        var user = User.builder()
                .email(dto.getIdentifier())
                .password(dto.getPassword())
                .build();

        if (dto == USER_1) {
            Mockito.doReturn(Optional.of(user)).when(userDao).findByEmail(dto.getIdentifier());
        }else if (dto == USER_2) {
            Mockito.doReturn(Optional.of(user)).when(userDao).findByNickname(dto.getIdentifier());
        }

        assertThat(validator.isValid(dto).isValid()).isTrue();
    }

    static Stream<LoginUserDto> correctDtoProvider() {
        return Stream.of(USER_1, USER_2);
    }

    @ParameterizedTest
    @MethodSource("failDtoProvider")
    @Tag("validation")
    void failIfDtoIdentifierIsEmpty(LoginUserDto userDto) {
        var errors = validator.isValid(userDto).getErrors();

        assertThat(errors)
                .extracting("code")
                .contains(ErrorCodes.IDENTIFIER_REQUIRED)
                .as("An error is expected if the ID is missing");
    }

    static Stream<LoginUserDto> failDtoProvider() {
        return Stream.of(
                LoginUserDto.builder().build(),
                LoginUserDto.builder()
                        .identifier("")
                        .build()
        );
    }

    @ParameterizedTest
    @MethodSource("dtoWithIncorrectEmailProvider")
    @Tag("validation")
    void failIfEmailIsInvalid() {
        var userDto = createLoginUserDto("alsdk@fjlaksdjf", null, true);

        var errors = validator.isValid(userDto).getErrors();
        assertThat(errors)
                .extracting("code")
                .contains(ErrorCodes.INVALID_EMAIL)
                .as("An error is expected if email is invalid");
    }

    static Stream<LoginUserDto> dtoWithIncorrectEmailProvider() {
        return Stream.of(
                LoginUserDto.builder()
                        .identifier("alsdk@fjlaksdjf")
                        .build(),
                LoginUserDto.builder()
                        .identifier("alsdk@fjla.adfas")
                        .build(),
                LoginUserDto.builder()
                        .identifier("pulsarmn@yandex.asdfadsf")
                        .build(),
                LoginUserDto.builder()
                        .identifier("@asdfasdf")
                        .build(),
                LoginUserDto.builder()
                        .identifier("pulsarmn@@yandex.ru")
                        .build()
        );
    }

    @ValidationTest
    void failIfEmailNotFound() {
        var userDto = createLoginUserDto("random-email@yandex.com", null, true);
        Mockito.doReturn(Optional.empty()).when(userDao).findByEmail(userDto.getIdentifier());

        var errors = validator.isValid(userDto).getErrors();
        assertThat(errors)
                .extracting("code")
                .contains(ErrorCodes.EMAIL_NOT_EXIST)
                .as("An error is expected if the email is not found");
    }

    @ValidationTest
    void failIfNicknameDoesNotExist() {
        var userDto = createLoginUserDto("random-nickname", null, false);
        Mockito.doReturn(Optional.empty()).when(userDao).findByNickname(userDto.getIdentifier());

        var errors = validator.isValid(userDto).getErrors();
        assertThat(errors)
                .extracting("code")
                .contains(ErrorCodes.INVALID_NICKNAME)
                .as("An error is expected if the nickname is not found");
    }

    @ParameterizedTest
    @MethodSource("correctDtoProvider")
    @Tag("validation")
    void failIfPasswordIsNotCorrect(LoginUserDto userDto) {
        var user = User.builder()
                .password("incorrect_password")
                .build();

        if (userDto == USER_1) {
            Mockito.doReturn(Optional.of(user)).when(userDao).findByEmail(USER_1.getIdentifier());
        }else if (userDto == USER_2) {
            Mockito.doReturn(Optional.of(user)).when(userDao).findByNickname(USER_2.getIdentifier());
        }

        var errors = validator.isValid(userDto).getErrors();
        assertThat(errors)
                .extracting("code")
                .contains(ErrorCodes.INVALID_PASSWORD)
                .as("An error is expected if the password is not correct");
    }


    private LoginUserDto createLoginUserDto(String identifier, String password, boolean isEmail) {
        return LoginUserDto.builder()
                .identifier(identifier)
                .password(password)
                .isEmail(isEmail)
                .build();
    }
}
