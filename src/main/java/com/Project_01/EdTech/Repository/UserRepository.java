package com.Project_01.EdTech.Repository;

import com.Project_01.EdTech.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByuserName(String username);

    public boolean existsByuserName(String username);

    public boolean existsByemail(String email);
}
