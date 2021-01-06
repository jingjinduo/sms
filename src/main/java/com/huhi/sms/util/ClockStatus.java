package com.huhi.sms.util;

public enum ClockStatus {
    //早晨八点，下午五点
    DEFUALT(0),//初始,迟到并且早退

    SIGNIN(1),//签到

    LATE(2),//迟到

    COMPLETE(3),//正常签到签退

    HARFDAY(4);//半天工资，正常到但是早退，迟到或者正常退

    private final int statenum;

    ClockStatus(int statenum){

        this.statenum = statenum;

    }

    public int getStatenum() {

        return statenum;

    }
}