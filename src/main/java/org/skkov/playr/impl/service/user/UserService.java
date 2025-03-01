package org.skkov.playr.impl.service.user;

import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.UserDTO;
import org.skkov.playr.domain.repositoty.UserRepository;
import org.skkov.playr.impl.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
  public Optional<UserDTO> getUserById(UUID id) {
    return userRepository.findById(id).map(user -> userMapper.toDTO(user));
  }

  /**
   * Обновить профиль пользователя.
   */
  public UserDTO updateUser(UUID id, UserDTO userDTO) {
    return userRepository.findById(id)
        .map(user -> {
          user.setFirstName(userDTO.getFirstName());
          user.setLastName(userDTO.getLastName());
          user.setEmail(userDTO.getEmail());
          return userMapper.toDTO(userRepository.save(user));
        })
        .orElseThrow(() -> new RuntimeException("UserProfile not found"));
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
