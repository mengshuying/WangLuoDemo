package utils;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "child_info",onCreated = "CREATE UNIQUE INDEX realative_unique ON child_info(uri,title)")
public class ChildInfo{
    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id",isId=true,autoGen=true)
    public int id;
    @Column(name = "uri")
    public String uri;
    @Column(name = "title")
    public String title;
    @Column(name = "zhangtai")
    public String zhangtai;//状态
    @Override
    public String toString(){
        return "ChildInfo{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}