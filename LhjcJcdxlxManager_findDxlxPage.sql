select
  t.id,
  (select
    dept.userdepartmentname
  from xy_base.userdepartment dept
    where dept.userdepartmentcode=t.ssbm) ssbm,
  t.ssbm deptcode,
  t.jclxmc,
  t.jclxms,
  t.hhmdlx,
  (select
    bwl.bwlmc
  from lhjc_jcbwl bwl
    where bwl.id = t.glbwl and bwl.fbzt = '1') glbwl
  from lhjc_jcdxlx t
where 1=1
/~ and t.ssbm = {ssbm} ~/
/~ and t.jclxmc like '%' || {jclxmc} || '%' ~/
/~ and t.jclxms = {jclxms} ~/
/~ and t.glbwl = {glbwl} ~/
order by inserttime desc