package sample.bean;

/**
 * @author baikunlong
 * @date 2019/12/11 20:47
 */
public class BaseBean {
    /**
     * ID
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 是否被更改标记
     */
    private boolean isChange;

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }
}
