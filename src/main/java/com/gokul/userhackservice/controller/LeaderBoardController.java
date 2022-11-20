package com.gokul.userhackservice.controller;


import com.gokul.userhackservice.model.AppUser;
import com.gokul.userhackservice.model.Compost_Activity;
import com.gokul.userhackservice.repository.CompostRepository;
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




    Long score=0L;

    @GetMapping("/showleaderboard")
    public Map<String,Long>getCompostMap(){

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

        Map<String, Long> map = compostMap;
        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        ArrayList<Long> list = new ArrayList<>();

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            list.add(entry.getValue());
        }

        Collections.sort(list, new Comparator<Long>() {
            public int compare(Long lg, Long lg1) {
                return (lg).compareTo(lg1);
            }
        });
        for (Long lr : list) {
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                if (entry.getValue().equals(lr)) {
                    sortedMap.put(entry.getKey(), lr);
                }
            }
        }
        return sortedMap;


    }




}
