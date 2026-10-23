# ZhuaTech AI Governance · 知华 AI 治理平台


## 本次升级：AI应用评估版本与整改关闭

核对生产应用版本与评估版本、评估有效期，以及高风险发现整改与独立验证关闭数量；不是法规合规认证。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。
## 本次功能更新：数据保留期限复核

按数据类别配置保留天数，生成到期复核和保留状态，法律保留优先且缺失规则显式阻断；不自动删除数据，也不作合规结论。 登录后从「业务计算工具」进入，支持真实后端校验与结果导出。详见[接口、示例与边界说明](docs/AI_BUSINESS_UPGRADE_20261023.md)。本次不改变既有许可及联系方式。

[简体中文](README.md) | [English](README.en.md)

面向企业 AI 系统全生命周期的治理控制中心。它把系统登记、风险分级、证据评审、上线决策和运行复评放进同一条可追踪流程，而不是停留在一份静态制度文档中。

项目由 **上海如静知华信息科技有限公司（知华科技）**发布。产品与技术服务请访问[知华科技官网](https://www.zhuatech.cn/)。

![Java 21](https://img.shields.io/badge/Java-21-4b5fb8) ![Vue 3](https://img.shields.io/badge/Vue-3-42b883) ![MySQL 8](https://img.shields.io/badge/MySQL-8-4479A1) ![License](https://img.shields.io/badge/license-Non--Commercial-c6923b)

## 为什么做这个项目

AI 项目快速增长后，企业通常会遇到三个问题：系统台账分散、风险判断缺少统一尺度、上线证据无法复用。ZhuaTech AIGOV 提供一套可运行的社区版参考实现，用具体的数据模型和流程表达治理要求。

```text
业务提出用途 → 登记模型与数据 → 自动风险初筛 → 控制与证据评审 → 上线决策 → 运行复评
```

核心能力包括 AI 系统台账、风险分级、模型卡与数据卡、评测证据、人工监督、整改闭环、委员会决策及治理指标。`POST /api/shopfloor/ai-risk-assessment` 提供一套不依赖外部模型的可解释风险评分实现。

## 真实界面

### 治理负责人：风险与决策全景

![知华科技 AI 治理控制中心](docs/images/aigov-security-dashboard.png)

管理端以评审队列为主线，同时展示风险等级、治理领域负荷、证据缺口和委员会关注事项。

### 评审员：移动评审工作台

![知华科技 AI 治理 H5 工作台](docs/images/aigov-analyst-h5.png)

H5 端适合评审员查看任务、核验证据、提交意见和发起风险升级。

## 工程结构

| 目录 | 说明 |
| --- | --- |
| `backend` | Java 21、Spring Boot、Spring Security、JWT、JPA、Flyway |
| `frontend` | Vue 3、Pinia、Vue Router、Axios、Vite，兼顾桌面管理端和 H5 |
| `docs` | API、架构、数据库和界面资料 |
| `deploy` | Docker Compose 与部署说明 |

Java 工程包名为 `cn.zhuatech.aigov`，默认 MySQL 数据库为 `zhuatech_aigov`。演示数据均为虚构数据。

## 5 分钟体验

```bash
cd frontend
npm install
npm run dev:demo
```

打开 `http://localhost:5173`。治理端使用 `planner / Demo@2026`，评审端使用 `operator / Demo@2026`。完整 API 与容器启动方式见 [API 文档](docs/api.md)和[部署说明](deploy/README.md)。

## 使用边界

本工程仅允许用于个人学习、研究及非商业技术交流，**不得商用**。企业内部使用、生产部署、SaaS 服务、客户项目交付、收费培训、咨询实施、品牌替换或商业分发，均需事先取得上海如静知华信息科技有限公司书面授权。详细条款以 [LICENSE](LICENSE) 为准。

如需 AI 治理制度落地、平台集成、私有化部署或深度开发定制，可访问[知华科技官网](https://www.zhuatech.cn/)或扫码咨询：

| AI 治理咨询 | 深度定制咨询 |
| --- | --- |
| ![知华科技微信二维码一](docs/images/zhuatech-wechat-consulting.png) | ![知华科技微信二维码二](docs/images/zhuatech-wechat-consulting-2.png) |

SEO：AI 治理平台、AI Governance、模型风险管理、AI 系统台账、算法治理、Java AI 治理源码、Vue 管理系统、知华科技。

## 人工监督就绪度

新增 `POST /api/aigov/insights/human-oversight-readiness`。面向高影响 AI 场景，检查人工复核、可解释性、持续监控、回滚机制和责任人配置，形成监督完备度评分并返回 `APPROVE`、`REVIEW` 或 `BLOCK`，用于上线评审和治理证据留存。

## 企业级 AI 用例生产审批

新增 `POST /api/enterprise/aigov/use-case-production-approval`，覆盖风险分级、影响评估、模型卡、评测、公平性、人工监督、监控、回滚与供应商风险，返回 `APPROVE / CONDITIONAL / BLOCKED`。详见 [AI 用例审批说明](docs/ENTERPRISE_AI_USE_CASE_APPROVAL.md)。

## 输入对照与版本化快照

在既有「AI应用评估版本与整改关闭」基础上，新增 `/comparison.html`：基准与候选两组数据都由当前真实算法重算，按编号展示新增、移除、逐字段变化和精确数值差，支持筛选、分页和全量导出。原核算入口可导出输入快照，对照页经后端完整校验后整体导入；失败不覆盖当前输入。

快照区分项目、字段契约及算法版本。建议较宽松不代表风险已下降，输入摘要不是签名；操作人和时间仅为响应元数据，不是持久化审计。本次未新增模型调用或业务写入，也不代表全系统生产认证。详见[输入对照操作与接口说明](docs/INPUT-COMPARISON.md)。
