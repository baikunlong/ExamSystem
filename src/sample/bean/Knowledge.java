package sample.bean;

/**
 * @author baikunlong
 * @date 2019/12/9 21:06
 */
public class Knowledge {
    /**
     * 知识点内容
     */
    private String kContent;

    @Override
    public String toString() {
        return "Knowledge{" +
                "kContent='" + kContent + '\'' +
                '}';
    }

    public String getkContent() {
        return kContent;
    }

    public void setkContent(String kContent) {
        this.kContent = kContent;
    }

    public Knowledge() {
    }

    public Knowledge(String kContent) {
        this.kContent = kContent;
    }
}
