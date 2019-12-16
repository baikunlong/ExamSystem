package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.bean.Course;
import sample.bean.Knowledge;
import sample.bean.Question;
import sample.common.Common;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author baikunlong
 * @date 2019/12/13 14:08
 */
public class ExamController implements Initializable {

    public Button btnStart;
    public VBox vbox;

    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private final ObservableList<Knowledge> knowledges = FXCollections.observableArrayList();
    private final ObservableList<Question> questions = FXCollections.observableArrayList();
    private final ObservableList<Question> errorQuestions = FXCollections.observableArrayList();
    public ChoiceBox<String> cbCourse;
    public ChoiceBox<String> cbSingle;
    public ChoiceBox<String> cbMulti;
    public ChoiceBox<String> cbJudge;
    public ChoiceBox<String> cbCompletion;
    private Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //获取所有数据
        initData();

        btnStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = Common.setConfirmationDialog("确认", "确定开始考试吗？");
                if (!"确定".equals(alert.getResult().getText())) {
                    return;
                }
                //todo 有时间改成随机
                //重新生成试卷
                for (int i = 0; i < questions.size(); i++) {
                    //清空题目状态
                    questions.get(i).setUserAnswer("");
                    questions.get(i).setExam(false);
                    //设置题目序号
                    questions.get(i).setOrder(0);
                }
                //清空上次试卷
                vbox.getChildren().clear();
                //生成试卷
                generatePage();
            }
        });

