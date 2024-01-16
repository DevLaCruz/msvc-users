package org.devlacruz.springcloud.msvc.users.msvcusers.services;
import java.util.List;
import java.util.Optional;

import org.devlacruz.springcloud.msvc.users.msvcusers.models.entity.User;


public interface UserService {
    List<User> list();
    Optional<User> forId(Long id);
    User save(User user);
    void delete(Long id);
    
    Optional <User> forEmail(String email);
    boolean existsByEmail(String email);
}
