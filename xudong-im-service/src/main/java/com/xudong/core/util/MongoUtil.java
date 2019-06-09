package com.xudong.core.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author evan.shen
 * @since 2017/10/8
 */
public class MongoUtil {
    public static void buildUpdate(Update update, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            update.set(key, val);
        }
    }

    public static void buildUpdateForList(Update update, String key, List val) {
        if (val != null && val.size() > 0) {
            update.set(key, val);
        }
    }

    public static void buildUpdateForCollection(Update update, String key, Collection val) {
        if (val != null && val.size() > 0) {
            update.set(key, val);
        }
    }

    public static void buildUpdateForListToNull(Update update, String key, Collection val) {
        if (val != null) {
            update.set(key, val);
        }
    }

    public static void buildQueryForIs(Criteria criteria, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            criteria.and(key).in(val);
        }
    }

    public static void buildQueryForIs(Query query, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            query.addCriteria(Criteria.where(key).is(val));
        }
    }


    /**
     * 数值类型 1.为null直接更新为null，
     * 2.blank更新为blank
     * @param update
     * @param key
     * @param val
     */
    public static void buildUpdateUnset(Update update, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            update.set(key, val);
        } else {
            update.unset(key);
        }
    }

    /**
     * 排除该字段为空的数据
     *
     * @param query
     * @param key
     * @param val   true：排除 false或者val为空：不排除
     */
    public static void buildQueryForNe(Query query, String key, Boolean val) {
        if (null != val && val) {
            query.addCriteria(Criteria.where(key).ne(null));
        }
    }

    public static void buildQueryForNe(Query query, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            query.addCriteria(Criteria.where(key).ne(val));
        }
    }

    public static void buildQueryForLike(Criteria criteria, String key, String val, LikeType likeType) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            String regex = getRegex(val, likeType);
            criteria.and(key).regex(regex);
        }
    }


    public static void buildQueryForLike(Query query, String key, String val, LikeType likeType) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            String regex = getRegex(val, likeType);
            query.addCriteria(Criteria.where(key).regex(regex));
        }
    }

    public static void buildQueryForRegex(Criteria criteria, String key, String regex) {
        if (regex != null && StringUtils.isNotBlank(regex + "")) {
            criteria.and(key).regex(regex);
        }
    }

    public static void buildQueryForRegex(Query query, String key, String regex) {
        if (regex != null && StringUtils.isNotBlank(regex + "")) {
            query.addCriteria(Criteria.where(key).regex(regex));
        }
    }

    public static void buildQueryForRegex(Criteria criteria, String key, Pattern pattern) {
        if (pattern != null) {
            criteria.and(key).regex(pattern);
        }
    }

    public static void buildQueryForRegex(Query query, String key, Pattern pattern) {
        if (pattern != null) {
            query.addCriteria(Criteria.where(key).regex(pattern));
        }
    }

    public static void buildQueryForIn(Criteria criteria, String key, Collection<?> val) {
        if (val != null && !CollectionUtils.isEmpty(val)) {
            criteria.and(key).in(val);
        }
    }

    public static void buildQueryForIn(Query query, String key, Collection<?> val) {
        if (val != null && !CollectionUtils.isEmpty(val)) {
            query.addCriteria(Criteria.where(key).in(val));
        }
    }

    public static void buildQueryForIn(Criteria criteria, String key, Serializable... vals) {
        if (vals != null && ArrayUtils.isNotEmpty(vals)) {
            criteria.and(key).in(vals);
        }
    }

    public static void buildQueryForIn(Query query, String key, Serializable... vals) {
        if (vals != null && ArrayUtils.isNotEmpty(vals)) {
            query.addCriteria(Criteria.where(key).in(vals));
        }
    }

    public static void buildQueryForIn(Query query, String key, Object vals) {
        if (vals != null) {
            query.addCriteria(Criteria.where(key).in(vals));
        }
    }

    /**
     * 大于或等于
     *
     * @param query
     * @param key
     * @param val
     */
    public static void buildQueryForGte(Query query, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            query.addCriteria(Criteria.where(key).gte(val));
        }
    }

    public static void buildQueryForGte(Criteria criteria, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            criteria.and(key).gte(val);
        }
    }

    /**
     * 大于
     *
     * @param query
     * @param key
     * @param val
     */
    public static void buildQueryForGt(Query query, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            query.addCriteria(Criteria.where(key).gt(val));
        }
    }

    /**
     * 小于
     *
     * @param query
     * @param key
     * @param val
     */
    public static void buildQueryForLt(Query query, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            query.addCriteria(Criteria.where(key).lt(val));
        }
    }

    public static void buildQueryForLt(Criteria criteria, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            criteria.and(key).lt(val);
        }
    }

    public static void buildQueryOrArray(Query query, Criteria criteria, String[] keys, String val, LikeType likeType) {
        if(ArrayUtils.isEmpty(keys)){
            return;
        }
        Set<Criteria> criterias = new HashSet<>();

        String regex = getRegex(val, likeType);
        for (String key : keys) {
            criterias.add(Criteria.where(key).regex(regex));
        }
        criteria.orOperator(criterias.toArray(new Criteria[criterias.size()]));
    }

    /**
     * 大于等于开始时间，小于结束时间
     * 做非空判断
     * @param query
     * @param key
     * @param start
     * @param end
     */
    public static void buildQueryForStartAndTo(Query query, String key, Date start, Date end) {
        if (start != null && end != null) {
            buildQueryForGtAndLtDate(query, key, start, end);
        } else {
            buildQueryForGt(query, key, start);
            buildQueryForLt(query, key, end);
        }
    }

    /**
     * 大于等于开始时间，小于结束时间
     *
     * @param query
     * @param key
     * @param start
     * @param end
     */
    public static void buildQueryForGtAndLtDate(Query query, String key, Date start, Date end) {
        if (null != start && null != end) {
            query.addCriteria(Criteria.where(key).gte(start).lt(end));
        }
    }

    /**
     * 大于date
     *
     * @param query
     * @param key
     * @param date
     */
    public static void buildQueryForGtDate(Query query, String key, Date date) {
        if (null != date) {
            query.addCriteria(Criteria.where(key).gt(date));
        }
    }

    /**
     * 小于date
     *
     * @param query
     * @param key
     * @param date
     */
    public static void buildQueryForLtDate(Query query, String key, Date date) {
        if (null != date) {
            query.addCriteria(Criteria.where(key).lt(date));
        }
    }

    /**
     * 数组字段 查询其中的单个元素
     *
     * @param query
     * @param key
     * @param val
     */
    public static void buildQueryForAll(Query query, String key, Serializable val) {
        if (val != null && StringUtils.isNotBlank(val + "")) {
            query.addCriteria(Criteria.where(key).all(val));
        }
    }

    private static String getRegex(String val, LikeType likeType) {
        String regex = null;
        if (LikeType.ALL.equals(likeType)) {
            regex = ".*" + val + ".*";
        } else if (LikeType.LEFT.equals(likeType)) {
            regex = ".*" + val;
        } else {
            regex = val + ".*";
        }
        return regex;
    }




    public enum LikeType {
        ALL, LEFT, RIGHT
    }
}
