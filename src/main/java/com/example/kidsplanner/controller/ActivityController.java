package com.example.kidsplanner.controller;

import com.example.kidsplanner.DTO.ActivityRequestDTO;
import com.example.kidsplanner.DTO.AddEvaluateDTO;
import com.example.kidsplanner.DTO.AddIsDoneDTO;
import com.example.kidsplanner.DTO.UpdateActivityDTO;
import com.example.kidsplanner.model.Activity;
import com.example.kidsplanner.model.ActivityTypeNotFoundException;
import com.example.kidsplanner.model.PeriodActivity;
import com.example.kidsplanner.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//
@RequestMapping("/activity")
public class ActivityController {

    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/add")
    public Activity addActivity(@RequestBody ActivityRequestDTO activityRequestDTO) throws ActivityTypeNotFoundException {
        return  activityService.addActivityToChild(activityRequestDTO);
    }
    @PostMapping("/evaluate")
    public PeriodActivity evaluateActivity(@RequestBody AddEvaluateDTO evaluateDTO){
        return activityService.evaluateActivity(evaluateDTO);
    }
    @PostMapping("/isDoneChild")
    public PeriodActivity getIsDoneHomeworkByChild(@RequestBody AddIsDoneDTO isDoneDTO){
        return activityService.isDoneHomeworkByChild(isDoneDTO);
    }
    @PostMapping("/isDoneParent")
    public PeriodActivity checkHomeworkByParent(@RequestBody AddIsDoneDTO isDoneDTO){
        return activityService.checkHomeworkByParent(isDoneDTO);
    }

    @GetMapping("/")
    public List<Activity> getAllActivities(){
        return activityService.getAllActivities();
    }
    @GetMapping("/homework")
    public List<Activity> getHomeworksActivity(){
        return activityService.getActivitiesByHomework();
    }

    @GetMapping("/behavior")
    public List<Activity> getBehaviorsActivity(){
        return activityService.getActivitiesByBehavior();
    }

    @GetMapping("/extra")
    public List<Activity> getExtraActivities(){
        return activityService.getActivitiesByExtraActivities();
    }
    @PutMapping ("/update/{activityId}")
    public Activity updateActivity (@PathVariable Long activityId, @RequestBody UpdateActivityDTO updateActivityDTO){
        return activityService.updateActivity(activityId,updateActivityDTO);
    }

    @DeleteMapping("/delete/{activityId}")
    public void deleteActivity(@PathVariable Long activityId){
        activityService.deleteActivity(activityId);
    }


}
