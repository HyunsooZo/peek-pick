package com.peekpick.member.domain;

import java.util.Arrays;

enum PreferenceTime {
    H_0_TO_1("00:00-01:00", 0),
    H_1_TO_2("01:00-02:00", 1),
    H_2_TO_3("02:00-03:00", 2),
    H_3_TO_4("03:00-04:00", 3),
    H_4_TO_5("04:00-05:00", 4),
    H_5_TO_6("05:00-06:00", 5),
    H_6_TO_7("06:00-07:00", 6),
    H_7_TO_8("07:00-08:00", 7),
    H_8_TO_9("08:00-09:00", 8),
    H_9_TO_10("09:00-10:00", 9),
    H_10_TO_11("10:00-11:00", 10),
    H_11_TO_12("11:00-12:00", 11),
    H_12_TO_13("12:00-13:00", 12),
    H_13_TO_14("13:00-14:00", 13),
    H_14_TO_15("14:00-15:00", 14),
    H_15_TO_16("15:00-16:00", 15),
    H_16_TO_17("16:00-17:00", 16),
    H_17_TO_18("17:00-18:00", 17),
    H_18_TO_19("18:00-19:00", 18),
    H_19_TO_20("19:00-20:00", 19),
    H_20_TO_21("20:00-21:00", 20),
    H_21_TO_22("21:00-22:00", 21),
    H_22_TO_23("22:00-23:00", 22),
    H_23_TO_0("23:00-00:00", 23);

    private final String timeRange;
    private final int startHour;

    PreferenceTime(String timeRange, int startHour) {
        this.timeRange = timeRange;
        this.startHour = startHour;
    }

    String getTimeRange() {
        return timeRange;
    }

    int getStartHour() {
        return startHour;
    }

    static PreferenceTime of(Integer preferenceHour) {
        return preferenceHour == null ? H_1_TO_2 :
                Arrays.stream(values())
                        .filter(pt -> pt.startHour == preferenceHour)
                        .findFirst()
                        .orElse(H_1_TO_2);
    }
}