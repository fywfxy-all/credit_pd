package com.lnlic.credit.web.platform.lhjc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lnlic.credit.common.core.base.BaseReturnMessage;
import com.lnlic.credit.common.core.constant.LhjcConstant;
import com.lnlic.credit.common.core.db.CodeHelper;
import com.lnlic.credit.common.web.constant.SecurityConstant;
import com.lnlic.credit.lhjc.facade.provider.LhjcJcbwlFacade;
import com.lnlic.credit.lhjc.facade.provider.LhjcJcdxlxFacade;
import com.lnlic.credit.lhjc.facade.provider.LhjcjcdxlxmlFacade;
import com.lnlic.credit.lhjc.model.LhjcJcdxlx;
import com.lnlic.credit.lhjc.model.Lhjcjcdxlxml;
import com.lnlic.credit.lhjc.vo.query.LhjcJcbwlQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcJcdxlxQuery;
import com.lnlic.credit.lhjc.vo.query.LhjcjcdxlxmlQuery;
import com.lnlic.credit.security.facade.provider.UserdepartmentFacade;
import com.lnlic.credit.security.vo.query.UserQuery;
import org.hlj.framework.core.modules.utils.BeanUtils;
import org.hlj.framework.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.hibernate.loader.custom.sql.SQLQueryReturnProcessor.log;

/**
 * 描述:
 * 联合奖惩-奖惩对象类型Controller
 *
 * @author xiao
 * @create 2019-06-24 15:33
 */
@Controller
@RequestMapping("/lhjcjcdxlx")
public class LhjcjcdxlxController {

    // 列表页面
    private final String INDEX_PATH = "/admin/lhjc/lhjcjcdx/lhjcjcdxlx/index";
    // 添加页面
    private final String NEW_PATH = "/admin/lhjc/lhjcjcdx/lhjcjcdxlx/new";
    // 编辑页面
    private final String EDIT_PATH = "/admin/lhjc/lhjcjcdx/lhjcjcdxlx/edit";

    @Autowired
    private LhjcJcdxlxFacade lhjcJcdxlxFacade;
    @Autowired
    private LhjcJcbwlFacade lhjcJcbwlFacade;
    @Autowired
    private UserdepartmentFacade userdepartmentFacade;
    @Autowired
    private LhjcjcdxlxmlFacade lhjcjcdxlxmlFacade;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView();
        UserQuery user = (UserQuery) request.getSession().getAttribute("user");
        if (user.isAdmin() || user.getUserRoleCodes().contains(SecurityConstant.ADMIN_CODE)) {
            view.addObject("isAdmin", true);
        } else {
            view.addObject("user", user);
        }

        List<LinkedHashMap> glbwl = new LinkedList();
        LhjcJcbwlQuery query = new LhjcJcbwlQuery();
        query.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        glbwl = lhjcJcbwlFacade.findList(query);
        view.addObject("glbwl", glbwl);

        view.setViewName(INDEX_PATH);


