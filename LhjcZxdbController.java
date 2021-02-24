package com.lnlic.credit.web.platform.lhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lnlic.credit.common.core.base.BaseReturnMessage;
import com.lnlic.credit.common.core.constant.LhjcConstant;
import com.lnlic.credit.common.core.db.CodeHelper;
import com.lnlic.credit.common.core.util.IdCard;
import com.lnlic.credit.common.core.util.StringUtil;
import com.lnlic.credit.common.web.constant.SecurityConstant;
import com.lnlic.credit.common.web.util.JsonUtil;
import com.lnlic.credit.lhjc.facade.provider.*;
import com.lnlic.credit.lhjc.vo.query.*;
import com.lnlic.credit.security.vo.query.UserQuery;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hlj.framework.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 联合奖惩-执行督办子系统
 */
@Controller
@RequestMapping(value = "/lhjczxdb")
@Slf4j
public class LhjcZxdbController {

    // 联合惩戒（法人）督办列表页面
    private final String INDEX4FR_PATH = "/admin/lhjc/lhjczxdb/index4fr";
    // 联合惩戒（自然人）督办列表页面
    private final String INDEX4ZRR_PATH = "/admin/lhjc/lhjczxdb/index4zrr";
    // 联合惩戒 执行督办 详细页面
    private final String INDEX_DETAIL = "/admin/lhjc/lhjczxdb/detail";
    // 联合奖惩 奖惩案例 列表页面
    private final String INDEX4AL_PATH = "/admin/lhjc/lhjczxdb/index4al";

    @Autowired
    private LhjcZxdbFacade lhjcZxdbFacade;
    @Autowired
    private LhjcJcdxlxFacade lhjcJcdxlxFacade;
    @Autowired
    private LhjcCodeFacade lhjcCodeFacade;
    @Autowired
    private LhjcJcbwlFacade lhjcJcbwlFacade;
    @Autowired
    private LhjcJcalFacade lhjcJcalFacade;

    /**
     * 跳转到联合惩戒（法人）督办列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/index4fr", method = RequestMethod.POST)
    public String index4fr(ModelMap model, HttpServletRequest request) {
        UserQuery user = (UserQuery) request.getSession().getAttribute("user");
        if (user.isAdmin() || user.getUserRoleCodes().contains(SecurityConstant.ADMIN_CODE)) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("user", user);
        }
        //获取奖惩对象类型列表
        LhjcJcdxlxQuery lhjcJcdxlxQuery = new LhjcJcdxlxQuery();
        lhjcJcdxlxQuery.setBwllx(String.valueOf(LhjcConstant.Bwllx.LHCJ.getValue()));
        lhjcJcdxlxQuery.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        List<LinkedHashMap> blkList = lhjcJcdxlxFacade.findJcdxlxList(lhjcJcdxlxQuery);
        model.addAttribute("blkList", blkList);

        //获取关联备忘录下拉
        List<LinkedHashMap> glbwl = new LinkedList();
        LhjcJcbwlQuery query = new LhjcJcbwlQuery();
        query.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        glbwl = lhjcJcbwlFacade.findList(query);
        model.addAttribute("glbwl", glbwl);

        //获取主体类别列表
        LhjcCodeQuery lhjcCodeQuery = new LhjcCodeQuery();
        lhjcCodeQuery.setDlbm("80");
        List<LinkedHashMap> ztlbList = lhjcCodeFacade.findCodeList(lhjcCodeQuery);
        model.addAttribute("ztlbList", ztlbList);

        return INDEX4FR_PATH;
    }

    /**
     * 联合惩戒（自然人）督办列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/index4zrr", method = RequestMethod.POST)
    public String index4zrr(ModelMap model, HttpServletRequest request) {
        UserQuery user = (UserQuery) request.getSession().getAttribute("user");
        if (user.isAdmin() || user.getUserRoleCodes().contains(SecurityConstant.ADMIN_CODE)) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("user", user);
        }
        //获取奖惩对象类型列表
        LhjcJcdxlxQuery lhjcJcdxlxQuery = new LhjcJcdxlxQuery();
        lhjcJcdxlxQuery.setBwllx(String.valueOf(LhjcConstant.Bwllx.LHCJ.getValue()));
        lhjcJcdxlxQuery.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        List<LinkedHashMap> blkList = lhjcJcdxlxFacade.findJcdxlxList(lhjcJcdxlxQuery);
        model.addAttribute("blkList", blkList);

        //获取关联备忘录下拉
        List<LinkedHashMap> glbwl = new LinkedList();
        LhjcJcbwlQuery query = new LhjcJcbwlQuery();
        query.setFbzt(String.valueOf(LhjcConstant.Bwlfbzt.YES.getValue()));
        glbwl = lhjcJcbwlFacade.findList(query);
        model.addAttribute("glbwl", glbwl);

        //获取主体类别列表
        LhjcCodeQuery lhjcCodeQuery = new LhjcCodeQuery();
        lhjcCodeQuery.setDlbm("80");
        List<LinkedHashMap> ztlbList = lhjcCodeFacade.findCodeList(lhjcCodeQuery);
        model.addAttribute("ztlbList", ztlbList);

        return INDEX4ZRR_PATH;
    }

    /**
     * 执行督办列表（法人）
     * @param query
     * @return
     */
    @RequestMapping(value = "/search4fr", method = RequestMethod.POST)
    @ResponseBody
    public String search4fr(LhjcJcdxblkQuery query, HttpServletRequest request) {

        JSONObject result = new JSONObject();
        Page<LinkedHashMap> page = lhjcZxdbFacade.findLhcjFrZxdbPage(query);

        result.put("rows", page.getResult());
        result.put("total", page.getTotalCount());
        return result.toJSONString();

    }

