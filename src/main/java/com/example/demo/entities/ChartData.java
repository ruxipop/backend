package com.example.demo.entities;

import lombok.*;

@Builder
@Setter
@Getter
public class ChartData {
    public String hour;
    public Double consumation;
}