        return view;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String search(LhjcJcdxlxQuery query) throws Exception {
        log.debug("LhjcjcdxlxController search 参数 {}", query);

        JSONObject result = new JSONObject();

        Page page = lhjcJcdxlxFacade.findDxlxPage(query);

        result.put("rows", page.getResult());
        result.put("total", page.getTotalCount());

        return result.toJSONString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(ModelMap model) throws Exception {
        List<LinkedHashMap> glbwl = new LinkedList();
        LhjcJcbwlQuery query = new LhjcJcbwlQuery();
        query.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        glbwl = lhjcJcbwlFacade.findList(query);
        model.addAttribute("glbwl", glbwl);

        return NEW_PATH;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(ModelMap model, @RequestParam(value = "id", required = true) String id) throws Exception {
        List<LinkedHashMap> glbwl = new LinkedList();
        LhjcJcbwlQuery query = new LhjcJcbwlQuery();
        query.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        glbwl = lhjcJcbwlFacade.findList(query);
        model.addAttribute("glbwl", glbwl);
        LhjcJcdxlx lhjcJcdxlx = lhjcJcdxlxFacade.getById(id);
        String departmentname = userdepartmentFacade.getById(lhjcJcdxlx.getSsbm()).getUserdepartmentname();
        model.addAttribute("lhjcJcdxlx", lhjcJcdxlx);
        model.addAttribute("departmentname", departmentname);
        return EDIT_PATH;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(ModelMap model, @RequestParam(value = "id", required = true) String id) throws Exception {
        List<LinkedHashMap> glbwl = new LinkedList();
        LhjcJcbwlQuery query = new LhjcJcbwlQuery();
        query.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        glbwl = lhjcJcbwlFacade.findList(query);
        model.addAttribute("glbwl", glbwl);
        LhjcJcdxlx lhjcJcdxlx = lhjcJcdxlxFacade.getById(id);
        String departmentname = userdepartmentFacade.getById(lhjcJcdxlx.getSsbm()).getUserdepartmentname();
        model.addAttribute("lhjcJcdxlx", lhjcJcdxlx);
        model.addAttribute("departmentname", departmentname);

        // 3表示查看页面
        model.addAttribute("view", 3);
        return EDIT_PATH;
    }

    /**
     * 联合奖惩-奖惩对象类型 新增页面表单提交
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(LhjcJcdxlxQuery query) throws Exception {
        //region log4j日志信息
        log.debug("LhjcjcdxlxController add 参数 {}", query);
        // endregion

        BaseReturnMessage result = new BaseReturnMessage();
        result.setSuccess();

        LhjcJcdxlx lhjcJcdxlx = new LhjcJcdxlx();
        BeanUtils.copyProperties(lhjcJcdxlx, query);
        lhjcJcdxlx.setId(CodeHelper.GetCode());
        lhjcJcdxlx.setInserttime(new Date());

        lhjcJcdxlxFacade.save(lhjcJcdxlx);
        return JSON.toJSONString(result);
    }

    /**
     * 联合奖惩-奖惩对象类型 编辑页面表单提交
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestParam Map query) throws Exception {
        //region log4j日志信息
        log.debug("LhjcJcbwlController update 参数 {}", query);
        // endregion

        BaseReturnMessage result = new BaseReturnMessage();
        result.setSuccess();

        LhjcJcdxlx lhjcJcdxlx = lhjcJcdxlxFacade.getById(query.get("id").toString());
        query.put("inserttime", lhjcJcdxlx.getInserttime());
        BeanUtils.copyProperties(lhjcJcdxlx, query);
        lhjcJcdxlx.setId(query.get("id").toString());
        lhjcJcdxlxFacade.update(lhjcJcdxlx);

        return JSON.toJSONString(result);
    }

    /**
     * 联合奖惩-奖惩对象类型 编辑页面表单提交
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/updatecatalogcode", method = RequestMethod.POST)
    @ResponseBody
    public String updatecatalogcode(LhjcJcdxlxQuery query) {
        //region log4j日志信息
        log.debug("LhjcJcbwlController updatecatalogcode 参数 {}", query);
        // endregion
        BaseReturnMessage result = new BaseReturnMessage();
        result.setSuccess();
        LhjcJcdxlx lhjcJcdxlx = lhjcJcdxlxFacade.getById(query.getId());
//        lhjcJcdxlx.setMlywbm(query.getMlywbm());
        lhjcJcdxlxFacade.update(lhjcJcdxlx);
        return JSON.toJSONString(result);
    }

    /**
     * 联合奖惩-奖惩对象类型 删除
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public String del(LhjcJcdxlxQuery query) throws Exception {

        BaseReturnMessage result = new BaseReturnMessage();
        result.setSuccess();

        String[] ids = query.getIds().split(",");
        for (String id : ids) {
            lhjcJcdxlxFacade.removeById(id);
        }

        return JSON.toJSONString(result);
    }

    /**
     * 联合奖惩-奖惩对象类型关联目录
     *
     * @param jsonlist
     * @return
     */
    @RequestMapping(value = "/relation", method = RequestMethod.POST)
    @ResponseBody
    public String relation(String jsonlist, String jcdxlxid) throws Exception {

        BaseReturnMessage result = new BaseReturnMessage();
        result.setSuccess();
        JSONArray parse = JSONArray.parseArray(jsonlist);
        lhjcjcdxlxmlFacade.delJcdxlxmlByJcdxlxid(jcdxlxid);
        if (!CollectionUtils.isEmpty(parse)) {
            List<HashMap> list = JSONObject.parseArray(parse.toJSONString(), HashMap.class);
            for (HashMap map : list) {
                Lhjcjcdxlxml lhjcjcdxlxml = new Lhjcjcdxlxml();
                lhjcjcdxlxml.setId(CodeHelper.GetCode());
                lhjcjcdxlxml.setJcdxlxid((String) (map.get("jcdxlxid")));
                lhjcjcdxlxml.setBzbm((String) (map.get("bzbm")));
                lhjcjcdxlxml.setBzbzd((String) (map.get("bzbzd")));
                lhjcjcdxlxml.setGlbm((String) (map.get("glbm")));
                lhjcjcdxlxml.setGlbzd((String) (map.get("glbzd")));
                lhjcjcdxlxmlFacade.save(lhjcjcdxlxml);
            }
        }
        return JSON.toJSONString(result);
    }

    /**
     * 联合奖惩-奖惩对象类型关联目录页面初始化信息
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/relationinit", method = RequestMethod.POST)
    @ResponseBody
    public String relationinit(LhjcjcdxlxmlQuery query) throws Exception {
        List<LinkedHashMap> jcdxlxmlList = lhjcjcdxlxmlFacade.findJcdxlxmlList(query);

        return JSON.toJSONString(jcdxlxmlList);
    }
}
