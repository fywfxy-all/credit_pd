package com.lnlic.credit.lhjc.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hlj.framework.core.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 联合奖惩-奖惩对象红名单
 * Created by lnlic_guozw on 2019/6/19.
 */
@Data
@Entity
@Table(name = "lhjc_jcdx_red")
public class LhjcJcdxred extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6170614222016477723L;


    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "assigned")
    @Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 36)
    private String id;//主键

    private String ztlb;//主体类别   见字典表

    private String mdlxid;//名单类型id 见lhjc_jcdxlx

    private String ztmc;//主体名称

    private String tyshxydm;//统一社会信用代码

    private String fddbr;//法定代表人

    private String zjhm;//证件号码

    private String rdbm;//认定部门

    private String rdyj;//认定依据

    private String rdrq;//认定日期

    private String yxqz;//有效期止

    private String lrmdsy;//列入名单事由

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inserttime;//插入时间

}
