package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name); //select * from where name = ?
    boolean existsByName(String name); // 이름이 존재하는지

    long countByAge(Integer age); // 해당 나이를 가진 유저의 수를 센다

    //findAllByNameAndAge : select * from user where name = ? and age = ?;
 }
