package com.peekpick.member.domain;

record MemberProfile(
        String nickname,
        String email
) {
    MemberProfile updateEmail(String email) {
        return new MemberProfile(nickname, email);
    }

    MemberProfile updateNickname(String nickname) {
        return new MemberProfile(nickname, email);
    }
}
