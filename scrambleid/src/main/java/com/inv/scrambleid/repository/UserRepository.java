package com.inv.scrambleid.repository;

import com.inv.scrambleid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
