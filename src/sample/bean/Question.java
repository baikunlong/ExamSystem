package sample.bean;

/**
 * @author baikunlong
 * @date 2019/12/9 21:08
 */
public class Question {
    /**
     * 题型：0-单选题、1-多选题、2-判断题、3-填空题等四种题型。
     */
    private int qType;
    /**
     * 题干
     */
    private String qContent;
    /**
     * 备选项若干（不超过5个），通过题型分配选项
     */
    private String answer;
    /**
     * 所属知识点
     */
    private String kContent;
    /**
     * 所属课程号
     */
    private String cNum;
    /**
     * 正确答案
     */
    private String rightAnswer;

    @Override
    public String toString() {
        return "Question{" +
                "qType=" + qType +
                ", qContent='" + qContent + '\'' +
                ", answer='" + answer + '\'' +
                ", kContent='" + kContent + '\'' +
                ", cNum='" + cNum + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                '}';
    }

    public int getqType() {
        return qType;
    }

    public void setqType(int qType) {
        this.qType = qType;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getkContent() {
        return kContent;
    }

    public void setkContent(String kContent) {
        this.kContent = kContent;
    }

    public String getcNum() {
        return cNum;
    }

    public void setcNum(String cNum) {
        this.cNum = cNum;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Question(int qType, String qContent, String answer, String kContent, String cNum, String rightAnswer) {
        this.qType = qType;
        this.qContent = qContent;
        this.answer = answer;
        this.kContent = kContent;
        this.cNum = cNum;
        this.rightAnswer = rightAnswer;
    }

    public Question() {
    }
}
