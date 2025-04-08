package com.peekpick.member.infrastructure.mongo;

import com.peekpick.member.domain.model.Member;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "members")
public record MemberDocument(
        @Id
        String id,
        String nickname,
        String email,
        boolean notificationEnabled,
        Integer preferenceHour,
        List<String> stockCodes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
        public static MemberDocument from(Member member) {
                return new MemberDocument(
                        member.id(),
                        member.nickname(),
                        member.email(),
                        member.isNotificationEnabled(),
                        member.preferenceHour(),
                        member.stockCodes(),
                        member.createdAt(),
                        member.updatedAt()
                );
        }

        public Member toDomain() {
                return new Member(id, nickname, email, notificationEnabled, preferenceHour, stockCodes, createdAt, updatedAt);
        }
}
