package com.example.kidsplanner;

import com.example.kidsplanner.DTO.ActivityRequestDTO;
import com.example.kidsplanner.DTO.AddEvaluateDTO;
import com.example.kidsplanner.DTO.AddIsDoneDTO;
import com.example.kidsplanner.model.*;
import com.example.kidsplanner.repository.ActivityRepository;
import com.example.kidsplanner.repository.PeriodActivityRepository;
import com.example.kidsplanner.repository.PeriodRepository;
import com.example.kidsplanner.repository.UserRepository;
import com.example.kidsplanner.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    @InjectMocks
    private ActivityService activityService;
    @Mock
    private PeriodRepository periodRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private PeriodActivityRepository periodActivityRepository;
@Test
    void AddActivity_ShouldThrowException() {
        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO("thalia", null, "home", null);
        when(userRepository.findUserByUsername(activityRequestDTO.getUsername())).thenReturn(Optional.of(new User(null, activityRequestDTO.getUsername(), null, null, null, null, null, null, null)));
        when(periodRepository.findById(any())).thenReturn(Optional.of(new Period()));
        assertThrows(ActivityTypeNotFoundException.class, () -> activityService.addActivityToChild(activityRequestDTO));

    }

    @Test
    void EvaluateActivity_ShouldThrowException() {
        AddEvaluateDTO addEvaluateDTO = new AddEvaluateDTO(10L, 23L, EvaluationType.SUN);
        when(activityRepository.findById(any())).thenReturn(Optional.of(new Activity()));
        assertThrows(ResponseStatusException.class, () -> activityService.evaluateActivity(addEvaluateDTO));
    }

    @Test
    void EvaluateIsDoneByParent_ShouldReturnTrue() {
    //given
        AddIsDoneDTO addIsDoneDTO = new AddIsDoneDTO(12L, true, false);
        Activity foundActivity = new Activity(1L,ActivityType.HOMEWORK,"tema romana",null,new ArrayList<>(),new Period());
        PeriodActivity foundPeriod = new PeriodActivity(12L,true,false,EvaluationType.RAIN,foundActivity,new Period());
       //when
        when(periodActivityRepository.findById(addIsDoneDTO.getPeriodActivityId())).thenReturn(Optional.of(new PeriodActivity()));
        PeriodActivity result = activityService.checkHomeworkByParent(addIsDoneDTO);
       //then
        assertEquals(addIsDoneDTO.getDoneParent(), result);

    }
}