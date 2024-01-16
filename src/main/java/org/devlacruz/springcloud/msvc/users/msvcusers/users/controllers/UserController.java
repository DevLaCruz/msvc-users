package org.devlacruz.springcloud.msvc.users.msvcusers.users.controllers;
import org.devlacruz.springcloud.msvc.users.msvcusers.models.entity.User;
import org.devlacruz.springcloud.msvc.users.msvcusers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.list();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<User> userOptional = service.forId(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        
        if (result.hasErrors()) {
            return validator(result);
        }

        //Validamos si existe el correo
        if (!user.getEmail().isEmpty() && service.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Ya existe un usuario registrado con el mismo correo electrónico"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
    
        
        if (result.hasErrors()) {
            return validator(result);
        }
        
        Optional<User> o = service.forId(id);
        if (o.isPresent()) {
            User userdb=o.get();

            //Validamos si existe el correo y que sea diferente al que ya se tiene
        if (!user.getEmail().isEmpty() &&  !user.getEmail().equalsIgnoreCase(userdb.getEmail()) && service.forEmail(user.getEmail()). isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Ya existe un usuario registrado con el mismo correo electrónico"));
        }

            userdb.setName(user.getName());
            userdb.setEmail(user.getEmail());
            userdb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userdb));
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> o = service.forId(id);
        if (o.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


    //Método que valida:
    private ResponseEntity<?> validator(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    
}
