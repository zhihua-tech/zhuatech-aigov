/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class AiUseCaseProductionApprovalServiceTest {
    private final AiUseCaseProductionApprovalService service = new AiUseCaseProductionApprovalService();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void approvesGovernedUseCase() {
        var result = service.assess(new AiUseCaseProductionApprovalService.Request("U1", true, true, true,
                true, true, true, true, true, true, true, 0));
        assertThat(result.decision()).isEqualTo(AiUseCaseProductionApprovalService.Decision.APPROVE);
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void conditionallyApprovesOperationalGaps() {
        var result = service.assess(new AiUseCaseProductionApprovalService.Request("U2", true, true, true,
                true, true, true, true, false, false, false, 0));
        assertThat(result.actions()).hasSize(3);
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksGovernanceFailures() {
        var result = service.assess(new AiUseCaseProductionApprovalService.Request("U3", false, false, false,
                false, false, false, false, true, true, true, 2));
        assertThat(result.blockers()).hasSize(8);
    }
}
