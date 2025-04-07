package com.learning.blogappapis.repository;

import com.learning.blogappapis.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles,Integer>
{
    Roles findByRoleName(String roleName);
}
