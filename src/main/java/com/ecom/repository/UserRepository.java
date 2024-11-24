package com.ecom.repository;

import com.ecom.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {

    UserDetails findByEmail(String email);

    UserDetails findByEmailAndPassword(String email, String password);
}
