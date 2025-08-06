package com.example.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.data.entity.Notification;
import com.example.data.entity.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    void deleteByActorUserAndActionTypeAndSourceId(User actorUser, String actionType, Long sourceId);
}
