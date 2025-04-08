package com.peekpick.member.domain.model;

import java.util.Arrays;

enum PreferenceTime {
    H_0_TO_1(0),
    H_1_TO_2(1),
    H_2_TO_3(2),
    H_3_TO_4(3),
    H_4_TO_5(4),
    H_5_TO_6(5),
    H_6_TO_7(6),
    H_7_TO_8(7),
    H_8_TO_9(8),
    H_9_TO_10(9),
    H_10_TO_11(10),
    H_11_TO_12(11),
    H_12_TO_13(12),
    H_13_TO_14(13),
    H_14_TO_15(14),
    H_15_TO_16(15),
    H_16_TO_17(16),
    H_17_TO_18(17),
    H_18_TO_19(18),
    H_19_TO_20(19),
    H_20_TO_21(20),
    H_21_TO_22(21),
    H_22_TO_23(22),
    H_23_TO_0(23);

    private final int startHour;

    PreferenceTime(int startHour) {
        this.startHour = startHour;
    }

    int startHour() {
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