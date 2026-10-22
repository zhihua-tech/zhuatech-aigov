/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aigov.workbench;

import static cn.zhuatech.aigov.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "asOf", "policies", "records");
    var asOf = date(text(r, "asOf", 10));
    Map<String, Integer> policies = new HashMap<>();
    for (var p : rows(r, "policies", 1, 100)) {
      keys(p, "category", "retentionDays");
      String category = id(p, "category");
      if (policies.putIfAbsent(category, integer(p, "retentionDays", 1, 36500)) != null)
        throw new IllegalArgumentException("类别保留规则重复");
    }
    Set<String> ids = new HashSet<>();
    List<Map<String, Object>> out = new ArrayList<>();
    int due = 0;
    for (var record : rows(r, "records", 1, 500)) {
      keys(record, "id", "category", "createdOn", "legalHold");
      String id = id(record, "id");
      unique(ids, id);
      String category = id(record, "category");
      var created = date(text(record, "createdOn", 10));
      if (created.isAfter(asOf)) throw new IllegalArgumentException("记录创建日不可晚于复核日");
      boolean hold = flag(record, "legalHold");
      Integer days = policies.get(category);
      var expiry = days == null ? null : created.plusDays(days);
      String status =
          hold
              ? "LEGAL_HOLD"
              : days == null ? "POLICY_MISSING" : !asOf.isBefore(expiry) ? "REVIEW_DUE" : "RETAIN";
      if (status.equals("REVIEW_DUE")) due++;
      out.add(
          Map.of(
              "id",
              id,
              "category",
              category,
              "status",
              status,
              "expiresOn",
              expiry == null ? "" : expiry.toString(),
              "automaticDeletion",
              false));
    }
    return Map.of(
        "asOf", asOf.toString(), "reviewDue", due, "records", out, "automaticDeletion", false);
  }
}
