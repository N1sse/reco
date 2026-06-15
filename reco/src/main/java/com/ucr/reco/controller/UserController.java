package com.ucr.reco.controller;

import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.LoginDTO;
import com.ucr.reco.model.dto.UserDTO;
import com.ucr.reco.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<User> users = service.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        User user = service.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors =new ArrayList<>();
            for (ObjectError error: result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        if (service.add(user) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya está registrado o faltan campos obligatorios");

        }
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody UserDTO user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        User updatedUser = service.update(id, user);

        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un usuario con ese id");
        }

        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        if (service.delete(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }

    @PatchMapping("/change/{email}")
    public ResponseEntity<?> changePassword(@Valid @PathVariable String email, @RequestBody User user, BindingResult result) {
        User updatedUser = service.changePassword(email, user.getPassword());
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Contraseña actualizada exitosamente");
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO user, BindingResult result){
        if (result.hasErrors()) {
            List<String> errors =new ArrayList<>();
            for (ObjectError error: result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if (service.login(user) != null){
            return ResponseEntity.ok("Ha iniciado sesión exitosamente");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este correo no esta registrado");
    }


}
