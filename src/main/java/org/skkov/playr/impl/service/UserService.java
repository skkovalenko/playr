package org.skkov.playr.impl.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.UserDto;
import org.skkov.playr.domain.repositoty.UserRepository;
import org.skkov.playr.impl.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для управления пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  /**
   * Получить профиль пользователя по ID.
   */
  public Optional<UserDto> getUserById(UUID id) {
    return userRepository.findById(id).map(userMapper::toDto);
  }

  /**
   * Обновить профиль пользователя.
   */
  public UserDto updateUser(UUID id, UserDto userDto) {
    var foundUser = userRepository.findById(id);
    if (foundUser.isPresent()) {
      var user = userRepository.save(userMapper.toEntity(userDto));
      return userMapper.toDto(user);
    }
    throw new RuntimeException("UserProfile not found");
  }

  /**
   * Загрузить аватар пользователя (заглушка).
   */
  public void uploadAvatar(UUID id, MultipartFile file) {
    userRepository.findById(id).ifPresent(user -> {
      user.setAvatarUrl("https://cdn.example.com/avatars/" + id); // Заглушка URL
      userRepository.save(user);
    });
  }

  /**
   * Получить список мероприятий пользователя (заглушка).
   */
  public List<String> getUserEvents(UUID userId) {
    return List.of("Football match", "Basketball training"); // Заглушка
  }
}
