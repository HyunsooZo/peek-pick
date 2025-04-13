package com.peekpick.member.presentation;

import com.peekpick.member.application.MemberApplicationData;

public class MemberInformationMapper {
    public static MemberInformationPayload.InformationResponse mapToResponse(
            MemberApplicationData.MemberInfoResult memberInformation
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
