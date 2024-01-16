package org.devlacruz.springcloud.msvc.users.msvcusers.services;

import java.util.List;
import java.util.Optional;

import org.devlacruz.springcloud.msvc.users.msvcusers.models.entity.User;
import org.devlacruz.springcloud.msvc.users.msvcusers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return (List<User>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> forId(Long id) {
        
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
      
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> forEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

}
