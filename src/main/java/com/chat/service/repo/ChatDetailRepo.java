package com.chat.service.repo;

import com.chat.service.entity.ChatDetails;
import com.chat.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDetailRepo extends JpaRepository<ChatDetails, Long> {

}
