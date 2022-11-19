package com.gokul.userhackservice.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class ClassificationDto {

    private String deviceId;

    private float metal;

    private float plastic;

    private float paper;

    private float mixed;

    private float compost_point;
}
