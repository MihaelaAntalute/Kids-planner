package com.example.kidsplanner.service;

import com.example.kidsplanner.DTO.AddChildRequestDTO;
import com.example.kidsplanner.DTO.RegisterDTO;
import com.example.kidsplanner.model.*;
import com.example.kidsplanner.repository.PeriodRepository;
import com.example.kidsplanner.repository.RoleRepository;
import com.example.kidsplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.Period.ofDays;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    private PeriodRepository periodRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PeriodRepository periodRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.periodRepository = periodRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(RegisterDTO newUser)throws InvalidEmailException {
        Optional<User> foundUserOptional = userRepository.findUserByUsername(newUser.getUsername());
        if (foundUserOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CREATED, "already exist");
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        String roleString = newUser.getRole();
        Role userRole = new Role();
        if ("parent".equals(roleString)) {
            Optional<Role> roleOptional = roleRepository.findByRoleType(RoleType.ROLE_PARENT);
            if (roleOptional.isEmpty()) {
                userRole.setRoleType(RoleType.ROLE_PARENT);
                userRole = roleRepository.save(userRole);
            } else {
                userRole = roleOptional.get();
            }
        } else if ("child".equals(roleString)) {
            Optional<Role> roleOptional = roleRepository.findByRoleType(RoleType.ROLE_CHILD);
            if (roleOptional.isEmpty()) {
                userRole.setRoleType(RoleType.ROLE_CHILD);
                userRole = roleRepository.save(userRole);
            } else {
                userRole = roleOptional.get();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you cannot register with this role");
        }
        Role role = roleRepository.findByRoleType(userRole.getRoleType()).get();
        user.getRoleList().add(role);
        role.getUserList().add(user);
        return userRepository.save(user);
    }

    public User findLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return foundUser;
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the user was not found"));
    }


    //adaugam copil
    //caut copilul dupa nume,daca este sa aruncam exceptie ca exista deja acest nume
    //cu un DTO,adaug nume copil,data nasterii,ii pun imagine
    //creez o perioada(perioada va incepe de luni si se va termina duminica)
    //salvez in baza de date perioade pentru un an intreg(o sa am 52 de perioade)
    //daca adaug copil in zi de duminica,perioada incepe de luni
    //daca adaug copil in zi de luni-sambata,perioada incepe din saptamana in curs
    // nu vom seta media notelor
    //salvam copilul in baza de date


    public User addChild(AddChildRequestDTO addChildRequestDTO) throws InvalidEmailException {
        Optional<User> foundChild = userRepository.findUserByUsername(addChildRequestDTO.getName());
        if (foundChild.isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "This child name already exist");
        }
        User childToBeAdd = new User();
        childToBeAdd.setUsername(addChildRequestDTO.getName());
        childToBeAdd.setBirthday(addChildRequestDTO.getBirthday());
        childToBeAdd.setEmail(addChildRequestDTO.getEmail());
        //cand adaug copilul ii adaug si rolul,ca dupa el cu rolul de copil a adauge dorinte in wishlist
        getRole(addChildRequestDTO, childToBeAdd);
        List<Period> periodList = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate;
        boolean isSunday = LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        List<LocalDate> mondays = findAllMondaysOfTheYear(startDate);
        if (isSunday) {
            mondays.remove(0);
        }
        for (LocalDate monday : mondays) {
            startDate = monday;
            endDate = startDate.plus(ofDays(6));
            periodList.add(addPeriod(startDate, endDate, childToBeAdd));
        }
        return userRepository.save(childToBeAdd);
    }

    private void getRole(AddChildRequestDTO addChildRequestDTO, User childToBeAdd) {
        String roleString = addChildRequestDTO.getRole();
        Role userRole = new Role();
        if ("child".equals(roleString)) {
            userRole.setRoleType(RoleType.ROLE_CHILD);
            userRole = roleRepository.save(userRole);
            childToBeAdd.getRoleList().add(userRole);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you cannot register with this role");
        }
    }

    public Period addPeriod(LocalDate startDate, LocalDate endDate, User childToBeAdd) {
        Period period = new Period();
        period.setStartDate(startDate);
        period.setEndDate(endDate);
        period.setUser(childToBeAdd);
        childToBeAdd.getPeriod().add(period);
        return period;
    }

    public List<LocalDate> findAllMondaysOfTheYear(LocalDate startDate) {
        int year = startDate.getYear();
        int month = startDate.getMonthValue();
        int day = startDate.getDayOfMonth();
        List<LocalDate> mondaysList = new ArrayList<>();
        LocalDate.of(year, month, day);
        LocalDate monday =
                LocalDate.now()
                        .with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        do {
            mondaysList.add(monday);
            System.out.println(monday.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            monday = monday.plus(ofDays(7));
        } while (monday.getYear() == year);
        return mondaysList;
    }


    //vizualizare copil dupa nume
    public User getUserByName(String name) {
        User foundChild = userRepository.findUserByUsername(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return foundChild;
    }

    //vizualizare copil dupa id
    public User getUser(Long userId) {
        User foundChild = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return foundChild;
    }

    //update user
    public User updateUser(Long userId, AddChildRequestDTO childRequestDTO) {
        User foundChild = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        foundChild.setUsername(childRequestDTO.getName());
        foundChild.setBirthday(childRequestDTO.getBirthday());
        return foundChild;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<String> getAllUsersNames() {
        List<User> userList = userRepository.findAll();
        List<String> userNames = new ArrayList<>();
        for (User user : userList) {
            userNames.add(user.getUsername());
        }
        return userNames;
    }

    //delete user
    public void deleteUser(Long userId) {
        User foundChild = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        userRepository.delete(foundChild);
    }

}
