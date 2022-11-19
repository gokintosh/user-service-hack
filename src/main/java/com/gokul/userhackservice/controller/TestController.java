package com.gokul.userhackservice.controller;


import com.gokul.userhackservice.model.Compost_Activity;
import com.gokul.userhackservice.repository.CompostRepository;
import com.gokul.userhackservice.request.ClassificationDto;
import com.gokul.userhackservice.request.GetActivityRequest;
import com.gokul.userhackservice.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionService;

@RestController
@RequestMapping("/classification")
@RequiredArgsConstructor
public class TestController {

    private final CompostRepository compostRepository;



    @GetMapping("/sayname")
    @PreAuthorize("hasRole('ADMIN')")
    public String sayHello(){

        UserDetailsImpl userDetails= (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=userDetails.getUsername();
        return "your name is "+username;
    }

    @PostMapping("/classify")
    public void classify(@RequestParam ClassificationDto classificationDto){

        UserDetailsImpl userDetails=(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username= userDetails.getUsername();

        String deviceId=classificationDto.getDeviceId();






    }

    @GetMapping("getallactivity")
    public List<Compost_Activity>getActivities(@RequestBody GetActivityRequest request){
        List<Compost_Activity>activities=compostRepository.findAllByUser_Id(Integer.toUnsignedLong(request.getId()));
        return activities;
    }

    @GetMapping("getmonthlyaverage")
    public Long returnAverageScore(@RequestBody GetActivityRequest request){
        List<Compost_Activity>activities=compostRepository.findAllByUser_Id(Integer.toUnsignedLong(request.getId()));
        Long score=0L;
        int count=0;
        for(Compost_Activity activity:activities){
            LocalDate date=activity.getDate();

            if(date.getMonth()==LocalDate.now().getMonth()&&date.getYear()==LocalDate.now().getYear()){
                score+=activity.getScore();
                count++;
            }


        }
        return score/count;
    }



}
