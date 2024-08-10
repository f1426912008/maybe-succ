package com.ruoyi.dust.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.dust.bean.entity.RedisBean;
import com.ruoyi.dust.service.RedisMonitorService;
import com.ruoyi.dust.utils.PageQuery;
import com.ruoyi.dust.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RedisMonitorServiceImpl implements RedisMonitorService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public AjaxResult selectData(PageQuery<Map<String, String>> pageQuery) {
        Map<String, String> data = pageQuery.getData();
        ArrayList<RedisBean> list = new ArrayList<>();
        String key = data.get("redisKey");
        if (StringUtils.isNotBlank(key)) {
            list.add(new RedisBean(key, redisUtil.get(key) + "", redisUtil.getExpire(key)));
        } else {
            Set<String> keys = redisUtil.keys();
            if (CollUtil.isNotEmpty(keys))
                keys.forEach(s -> list.add(new RedisBean(s, redisUtil.get(s) + "", redisUtil.getExpire(s))));
        }
        return AjaxResult.success(new PageInfo<>(list));
    }

    @Override
    public AjaxResult flushDb(String database) {
        return AjaxResult.success(redisUtil.delete(redisUtil.keys()));
    }
}
