package com.ruoyi.dust.service.impl;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.dust.bean.RedisBean;
import com.ruoyi.dust.service.RedisMonitorService;
import com.ruoyi.dust.utils.PageQuery;
import com.ruoyi.dust.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class RedisMonitorServiceImpl implements RedisMonitorService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public AjaxResult selectData(PageQuery<Map<String, String>> pageQuery) {
        Set<String> keys = redisUtil.keys();
        ArrayList<RedisBean> list = new ArrayList<>();
        if (keys != null && keys.size() > 0) {
            for (String s : keys) {
                list.add(new RedisBean(s, redisUtil.get(s).toString(), redisUtil.getExpire(s)));
            }
        }
        return AjaxResult.success(new PageInfo<>(list));
    }

    @Override
    public AjaxResult flushDb(String database) {
        return AjaxResult.success(redisUtil.delete(redisUtil.keys()));
    }
}
