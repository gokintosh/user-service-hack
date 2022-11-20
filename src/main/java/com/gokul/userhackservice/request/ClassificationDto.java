package com.gokul.userhackservice.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class ClassificationDto {

    private String device_id;

    private float pointsEarned;

    private float weight;
}
