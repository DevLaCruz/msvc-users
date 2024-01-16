package org.devlacruz.springcloud.msvc.users.msvcusers.repositories;

import java.util.Optional;

import org.devlacruz.springcloud.msvc.users.msvcusers.models.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email=?1")
    Optional<User> forEmail(String email);

    boolean existsByEmail(String email);
}
