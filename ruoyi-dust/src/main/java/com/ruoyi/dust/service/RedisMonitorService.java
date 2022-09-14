package com.ruoyi.dust.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.dust.utils.PageQuery;

import java.util.Map;

public interface RedisMonitorService {
    AjaxResult selectData(PageQuery<Map<String, String>> pageQuery);

    AjaxResult flushDb(String database);
}
