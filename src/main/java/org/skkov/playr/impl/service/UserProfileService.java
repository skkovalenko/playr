package org.skkov.playr.impl.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.UserProfileDto;
import org.skkov.playr.domain.repositoty.UserProfileRepository;
import org.skkov.playr.domain.tables.records.UserProfileRecord;
import org.skkov.playr.impl.mapper.UserProfileMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для управления профилями пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserProfileService {
  private final UserProfileRepository userProfileRepository;
  private final UserProfileMapper userProfileMapper;
  private final AvatarStorageService avatarStorageService;

  /**
   * Получить текущий профиль пользователя.
   *
   * @param authentication объект текущей аутентификации
   * @return DTO профиля пользователя
   */
  public UserProfileDto getMyProfile(Authentication authentication) {
    UUID accountId = extractAccountId(authentication);
    UserProfileRecord profile = userProfileRepository.findByAccountId(accountId)
        .orElseThrow(() -> new RuntimeException("Профиль не найден"));
    return userProfileMapper.toDto(profile);
  }

  /**
   * Обновить текущий профиль пользователя.
   *
   * @param authentication объект текущей аутентификации
   * @param profileDto     новые данные профиля
   * @return обновлённый DTO профиля
   */
  public UserProfileDto updateMyProfile(Authentication authentication, UserProfileDto profileDto) {
    UUID accountId = extractAccountId(authentication);
    UserProfileRecord existing = userProfileRepository.findByAccountId(accountId)
        .orElseThrow(() -> new RuntimeException("Профиль не найден"));

    existing.setFirstName(profileDto.getFirstName());
    existing.setLastName(profileDto.getLastName());
    existing.setUsername(profileDto.getUsername());
    existing.setBio(profileDto.getBio());
    existing.setBirthDate(profileDto.getBirthDate());

    UserProfileRecord updated = userProfileRepository.save(existing);
    return userProfileMapper.toDto(updated);
  }

  /**
   * Получить публичный профиль пользователя по ID.
   *
   * @param id идентификатор профиля
   * @return DTO профиля
   */
  public UserProfileDto getProfileById(UUID id) {
    UserProfileRecord profile = userProfileRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Профиль не найден"));
    return userProfileMapper.toDto(profile);
  }

  /**
   * Поиск профилей по строке запроса.
   *
   * @param query строка для поиска (имя, никнейм и т.д.)
   * @return список DTO профилей
   */
  public List<UserProfileDto> searchProfiles(String query) {
    List<UserProfileRecord> profiles = query == null || query.isBlank()
        ? userProfileRepository.findAll()
        : userProfileRepository.search(query);

    return profiles
        .stream()
        .map(userProfileMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Загрузка аватара для текущего пользователя.
   *
   * @param authentication объект текущей аутентификации
   * @param file           изображение аватара
   */
  public void uploadAvatar(Authentication authentication, MultipartFile file) {
    UUID accountId = extractAccountId(authentication);
    UserProfileRecord profile = userProfileRepository.findByAccountId(accountId)
        .orElseThrow(() -> new RuntimeException("Профиль не найден"));

    String avatarUrl = avatarStorageService.storeAvatar(file);
    profile.setAvatarUrl(avatarUrl);
    userProfileRepository.save(profile);
  }

  /**
   * Извлечение UUID аккаунта из объекта аутентификации.
   *
   * @param authentication объект Spring Security
   * @return UUID аккаунта
   */
  private UUID extractAccountId(Authentication authentication) {
    // В зависимости от вашей реализации UserDetails
    // Пример:
    return UUID.fromString(authentication.getName());
  }
}
