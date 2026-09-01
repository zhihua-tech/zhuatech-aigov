/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.controller;

import cn.zhuatech.aigov.common.ApiResponse;
import cn.zhuatech.aigov.service.AiUseCaseProductionApprovalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/aigov")
public class AiUseCaseProductionApprovalController {
    private final AiUseCaseProductionApprovalService service;
    public AiUseCaseProductionApprovalController(AiUseCaseProductionApprovalService service) { this.service = service; }
    @PostMapping("/use-case-production-approval")
    public ApiResponse<AiUseCaseProductionApprovalService.Assessment> assess(
            @Valid @RequestBody AiUseCaseProductionApprovalService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
