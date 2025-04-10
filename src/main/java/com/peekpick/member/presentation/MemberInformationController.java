package com.peekpick.member.presentation;

import com.peekpick.member.application.service.MemberInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/members")
public class MemberInformationController {
    private final MemberInformationService memberInformationService;

    public MemberInformationController(MemberInformationService memberInformationService) {
        this.memberInformationService = memberInformationService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberInformationPayload.InformationResponse> register(
            @RequestBody MemberInformationPayload.InformationRequest request
    ) {
        final var memberInfo = memberInformationService.register(request.toRegistration());
        return ResponseEntity.ok(MemberInformationMapper.mapToResponse(memberInfo));
    }

    @PatchMapping("/update")
    public ResponseEntity<MemberInformationPayload.InformationResponse> update(
            @RequestBody MemberInformationPayload.InformationRequest request
    ) {
        final var memberInfo = memberInformationService.update(request.toModification());
        return ResponseEntity.ok(MemberInformationMapper.mapToResponse(memberInfo));
    }
}
