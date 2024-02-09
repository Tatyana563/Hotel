package com.hotel.service.api.notification;

import com.hotel.events.model.PasswordResetEvent;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;

public interface PasswordResetService {
     void sendResetPasswordLink(PasswordResetEvent passwordResetEvent);

}
