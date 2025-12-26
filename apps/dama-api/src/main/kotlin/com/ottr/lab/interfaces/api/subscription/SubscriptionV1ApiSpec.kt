package com.ottr.lab.interfaces.api.subscription

import com.ottr.lab.interfaces.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Subscription V1 API", description = "구독 관리 API")
interface SubscriptionV1ApiSpec {
    @Operation(
        summary = "구독 등록",
        description = "새로운 구독을 등록합니다. user_id path로 사용자 ID를 전달받습니다.",
    )
    fun createSubscription(
        @Schema(description = "사용자 ID (path)", example = "1")
        userId: Long,
        @Schema(description = "구독 등록 요청 정보")
        request: SubscriptionV1Dto.CreateSubscriptionRequest,
    ): ApiResponse<SubscriptionV1Dto.SubscriptionSummaryResponse>

    @Operation(
        summary = "구독 목록 조회",
        description = "사용자의 구독 목록을 조회합니다.",
    )
    fun getSubscriptions(
        @Schema(description = "사용자 ID (path)", example = "1")
        userId: Long,
    ): ApiResponse<List<SubscriptionV1Dto.SubscriptionSummaryResponse>>

    @Operation(
        summary = "월 구독 비용 조회",
        description = "이번 달 총 구독 비용과 지난달 대비 비교 정보를 조회합니다.",
    )
    fun getMonthlySubscriptionCost(
        @Schema(description = "사용자 ID (path)", example = "1")
        userId: Long,
    ): ApiResponse<SubscriptionV1Dto.MonthlySubscriptionCostResponse>

    @Operation(
        summary = "내 구독 리스트 조회 (상세)",
        description = "사용자 이름과 함께 구독 리스트 상세 정보를 조회합니다. 최대 5개 구독만 반환됩니다.",
    )
    fun getMySubscriptionDetails(
        @Schema(description = "사용자 ID (path)", example = "1")
        userId: Long,
    ): ApiResponse<SubscriptionV1Dto.MySubscriptionDetailsResponse>

    @Operation(
        summary = "구독 랭킹 조회",
        description = "서비스별 구독자 수 랭킹을 조회합니다. age path 파라미터로 연령대별 필터링이 가능합니다. 전체 조회 시 'all'을 전달합니다.",
    )
    fun getSubscriptionRanking(
        @Schema(description = "연령대 필터 (10/20/30/... 또는 전체 조회 시 'all')", example = "20")
        age: String,
    ): ApiResponse<List<SubscriptionV1Dto.SubscriptionRankingResponse>>

    @Operation(
        summary = "구독 삭제",
        description = "구독을 삭제합니다 (soft delete). user_id path로 사용자 ID를 전달받습니다.",
    )
    fun deleteSubscription(
        @Schema(description = "구독 ID", example = "1")
        id: Long,
        @Schema(description = "사용자 ID (path)", example = "1")
        userId: Long,
    ): ApiResponse<Unit>
}
