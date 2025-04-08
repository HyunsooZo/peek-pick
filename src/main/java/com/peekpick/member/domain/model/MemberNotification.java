package com.peekpick.member.domain.model;

record MemberNotification(
        boolean notificationEnabled,
        PreferenceTime preferenceTime
) {
    MemberNotification receiveNotification() {
        return new MemberNotification(true, preferenceTime);
    }

    MemberNotification stopNotification() {
        return new MemberNotification(false, preferenceTime);
    }

    MemberNotification updateTime(PreferenceTime preferenceTime) {
        return new MemberNotification(notificationEnabled, preferenceTime);
    }
}