    /**
     * 执行督办列表（自然人）
     * @param query
     * @return
     */
    @RequestMapping(value = "/search4zrr", method = RequestMethod.POST)
    @ResponseBody
    public String search4zrr(LhjcJcdxblkQuery query, HttpServletRequest request) {

        JSONObject result = new JSONObject();
        Page<LinkedHashMap> page = lhjcZxdbFacade.findLhcjZrrZxdbPage(query);

        result.put("rows", page.getResult());
        result.put("total", page.getTotalCount());
        return result.toJSONString();

    }

    /**
     * 执行督办-法人详细
     * @return
     */
    @RequestMapping(value = "/detailFr")
    public ModelAndView detailFr(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        String ztmc = request.getParameter("ztmc");
        String tyshxydm = request.getParameter("tyshxydm");
        String mlid = request.getParameter("mlid");
        String rdrq = request.getParameter("rdrq");
        Map<String, Object> conditions = new HashMap<>(16);
        conditions.put("ztmc", ztmc);
        conditions.put("tyshxydm", tyshxydm);
        conditions.put("mlid", mlid);
        conditions.put("rdrq", rdrq);

        List<LinkedHashMap> lhcjZxdbDetailList = lhjcZxdbFacade.findLhcjZxdbDetailList(conditions);
        if (!CollectionUtils.isEmpty(lhcjZxdbDetailList)) {
            view.addObject("lhcjZxdbDetailList", lhcjZxdbDetailList.get(0));
        }

        view.setViewName(INDEX_DETAIL);


        return view;
    }

    /**
     * 执行督办-自然人详细
     * @return
     */
    @RequestMapping(value = "/detailZrr")
    public ModelAndView detailZrr(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        String ztmc = request.getParameter("ztmc");
        String zjhm = request.getParameter("zjhm");
        String mlid = request.getParameter("mlid");
        String rdrq = request.getParameter("rdrq");
        Map<String, Object> conditions = new HashMap<>(16);
        conditions.put("ztmc", ztmc);
        conditions.put("zjhm", IdCard.encode(zjhm));
        conditions.put("mlid", mlid);
        conditions.put("rdrq", rdrq);

        List<LinkedHashMap> lhcjZxdbDetailList = lhjcZxdbFacade.findLhcjZxdbDetailList(conditions);

        if (!CollectionUtils.isEmpty(lhcjZxdbDetailList)) {
            view.addObject("lhcjZxdbDetailList", lhcjZxdbDetailList.get(0));
        }



        view.setViewName(INDEX_DETAIL);

        return view;
    }


