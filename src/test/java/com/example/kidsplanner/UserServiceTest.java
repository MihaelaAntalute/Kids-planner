package com.example.kidsplanner;

import com.example.kidsplanner.DTO.AddChildRequestDTO;
import com.example.kidsplanner.repository.UserRepository;
import com.example.kidsplanner.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

//    void TestAddChild_ShouldThrowException() {
//        //given
//        AddChildRequestDTO addChildRequestDTO = new AddChildRequestDTO("Roberta", 2019.02.03, "robertagmail", "child", null);
//        //when
//       when(u)
//        // then
//
//    }


}
