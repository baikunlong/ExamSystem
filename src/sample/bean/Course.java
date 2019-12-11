package sample.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * @author baikunlong
 * @date 2019/12/9 21:03
 */
public class Course extends BaseBean{
    /**
     * 课程名称
     */
    private StringProperty cName;
    /**
     * 课程号
     */
    private StringProperty cNum;
    /**
     * 学分
     */
    private StringProperty cScore;
    /**
     * 知识点
     */
    private List<Knowledge>knowledges;

    @Override
    public String toString() {
        return "Course{" +
                "cName=" + cName +
                ", cNum=" + cNum +
                ", cScore=" + cScore +
                ", knowledges=" + knowledges +
                '}';
    }

    public String getcName() {
        return cNameProperty().get();
    }

    public StringProperty cNameProperty() {
        if(cName==null)cName=new SimpleStringProperty(this,"cName");
        return cName;
    }

    public void setcName(String cName) {
        this.cNameProperty().set(cName);
    }

    public String getcNum() {
        return cNumProperty().get();
    }

    public StringProperty cNumProperty() {
        if(cNum==null)cNum=new SimpleStringProperty(this,"cNum");
        return cNum;
    }

    public void setcNum(String cNum) {
        this.cNumProperty().set(cNum);
    }

    public String getcScore() {
        return cScoreProperty().get();
    }

    public StringProperty cScoreProperty() {
        if(cScore==null)cScore=new SimpleStringProperty(this,"cScore");
        return cScore;
    }

    public void setcScore(String cScore) {
        this.cScoreProperty().set(cScore);
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }

    public Course() {
    }

    public Course(StringProperty cName, StringProperty cNum, StringProperty cScore, List<Knowledge> knowledges) {
        this.cName = cName;
        this.cNum = cNum;
        this.cScore = cScore;
        this.knowledges = knowledges;
    }


//    /**
//     * 课程名称
//     */
//    private String cName;
//    /**
//     * 课程号
//     */
//    private String cNum;
//    /**
//     * 学分
//     */
//    private String cScore;
//    /**
//     * 知识点
//     */
//    private List<Knowledge>knowledges;
//
//    @Override
//    public String toString() {
//        return "Course{" +
//                "cName='" + cName + '\'' +
//                ", cNum='" + cNum + '\'' +
//                ", cScore='" + cScore + '\'' +
//                ", knowledges=" + knowledges +
//                '}';
//    }
//
//    public String getcName() {
//        return cName;
//    }
//
//    public void setcName(String cName) {
//        this.cName = cName;
//    }
//
//    public String getcNum() {
//        return cNum;
//    }
//
//    public void setcNum(String cNum) {
//        this.cNum = cNum;
//    }
//
//    public String getcScore() {
//        return cScore;
//    }
//
//    public void setcScore(String cScore) {
//        this.cScore = cScore;
//    }
//
//    public List<Knowledge> getKnowledges() {
//        return knowledges;
//    }
//
//    public void setKnowledges(List<Knowledge> knowledges) {
//        this.knowledges = knowledges;
//    }
//
//    public Course() {
//    }
//
//    public Course(String cName, String cNum, String cScore, List<Knowledge> knowledges) {
//        this.cName = cName;
//        this.cNum = cNum;
//        this.cScore = cScore;
//        this.knowledges = knowledges;
//    }
}
