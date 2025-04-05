package org.skkov.playr.account.dto;

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
