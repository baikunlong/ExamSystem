package sample.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author baikunlong
 * @date 2019/12/9 21:08
 */
public class Question extends BaseBean{

    /**
     * 题型：0-单选题、1-多选题、2-判断题、3-填空题等四种题型。
     * 时间不够，全部改String
     */
    private StringProperty qType;
    /**
     * 题干
     */
    private StringProperty qContent;
    /**
     * 备选项若干（不超过5个），通过题型分配选项
     */
    private StringProperty answer;
    /**
     * 所属知识点
     */
    private StringProperty kContent;
    /**
     * 所属课程号
     */
    private StringProperty cNum;
    /**
     * 正确答案
     */
    private StringProperty rightAnswer;

    @Override
    public String toString() {
        return "Question{" +
                "qType=" + qType +
                ", qContent=" + qContent +
                ", answer=" + answer +
                ", kContent=" + kContent +
                ", cNum=" + cNum +
                ", rightAnswer=" + rightAnswer +
                '}';
    }

    public String getqType() {
        return qTypeProperty().get();
    }

    public StringProperty qTypeProperty() {
        if(qType==null)qType=new SimpleStringProperty(this,"qType");
        return qType;
    }

    public void setqType(String qType) {
        qTypeProperty().set(qType);
    }

    public String getqContent() {
        return qContentProperty().get();
    }

    public StringProperty qContentProperty() {
        if(qContent==null)qContent=new SimpleStringProperty(this,"qContent");

        return qContent;
    }

    public void setqContent(String qContent) {
        qContentProperty().set(qContent);
    }

    public String getAnswer() {
        return answerProperty().get();
    }

    public StringProperty answerProperty() {
        if(answer==null)answer=new SimpleStringProperty(this,"answer");

        return answer;
    }

    public void setAnswer(String answer) {
        answerProperty().set(answer);
    }

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

    public String getcNum() {
        return cNumProperty().get();
    }

    public StringProperty cNumProperty() {
        if(cNum==null)cNum=new SimpleStringProperty(this,"cNum");

        return cNum;
    }

    public void setcNum(String cNum) {
        cNumProperty().set(cNum);
    }

    public String getRightAnswer() {
        return rightAnswerProperty().get();
    }

    public StringProperty rightAnswerProperty() {
        if(rightAnswer==null)rightAnswer=new SimpleStringProperty(this,"rightAnswer");

        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        rightAnswerProperty().set(rightAnswer);
    }

    public Question() {
    }

    public Question(StringProperty qType, StringProperty qContent, StringProperty answer, StringProperty kContent, StringProperty cNum, StringProperty rightAnswer) {
        this.qType = qType;
        this.qContent = qContent;
        this.answer = answer;
        this.kContent = kContent;
        this.cNum = cNum;
        this.rightAnswer = rightAnswer;
    }
}
