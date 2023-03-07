package com.example.kidsplanner.controller;

import com.example.kidsplanner.DTO.WishDTO;
import com.example.kidsplanner.model.Wish;
import com.example.kidsplanner.service.WishService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
public class WishController {

    private WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/add")
    public Wish addWish(@RequestBody WishDTO wishDTO) {
        return wishService.addWish(wishDTO);
    }

    @GetMapping("/{userId}")
    public List<Wish> getWishesByUser(@PathVariable Long userId) {
        return wishService.getWishesByUser(userId);
    }

    @PutMapping("/update/{wishId}")
    public Wish updateWishByUser(@PathVariable Long wishId, @RequestBody WishDTO wishDTO) {
        return wishService.updateWishByUser(wishId, wishDTO);
    }

    @DeleteMapping("/delete/{wishId}")
    public void deleteWish(@PathVariable Long wishId) {
        wishService.deleteWish(wishId);
    }

    @DeleteMapping("/deleteAll/{userId}")
    public void deleteAllWishesByUser(@PathVariable Long userId) {
        wishService.deleteAllWishesFromUser(userId);
    }

}
