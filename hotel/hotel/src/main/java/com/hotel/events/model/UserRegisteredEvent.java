package com.hotel.events.model;


import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.VerificationToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisteredEvent {
  private VerificationToken token;

}
