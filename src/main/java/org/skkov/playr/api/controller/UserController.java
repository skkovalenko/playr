package org.skkov.playr.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.UserDTO;
import org.skkov.playr.impl.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с пользователями.
 * Предоставляет API для получения, обновления профиля, загрузки аватаров и просмотра мероприятий.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "API для работы с профилями пользователей")
public class UserController {

  private final UserService userService;

  /**
   * Получить профиль пользователя по его идентификатору.
   *
   * @param id UUID пользователя
   * @return ResponseEntity с UserDTO, если найден, или 404 Not Found
   */
  @Operation(summary = "Получить профиль пользователя")
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
    Optional<UserDTO> user = userService.getUserById(id);
    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Обновить профиль пользователя.
   *
   * @param id      UUID пользователя
   * @param userDTO DTO с обновлёнными данными
   * @return ResponseEntity с обновленным UserDTO
   */
  @Operation(summary = "Обновить профиль пользователя")
  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.updateUser(id, userDTO));
  }

  /**
   * Загрузить аватар пользователя.
   *
   * @param id   UUID пользователя
   * @param file Загружаемый файл изображения
   * @return ResponseEntity с сообщением об успешной загрузке
   */
  @Operation(summary = "Загрузить аватар пользователя")
  @PostMapping("/{id}/avatar")
  public ResponseEntity<String> uploadAvatar(
      @PathVariable UUID id,
      @RequestParam MultipartFile file
  ) {
    userService.uploadAvatar(id, file);
    return ResponseEntity.ok("Avatar uploaded successfully");
  }

  /**
   * Получить список мероприятий, связанных с пользователем.
   *
   * @param id UUID пользователя
   * @return ResponseEntity со списком названий мероприятий
   */
  @Operation(summary = "Получить список мероприятий пользователя")
  @GetMapping("/{id}/events")
  public ResponseEntity<List<String>> getUserEvents(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.getUserEvents(id));
  }
}
