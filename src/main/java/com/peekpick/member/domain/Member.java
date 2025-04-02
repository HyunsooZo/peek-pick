package com.peekpick.member.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Member addStock(String stockCode) {
        Objects.requireNonNull(stockCode, "Stock cannot be null");

        if (stockCodes.stream().anyMatch(existingCode -> existingCode.equals(stockCode))) {
            throw new IllegalStateException("Stock with code " + stockCode + " already exists");
        }

        if (stockCodes.size() >= 5) {
            throw new IllegalStateException("Cannot add more than 5 stockCodes");
        }

        var newStocks = new ArrayList<>(stockCodes);
        newStocks.add(stockCode);

        return new Member(
                this.id,
                this.profile,
                this.notificationSettings,
                newStocks,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Member removeStock(String stockCode) {
        Objects.requireNonNull(stockCode, "Stock cannot be null");

        if (stockCodes.stream().noneMatch(existingCode ->existingCode.equals(stockCode))) {
            throw new IllegalStateException("Stock with code " + stockCode + " does not exist");
        }

        var newStocks = stockCodes.stream().filter(existing -> !existing.equals(stockCode)).toList();

        return new Member(
                this.id,
                this.profile,
                this.notificationSettings,
                newStocks,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Member updateNickname(String nickname) {
        return new Member(
                this.id,
                this.profile.updateNickname(nickname),
                this.notificationSettings,
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Member updateEmail(String email) {
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
        return new Member(
                this.id,
                this.profile,
                this.notificationSettings.updateTime(PreferenceTime.of(preferenceHour)),
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Member receiveNotification() {
        return new Member(
                this.id,
                this.profile,
                this.notificationSettings.receiveNotification(),
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Member stopNotification() {
        return new Member(
                this.id,
                this.profile,
                this.notificationSettings.stopNotification(),
                this.stockCodes,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public String email(){
        return profile.email();
    }

    public String nickname(){
        return profile.nickname();
    }

    public boolean isNotificationEnabled(){
        return notificationSettings.notificationEnabled();
    }
}
