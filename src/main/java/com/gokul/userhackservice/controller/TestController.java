package com.gokul.userhackservice.controller;


import com.gokul.userhackservice.model.AppUser;
import com.gokul.userhackservice.model.Compost_Activity;
import com.gokul.userhackservice.model.ELevel;
import com.gokul.userhackservice.model.MontlyHighScore;
import com.gokul.userhackservice.repository.CompostRepository;
import com.gokul.userhackservice.repository.MonthlyHighScoreRepo;
import com.gokul.userhackservice.repository.UserRepository;
import com.gokul.userhackservice.request.ClassificationDto;
import com.gokul.userhackservice.request.GetActivityRequest;
import com.gokul.userhackservice.response.UserResponse;
import com.gokul.userhackservice.security.service.UserDetailsImpl;
import com.gokul.userhackservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionService;

@RestController
@RequestMapping("/classification")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final CompostRepository compostRepository;

    private final UserService userService;

    private final MonthlyHighScoreRepo monthlyHighScoreRepo;

    private final UserRepository userRepository;


    @GetMapping("/settopscore")
    @PreAuthorize("hasRole('ADMIN')")
    public int setTopScore() {


        int monthlyTopScore=300;

        MontlyHighScore montlyHighScore=new MontlyHighScore();
        montlyHighScore.setYear(String.valueOf(LocalDate.now().getYear()));
        montlyHighScore.setMonth(String.valueOf(LocalDate.now().getMonth()));
        montlyHighScore.setScore(String.valueOf(monthlyTopScore));

        monthlyHighScoreRepo.save(montlyHighScore);

        return monthlyTopScore;
    }

    @PostMapping("/classify")
    public void classify(@RequestBody ClassificationDto classificationDto) {


        String deviceId = classificationDto.getDevice_id();

        AppUser user=userRepository.findByDeviceId(Long.valueOf(deviceId));

        Compost_Activity compost_activity=new Compost_Activity();

        compost_activity.setDate(LocalDate.now());
        compost_activity.setUser(user);
        compost_activity.setScore((long) classificationDto.getPointsEarned());
        compostRepository.save(compost_activity);

        log.info("Compost activity saved!!");





    }

    @GetMapping("getallactivity/{id}")
    public List<Compost_Activity> getActivities(@PathVariable("id") Long id) {
        List<Compost_Activity> activities = compostRepository.findAllByUser_Id(id);
        return activities;
    }



    @GetMapping("getmonthlyaverage/{id}")
    public Long returnAverageScore(@PathVariable("id") Long id) {
        Long result= userService.getMonthlyAverage(Long.valueOf(id));
        return result;
    }


    @GetMapping("/me")
    public UserResponse getMe() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        AppUser user = userService.getUser(username);

        UserResponse response = new UserResponse();
        response.setUserName(user.getUsername());
        response.setEmail(user.getEmail());
        response.setGmnia(user.getGmnia());
        response.setLevel(assignLevel(user));
        response.setUserId(user.getId());

        return response;

    }


    public String assignLevel(AppUser user){

        if(userService.getMonthlyAverage(user.getId())>250&&(userService.getMonthlyAverage(user.getId())<=300)){
            user.setLevel(ELevel.MR_BIN);

        } else if (userService.getMonthlyAverage(user.getId())>200&&(userService.getMonthlyAverage(user.getId())<=250)) {
            user.setLevel(ELevel.TRASH_GOD);
        }
        else if (userService.getMonthlyAverage(user.getId())>150&&(userService.getMonthlyAverage(user.getId())<=200)) {
            user.setLevel(ELevel.BIG_BIN);
        }
        else if (userService.getMonthlyAverage(user.getId())>100&&(userService.getMonthlyAverage(user.getId())<=150)) {
            user.setLevel(ELevel.DE_COMPOSTER);
        }
        else if (userService.getMonthlyAverage(user.getId())>50&&(userService.getMonthlyAverage(user.getId())<=100)) {
            user.setLevel(ELevel.DE_COMPOSTER);
        }
        else if (userService.getMonthlyAverage(user.getId())>0&&(userService.getMonthlyAverage(user.getId())<=50)) {
            user.setLevel(ELevel.ROOKIE_TRASHMAN);
        }
        else {
            user.setLevel(ELevel.NOO_BIN);
        }

        userRepository.save(user);

        return  user.getLevel().toString();

    }





}
