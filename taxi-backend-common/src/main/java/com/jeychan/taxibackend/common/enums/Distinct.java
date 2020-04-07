package com.jeychan.taxibackend.common.enums;


/**
 * @author WilderGao
 * time 2020-04-07 21:16
 * motto : everything is no in vain
 * description
 */
public enum Distinct {

    /**
     * 纽约五大区
     */
    BRONX(1),
    MANHATTAN(2),
    BROOKLYN(3),
    QUEENS(4),
    STATEN_ISLAND(5);

    private int distinctId;

    Distinct(int distinctId) {
        this.distinctId = distinctId;
    }

    public int getDistinctId() {
        return distinctId;
    }
}
