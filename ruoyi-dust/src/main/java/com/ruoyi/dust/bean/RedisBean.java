package com.ruoyi.dust.bean;

import lombok.Data;

@Data
public class RedisBean {

    private String redisKey;

    private String redisValue;

    private Long redisTTL;

    public RedisBean() {
    }

    public RedisBean(String redisKey, String redisValue, Long redisTTL) {
        this.redisKey = redisKey;
        this.redisValue = redisValue;
        this.redisTTL = redisTTL;
    }
}
