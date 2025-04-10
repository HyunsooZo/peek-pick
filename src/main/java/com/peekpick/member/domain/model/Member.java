package com.peekpick.member.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record Member(
        String id,
        MemberProfile profile,
        MemberNotification notificationSettings,
        List<String> stockCodes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Member(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks
    ) {
        this(
                null,
                new MemberProfile(nickname, email),
                new MemberNotification(true, PreferenceTime.of(preferenceHour)),
                stocks,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public Member(
            String id,
            String nickname,
            String email,
            boolean notificationEnabled,
            Integer preferenceHour,
            List<String> stockCodes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this(
                id,
                new MemberProfile(nickname, email),
                new MemberNotification(notificationEnabled, PreferenceTime.of(preferenceHour)),
                stockCodes,
                createdAt,
                updatedAt
        );
    }

    public Integer preferenceHour() {
        return notificationSettings.preferenceTime().startHour();
    }

    public Member updateNickname(String nickname) {
        if (Objects.isNull(nickname)) {
            return this;
        }
        if (nickname.length() < 2 || nickname.length() > 10) {
            throw new IllegalArgumentException("Nickname must be between 2 and 10 characters");
        }
        return new Member(
                this.id,
                this.profile.updateNickname(nickname),
                this.notificationSettings,
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public String email() {
        return profile.email();
    }

    public String nickname() {
        return profile.nickname();
    }

    public List<String> stocks() {
        return this.stockCodes == null ? List.of() : this.stockCodes;
    }

    public Member updateEmail(String email) {
        if (Objects.isNull(email)) return this;
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new Member(
                this.id,
                this.profile.updateEmail(email),
                this.notificationSettings,
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Member updatePreferenceHour(Integer preferenceHour) {
        if (Objects.isNull(preferenceHour)) return this;
        return new Member(
                this.id,
                this.profile,
                this.notificationSettings.updateTime(PreferenceTime.of(preferenceHour)),
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public boolean isNotificationEnabled(){
        return notificationSettings.notificationEnabled();
    }

    public Member updateStocks(List<String> stocks) {
        if (stocks.isEmpty()) return this;
        return new Member(
                this.id,
                this.profile,
                this.notificationSettings,
                stocks,
                this.createdAt,
                LocalDateTime.now()
        );
    }


    public Boolean notificationEnabled() {
        return this.notificationSettings.notificationEnabled();
    }

    public Member updateNotification(Boolean enable) {
        if (enable == null) return this;
        return new Member(
                this.id,
                this.profile,
                enable ? this.notificationSettings.receiveNotification() : this.notificationSettings.stopNotification(),
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }
}
