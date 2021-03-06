package org.storm.commons.offlinetask.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

@Slf4j
public class RedisTools {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, long expireTime) {
        SetParams params = new SetParams();
        params.nx();
        params.px(expireTime);
        String result = jedis.set(lockKey, requestId, params);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁（在redis环境可用）
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock2(Jedis jedis, String lockKey, String requestId) {
        String val = jedis.get(lockKey);
        if (StringUtils.isBlank(val)) {
            return true;
        }
        if (val.equals(requestId)) {
            Long result = jedis.del(lockKey);
            return RELEASE_SUCCESS.equals(result);
        }
        return false;
    }
}
