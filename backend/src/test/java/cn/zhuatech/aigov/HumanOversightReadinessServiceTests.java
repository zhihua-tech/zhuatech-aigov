/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov;

import cn.zhuatech.aigov.service.HumanOversightReadinessService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class HumanOversightReadinessServiceTests {
    private final HumanOversightReadinessService service = new HumanOversightReadinessService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksHighImpactAutomationWithoutOversight() {
        var result = service.evaluate(new HumanOversightReadinessService.Request(
            "CREDIT-DECISION", 5, true, false, false, true, false, true, true));
        assertEquals("BLOCK", result.decision());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void approvesGovernedAssistiveUseCase() {
        var result = service.evaluate(new HumanOversightReadinessService.Request(
            "KNOWLEDGE-ASSIST", 2, false, true, true, true, true, true, true));
        assertEquals(100, result.readinessScore());
        assertEquals("APPROVE", result.decision());
    }
}
