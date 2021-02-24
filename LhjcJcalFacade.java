package com.lnlic.credit.lhjc.facade.provider;

import com.lnlic.credit.common.core.base.BaseManagerInterface;
import com.lnlic.credit.lhjc.model.LhjcJcal;
import com.lnlic.credit.lhjc.vo.query.LhjcJcalQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxblkQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxredQuery;
import org.hlj.framework.core.page.Page;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 联合奖惩-奖惩案例
 *
 * @author xiao
 * @create 2019-07-23 10:12
 */
public interface LhjcJcalFacade extends BaseManagerInterface<LhjcJcal, String> {

    Page<LinkedHashMap> findLhcjPage(LhjcJcdxblkQuery query);

    Page<LinkedHashMap> findKsslhjcbmPage(Map query);

    Page<LinkedHashMap> findLhjlPage(LhjcJcdxredQuery query);

    List<LinkedHashMap> findJcalList(LhjcJcalQuery query);

    Page<LinkedHashMap> findJcalPage(LhjcJcalQuery query);

    LinkedHashMap getEntityById(String id);

    List<LinkedHashMap> findLhcjList4WS(LhjcJcdxblkQuery query);

    List<LinkedHashMap> findLhjlList4WS(LhjcJcdxredQuery query);

    /**
     * 修改删除状态
     *
     * @param ids
     * @return
     */
    int updateDeleteJcal(String[] ids);
}

