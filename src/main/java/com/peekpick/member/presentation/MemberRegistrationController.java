package com.peekpick.member.presentation;

import com.peekpick.member.application.service.MemberRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/members")
public class MemberRegistrationController {
    private final MemberRegistrationService memberRegistrationService;

    public MemberRegistrationController(MemberRegistrationService memberRegistrationService) {
        this.memberRegistrationService = memberRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberPayload.RegisterResponse> register(
            @RequestBody MemberPayload.RegisterRequest request
    ) {
        var register = memberRegistrationService.register(request.toCommand());
        return ResponseEntity.ok(MemberPayload.RegisterResponse.from(register));
    }
}
