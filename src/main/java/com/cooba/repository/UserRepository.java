package com.cooba.repository;

import com.cooba.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
}
