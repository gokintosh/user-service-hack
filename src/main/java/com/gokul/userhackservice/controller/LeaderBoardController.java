package com.gokul.userhackservice.controller;


import com.gokul.userhackservice.model.AppUser;
import com.gokul.userhackservice.model.Compost_Activity;
import com.gokul.userhackservice.repository.CompostRepository;
import com.gokul.userhackservice.response.LeaderResponse;
import com.gokul.userhackservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
@Slf4j
public class LeaderBoardController {

    private final CompostRepository compostRepository;

    private final UserService userService;




    Long score=0L;

    @GetMapping("/showleaderboard")
    public List<LeaderResponse>getCompostMap(){

        List<Compost_Activity>compost_activities=compostRepository.findAll();
        Map<String,Long>compostMap=new HashMap<>();

        Long score=0L;
        log.info(compost_activities.toString());

        for(Compost_Activity activity:compost_activities){
            if(!compostMap.containsKey(activity.getUser().getUsername())){
                compostMap.put(activity.getUser().getUsername(), activity.getScore());
            }
            else {
                score=compostMap.get(activity.getUser().getUsername())+activity.getScore();
                compostMap.putIfAbsent(activity.getUser().getUsername(),score);
            }
            compostMap.put(activity.getUser().getUsername(),activity.getScore());
        }

        ArrayList<LeaderResponse>leaderResponses=new ArrayList<>();

        for(String key:compostMap.keySet()){
            LeaderResponse response=new LeaderResponse();
            response.setUsername(key);
            response.setGiftsavailable("cinema ticjers");
            AppUser user = userService.getUser(key);
            response.setLevel(String.valueOf(user.getLevel()));
            response.setScore(compostMap.get(key));

            leaderResponses.add(response);

        }

        return leaderResponses;





    }




}
