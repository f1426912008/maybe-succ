package com.ruoyi.dust.bean.entity;

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

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getRedisValue() {
        return redisValue;
    }

    public void setRedisValue(String redisValue) {
        this.redisValue = redisValue;
    }

    public Long getRedisTTL() {
        return redisTTL;
    }

    public void setRedisTTL(Long redisTTL) {
        this.redisTTL = redisTTL;
    }

    @Override
    public String toString() {
        return "RedisBean{" +
                "redisKey='" + redisKey + '\'' +
                ", redisValue='" + redisValue + '\'' +
                ", redisTTL=" + redisTTL +
                '}';
    }
}
