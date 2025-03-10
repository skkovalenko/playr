package org.skkov.playr.api.dto;

import lombok.Data;

/**
 * DTO пользователя для API.
 */
@Data
public class UserDto {
  private String firstName;
  private String lastName;
  private String email;
  private String avatarUrl;
}
