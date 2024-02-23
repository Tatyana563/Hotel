package com.hotel.service.impl;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.model.dto.request.ShortRegistrationRequest;
import com.hotel.model.dto.response.NotifyAgainResponse;
import com.hotel.model.entity.User;
import com.hotel.model.entity.VerificationToken;
import com.hotel.repository.TokenRepository;
import com.hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@SpringBootTest(classes=)
//        (classes = RegistrationProperties.class)
//@TestPropertySource("classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

 //   public static final Instant NOW = Instant.parse("2024-02-07T17:00:00.432702600Z");
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private RegistrationProperties registrationProperties;
    @Mock
    private Clock clock;
    @Captor
    private ArgumentCaptor<UserRegisteredEvent> registeredEventArgumentCaptor;
    @InjectMocks
    private UserServiceImpl userService;
// This aproach can be used instead of GeneralConfiguration
//    @BeforeEach
//    public void init() {
//        userService = new UserServiceImpl(publisher, passwordEncoder, userRepository, tokenRepository, roleRepository, registrationProperties){
//            @Override
//            protected Instant now() {
//                return NOW;
//            }
//        };
//    }


    @Test
    public void resendRegistrationTokenRequestWhenTokenIsNotExpiredAndNotAboutToBeExpiredAndRequestAllowed() {

        ShortRegistrationRequest request = new ShortRegistrationRequest();
        User user = new User();
        user.setUsername("test_user");
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        Instant lastNotificationDate = Instant.parse("2024-02-09T17:00:41.432702600Z");
        Instant expiryDate = Instant.parse("2024-02-09T20:57:41.432702600Z");

        verificationToken.setLastNotificationDate(lastNotificationDate);
        verificationToken.setExpiryDate(expiryDate);
        request.setEmail("example@gmail.com");

        when(tokenRepository.findByUserEmail(request.getEmail())).thenReturn(verificationToken);
        when(registrationProperties.getRequestRetryDuration()).thenReturn(Duration.ofMinutes(30));
        when(registrationProperties.getTokenTimeLeftToRenew()).thenReturn(Duration.ofMinutes(40));
        Instant now = Instant.parse("2024-02-09T19:00:00.432702600Z");
        when(clock.instant()).thenReturn(now);

        NotifyAgainResponse notifyAgainResponse = userService.resendRegistrationTokenRequest(request);

        verify(publisher).publishEvent(registeredEventArgumentCaptor.capture());
        UserRegisteredEvent value = registeredEventArgumentCaptor.getValue();

        assertThat(value.getToken().getLastNotificationDate()).isEqualTo(now);
        assertThat(value.getToken().getExpiryDate()).isEqualTo(expiryDate);
        assertThat(notifyAgainResponse.isSuccess()).isTrue();
        assertThat(notifyAgainResponse.getNextAttempt()).isEqualTo("2024-02-09T19:30:00.432702600Z");
    }

    @Test
    public void resendRegistrationTokenRequestWhenTokenIsNull() {

        ShortRegistrationRequest request = new ShortRegistrationRequest();
        User user = new User();
        user.setUsername("test_user");
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        Instant lastNotificationDate = Instant.parse("2024-02-09T17:00:41.432702600Z");
        Instant expiryDate = Instant.parse("2024-02-09T20:57:41.432702600Z");

        verificationToken.setLastNotificationDate(lastNotificationDate);
        verificationToken.setExpiryDate(expiryDate);
        request.setEmail("example@gmail.com");

        when(userRepository.findUserByEmail("example@gmail.com")).thenReturn(user);
        when(registrationProperties.getRequestRetryDuration()).thenReturn(Duration.ofMinutes(30));
        when(registrationProperties.getTokenExpiration()).thenReturn(Duration.ofMinutes(60));
        Instant now = Instant.parse("2024-02-09T19:00:00.432702600Z");
        when(clock.instant()).thenReturn(now);

        NotifyAgainResponse notifyAgainResponse = userService.resendRegistrationTokenRequest(request);

        verify(publisher).publishEvent(registeredEventArgumentCaptor.capture());
        UserRegisteredEvent value = registeredEventArgumentCaptor.getValue();

        assertThat(value.getToken().getLastNotificationDate()).isEqualTo("2024-02-09T20:00:00.432702600Z");
        assertThat(value.getToken().getExpiryDate()).isEqualTo("2024-02-09T20:00:00.432702600Z");
        assertThat(notifyAgainResponse.isSuccess()).isTrue();
        assertThat(notifyAgainResponse.getNextAttempt()).isEqualTo("2024-02-09T19:30:00.432702600Z");
    }

    @Test
    public void resendRegistrationTokenRequestWhenTokenIsNotNullAndRequestNotAllowed() {

        ShortRegistrationRequest request = new ShortRegistrationRequest();
        User user = new User();
        user.setUsername("test_user");
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        Instant lastNotificationDate = Instant.parse("2024-02-09T17:00:41.432702600Z");
        Instant expiryDate = Instant.parse("2024-02-09T20:57:41.432702600Z");

        verificationToken.setLastNotificationDate(lastNotificationDate);
        verificationToken.setExpiryDate(expiryDate);
        request.setEmail("example@gmail.com");

        when(tokenRepository.findByUserEmail(request.getEmail())).thenReturn(verificationToken);
        when(registrationProperties.getRequestRetryDuration()).thenReturn(Duration.ofMinutes(30));

        Instant now = Instant.parse("2024-02-09T17:10:00.432702600Z");
        when(clock.instant()).thenReturn(now);

        NotifyAgainResponse notifyAgainResponse = userService.resendRegistrationTokenRequest(request);

        assertThat(notifyAgainResponse.isSuccess()).isFalse();
        assertThat(notifyAgainResponse.getNextAttempt()).isEqualTo("2024-02-09T17:30:41.432702600Z");
    }

    @Test
    public void resendRegistrationTokenRequestWhenTokenIsExpired() {

        ShortRegistrationRequest request = new ShortRegistrationRequest();
        User user = new User();
        user.setUsername("test_user");
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        Instant lastNotificationDate = Instant.parse("2024-02-09T17:00:41.432702600Z");
        Instant expiryDate = Instant.parse("2024-02-09T18:55:41.432702600Z");

        verificationToken.setLastNotificationDate(lastNotificationDate);
        verificationToken.setExpiryDate(expiryDate);
        request.setEmail("example@gmail.com");
        when(userRepository.findUserByEmail("example@gmail.com")).thenReturn(user);

        when(tokenRepository.findByUserEmail(request.getEmail())).thenReturn(verificationToken);
        when(registrationProperties.getRequestRetryDuration()).thenReturn(Duration.ofMinutes(30));

        when(registrationProperties.getTokenExpiration()).thenReturn(Duration.ofMinutes(60));
        Instant now = Instant.parse("2024-02-09T19:00:00.432702600Z");
        when(clock.instant()).thenReturn(now);

        NotifyAgainResponse notifyAgainResponse = userService.resendRegistrationTokenRequest(request);

        verify(publisher).publishEvent(registeredEventArgumentCaptor.capture());
        UserRegisteredEvent value = registeredEventArgumentCaptor.getValue();

        assertThat(value.getToken().getLastNotificationDate()).isEqualTo("2024-02-09T20:00:00.432702600Z");
        assertThat(value.getToken().getExpiryDate()).isEqualTo("2024-02-09T20:00:00.432702600Z");
        assertThat(notifyAgainResponse.isSuccess()).isTrue();
        assertThat(notifyAgainResponse.getNextAttempt()).isEqualTo("2024-02-09T19:30:00.432702600Z");
    }

    @Test
    public void resendRegistrationTokenRequestWhenTokenIsAboutToBeExpired() {

        ShortRegistrationRequest request = new ShortRegistrationRequest();
        User user = new User();
        user.setUsername("test_user");
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        Instant lastNotificationDate = Instant.parse("2024-02-09T17:00:41.432702600Z");
        Instant expiryDate = Instant.parse("2024-02-09T20:00:41.432702600Z");

        verificationToken.setLastNotificationDate(lastNotificationDate);
        verificationToken.setExpiryDate(expiryDate);
        request.setEmail("example@gmail.com");

        when(tokenRepository.findByUserEmail(request.getEmail())).thenReturn(verificationToken);
        when(registrationProperties.getRequestRetryDuration()).thenReturn(Duration.ofMinutes(30));
        when(registrationProperties.getTokenTimeLeftToRenew()).thenReturn(Duration.ofMinutes(40));
        when(registrationProperties.getTokenExpiration()).thenReturn(Duration.ofMinutes(60));
        Instant now = Instant.parse("2024-02-09T19:55:00.432702600Z");
        when(clock.instant()).thenReturn(now);

        NotifyAgainResponse notifyAgainResponse = userService.resendRegistrationTokenRequest(request);

        verify(publisher).publishEvent(registeredEventArgumentCaptor.capture());
        UserRegisteredEvent value = registeredEventArgumentCaptor.getValue();

        assertThat(value.getToken().getLastNotificationDate()).isEqualTo(now);
        assertThat(value.getToken().getExpiryDate()).isEqualTo("2024-02-09T20:55:00.432702600Z");
        assertThat(notifyAgainResponse.isSuccess()).isTrue();
        assertThat(notifyAgainResponse.getNextAttempt()).isEqualTo("2024-02-09T20:25:00.432702600Z");
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        public TokenRepository tokenRepository() {
            return Mockito.mock(TokenRepository.class);
        }
    }
}