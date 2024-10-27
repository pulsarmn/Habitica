package com.pulsar.habitica.validation;

import com.pulsar.habitica.annotation.ValidationTest;
import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;
import com.pulsar.habitica.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


public class RegisterValidatorTest {

    private static final RegisterUserDto CORRECT_USER_DTO = RegisterUserDto.builder()
            .nickname("pulsar")
            .email("pulsarmn@yandex.ru")
            .password("123456789gB")
            .doublePassword("123456789gB")
            .build();
    private UserDao userDao;
    private Validator<RegisterUserDto> validator;

    @BeforeEach
    void prepareObjects() {
        userDao = Mockito.mock(UserDao.class);
        validator = new RegisterValidator(userDao);
    }

    @ValidationTest
    void successIfUserDtoIsValid() {
        Mockito.doReturn(Optional.empty()).when(userDao).findByNickname(CORRECT_USER_DTO.getNickname());
        Mockito.doReturn(Optional.empty()).when(userDao).findByEmail(CORRECT_USER_DTO.getEmail());

        assertThat(validator.isValid(CORRECT_USER_DTO).isValid()).isTrue();
    }

    @Nested
    class TestNickname {

        @ValidationTest
        void failIfUserNicknameIsEmpty() {
            var userDto = RegisterUserDto.builder()
                    .nickname("")
                    .email("pulsarmn@yandex.ru")
                    .password("123456789gB")
                    .doublePassword("123456789gB")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.NICKNAME_REQUIRED));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserNicknameAlreadyExists() {
            Mockito.doReturn(Optional.of(User.builder().build())).when(userDao).findByNickname(CORRECT_USER_DTO.getNickname());

            var result = validator.isValid(CORRECT_USER_DTO).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.NICKNAME_TAKEN));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserNicknameIsTooLong() {
            var userDto = RegisterUserDto.builder()
                    .nickname("too_long_nickname_too_long_nickname_too_long_nickname")
                    .email("pulsarmn@yandex.ru")
                    .password("123456789gB")
                    .doublePassword("123456789gB")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.LONG_NICKNAME));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserNicknameIsDigits() {
            var userDto = RegisterUserDto.builder()
                    .nickname("23432847238742")
                    .email("pulsarmn@yandex.ru")
                    .password("123456789gB")
                    .doublePassword("123456789gB")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.INVALID_NICKNAME));
            assertThat(result).isTrue();
        }
    }

    @Nested
    class TestEmail {

        @ValidationTest
        void failIfUserEmailIsEmpty() {
            var userDto = RegisterUserDto.builder()
                    .nickname("pulsar")
                    .email("")
                    .password("123456789gB")
                    .doublePassword("123456789gB")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.EMAIL_REQUIRED));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserEmailIsInvalid() {
            var userDto = RegisterUserDto.builder()
                    .nickname("pulsar")
                    .email("asdfjalsk")
                    .password("123456789gB")
                    .doublePassword("123456789gB")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.INVALID_EMAIL));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserEmailAlreadyExists() {
            Mockito.doReturn(Optional.of(User.builder().build())).when(userDao).findByEmail(CORRECT_USER_DTO.getEmail());

            var result = validator.isValid(CORRECT_USER_DTO).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.EMAIL_TAKEN));
            assertThat(result).isTrue();
        }
    }

    @Nested
    class TestPassword {
        @ValidationTest
        void failIfUserPasswordIsEmpty() {
            var userDto = RegisterUserDto.builder()
                    .nickname("pulsar")
                    .email("pulsarmn@yandex.ru")
                    .password("")
                    .doublePassword("123456789gB")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.PASSWORD_REQUIRED));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserPasswordTooShort() {
            var userDto = RegisterUserDto.builder()
                    .nickname("pulsar")
                    .email("pulsarmn@yandex.ru")
                    .password("1234")
                    .doublePassword("1234")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.SHORT_PASSWORD));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserPasswordTooLong() {
            var userDto = RegisterUserDto.builder()
                    .nickname("pulsar")
                    .email("pulsarmn@yandex.ru")
                    .password("too-long-password-too-long-password-too-long-password-too-long-password-too-long-password")
                    .doublePassword("too-long-password-too-long-password-too-long-password-too-long-password-too-long-password")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.LONG_PASSWORD));
            assertThat(result).isTrue();
        }

        @ValidationTest
        void failIfUserSecondPasswordDoesntMatch() {
            var userDto = RegisterUserDto.builder()
                    .nickname("pulsar")
                    .email("pulsarmn@yandex.ru")
                    .password("123456789a")
                    .doublePassword("123456789aA")
                    .build();

            var result = validator.isValid(userDto).getErrors().stream()
                    .anyMatch(error -> error.getCode().equals(ErrorCodes.DONT_MATCH));
            assertThat(result).isTrue();
        }
    }
}