//        for (int i = 0; i < 100; i++) {
//            vbox.getChildren().add(new Label("顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶"));
//        }
//        ToggleGroup group = new ToggleGroup();
//        RadioButton button1 = new RadioButton("asd");
//        button1.setToggleGroup(group);
//        button1.setSelected(true);
//        RadioButton button2 = new RadioButton("select second");
//        button2.setToggleGroup(group);
//
//        vbox.getChildren().add(button1);
//        vbox.getChildren().add(button2);


    }

    private void generatePage() {
        Integer single = Integer.valueOf(cbSingle.getValue());
        Integer multi = Integer.valueOf(cbMulti.getValue());
        Integer judge = Integer.valueOf(cbJudge.getValue());
        Integer completion = Integer.valueOf(cbCompletion.getValue());
        //总共的题目数量，用于计算分数
        int sum = single + multi + judge + completion;
        if(sum==0){
            Common.setAlert("警告","至少一道题");
            return;
        }
        //用于记录题目序号
        int index = 0;

        //生成单选题
        if (single > 0) {
            Label e = new Label("*******************************单选题*************************************");
            e.setFont(new Font("微软雅黑",20));
            vbox.getChildren().add(e);
        }
        Random random = new Random();
        //这一轮是否添加了题目的标志
        boolean isAdd;

        for (int i = 0; i < single; i++) {
            isAdd=false;
            for (int j = 0; j < questions.size(); j++) {
                Question question = questions.get(j);
                //要求单选题，课程类型对应，考试状态为false才可以添加
                if (question.getqType().equals("单选题")&&question.getcNum().equals(cbCourse.getValue())&&!question.isExam()) {
                    //随机添加
                    if(random.nextInt(10)<5){
                        continue;
                    }
                    addSingle(++index, question);
                    //设置状态
                    question.setExam(true);
                    //设置题目序号
                    question.setOrder(index);
                    //设置为已经添加题目
                    isAdd=true;
                    break;
                }
            }
            if(!isAdd){
                i--;
            }
        }

        //生成多选题
        if (multi > 0) {
            Label e = new Label("*******************************多选题*************************************");
            e.setFont(new Font("微软雅黑",20));
            vbox.getChildren().add(e);
        }
        for (int i = 0; i < multi; i++) {
            isAdd=false;
            for (int j = 0; j < questions.size(); j++) {
                Question question = questions.get(j);
                //要求多选题，课程类型对应，考试状态为false才可以添加
                if (question.getqType().equals("多选题")&&question.getcNum().equals(cbCourse.getValue())&&!question.isExam()) {
                    //随机添加
                    if(random.nextInt(10)<5){
                        continue;
                    }
                    addMulti(++index, question);
                    //设置状态
                    question.setExam(true);
                    //设置题目序号
                    question.setOrder(index);
                    //设置为已经添加题目
                    isAdd=true;
                    break;
                }
            }
            if(!isAdd){
                i--;
            }
        }

        //生成判断题
        if (judge > 0) {
            Label e = new Label("*******************************判断题*************************************");
            e.setFont(new Font("微软雅黑",20));
            vbox.getChildren().add(e);
        }
        for (int i = 0; i < judge; i++) {
            isAdd=false;
            for (int j = 0; j < questions.size(); j++) {
                Question question = questions.get(j);
                //要求多选题，课程类型对应，考试状态为false才可以添加
                if (question.getqType().equals("判断题")&&question.getcNum().equals(cbCourse.getValue())&&!question.isExam()) {
                    //随机添加
                    if(random.nextInt(10)<5){
                        continue;
                    }
                    addJudge(++index, question);
                    //设置状态
                    question.setExam(true);
                    //设置题目序号
                    question.setOrder(index);
                    //设置为已经添加题目
                    isAdd=true;
                    break;
                }
            }
            if(!isAdd){
                i--;
            }
        }

        //生成填空题
        if (completion > 0) {
            Label e = new Label("*******************************填空题*************************************");
            e.setFont(new Font("微软雅黑",20));
            vbox.getChildren().add(e);
        }
        for (int i = 0; i < completion; i++) {
            isAdd=false;
            for (int j = 0; j < questions.size(); j++) {
                Question question = questions.get(j);
                //要求多选题，课程类型对应，考试状态为false才可以添加
                if (question.getqType().equals("填空题")&&question.getcNum().equals(cbCourse.getValue())&&!question.isExam()) {
                    //随机添加
                    if(random.nextInt(10)<5){
                        continue;
                    }
                    addCompletion(++index, question);
                    //设置状态
                    question.setExam(true);
                    //设置题目序号
                    question.setOrder(index);
                    //设置为已经添加题目
                    isAdd=true;
                    break;
                }
            }
            if(!isAdd){
                i--;
            }
        }


        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(new Label(""));
        //添加提交按钮
        Button submit = new Button("提交");
        submit.setMinSize(100,40);
        vbox.getChildren().add(submit);
        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(new Label(""));
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = Common.setConfirmationDialog("确认信息", "确定提交吗？");
                ButtonType result = alert.getResult();
                if ("确定".equals(result.getText())) {
                    //评分
                    correct(sum);
                }
            }
        });
    }

    /**
     * 评分
     *
     * @param sum 当前试卷的题目数量
     */
    private void correct(int sum) {
        //平均每题的分数
        int score = 100;
        int avg = score / sum;
        errorQuestions.clear();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (question.isExam()) {
                switch (question.getqType()) {
                    case "多选题":
                        if (!Common.canPerm(question.getRightAnswer(), question.getUserAnswer())) {
                            score -= avg;
                            errorQuestions.add(question);
                        }
                        break;
                    default:
                        if (!question.getRightAnswer().equals(question.getUserAnswer())) {
                            score -= avg;
                            errorQuestions.add(question);
                        }
                        break;
                }
            }
        }
        //当除不尽时，会出现如果全错也是一分，所以得判断是否全错
        if (errorQuestions.size() == sum) {
            score = 0;
        }
        //生成错题解析
        generateErrorPage(score);

    }

    /**
     * 错题解析页
     * @param score
     */
    private void generateErrorPage(int score) {
        vbox.getChildren().clear();
        Label label = new Label("总分100分，你的得分：" + score+"分");
        label.setFont(new Font("微软雅黑",32));
        vbox.getChildren().add(label);
        for (int i = 0; i < errorQuestions.size(); i++) {
            Question question = errorQuestions.get(i);
            Label e = new Label(question.getOrder()+"、"+question.getqContent());
            e.setFont(new Font("微软雅黑",18));
            vbox.getChildren().add(e);
            if("单选题".equals(question.getqType()) || "多选题".equals(question.getqType())){
                vbox.getChildren().add(new Label("备选答案："+question.getAnswer()));
            }
            Label e1 = new Label("你的答案：" + question.getUserAnswer());
            e1.setTextFill(Color.RED);
            vbox.getChildren().add(e1);
            Label e2 = new Label("正确答案：" + question.getRightAnswer());
            e2.setTextFill(Color.GREEN);
            vbox.getChildren().add(e2);
            vbox.getChildren().add(new Label(""));
        }
    }

    private void addJudge(int index, Question question) {
        Label e = new Label(index + "、" + question.getqContent());
        e.setFont(new Font("微软雅黑",18));
        vbox.getChildren().add(e);
        ToggleGroup group = new ToggleGroup();
        RadioButton buttonY = new RadioButton("对");
        RadioButton buttonN = new RadioButton("错");
        //默认选中对
//        buttonY.setSelected(true);
        buttonY.setToggleGroup(group);
        buttonN.setToggleGroup(group);

        vbox.getChildren().add(buttonY);
        vbox.getChildren().add(buttonN);

        //添加一行空行
        vbox.getChildren().add(new Label(""));

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                question.setUserAnswer(((RadioButton) newValue).getText());
            }
        });
    }

    private void addCompletion(int index, Question question) {
        Label e = new Label(index + "、" + question.getqContent());
        e.setFont(new Font("微软雅黑",18));
        vbox.getChildren().add(e);
        TextField textField = new TextField();
        vbox.getChildren().add(textField);
        //添加一行空行
        vbox.getChildren().add(new Label(""));
        //设置填空题答案
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                question.setUserAnswer(textField.getText());
            }
        });
    }

    private void addMulti(int index, Question question) {
        Label e = new Label(index + "、" + question.getqContent());
        e.setFont(new Font("微软雅黑",18));
        vbox.getChildren().add(e);
        String answer = question.getAnswer();
        CheckBox checkBoxA = new CheckBox(answer.substring(answer.indexOf("A."), answer.indexOf("B.")));
        CheckBox checkBoxB = new CheckBox(answer.substring(answer.indexOf("B."), answer.indexOf("C.")));
        CheckBox checkBoxC = new CheckBox(answer.substring(answer.indexOf("C."), answer.indexOf("D.")));
        CheckBox checkBoxD = new CheckBox(answer.substring(answer.indexOf("D.")));
        vbox.getChildren().add(checkBoxA);
        vbox.getChildren().add(checkBoxB);
        vbox.getChildren().add(checkBoxC);
        vbox.getChildren().add(checkBoxD);
        //添加一行空行
        vbox.getChildren().add(new Label(""));

        ArrayList<String> multiAnswer = new ArrayList<>();
        //记录多选题答案
        checkBoxA.selectedProperty().addListener(getBooleanChangeListener(checkBoxA, multiAnswer, question));
        checkBoxB.selectedProperty().addListener(getBooleanChangeListener(checkBoxB, multiAnswer, question));
        checkBoxC.selectedProperty().addListener(getBooleanChangeListener(checkBoxC, multiAnswer, question));
        checkBoxD.selectedProperty().addListener(getBooleanChangeListener(checkBoxD, multiAnswer, question));
    }

    private ChangeListener<Boolean> getBooleanChangeListener(CheckBox checkBox, ArrayList<String> multiAnswer, Question question) {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    multiAnswer.add(checkBox.getText().substring(0, 1));
                } else {
                    multiAnswer.remove(checkBox.getText().substring(0, 1));
                }
                StringBuilder ss = new StringBuilder();
                for (int i = 0; i < multiAnswer.size(); i++) {
                    ss.append(multiAnswer.get(i));
                }
                question.setUserAnswer(ss.toString());
            }
        };
    }

    /**
     * 生成一道单项选择题
     *
     * @param index    题目序号
     * @param question 问题题干
     */
    private void addSingle(int index, Question question) {
        Label e = new Label(index + "、" + question.getqContent());
        e.setFont(new Font("微软雅黑",18));
        vbox.getChildren().add(e);
        String answer = question.getAnswer();
        ToggleGroup group = new ToggleGroup();
        RadioButton buttonA = new RadioButton(answer.substring(answer.indexOf("A."), answer.indexOf("B.")));
        RadioButton buttonB = new RadioButton(answer.substring(answer.indexOf("B."), answer.indexOf("C.")));
        RadioButton buttonC = new RadioButton(answer.substring(answer.indexOf("C."), answer.indexOf("D.")));
        RadioButton buttonD = new RadioButton(answer.substring(answer.indexOf("D.")));
        //默认选中A
//        buttonA.setSelected(true);
        buttonA.setToggleGroup(group);
        buttonB.setToggleGroup(group);
        buttonC.setToggleGroup(group);
        buttonD.setToggleGroup(group);

        vbox.getChildren().add(buttonA);
        vbox.getChildren().add(buttonB);
        vbox.getChildren().add(buttonC);
        vbox.getChildren().add(buttonD);

        //添加一行空行
        vbox.getChildren().add(new Label(""));

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                //强转成RadioButton即可获取text
                question.setUserAnswer(((RadioButton) newValue).getText().substring(0, 1));
            }
        });
    }


    /**
     * 初始化下拉框数据
     */
    private void initData() {
        con = Common.initializeDB();
        Common.initCourse(con, courses);
        //知识点好像一直就没用过，还有学分
//        Common.initKnowledges(con,knowledges);
        Common.initQuestions(con, questions);

        //填充课程下拉框数据
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < courses.size(); i++) {
            items.add(courses.get(i).getcName());
        }
        cbCourse.setItems(items);
        //设置默认选中第0个
        cbCourse.setValue(items.get(0));
        //当课程改变后，题目数量也会变
        cbCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                initChoiesNum();
            }
        });

        //填充数量下拉框数据
        initChoiesNum();
    }

    private void initChoiesNum() {
        int single = 0;
        int muti = 0;
        int judge = 0;
        int completion = 0;
        for (int i = 0; i < questions.size(); i++) {
            //判断当前题是否为当前课程，是则放行
            if (!questions.get(i).getcNum().equals(cbCourse.getValue())) {
                continue;
            }
            switch (questions.get(i).getqType()) {
                case "单选题":
                    single++;
                    break;
                case "多选题":
                    muti++;
                    break;
                case "判断题":
                    judge++;
                    break;
                case "填空题":
                    completion++;
                    break;
                default:
                    break;
            }
        }
        //单选
        setNum(single, cbSingle);
        //多选
        setNum(muti, cbMulti);
        //判断
        setNum(judge, cbJudge);
        //填空
        setNum(completion, cbCompletion);

    }

    private void setNum(int size, ChoiceBox<String> choiceBox) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add(0 + "");
        for (int i = 1; i <= size; i++) {
            items.add(i + "");
        }
        choiceBox.setItems(items);
        choiceBox.setValue(items.get(0));
    }
}
