package com.gokul.userhackservice.response;


import lombok.Data;

@Data
public class LeaderResponse {

    private String username;
    private String level;
    private Long score;
    private String giftsavailable;
}
