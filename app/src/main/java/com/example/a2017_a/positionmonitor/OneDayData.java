package com.example.a2017_a.positionmonitor;

import java.io.Serializable;

public class OneDayData implements Serializable {
    PositionWithTime[] data;
    public OneDayData() {
        data = new PositionWithTime[24*60];
    }
}
