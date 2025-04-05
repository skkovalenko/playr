package org.skkov.playr.api.dto.account;

import lombok.Data;

/**
 * Описание ChangePasswordRequest.
 *
 * @author SKKOV
 */
@Data
public class ChangePasswordRequest {
  private String oldPassword;
  private String newPassword;
}
