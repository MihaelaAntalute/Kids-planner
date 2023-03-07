package com.example.kidsplanner.repository;

import com.example.kidsplanner.model.User;
import com.example.kidsplanner.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish,Long> {

Optional<Wish> findWishByUser(User user);
void deleteAllByUser(User user);
}
