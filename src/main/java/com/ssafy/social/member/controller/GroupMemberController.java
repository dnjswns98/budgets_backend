package com.ssafy.social.member.controller;

import com.ssafy.common.annotation.UserId;
import com.ssafy.social.member.dto.request.AddGroupMemberRequest;
import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.social.member.service.GroupMemberService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups/{groupId}/members")
@Tag(name = "그룹 멤버 관리", description = "멤버 CRUD")
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @PostMapping("/invite")
    @Operation(summary = "그룹 멤버 초대")
    public ResponseEntity<?> inviteMember(
            @PathVariable int groupId,
            @UserId Long userId,
            @RequestBody AddGroupMemberRequest request) {
        groupMemberService.inviteMember(groupId, userId, request.getTargetId());
        return ResponseEntity.ok(Map.of("message", "초대가 완료되었습니다."));
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "그룹 멤버 강퇴 or 자발적 탈퇴")
    public ResponseEntity<?> removeMember(
            @PathVariable int groupId,
            @PathVariable Long memberId,
            @UserId Long userId) {
        groupMemberService.removeMember(groupId, userId, memberId);
        return ResponseEntity.ok(Map.of("message", "탈퇴 처리 완료"));
    }
    
    @GetMapping("/{memberId}")
    @Operation(summary = "특정 멤버 조회")
    public ResponseEntity<GroupMemberResponse> getMember(
            @PathVariable int groupId,
            @PathVariable Long memberId,
            @UserId Long userId) {
        GroupMemberResponse response = groupMemberService.getMember(groupId, memberId);
        return ResponseEntity.ok(response);
    }
}
