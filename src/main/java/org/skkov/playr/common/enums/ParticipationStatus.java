package org.skkov.playr.common.enums;

/**
 * Перечисление возможных статусов участия пользователя в мероприятии.
 *
 * @author WorkSKKov
 */
public enum ParticipationStatus {
  /**
   * Пользователь подал заявку, ожидает одобрения
   */
  REGISTERED,
  /**
   * Участие одобрено организатором
   */
  APPROVED,
  /**
   * Заявка отклонена организатором
   */
  REJECTED,
  /**
   * Участник самостоятельно отменил участие
   */
  CANCELLED
}
