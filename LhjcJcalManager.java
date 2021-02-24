package com.lnlic.credit.lhjc.service;

import com.alibaba.fastjson.JSON;
import com.lnlic.credit.common.core.constant.LhjcConstant;
import com.lnlic.credit.common.core.util.IdCard;
import com.lnlic.credit.lhjc.dao.LhjcJcalDao;
import com.lnlic.credit.lhjc.dao.LhjcJcalQueryDao;
import com.lnlic.credit.lhjc.model.LhjcJcal;
import com.lnlic.credit.lhjc.vo.query.LhjcJcalQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxblkQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxredQuery;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hlj.framework.core.base.BaseManager;
import org.hlj.framework.core.base.EntityDao;
import org.hlj.framework.core.file.sql.SqlFileUtil;
import org.hlj.framework.core.page.Page;
import org.hlj.framework.core.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 描述:
 * 联合奖惩-奖惩案例Manager
 * @author xiao
 * @create 2019-07-23 10:20
 */
@Service
@Transactional
public class LhjcJcalManager extends BaseManager<LhjcJcal, String> {


    private final Logger logger = LoggerFactory.getLogger(LhjcJcalManager.class);

    @Autowired
    private LhjcJcalDao lhjcJcalDao;

    @Autowired
    private LhjcJcalQueryDao lhjcJcalQueryDao;

    @Override
    public EntityDao getEntityDao() {
        return this.lhjcJcalDao;
    }

    /**
     * 联合惩戒（法人/自然人）page
     * @param query
     * @return
     */
    public Page<LinkedHashMap> findLhcjPage(LhjcJcdxblkQuery query){
        logger.debug("LhjcJcalManager findLhcjPage 参数 query：{}", query);

        String sql = SqlFileUtil.getMethodSql(LhjcJcdxblkManager.class, SqlFileUtil.getMethodUrl());

        Map<String, Object> conditions = JSON.parseObject(JSON.toJSONString(query), Map.class);
        Page<LinkedHashMap> page = lhjcJcalQueryDao.pageSqlQuery(sql, conditions, query);
        if (!CollectionUtils.isEmpty(page.getResult())) {
            for (LinkedHashMap item : page.getResult()) {
                if (item.containsKey("ztlb") && item.containsKey("zjhm")) {
                    if (!org.springframework.util.StringUtils.isEmpty(item.get("ztlb")) && !org.springframework.util.StringUtils.isEmpty(item.get("zjhm"))) {
                        if (LhjcConstant.SubjectType2.NATURAL_PERSON.getValue() == Integer.parseInt(item.get("ztlb").toString())) {
                            item.put("zjhm", IdCard.idMask(IdCard.decode(item.get("zjhm").toString())));
                        }
                    }
                }

            }
        }
        return page;
    }