    /**
     * 联合奖惩-奖惩案例 page
     * @return
     */
    @RequestMapping(value = "/index4al")
    public ModelAndView index4al() {
        log.info("index4al 联合奖惩-奖惩案例  start");
        ModelAndView view = new ModelAndView();
        //获取主体类别列表
        LhjcCodeQuery lhjcCodeQuery = new LhjcCodeQuery();
        lhjcCodeQuery.setDlbm("80");
        List<LinkedHashMap> ztlbList = lhjcCodeFacade.findCodeList(lhjcCodeQuery);
        view.addObject("ztlbList", ztlbList);
        view.setViewName(INDEX4AL_PATH);
        log.info("index4al 联合奖惩-奖惩案例  end");

        return view;
    }

    /**
     * 联合奖惩-奖惩案例 search
     * @param query
     * @return
     */
    @RequestMapping(value = "/search4al", method = RequestMethod.POST)
    @ResponseBody
    public String search4al(LhjcJcalQuery query) {
        log.debug("LhjcZxdbController search4al start 参数: {}", query);

        JSONObject result = new JSONObject();
        Page<LinkedHashMap> page = lhjcJcalFacade.findJcalPage(query);

        List<LinkedHashMap> pageResult = page.getResult();
        for (int i = 0; i < pageResult.size(); i++) {
            LinkedHashMap map = pageResult.get(i);
            if ("自然人".equals(map.get("ztlb"))){
                String zjhm = map.get("ztwybs").toString();
                String tmzjhm = zjhm.substring(0, 6) + "********" + zjhm.substring(14);
                map.put("ztwybs", tmzjhm);
            }
            pageResult.set(i, map);
        }
        page.setResult(pageResult);

        result.put("rows", page.getResult());
        result.put("total", page.getTotalCount());
        log.debug("LhjcZxdbController search4al end 返回: {}", page);
        return result.toJSONString();

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@Validated @RequestBody JSONObject jsonobject){
        // 返回值对象
        BaseReturnMessage returnMessage = new BaseReturnMessage();

        String ids = (String) jsonobject.get("ids");
        String[] id = ids.split(",");
        int reportingsDissenttype = lhjcJcalFacade.updateDeleteJcal(id);

        if (reportingsDissenttype > 0) {
            returnMessage.setSuccess();
        } else {
            returnMessage.setError();
        }
        String result = JsonUtil.obj2json(returnMessage);
        return result;
    }



    /**
     * 根据查询条件导出督办清单
     * @param query
     */
    @RequestMapping(value = "/exportList")
    public void exportList(@RequestParam Map query, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/file/upload/lhjc/");
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = filePath + File.separator + CodeHelper.GetCode() + ".doc";
        String showName = "联合奖惩督办清单.doc";
        createDoc(fileName, query);

        File tempFile = new File(fileName);
        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            response.reset();
            response.setContentType("application/msword; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(showName.getBytes("GBK"), "ISO8859_1"));

            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(tempFile);
            StreamUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            //删除临时文件 流要先关闭才能删除掉文件
            if (tempFile.exists()) {
                boolean b = tempFile.delete();
                String s = "";
            }
        }

    }


