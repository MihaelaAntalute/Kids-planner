package com.example.kidsplanner.service;

import com.example.kidsplanner.DTO.AddChildRequestDTO;
import com.example.kidsplanner.DTO.PeriodRequestDTO;
import com.example.kidsplanner.DTO.PeriodResponseDTO;
import com.example.kidsplanner.DTO.PeriodResponseWithoutNameDTO;
import com.example.kidsplanner.model.*;
import com.example.kidsplanner.repository.ActivityRepository;
import com.example.kidsplanner.repository.PeriodActivityRepository;
import com.example.kidsplanner.repository.PeriodRepository;
import com.example.kidsplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodService {


    private UserRepository userRepository;
    private PeriodRepository periodRepository;
    private PeriodActivityRepository periodActivityRepository;
    private ActivityRepository activityRepository;
    private MailService mailService;


    @Autowired
    public PeriodService(UserRepository userRepository, PeriodRepository periodRepository, PeriodActivityRepository periodActivityRepository, ActivityRepository activityRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.periodRepository = periodRepository;
        this.periodActivityRepository = periodActivityRepository;
        this.activityRepository = activityRepository;
        this.mailService = mailService;
    }

    //evaluare perioada
    //caut perioada dupa id
    //verific daca este duminica
    //doar daca este duminica,adica endDate de la perioada sa fac media
    //daca evaluarea la activitate nu este facuta,sa se faca evaluarea(arunc exceptie)
    //trec prin fiecare periodActivitate si iau evaluarea ei
    //fac media si o salvez
    //daca media este mai mare decat 7,mesajul catre copil este ca va primi ceva din wishList
    //daca media e mai mica decat 7,mesajul este ca va trebui sa fie mai implicat
    //todo de testat sunday

    public PeriodResponseDTO getEvaluationFromPeriod(PeriodRequestDTO periodRequestDTO) throws ExceptionActivityIsNotEvaluated, MessagingException {
        Period foundPeriod = periodRepository.findById(periodRequestDTO.getPeriodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "period not found"));
        User foundUser = userRepository.findById(foundPeriod.getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Integer sumEvaluations = 0;
        Double averagePeriod = 0.0;
        boolean isSunday = LocalDate.now().equals(foundPeriod.getEndDate());
        int numbersOfPeriodsActivities = foundPeriod.getPeriodActivityList().size();
        //if(isSunday) {
        sumEvaluations = searchPeriodActivity(foundPeriod, sumEvaluations);
        averagePeriod = generateAverage(sumEvaluations, numbersOfPeriodsActivities);
        foundPeriod.setAverageEvaluation(averagePeriod);
        periodRepository.save(foundPeriod);
        mailService.sendEmailWithAverage(foundUser.getEmail(), foundPeriod);
        // }
        return constructNewEvaluationResponseDTO(foundPeriod);
    }

    private static Integer searchPeriodActivity(Period foundPeriod, Integer sumEvaluations) throws ExceptionActivityIsNotEvaluated {
        for (PeriodActivity periodActivity : foundPeriod.getPeriodActivityList()) {
            if (periodActivity.getEvaluationType().value == null) {
                throw new ExceptionActivityIsNotEvaluated("Activity not evaluated");
            } else {
                sumEvaluations += periodActivity.getEvaluationType().value;
            }
        }
        return sumEvaluations;
    }

    private static Double generateAverage(Integer sumEvaluations, int numbersOfPeriodsActivities) {
        Double averagePeriod;
        averagePeriod = (double) (sumEvaluations / numbersOfPeriodsActivities);
        return averagePeriod;
    }


    //construim un responseEvaluate
    public PeriodResponseDTO constructNewEvaluationResponseDTO(Period period) {
        PeriodResponseDTO newResponseEvaluation = new PeriodResponseDTO();
        newResponseEvaluation.setStartDate(period.getStartDate());
        newResponseEvaluation.setEndDate(period.getEndDate());
        newResponseEvaluation.setAverageEvaluation(period.getAverageEvaluation());
        newResponseEvaluation.setActivityList(period.getActivityList());
        return newResponseEvaluation;
    }


    //construire raspuns pt perioada vizualizata
    //construiesc PeriodResponseDTo
    //vreau sa vad startDAte,endDate si average
    public PeriodResponseDTO constructNewPeriodResponseDTO(Period period) {
        PeriodResponseDTO newPeriodResponseDTO = new PeriodResponseDTO();
        newPeriodResponseDTO.setStartDate(period.getStartDate());
        newPeriodResponseDTO.setEndDate(period.getEndDate());
        newPeriodResponseDTO.setAverageEvaluation(period.getAverageEvaluation());
        User foundChild = userRepository.findById(period.getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        newPeriodResponseDTO.setUsername(foundChild.getUsername());
        newPeriodResponseDTO.setActivityList(period.getActivityList());
        return newPeriodResponseDTO;
    }

    public List<PeriodResponseDTO> transformPeriodListDBTToPeriodResponseDToList(List<Period> periodList) {
        List<PeriodResponseDTO> periodResponseDTOList = new ArrayList<>();
        for (Period period : periodList) {
            PeriodResponseDTO periodResponseDTO = constructNewPeriodResponseDTO(period);
            periodResponseDTOList.add(periodResponseDTO);
        }
        return periodResponseDTOList;
    }

    public PeriodResponseWithoutNameDTO constructNewPeriodResponseWithoutNameDTO(Period period) {
        PeriodResponseWithoutNameDTO responseWithoutNameDTO = new PeriodResponseWithoutNameDTO();
        responseWithoutNameDTO.setStartDate(period.getStartDate());
        responseWithoutNameDTO.setEndDate(period.getEndDate());
        responseWithoutNameDTO.setAverageEvaluation(period.getAverageEvaluation());
        responseWithoutNameDTO.setActivityList(period.getActivityList());
        return responseWithoutNameDTO;
    }

    public List<PeriodResponseWithoutNameDTO> transformPeriodListDBTToPeriodResponseWithoutNameDToList(List<Period> periodList) {
        List<PeriodResponseWithoutNameDTO> periodResponseWithoutNameDTOList = new ArrayList<>();
        for (Period period : periodList) {
            PeriodResponseWithoutNameDTO periodResponseWithoutNameDTO = constructNewPeriodResponseWithoutNameDTO(period);
            periodResponseWithoutNameDTOList.add(periodResponseWithoutNameDTO);
        }
        return periodResponseWithoutNameDTOList;
    }

    //toate perioadele
    public List<PeriodResponseDTO> getAllPeriods() {
        List<Period> periodList = periodRepository.findAll();
        return transformPeriodListDBTToPeriodResponseDToList(periodList);
    }


    //caut perioada dupa id
    //returneaza startDate,endDate,average,username,activityList
    public PeriodResponseDTO getPeriodById(Long periodId) {
        Period foundPeriod = periodRepository.findById(periodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "period not found"));
        return constructNewPeriodResponseDTO(foundPeriod);
    }

    public List<PeriodResponseWithoutNameDTO> getPeriodByUsername(AddChildRequestDTO addChildRequestDTO) {
        User foundChild = userRepository.findUserByUsername(addChildRequestDTO.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        List<Period> periodList = foundChild.getPeriod();
        return transformPeriodListDBTToPeriodResponseWithoutNameDToList(periodList);
    }


//    //caut perioada dupa data.
//    //daca data este dupa startDate si inainte de EndDate,atunci returneaza perioada respectiva
//    //todo de cautat si dupa date din viitor,nu e ok
//    public PeriodResponseDTO getPeriodByDate(PeriodRequestDTO periodRequestDTO) throws ExceptionPeriodNotExist {
//        LocalDate date = periodRequestDTO.getDate();
//        List<Period> periodList = periodRepository.findAll();
//        PeriodResponseDTO periodResponseDTO = new PeriodResponseDTO();
//        for (Period period : periodList) {
//            LocalDate startDate = period.getStartDate();
//            LocalDate endDate = period.getEndDate();
//            if ((date.equals(endDate))){
//                periodResponseDTO = constructNewPeriodResponseDTO(period);
//                //|| (date.equals(startDate)) || (date.isAfter(startDate))){
//            }else{
//                throw new ExceptionPeriodNotExist("Period not exist with this date");
//            }
//        }
//        return periodResponseDTO;
//    }


}
