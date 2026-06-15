package com.ucr.reco.service;

import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.LoginDTO;
import com.ucr.reco.model.dto.UserDTO;
import com.ucr.reco.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserJpaRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    /*
        public User add(User user){
            if(repository.existsByEmail(user.getEmail())){
                return null;
            }
            return repository.save(user);
        }
    */

    //Add
    /*
    public User add(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            return null;
        } else {
            if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
                return null;
            }
        }
        return repository.save(user);
        //return "Proceso exitoso";
    }*/

    public User add(UserDTO user) {

        if (repository.existsByEmail(user.getEmail())) {
            return null;
        } else {
            if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
                return null;
            }
        }
        User userTemp=new User();
        userTemp.setName(user.getName());
        userTemp.setEmail(user.getEmail());
        userTemp.setPassword(user.getPassword());
        userTemp.setRole(user.getRole());
        return repository.save(userTemp);
        //return "Proceso exitoso";
    }

    public User login(LoginDTO user) {
        User userExists = repository.getByEmail(user.getEmail());

        if (userExists != null && userExists.getPassword().equals(user.getPassword())) {
            return userExists;
        }

        return null;
    }

    public User getById(Integer id) {
        User user = repository.findById(id.intValue());
        if (user != null) {
            return user;
        }
        /*if (repository.existsById(id)) {
            return repository.findById(id).get();
        }*/
        return null;
    }

    public User update(Integer id, UserDTO user) {

        Optional<User> userExists = repository.findById(id);

        if (userExists.isPresent()) {
            User userTemp = userExists.get();

            if (user.getName() != null) {
                userTemp.setName(user.getName());
            }

            if (user.getEmail() != null ) {
                userTemp.setEmail(user.getEmail());
            }

            if (user.getPassword() != null) {
                userTemp.setPassword(user.getPassword());
            }

            if (user.getRole() != null ) {
                userTemp.setRole(user.getRole());
            }

            return repository.save(userTemp);
        }

        return null;
    }

    public User delete(Integer id) {
        Optional<User> userExists = repository.findById(id);
        if (userExists.isPresent()) {
            repository.deleteById(id);
            return (User) userExists.get();
        } else {
            return null;
        }
    }


    public User changePassword(String email, String newPassword) {
        /*
        User userExists = repository.getByEmail(email);
        if (userExists != null) {
            userExists.setPassword(newPassword);
            return repository.save(userExists);
        } else {
            return null;
        }
        */

        if (email == null || newPassword == null) {
            return null;
        }

        User userExists = repository.getByEmail(email);

        if (userExists != null) {
            userExists.setPassword(newPassword);
            return repository.save(userExists);
        } else {
            return null;
        }
    }

    public  User getUserByEmail(String email){
        return repository.getByEmail(email);
    }
}
