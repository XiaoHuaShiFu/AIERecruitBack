package cn.scauaie.model.dao;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 21:54
 */
public class DepNumberDO {

    String dep;
    Integer number;

    public DepNumberDO() {
    }

    public DepNumberDO(String dep, Integer number) {
        this.dep = dep;
        this.number = number;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DepNumberDO{" +
                "dep='" + dep + '\'' +
                ", number=" + number +
                '}';
    }
}
