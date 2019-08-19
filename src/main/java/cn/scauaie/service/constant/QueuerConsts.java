package cn.scauaie.service.constant;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-20 2:22
 */
public final class QueuerConsts {

    /**
     * 每个人预计等待时间
     */
    public static final int GAP = 3;

    /**
     * 每个面试官出队一个人的间隔时间的key名前缀
     */
    public static final String KEY_PREFIX_QUEUE_DEL_GAP = "queue:gap:";

    /**
     * 每个面试官出队一个人的间隔时间
     */
    public static final int QUEUE_DEL_GAP = 300;

}
