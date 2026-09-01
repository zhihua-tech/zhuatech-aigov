/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiUseCaseProductionApprovalService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.riskTierAssigned()) blockers.add("AI 用例风险等级未确定");
        if (!request.impactAssessmentComplete()) blockers.add("影响评估未完成");
        if (!request.dataProtectionApproved()) blockers.add("数据保护评审未批准");
        if (!request.modelCardComplete()) blockers.add("模型卡与限制说明不完整");
        if (!request.evalThresholdPassed()) blockers.add("质量与安全评测未达到阈值");
        if (!request.fairnessAssessmentComplete()) blockers.add("公平性评估未完成");
        if (!request.humanOversightReady()) blockers.add("人工监督机制未就绪");
        if (request.openCriticalFindings() > 0) blockers.add("存在未关闭的严重治理问题");
        if (!blockers.isEmpty()) {
            actions.add("阻断生产审批并完成风险整改");
            return new Assessment(Decision.BLOCKED, blockers, actions);
        }
        if (!request.monitoringReady() || !request.rollbackReady() || !request.vendorRiskApproved()) {
            if (!request.monitoringReady()) actions.add("配置质量、漂移、安全和业务影响监控");
            if (!request.rollbackReady()) actions.add("完成停用与回滚演练");
            if (!request.vendorRiskApproved()) actions.add("完成第三方模型或服务风险审批");
            return new Assessment(Decision.CONDITIONAL, blockers, actions);
        }
        actions.add("批准生产使用并按风险等级执行周期复审");
        return new Assessment(Decision.APPROVE, blockers, actions);
    }

    public record Request(@NotBlank String useCaseId, boolean riskTierAssigned,
                          boolean impactAssessmentComplete, boolean dataProtectionApproved,
                          boolean modelCardComplete, boolean evalThresholdPassed,
                          boolean fairnessAssessmentComplete, boolean humanOversightReady,
                          boolean monitoringReady, boolean rollbackReady, boolean vendorRiskApproved,
                          @Min(0) int openCriticalFindings) {}
    public record Assessment(Decision decision, List<String> blockers, List<String> actions) {}
    public enum Decision { APPROVE, CONDITIONAL, BLOCKED }
}