    /**
     * 创建督办清单word文档
     * @param fileName
     */
    private void createDoc(String fileName, Map query) {

        List<LinkedHashMap> dataList = new ArrayList<>();

        //主体类别 1-法人 2-自然人
        String ztlb = query.get("ztlb").toString();


        //设置纸张的大小对象
        Rectangle rectangle = new Rectangle(PageSize.A4);
        // 创建word文档，并将纸张设置为横向
//        Document doc = new Document(rectangle.rotate());
        Document doc = new Document(rectangle);

        //标题字体
        RtfFont titleFont = new RtfFont("仿宋_GB2312", 12, Font.BOLD, Color.BLACK);
        RtfFont tableFont = new RtfFont("仿宋_GB2312", 10, Font.NORMAL, Color.BLACK);
        RtfFont rtfFont = new RtfFont("宋 体", 10, Font.NORMAL, Color.BLACK);

        try {
            //创建书写器
            RtfWriter2.getInstance(doc, new FileOutputStream(fileName));

            // 打开文档
            doc.open();

            //标题
            Paragraph title = new Paragraph(LhjcConstant.LHJC_ZXDB_DBQD_WORD_TITLE, titleFont);
            Paragraph p1 = new Paragraph(LhjcConstant.LHJC_ZXDB_DBQD_WORD_P1, rtfFont);
            //设置首行缩进
            p1.setFirstLineIndent(20);
            //居中
            title.setAlignment(Paragraph.ALIGN_CENTER);

            //添加标题
            doc.add(title);
            //添加一个空行
            doc.add(new Paragraph(StringUtils.EMPTY));
            //添加段落
            doc.add(p1);

            //表格
            Table table = null;
            //字段中文名
            String[] cncolumns = null;
            //字段英文属性名
            String[] encolumns = null;
            int[] width = null;
            String ids = query.get("ids").toString();
            if ("1".equals(ztlb)) {//法人
                if (StringUtil.stringNotEmpty(ids)) {
                    String[] idArray = ids.split(",");
                    List<LinkedHashMap> list = new ArrayList<>();
                    for (int i = 0; i < idArray.length; i++) {
                        query.put("id",idArray[i]);
                        list = lhjcZxdbFacade.findLhcjFrZxdbList(query);
                        dataList.addAll(list);
                    }
                }else {
                    dataList = lhjcZxdbFacade.findLhcjFrZxdbList(query);
                }
                cncolumns = LhjcConstant.LHJC_ZXDB_DBQD_WORD_TABLE_CNCOLUMN4FR.split(",");
                encolumns = LhjcConstant.LHJC_ZXDB_DBQD_WORD_TABLE_ENCOLUMN4FR.split(",");
                width = new int[cncolumns.length];
                for (int i = 0; i < width.length; i++) {
                    width[i] = Integer.parseInt(LhjcConstant.LHJC_ZXDB_DBQD_WORD_TABLE_WIDTH4FR.split(",")[i]);
                }

            } else if ("2".equals(ztlb)) {//自然人
                if (StringUtil.stringNotEmpty(ids)) {
                    String[] idArray = ids.split(",");
                    List<LinkedHashMap> list = new ArrayList<>();
                    for (int i = 0; i < idArray.length; i++) {
                        query.put("id",idArray[i]);
                        list = lhjcZxdbFacade.findLhcjZrrZxdbList(query);
                        dataList.addAll(list);
                    }
                }else {
                    dataList = lhjcZxdbFacade.findLhcjZrrZxdbList(query);
                }
                cncolumns = LhjcConstant.LHJC_ZXDB_DBQD_WORD_TABLE_CNCOLUMN4ZRR.split(",");
                encolumns = LhjcConstant.LHJC_ZXDB_DBQD_WORD_TABLE_ENCOLUMN4ZRR.split(",");
                width = new int[cncolumns.length];
                for (int i = 0; i < width.length; i++) {
                    width[i] = Integer.parseInt(LhjcConstant.LHJC_ZXDB_DBQD_WORD_TABLE_WIDTH4ZRR.split(",")[i]);
                }
            }

            table = getTable(cncolumns.length, width);
            for (int i = 0; i < cncolumns.length; i++) {
                //设置表头
                Cell cell = new Cell(new Paragraph(cncolumns[i], tableFont));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                // 设置垂直居中
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                // 设置水平居中
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (int i = 0; i < dataList.size(); i++) {

                for (String encolumnname : encolumns) {
                    if ("dbxq".equals(encolumnname)){
                        dataList.get(i).put("dbxq","未"+dataList.get(i).get("csmc"));
                    }
                    Cell cell = new Cell(new Paragraph(dataList.get(i).get(encolumnname) == null ? StringUtils.EMPTY : dataList.get(i).get(encolumnname).toString(), tableFont));

                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    // 设置垂直居中
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    // 设置水平居中
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

            }

            doc.add(table);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }

    /**
     * 获取一个Table对象
     * @param cols 列数
     * @param width 每列的宽度比例
     * @return
     * @throws DocumentException
     */
    public Table getTable(int cols, int[] width) throws DocumentException {
        Table table=new Table(cols);//列数必须设置，而行数则可以按照个人要求来决定是否需要设置
        //设置表格每列宽度比例
        table.setWidths(width);
        table.setWidth(100);//占页面宽度比例
        table.setAlignment(Element.ALIGN_CENTER);// 居中显示
        table.setAlignment(Element.ALIGN_MIDDLE);// 纵向居中显示
        table.setOffset(1f); //消除段落与表格之间的空行
//        table.setAutoFillEmptyCells(true);// 自动填满
//        table.setBorderColor(new Color(0, 125, 255));// 边框颜色
        table.setBorderWidth(1);// 边框宽度
        table.setPadding(3f);
        return table;
    }
}