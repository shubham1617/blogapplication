package com.learning.blogappapis.repository;

import com.learning.blogappapis.model.Roles;
import com.learning.blogappapis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {


    @Query(value = "SELECT * FROM user WHERE email = BINARY :email", nativeQuery = true)
    User findByEmailCaseSensitive(@Param("email") String email);

}
