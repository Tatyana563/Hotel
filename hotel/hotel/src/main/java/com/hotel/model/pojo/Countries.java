package com.hotel.model.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class Countries {
  private   List<Country> countries;
}
