/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class HumanOversightReadinessService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result evaluate(Request request) {
        int score = 100;
        if (request.automatedDecision() && !request.humanReviewEnabled()) score -= 35;
        if (!request.explainabilityAvailable()) score -= 20;
        if (!request.monitoringEnabled()) score -= 20;
        if (!request.rollbackReady()) score -= 20;
        if (!request.accountableOwnerAssigned()) score -= 25;
        if (!request.dataProtectionAssessmentComplete()) score -= 15;
        score = Math.max(0, score);
        boolean hardBlock = !request.accountableOwnerAssigned()
            || request.impactLevel() >= 4 && request.automatedDecision()
                && (!request.humanReviewEnabled() || !request.rollbackReady());
        String decision = hardBlock ? "BLOCK" : score < 80 ? "REVIEW" : "APPROVE";
        List<String> actions = new ArrayList<>();
        if (!request.humanReviewEnabled() && request.automatedDecision()) actions.add("为高影响决策增加人工确认与申诉入口");
        if (!request.monitoringEnabled()) actions.add("建立质量、偏差和漂移监控");
        if (!request.rollbackReady()) actions.add("准备模型回退与人工接管方案");
        if (!request.accountableOwnerAssigned()) actions.add("指定业务责任人和风险接受人");
        if (actions.isEmpty()) actions.add("批准上线并保存治理评审证据");
        return new Result(request.useCaseCode(), score, decision, actions);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String useCaseCode, @Min(1) @Max(5) int impactLevel,
                          boolean automatedDecision, boolean humanReviewEnabled,
                          boolean explainabilityAvailable, boolean monitoringEnabled,
                          boolean rollbackReady, boolean accountableOwnerAssigned,
                          boolean dataProtectionAssessmentComplete) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String useCaseCode, int readinessScore, String decision, List<String> actions) {}
}
