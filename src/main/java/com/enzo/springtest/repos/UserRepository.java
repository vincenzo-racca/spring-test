package com.enzo.springtest.repos;

import com.enzo.springtest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
