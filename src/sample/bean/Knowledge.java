package sample.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author baikunlong
 * @date 2019/12/9 21:06
 */
public class Knowledge extends BaseBean{
    /**
     * 知识点内容
     */
    private StringProperty kContent;
    /**
     * 所属课程
     */
    private StringProperty kCourse;

    public String getkContent() {
        return kContentProperty().get();
    }

    public StringProperty kContentProperty() {
        if(kContent==null)kContent=new SimpleStringProperty(this,"kContent");
        return kContent;
    }

    public void setkContent(String kContent) {
        kContentProperty().set(kContent);
    }

    public String getkCourse() {
        return kCourseProperty().get();
    }

    public StringProperty kCourseProperty() {
        if(kCourse==null)kCourse=new SimpleStringProperty(this,"kCourse");
        return kCourse;
    }

    public void setkCourse(String kCourse) {
        kCourseProperty().set(kCourse);
    }

    @Override
    public String toString() {
        return "Knowledge{" +
                "kContent=" + kContent +
                ", kCourse=" + kCourse +
                '}';
    }

    public Knowledge() {
    }

    public Knowledge(StringProperty kContent, StringProperty kCourse) {
        this.kContent = kContent;
        this.kCourse = kCourse;
    }
}
