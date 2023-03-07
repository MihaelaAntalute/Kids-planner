package com.example.kidsplanner.controller;

import com.example.kidsplanner.DTO.AddChildRequestDTO;
import com.example.kidsplanner.DTO.PeriodRequestDTO;
import com.example.kidsplanner.DTO.PeriodResponseDTO;
import com.example.kidsplanner.DTO.PeriodResponseWithoutNameDTO;
import com.example.kidsplanner.model.ExceptionActivityIsNotEvaluated;
import com.example.kidsplanner.service.MailService;
import com.example.kidsplanner.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/period")
public class PeriodController {
    private PeriodService periodService;
    private MailService mailService;

    @Autowired
    public PeriodController(PeriodService periodService,MailService mailService) {
        this.periodService = periodService;
        this.mailService = mailService;
    }

    @PostMapping("/evaluate")
    public PeriodResponseDTO evaluatePeriod(@RequestBody PeriodRequestDTO periodRequestDTO) throws ExceptionActivityIsNotEvaluated, MessagingException {
        return periodService.getEvaluationFromPeriod(periodRequestDTO);
    }

    @GetMapping("/{periodId}")
    public PeriodResponseDTO getPeriodById(@PathVariable Long periodId){
        return periodService.getPeriodById(periodId);
    }

    @GetMapping("/")
    public List<PeriodResponseDTO> getAllPeriods(){
        return periodService.getAllPeriods();
    }

    @GetMapping("/allByUsername")
    public List<PeriodResponseWithoutNameDTO> getPeriodByUserId(@RequestBody AddChildRequestDTO addChildRequestDTO) {
        return periodService.getPeriodByUsername(addChildRequestDTO);
    }



}
