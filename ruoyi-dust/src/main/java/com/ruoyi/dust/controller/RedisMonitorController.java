package com.ruoyi.dust.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.dust.service.RedisMonitorService;
import com.ruoyi.dust.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/redisUtil")
public class RedisMonitorController {

    @Autowired
    private RedisMonitorService redisMonitorService;

    @PostMapping("/queryData")
    public AjaxResult queryData(@RequestBody PageQuery<Map<String, String>> pageQuery) {
        return redisMonitorService.selectData(pageQuery);
    }

    @PostMapping("/flushDb")
    public AjaxResult flushDb(@RequestParam Map<String, String> map) {
        String database = map.get("database");
        return redisMonitorService.flushDb(database);
    }
}
