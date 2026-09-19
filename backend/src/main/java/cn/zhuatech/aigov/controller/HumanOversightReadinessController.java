/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.controller;

import cn.zhuatech.aigov.common.ApiResponse;
import cn.zhuatech.aigov.service.HumanOversightReadinessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/aigov/insights")
public class HumanOversightReadinessController {
    private final HumanOversightReadinessService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public HumanOversightReadinessController(HumanOversightReadinessService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/human-oversight-readiness")
    public ApiResponse<HumanOversightReadinessService.Result> evaluate(
        @Valid @RequestBody HumanOversightReadinessService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
