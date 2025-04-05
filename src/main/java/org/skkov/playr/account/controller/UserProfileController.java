package org.skkov.playr.account.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.account.dto.UserProfileDto;
import org.skkov.playr.account.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST-контроллер для управления профилями пользователей.
 * <p>
 * Позволяет получать и изменять собственный профиль пользователя,
 * загружать аватар, а также искать и просматривать профили других пользователей.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
@Tag(name = "User Profile API", description = "Управление профилями пользователей")
public class UserProfileController {
  private final UserProfileService userProfileService;

  /**
   * Получение профиля текущего пользователя.
   *
   * @param authentication текущий авторизованный пользователь
   * @return данные профиля текущего пользователя
   */
  @GetMapping("/me")
  public ResponseEntity<UserProfileDto> getMyProfile(Authentication authentication) {
    return ResponseEntity.ok(userProfileService.getMyProfile(authentication));
  }

  /**
   * Обновление профиля текущего пользователя.
   *
   * @param authentication текущий авторизованный пользователь
   * @param profileDto новые данные профиля для обновления
   * @return обновлённый профиль текущего пользователя
   */
  @PutMapping("/me")
  public ResponseEntity<UserProfileDto> updateMyProfile(
      Authentication authentication,
      @RequestBody UserProfileDto profileDto
  ) {
    return ResponseEntity.ok(userProfileService.updateMyProfile(authentication, profileDto));
  }

  /**
   * Получение профиля пользователя по ID.
   *
   * @param id идентификатор профиля пользователя
   * @return данные профиля указанного пользователя
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserProfileDto> getProfileById(@PathVariable UUID id) {
    return ResponseEntity.ok(userProfileService.getProfileById(id));
  }

  /**
   * Поиск профилей пользователей по заданному запросу.
   *
   * @param query строка поиска (имя, фамилия, никнейм)
   * @return список профилей пользователей, соответствующих запросу
   */
  @GetMapping
  public ResponseEntity<List<UserProfileDto>> searchProfiles(
      @RequestParam(required = false) String query
  ) {
    return ResponseEntity.ok(userProfileService.searchProfiles(query));
  }

  /**
   * Загрузка аватара текущим пользователем.
   *
   * @param authentication текущий авторизованный пользователь
   * @param file файл изображения аватара
   * @return HTTP-статус 200 OK при успешной загрузке аватара
   */
  @PostMapping("/me/avatar")
  public ResponseEntity<?> uploadAvatar(
      Authentication authentication,
      @RequestParam("file") MultipartFile file
  ) {
    userProfileService.uploadAvatar(authentication, file);
    return ResponseEntity.ok().build();
  }
}
