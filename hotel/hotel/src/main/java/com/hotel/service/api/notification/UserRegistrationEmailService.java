package com.hotel.service.api.notification;

import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;

public interface UserRegistrationEmailService {
     void sendEmailLinkConfirmation(UserRegisteredEvent userRegistrationEvent);
     void sendEmailSuccessfulConfirmation(UserConfirmedRegistrationEvent userConfirmedRegistrationEvent);
}
