package com.bootme.app.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * Redis工具类
 *
 * @author ZENG.XIAO.YAN
 * @date 2018年6月7日
 * 17
 */
@Component
public final class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 26
     * 指定缓存失效时间
     * 27
     *
     * @param key  键
     *             28
     * @param time 时间(秒)
     *             29
     * @return 30
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 44
     * 根据key 获取过期时间
     * 45
     *
     * @param key 键 不能为null
     *            46
     * @return 时间(秒) 返回0代表为永久有效
     * 47
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 53
     * 判断key是否存在
     * 54
     *
     * @param key 键
     *            55
     * @return true 存在 false不存在
     * 56
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 67
     * 删除缓存
     * 68
     *
     * @param key 可以传一个值 或多个
     *            69
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    // ============================String=============================

    /**
     * 83
     * 普通缓存获取
     * 84
     *
     * @param key 键
     *            85
     * @return 值
     * 86
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 92
     * 普通缓存放入
     * 93
     *
     * @param key   键
     *              94
     * @param value 值
     *              95
     * @return true成功 false失败
     * 96
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 109
     * 普通缓存放入并设置时间
     * 110
     *
     * @param key   键
     *              111
     * @param value 值
     *              112
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     *              113
     * @return true成功 false 失败
     * 114
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 130
     * 递增
     * 131
     *
     * @param key   键
     *              132
     * @param delta 要增加几(大于0)
     *              133
     * @return 134
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 143
     * 递减
     * 144
     *
     * @param key   键
     *              145
     * @param delta 要减少几(小于0)
     *              146
     * @return 147
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * 157
     * HashGet
     * 158
     *
     * @param key  键 不能为null
     *             159
     * @param item 项 不能为null
     *             160
     * @return 值
     * 161
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 167
     * 获取hashKey对应的所有键值
     * 168
     *
     * @param key 键
     *            169
     * @return 对应的多个键值
     * 170
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 176
     * HashSet
     * 177
     *
     * @param key 键
     *            178
     * @param map 对应多个键值
     *            179
     * @return true 成功 false 失败
     * 180
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 192
     * HashSet 并设置时间
     * 193
     *
     * @param key  键
     *             194
     * @param map  对应多个键值
     *             195
     * @param time 时间(秒)
     *             196
     * @return true成功 false失败
     * 197
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 212
     * 向一张hash表中放入数据,如果不存在将创建
     * 213
     *
     * @param key   键
     *              214
     * @param item  项
     *              215
     * @param value 值
     *              216
     * @return true 成功 false失败
     * 217
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 229
     * 向一张hash表中放入数据,如果不存在将创建
     * 230
     *
     * @param key   键
     *              231
     * @param item  项
     *              232
     * @param value 值
     *              233
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     *              234
     * @return true 成功 false失败
     * 235
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 250
     * 删除hash表中的值
     * 251
     *
     * @param key  键 不能为null
     *             252
     * @param item 项 可以使多个 不能为null
     *             253
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 259
     * 判断hash表中是否有该项的值
     * 260
     *
     * @param key  键 不能为null
     *             261
     * @param item 项 不能为null
     *             262
     * @return true 存在 false不存在
     * 263
     */

    public boolean hHasKey(String key, String item) {

        return redisTemplate.opsForHash().hasKey(key, item);

    }


    /**
     * 269
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * 270
     *
     * @param key  键
     *             271
     * @param item 项
     *             272
     * @param by   要增加几(大于0)
     *             273
     * @return 274
     */

    public double hincr(String key, String item, double by) {

        return redisTemplate.opsForHash().increment(key, item, by);

    }


    /**
     * 280
     * hash递减
     * 281
     *
     * @param key  键
     *             282
     * @param item 项
     *             283
     * @param by   要减少记(小于0)
     *             284
     * @return 285
     */

    public double hdecr(String key, String item, double by) {

        return redisTemplate.opsForHash().increment(key, item, -by);

    }

    // ============================set=============================

    /**
     * 292
     * 根据key获取Set中的所有值
     * 293
     *
     * @param key 键
     *            294
     * @return 295
     */

    public Set<Object> sGet(String key) {

        try {

            return redisTemplate.opsForSet().members(key);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

    }

    /**
     * 306
     * 根据value从一个set中查询,是否存在
     * 307
     *
     * @param key   键
     *              308
     * @param value 值
     *              309
     * @return true 存在 false不存在
     * 310
     */

    public boolean sHasKey(String key, Object value) {

        try {

            return redisTemplate.opsForSet().isMember(key, value);

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    /**
     * 321
     * 将数据放入set缓存
     * 322
     *
     * @param key    键
     *               323
     * @param values 值 可以是多个
     *               324
     * @return 成功个数
     * 325
     */

    public long sSet(String key, Object... values) {

        try {

            return redisTemplate.opsForSet().add(key, values);

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }


    /**
     * 336
     * 将set数据放入缓存
     * 337
     *
     * @param key    键
     *               338
     * @param time   时间(秒)
     *               339
     * @param values 值 可以是多个
     *               340
     * @return 成功个数
     * 341
     */

    public long sSetAndTime(String key, long time, Object... values) {

        try {

            Long count = redisTemplate.opsForSet().add(key, values);

            if (time > 0)

                expire(key, time);

            return count;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }


    /**
     * 355
     * 获取set缓存的长度
     * 356
     *
     * @param key 键
     *            357
     * @return 358
     */

    public long sGetSetSize(String key) {

        try {

            return redisTemplate.opsForSet().size(key);

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }


    /**
     * 369
     * 移除值为value的
     * 370
     *
     * @param key    键
     *               371
     * @param values 值 可以是多个
     *               372
     * @return 移除的个数
     * 373
     */

    public long setRemove(String key, Object... values) {

        try {

            Long count = redisTemplate.opsForSet().remove(key, values);

            return count;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }

    // ===============================list=================================


    /**
     * 386
     * 获取list缓存的内容
     * 387
     *
     * @param key   键
     *              388
     * @param start 开始
     *              389
     * @param end   结束 0 到 -1代表所有值
     *              390
     * @return 391
     */

    public List<Object> lGet(String key, long start, long end) {

        try {

            return redisTemplate.opsForList().range(key, start, end);

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }


    /**
     * 402
     * 获取list缓存的长度
     * 403
     *
     * @param key 键
     *            404
     * @return 405
     */

    public long lGetListSize(String key) {

        try {

            return redisTemplate.opsForList().size(key);

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }


    /**
     * 416
     * 通过索引 获取list中的值
     * 417
     *
     * @param key   键
     *              418
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     *              419
     * @return 420
     */

    public Object lGetIndex(String key, long index) {

        try {

            return redisTemplate.opsForList().index(key, index);

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }


    /**
     * 431
     * 将list放入缓存
     * 432
     *
     * @param key   键
     *              433
     * @param value 值
     *              434
     * @param time  时间(秒)
     *              435
     * @return 436
     */

    public boolean lSet(String key, Object value) {

        try {

            redisTemplate.opsForList().rightPush(key, value);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    /**
     * 448
     * 将list放入缓存
     * 449
     *
     * @param key   键
     *              450
     * @param value 值
     *              451
     * @param time  时间(秒)
     *              452
     * @return 453
     */

    public boolean lSet(String key, Object value, long time) {

        try {

            redisTemplate.opsForList().rightPush(key, value);

            if (time > 0)

                expire(key, time);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    /**
     * 467
     * 将list放入缓存
     * 468
     *
     * @param key   键
     *              469
     * @param value 值
     *              470
     * @param time  时间(秒)
     *              471
     * @return 472
     */

    public boolean lSet(String key, List<Object> value) {

        try {

            redisTemplate.opsForList().rightPushAll(key, value);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    /**
     * 484
     * 将list放入缓存
     * 485
     * <p>
     * 486
     *
     * @param key   键
     *              487
     * @param value 值
     *              488
     * @param time  时间(秒)
     *              489
     * @return 490
     */

    public boolean lSet(String key, List<Object> value, long time) {

        try {

            redisTemplate.opsForList().rightPushAll(key, value);

            if (time > 0)

                expire(key, time);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    /**
     * 504
     * 根据索引修改list中的某条数据
     * 505
     *
     * @param key   键
     *              506
     * @param index 索引
     *              507
     * @param value 值
     *              508
     * @return 509
     */

    public boolean lUpdateIndex(String key, long index, Object value) {

        try {

            redisTemplate.opsForList().set(key, index, value);
            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }


    /**
     * 521
     * 移除N个值为value
     * 522
     *
     * @param key   键
     *              523
     * @param count 移除多少个
     *              524
     * @param value 值
     *              525
     * @return 移除的个数
     * 526
     */

    public long lRemove(String key, long count, Object value) {

        try {

            Long remove = redisTemplate.opsForList().remove(key, count, value);

            return remove;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }

}