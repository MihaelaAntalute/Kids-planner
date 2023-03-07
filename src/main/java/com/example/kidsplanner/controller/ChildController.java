package com.example.kidsplanner.controller;

import com.example.kidsplanner.DTO.AddChildRequestDTO;
import com.example.kidsplanner.model.InvalidEmailException;
import com.example.kidsplanner.model.User;
import com.example.kidsplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController {
    private UserService userService;

    @Autowired
    public ChildController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public User addChild(@RequestBody AddChildRequestDTO addChildRequestDTO) throws InvalidEmailException {
        return userService.addChild(addChildRequestDTO);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody AddChildRequestDTO childRequestDTO) {
        return userService.updateUser(userId, childRequestDTO);
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/names/")
    public List<String> getAllUsersNames() {
        return userService.getAllUsersNames();
    }
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }


}

