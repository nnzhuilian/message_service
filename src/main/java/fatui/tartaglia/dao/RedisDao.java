package fatui.tartaglia.dao;

/**
 * @author ：tartaglia
 * @description：redis接口
 * @date ：Created in 2021/6/30 下午4:50
 */
public interface RedisDao {

    /**
     * 获取列表长度
     * @param key 列表键
     * @return 列表长度
     */
    Long llen(String key);

    /**
     * 列表末插入数据
     *
     * @param key   键
     * @param value 值
     * @return 插入成功条数
     */
    Long rpush(String key, String value);

    /**
     * 列表头获取数据
     *
     * @param key 键
     * @return 值
     */
    String lpop(String key);

}
