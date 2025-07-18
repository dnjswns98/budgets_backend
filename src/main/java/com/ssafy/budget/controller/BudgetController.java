package com.ssafy.budget.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.auth.service.AuthService;
import com.ssafy.budget.dto.CreateBudgetRequest;
import com.ssafy.budget.dto.UpdateBudgetRequest;
import com.ssafy.budget.entity.Budget;
import com.ssafy.budget.service.BudgetService;
import com.ssafy.common.annotation.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
@Tag(name = "가계부 관리", description = "사용자 가계부 항목을 생성, 조회, 수정, 삭제하는 API")
@SecurityRequirement(name = "JWT")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "새로운 가계부 항목 생성", description = "현재 로그인된 사용자를 위한 새로운 가계부 항목을 생성합니다.")
    public ResponseEntity<String> createBudget(
            @Parameter(hidden = true) @UserId Long userId,
            @Valid @RequestBody CreateBudgetRequest request) {
        budgetService.createBudget(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("가계부 기입이 완료되었습니다");
    }

    @DeleteMapping("/{budgetId}")
    @Operation(summary = "가계부 항목 삭제", description = "현재 로그인된 사용자를 위한 가계부 항목을 삭제합니다.")
    public ResponseEntity<String> deleteBudget(
            @Parameter(hidden = true) @UserId Long userId,
            @Parameter(name = "budgetId", description = "가계부 id", in = ParameterIn.PATH) @PathVariable Long budgetId) {
        budgetService.deleteBudget(userId, budgetId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("가계부 삭제가 완료되었습니다");
    }

    @PatchMapping("/{budgetId}")
    @Operation(summary = "가계부 항목 수정", description = "현재 로그인된 사용자를 위한 가계부 항목을 수정합니다.")
    public ResponseEntity<String> updateBudget(
            @Parameter(hidden = true) @UserId Long userId,
            @PathVariable Long budgetId,
            @Valid @RequestBody UpdateBudgetRequest request) {
        budgetService.updateBudget(userId, budgetId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("가계부 수정이 완료되었습니다");
    }

    // ✅ 예산 전체 조회 + used_amount 포함
    @GetMapping
    @Operation(summary = "가계부 항목 전체 조회", description = "현재 로그인된 사용자의 가계부 항목을 조회하고, 카테고리별 이번 달 지출 금액(used_amount)을 포함해 반환합니다.")
    public ResponseEntity<List<Budget>> getBudgets(
            @Parameter(hidden = true) @UserId Long userId) {
        List<Budget> budgets = budgetService.getBudgetsWithUsage(userId);
        return ResponseEntity.ok(budgets);
    }
}
