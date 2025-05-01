package com.peekpick.member.presentation;

import com.peekpick.member.application.MemberInformationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/members")
public class MemberCommandController {
    private final MemberInformationUseCase MemberInformationUseCase;

    public MemberCommandController(MemberInformationUseCase MemberInformationUseCase) {
        this.MemberInformationUseCase = MemberInformationUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberCommandPayload.InformationResponse> register(
            @RequestBody MemberCommandPayload.InformationRequest request
    ) {
        final var memberInfo = MemberInformationUseCase.register(request.toRegistration());
        return ResponseEntity.ok(MemberCommandMapper.mapToResponse(memberInfo));
    }

    @PatchMapping("/update")
    public ResponseEntity<MemberCommandPayload.InformationResponse> update(
            @RequestBody MemberCommandPayload.InformationRequest request
    ) {
        final var memberInfo = MemberInformationUseCase.update(request.toModification());
        return ResponseEntity.ok(MemberCommandMapper.mapToResponse(memberInfo));
    }
}
