package org.skkov.playr.api.dto;

import lombok.*;

/**
 * DTO пользователя для API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String avatarUrl;
}
