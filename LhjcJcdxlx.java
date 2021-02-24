package com.lnlic.credit.lhjc.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hlj.framework.core.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 联合奖惩-奖惩对象类型
 * Created by lnlic_guozw on 2019/6/19.
 */
@Data
@Entity
@Table(name = "lhjc_jcdxlx")
public class LhjcJcdxlx extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6389503570953644254L;

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "assigned")
    @Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 36)
    private String id;//主键

    private String ssbm;//所属部门

    private String jclxmc;//奖惩类型名称

    private String jclxms;//奖惩类型描述

    private String glbwl;//关联备忘录id

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inserttime;//插入时间

    private String hhmdlx;  //红黑名单类型

//    private String catalogcode;		//关联目录编码 对应catalogmanager.catalog表的主键
//    private String mlywbm;		//目录英文表名

}
