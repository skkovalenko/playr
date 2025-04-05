package org.skkov.playr.account.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Сервис для загрузки и хранения аватаров пользователей.
 */
@Service
public class AvatarStorageService {
  /**
   * Загрузка файла аватара и возврат публичной ссылки.
   *
   * @param file файл изображения
   * @return URL к загруженному файлу
   */
  public String storeAvatar(MultipartFile file) {
    // Пример простой имитации загрузки
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    // Здесь будет реальный upload в S3, Cloud, локально и т.д.
    return "https://cdn.playr.app/avatars/" + fileName;
  }
}
