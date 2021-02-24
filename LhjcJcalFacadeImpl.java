package com.lnlic.credit.lhjc.facade.provider.impl;

import com.lnlic.credit.lhjc.facade.provider.LhjcJcalFacade;
import com.lnlic.credit.lhjc.model.LhjcJcal;
import com.lnlic.credit.lhjc.service.LhjcJcalManager;
import com.lnlic.credit.lhjc.vo.query.LhjcJcalQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxblkQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxredQuery;
import org.hlj.framework.core.base.BaseManager;
import org.hlj.framework.core.base.EntityDao;
import org.hlj.framework.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 联合奖惩-联合奖惩案例
 *
 * @author xiao
 * @create 2019-07-23 10:15
 */
@Service("lhjcJcalFacade")
public class LhjcJcalFacadeImpl extends BaseManager<LhjcJcal, String> implements LhjcJcalFacade {

    @Autowired
    private LhjcJcalManager lhjcJcalManager;

    @Override
    protected EntityDao getEntityDao() {
        return lhjcJcalManager.getEntityDao();
    }

    @Override
    public Page<LinkedHashMap> findLhcjPage(LhjcJcdxblkQuery query) {
        return lhjcJcalManager.findLhcjPage(query);
    }

    @Override
    public Page<LinkedHashMap> findKsslhjcbmPage(Map query) {
        return lhjcJcalManager.findKsslhjcbmPage(query);
    }

    @Override
    public Page<LinkedHashMap> findLhjlPage(LhjcJcdxredQuery query) {
        return lhjcJcalManager.findLhjlPage(query);
    }

    @Override
    public List<LinkedHashMap> findJcalList(LhjcJcalQuery query){
        return lhjcJcalManager.findJcalList(query);
    }

    @Override
    public Page<LinkedHashMap> findJcalPage(LhjcJcalQuery query) {
        return lhjcJcalManager.findJcalPage(query);
    }

    @Override
    public LinkedHashMap getEntityById(String id) {
        return lhjcJcalManager.getEntityById(id);
    }

    @Override
    public List<LinkedHashMap> findLhcjList4WS(LhjcJcdxblkQuery query) {
        return lhjcJcalManager.findLhcjList4WS(query);
    }

    @Override
    public List<LinkedHashMap> findLhjlList4WS(LhjcJcdxredQuery query) {
        return lhjcJcalManager.findLhjlList4WS(query);
    }

    @Override
    public int updateDeleteJcal(String[] ids) {
        return lhjcJcalManager.updateDeleteJcal(ids);
    }

}
