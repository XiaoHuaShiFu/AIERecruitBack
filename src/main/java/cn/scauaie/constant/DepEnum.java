package cn.scauaie.constant;

/**
 * 描述: 校科联部门集合
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 2:44
 */
public enum DepEnum {
    BGS("办公室"), CHB("策划部"), CWB("财务部"), JSB("竞赛部"), SKB("社科部"), WLB("外联部"),
    XCB("宣传部"), XMB("项目部"), XWB("新闻部"), YYB("运营部"), ZKB("自科部");

    final String cn;

    DepEnum(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }
}
