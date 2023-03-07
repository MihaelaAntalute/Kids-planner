package com.example.kidsplanner.service;

import com.example.kidsplanner.DTO.ActivityRequestDTO;
import com.example.kidsplanner.DTO.AddEvaluateDTO;
import com.example.kidsplanner.DTO.AddIsDoneDTO;
import com.example.kidsplanner.DTO.UpdateActivityDTO;
import com.example.kidsplanner.model.*;
import com.example.kidsplanner.repository.ActivityRepository;
import com.example.kidsplanner.repository.PeriodActivityRepository;
import com.example.kidsplanner.repository.PeriodRepository;
import com.example.kidsplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private UserRepository userRepository;

    private PeriodRepository periodRepository;

    private PeriodActivityRepository periodActivityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository, PeriodRepository periodRepository, PeriodActivityRepository periodActivityRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.periodRepository = periodRepository;
        this.periodActivityRepository = periodActivityRepository;
    }

    //adaug activitate la copil
    //caut copilul dupa nume
    //caut perioada dupa id
    //fac o noua activitate,la care ii setez userul si periaoda
    //ii setez din DTO descrierea
    //ii setez tipul de activitate
    //daca tipul de activitate nu exista,aruc exceptie
    //iau lista de activitati a copilului si adaug activitatea in ea
    //fac o noua perioadActivity,dupa care salvez tot
    public Activity addActivityToChild(ActivityRequestDTO activityRequestDTO) throws ActivityTypeNotFoundException {
        User foundChild = userRepository.findUserByUsername(activityRequestDTO.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Period foundPeriod = periodRepository.findById(activityRequestDTO.getPeriodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "period not found"));
        Activity activityToAdd = new Activity();
        activityToAdd.setUser(foundChild);
        activityToAdd.setPeriod(foundPeriod);
        activityToAdd.setDescription(activityRequestDTO.getDescription());
        activityToAdd.setActivityType(transformDescriptionInActivityType(activityRequestDTO.getActivityType()));
        foundChild.getActivityList().add(activityToAdd);
        PeriodActivity periodActivity = new PeriodActivity();
        periodActivity.setActivity(activityToAdd);
        periodActivity.setPeriod(foundPeriod);
        periodActivityRepository.save(periodActivity);
        activityToAdd.getPeriodActivityList().add(periodActivity);
        return activityRepository.save(activityToAdd);
    }

    private ActivityType transformDescriptionInActivityType(String stringActivityType) throws ActivityTypeNotFoundException {
        if (stringActivityType.equals("behavior")) {
            stringActivityType.equals(ActivityType.BEHAVIOR);
            return ActivityType.BEHAVIOR;
        }
        if (stringActivityType.equals("homework")) {
            stringActivityType.equals(ActivityType.HOMEWORK);
            return ActivityType.HOMEWORK;
        }
        if (stringActivityType.equals("activitati extra")) {
            stringActivityType.equals(ActivityType.EXTRA_ACTIVITY);
            return ActivityType.EXTRA_ACTIVITY;
        } else {
            throw new ActivityTypeNotFoundException("Activity type not found");
        }

    }

    //evaluateActivity

    public PeriodActivity evaluateActivity(AddEvaluateDTO evaluateDTO) {
        Activity foundedActivity = activityRepository.findById(evaluateDTO.getActivityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "activity not found"));
        Period foundPeriod = periodRepository.findById(evaluateDTO.getPeriodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "period not found"));
        PeriodActivity periodActivity = periodActivityRepository.findByActivityAndPeriod(foundedActivity, foundPeriod);
        periodActivity.setEvaluationType(evaluateDTO.getEvaluationType());
        return periodActivityRepository.save(periodActivity);
    }

    //bifez activitate  de tip tema ca copil
    //todo period
    public PeriodActivity isDoneHomeworkByChild(AddIsDoneDTO addIsDoneDTO) {
        PeriodActivity periodActivity = periodActivityRepository.findById(addIsDoneDTO.getPeriodActivityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "periodActivity not found"));
        if (periodActivity.getActivity().getActivityType() == ActivityType.HOMEWORK) {
            periodActivity.setDoneChild(true);
        }
        return periodActivityRepository.save(periodActivity);
    }

    //bifez o activitate de tip tema ca parinte
    public PeriodActivity checkHomeworkByParent(AddIsDoneDTO addIsDoneDTO) {
        PeriodActivity periodActivity = periodActivityRepository.findById(addIsDoneDTO.getPeriodActivityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "periodActivity not found"));
        if (periodActivity.getActivity().getActivityType() == ActivityType.HOMEWORK) {
            periodActivity.setDoneParent(true);
        }
        return periodActivityRepository.save(periodActivity);
    }

    //getAllActivitiesType
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    //getAllActivitiesByHomework
    public List<Activity> getActivitiesByHomework() {
        List<Activity> activities = activityRepository.findAll();
        List<Activity> homeworkActivity = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getActivityType().equals(ActivityType.HOMEWORK)) {
                homeworkActivity.add(activity);
            }
        }
        return homeworkActivity;

    }


    //getAllActivitiesByBehavior
    public List<Activity> getActivitiesByBehavior() {
//        List<Activity> activities = activityRepository.findAll();
//        List<Activity> behaviorActivity = new ArrayList<>();
//        for (Activity activity : activities) {
//            if (activity.getActivityType().equals(ActivityType.BEHAVIOR)) {
//                behaviorActivity.add(activity);
//            }
//        }
//        return behaviorActivity;
        return activityRepository.findAll().stream()
                .filter(activity -> activity.getActivityType().equals(ActivityType.BEHAVIOR))
                .toList();
    }


    //getAllActivitiesByExtraActivity
    public List<Activity> getActivitiesByExtraActivities() {
        List<Activity> activities = activityRepository.findAll();
        List<Activity> activitiesExtra = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getActivityType().equals(ActivityType.EXTRA_ACTIVITY)) {
                activitiesExtra.add(activity);
            }
        }
        return activitiesExtra;
    }

    //update
    //todo
    public Activity updateActivity(Long activityId, UpdateActivityDTO updateActivityDTO) {
        Activity foundedActivity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "activity not found"));
        foundedActivity.setDescription(updateActivityDTO.getDescription());
        return activityRepository.save(foundedActivity);
    }

    //delete
    public void deleteActivity(Long activityId) {
        Activity foundedActivity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "activity not found"));
        activityRepository.delete(foundedActivity);
    }


}
