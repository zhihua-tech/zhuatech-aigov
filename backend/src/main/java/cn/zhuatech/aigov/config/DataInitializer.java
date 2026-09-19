/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.config;

import cn.zhuatech.aigov.model.*;
import cn.zhuatech.aigov.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration
public class DataInitializer {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Bean
    CommandLineRunner seed(OperatingUnitRepository operatingUnits, WorkRecordRepository orders,
                           ResourceRegisterRepository resources, ReviewRecordRepository reviewRecords,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (operatingUnits.count() > 0) return;
            OperatingUnit primaryUnit = operatingUnits.save(new OperatingUnit("AIGOV-RISK", "AI 风险评审组", "数字治理办公室", 180));
            OperatingUnit secondaryUnit = operatingUnits.save(new OperatingUnit("AIGOV-DATA", "数据与隐私组", "数据治理中心", 120));
            OperatingUnit tertiaryUnit = operatingUnits.save(new OperatingUnit("AIGOV-MODEL", "模型验证组", "AI 工程中心", 96));

            WorkRecord t1 = orders.save(new WorkRecord("GOV-260801-018", "AI-CREDIT-ASSIST", "授信材料智能审阅上线评估", primaryUnit, 24, 16, 1, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "HIGH-R2"));
            WorkRecord t2 = orders.save(new WorkRecord("GOV-260801-021", "AI-HR-COPILOT", "人力资源知识助手治理评审", secondaryUnit, 18, 8, 0, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "MEDIUM-R1"));
            WorkRecord t3 = orders.save(new WorkRecord("GOV-260802-006", "AI-SALES-AGENT", "销售跟进 Agent 自主性复核", primaryUnit, 12, 0, 0, LocalDate.now().plusDays(3), WorkRecord.Status.RELEASED, "HIGH-R3"));
            WorkRecord t4 = orders.save(new WorkRecord("GOV-260728-015", "AI-DOC-SEARCH", "内部文档检索助手年度复评", tertiaryUnit, 20, 20, 1, LocalDate.now(), WorkRecord.Status.COMPLETED, "LOW-R1"));

            resources.saveAll(List.of(
                new ResourceRegister("CTRL-DATA-03", "训练数据来源与授权控制", secondaryUnit, ResourceRegister.Status.RUNNING, 88),
                new ResourceRegister("CTRL-EVAL-02", "模型效果与偏差评测", tertiaryUnit, ResourceRegister.Status.IDLE, 76),
                new ResourceRegister("CTRL-HUMAN-05", "关键决策人工复核", primaryUnit, ResourceRegister.Status.RUNNING, 91),
                new ResourceRegister("CTRL-TRACE-08", "输出标识与审计追踪", primaryUnit, ResourceRegister.Status.ALARM, 62)
            ));
            reviewRecords.saveAll(List.of(
                new ReviewRecord("EV-260801-032", t1, "个人信息影响评估", 6, 0, ReviewRecord.Result.PASSED, "何谨"),
                new ReviewRecord("EV-260801-011", t2, "知识来源与版权核验", 3, 0, ReviewRecord.Result.PASSED, "陆遥"),
                new ReviewRecord("EV-260801-018", t4, "年度透明度复核", 5, 1, ReviewRecord.Result.FAILED, "何谨"),
                new ReviewRecord("EV-260802-003", t3, "自主行为边界确认", 4, 0, ReviewRecord.Result.PENDING, "陆遥")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "陆遥", UserAccount.Role.DOMAIN_USER, "AIGOV-RISK"),
                new UserAccount("planner", demo, "何谨", UserAccount.Role.DOMAIN_OPERATOR, null),
                new UserAccount("quality", demo, "顾清", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