    /**
     * 实施联合奖惩部门page
     * @param query
     * @return
     */
    public Page<LinkedHashMap> findKsslhjcbmPage(Map query){
        logger.debug("LhjcJcalManager findDigitlinkPage 参数 query：{}", query);

        String sql = SqlFileUtil.getMethodSql(LhjcJcdxblkManager.class, SqlFileUtil.getMethodUrl());

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(query.get("pageNumber").toString()));
        pageRequest.setPageSize(Integer.parseInt(query.get("pageSize").toString()));
        Page<LinkedHashMap> page = lhjcJcalQueryDao.pageSqlQuery(sql, query, pageRequest);
        return page;
    }

    /**
     * 联合惩戒（法人/自然人）page
     * @param query
     * @return
     */
    public Page<LinkedHashMap> findLhjlPage(LhjcJcdxredQuery query){
        logger.debug("LhjcJcalManager findLhjlPage 参数 query：{}", query);

        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());

        Map<String, Object> conditions = JSON.parseObject(JSON.toJSONString(query), Map.class);
        Page<LinkedHashMap> page = lhjcJcalQueryDao.pageSqlQuery(sql, conditions, query);
        if (!CollectionUtils.isEmpty(page.getResult())) {
            for (LinkedHashMap item : page.getResult()) {

                if (item.containsKey("ztlb") && item.containsKey("zjhm")) {
                    if (!org.springframework.util.StringUtils.isEmpty(item.get("ztlb")) && !org.springframework.util.StringUtils.isEmpty(item.get("zjhm"))) {
                        if (LhjcConstant.SubjectType2.NATURAL_PERSON.getValue() == Integer.parseInt(item.get("ztlb").toString())) {
                            item.put("zjhm", IdCard.idMask(IdCard.decode(item.get("zjhm").toString())));
                        }
                    }
                }


            }
        }
        return page;
    }



    /**
     * 联合奖惩-奖惩案例list
     * @param query
     * @return
     */
    public List<LinkedHashMap> findJcalList(LhjcJcalQuery query){
        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());
        Map<String, Object> conditions = JSON.parseObject(JSON.toJSONString(query), Map.class);
        List<LinkedHashMap> list = lhjcJcalQueryDao.findSqlAll(sql, conditions);
        return list;
    }

    /**
     * 联合奖惩-奖惩案例分页查询
     * @param query
     * @return
     */
    public Page<LinkedHashMap> findJcalPage(LhjcJcalQuery query) {
        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());
        Map<String, Object> conditions = JSON.parseObject(JSON.toJSONString(query), Map.class);
        Page<LinkedHashMap> page = lhjcJcalQueryDao.pageSqlQuery(sql, conditions, query);
        if (!CollectionUtils.isEmpty(page.getResult())) {
            for (LinkedHashMap item : page.getResult()) {
                // 如果是自然人， 对身份证解密加星号处理
                if (LhjcConstant.SubjectType2.NATURAL_PERSON.getValue() == Integer.parseInt(item.get("ztlb").toString())) {
                    item.put("ztwybs", IdCard.idMask(IdCard.decode(item.get("ztwybs").toString())));
                    break;
                }
            }
        }
        return page;
    }

    public LinkedHashMap getEntityById(String id) {
        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());
        Map<String, Object> conditions = new HashMap<>(16);
        conditions.put("id", id);
        LinkedHashMap item = lhjcJcalQueryDao.findSqlOne(sql, conditions);
        if (!CollectionUtils.isEmpty(item) && item.containsKey("ztwybs")) {
            // 如果是自然人， 对身份证解密加星号处理
            if (LhjcConstant.SubjectType2.NATURAL_PERSON.getValue() == Integer.parseInt(item.get("ztlb").toString())) {
                item.put("ztwybs", IdCard.idMask(IdCard.decode(item.get("ztwybs").toString())));
            }
        }
        return item;
    }


    /**
     * 联合奖惩-联合惩戒法人自然人列表
     * @param query
     * @return
     */
    public List<LinkedHashMap> findLhcjList4WS(LhjcJcdxblkQuery query){
        logger.debug("LhjcJcalManager findLhcjList4WS 参数 query：{}", query);

        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());
        Map<String, Object> conditions = JSON.parseObject(JSON.toJSONString(query), Map.class);
        List<LinkedHashMap> list = lhjcJcalQueryDao.findSqlAll(sql, conditions);
        logger.debug("LhjcJcalManager findLhcjList4WS 返回:{}", list);
        return list;
    }

    /**
     * 联合奖惩-联合激励法人自然人列表
     * @param query
     * @return
     */
    public List<LinkedHashMap> findLhjlList4WS(LhjcJcdxredQuery query){
        logger.debug("LhjcJcalManager findLhjlList4WS 参数 query：{}", query);

        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());
        Map<String, Object> conditions = JSON.parseObject(JSON.toJSONString(query), Map.class);
        List<LinkedHashMap> list = lhjcJcalQueryDao.findSqlAll(sql, conditions);
        logger.debug("LhjcJcalManager findLhjlList4WS 返回:{}", list);
        return list;
    }

    public int updateDeleteJcal(String[] ids) {
        logger.debug("LhjcJcalManager updateDeleteJcal 参数 query：{}", ids);
        String sql = SqlFileUtil.getMethodSql(this.getClass(), SqlFileUtil.getMethodUrl());
        int sqlUpdate = 0;
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            Map<String, Object> mapParams = new HashMap<String, Object>();
            mapParams.put("id",id);
            sqlUpdate = lhjcJcalQueryDao.executeSQLUpdate(sql, mapParams);
        }
        return sqlUpdate;
    }
}
