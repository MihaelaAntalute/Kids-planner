package com.example.kidsplanner.service;

import com.example.kidsplanner.DTO.WishDTO;
import com.example.kidsplanner.model.*;
import com.example.kidsplanner.repository.PeriodRepository;
import com.example.kidsplanner.repository.UserRepository;
import com.example.kidsplanner.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WishService {
    private WishRepository wishRepository;
    private UserRepository userRepository;
    private PeriodRepository periodRepository;

    @Autowired
    public WishService(WishRepository wishRepository, UserRepository userRepository,PeriodRepository periodRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
        this.periodRepository = periodRepository;
    }
    //todo vreau eu ca copil inregistrat de mama sa pot adauga o dorinta

    //caut copilul dupa nume
    //ii iau lista de dorinte si adaug wish
    //dupa data actuala,ii pun si perioada la wish

    public Wish addWish(WishDTO wishDTO) {
        Optional<User> foundChild = userRepository.findUserByUsername(wishDTO.getUsername());
        if (foundChild.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This child name do not exist");
        }
        Wish newWish = new Wish();
        newWish.setText(wishDTO.getText());
        foundChild.get().getWishList().add(newWish);
        newWish.setUser(foundChild.get());
        List<Period> periodsChild = foundChild.get().getPeriod();
        LocalDate date = LocalDate.now();
        for(Period period: periodsChild){
            if((date.isAfter(period.getStartDate())) && (date.isBefore(period.getEndDate()))){
           newWish.setPeriod(period);
            }
        }
        userRepository.save(foundChild.get());
        return wishRepository.save(newWish);
    }

    public List<Wish> getWishesByUser(Long userId) {
        User foundChild = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return foundChild.getWishList();
    }

    public Wish updateWishByUser(Long wishId, WishDTO wishDTO) {
        Wish foundWish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wish not found"));
        foundWish.setText(wishDTO.getText());
        return wishRepository.save(foundWish);
    }

    public void deleteWish(Long wishId) {
        Wish foundWish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wish not found"));
        wishRepository.delete(foundWish);
    }

    public void deleteAllWishesFromUser(Long userId) {
        User foundChild = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        wishRepository.deleteAllByUser(foundChild);
    }


}
