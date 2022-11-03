package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Long> {
    List<PhoneBook> findByUserId(Long userId);
    Optional<PhoneBook> findPhoneBookByTargetPhoneAndUserId(String targetPhone, Long userId);
}
