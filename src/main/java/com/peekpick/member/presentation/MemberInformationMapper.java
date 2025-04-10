package com.peekpick.member.presentation;

import com.peekpick.member.application.data.MemberInfoResult;

public class MemberInformationMapper {
    public static MemberInformationPayload.InformationResponse mapToResponse(
            MemberInfoResult memberInformation
    ) {
        return new MemberInformationPayload.InformationResponse(
                memberInformation.nickname(),
                memberInformation.email(),
                memberInformation.preferenceHour(),
                memberInformation.stocks(),
                memberInformation.notificationEnabled()
        );
    }
}
