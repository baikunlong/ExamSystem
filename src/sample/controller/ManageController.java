package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.bean.BaseBean;
import sample.bean.Knowledge;
import sample.bean.Question;
import sample.common.Common;
import sample.bean.Course;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author baikunlong
 * @date 2019/12/9 22:58
 */
public class ManageController implements Initializable {

    public TableColumn<Course, String> columnName;
    public TableColumn<Course, String> columnNum;
    public TableColumn<Course, String> columnScore;
    public TableColumn<Course, String> columnDelete;
    public TableView<Course> tableCourse;
    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private final ObservableList<Knowledge> knowledges= FXCollections.observableArrayList();
    private final ObservableList<Question> questions= FXCollections.observableArrayList();
    public TextField cName;
    public TextField cNum;
    public TextField cScore;
    public Button btnAdd;
    public Button btnChange;
    public TabPane tabPane;
    public Tab tabCourse;
    public Tab tabKnowledge;
    public Tab tabQuestion;
    public TableView<Knowledge> tableKnowledge;
    public TableColumn<Knowledge, String> columnKContent;
    public TableColumn<Knowledge, String> columnKCourse;
    public TableColumn<Knowledge, String> columnKDelete;
    public TextField kContent;
    public TextField kCourse;
    public Tab tabInstructions;
    public TableView<Question> tableQuestion;
    public TableColumn<Question,String> columnQType;
    public TableColumn<Question,String> columnQContent;
    public TableColumn<Question,String> columnAnswer;
    public TableColumn<Question,String> columnRightAnswer;
    public TableColumn<Question,String> columnQKContent;
    public TableColumn<Question,String> columnQDelete;
    public TableColumn<Question,String> columnQCourse;
    public TextField qType;
    public TextField qContent;
    public TextField qAnswer;
    public TextField qRightAnswer;
    public TextField qKnowledge;
    public TextField qCourse;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //默认加载
        initCourse();
        //监听tab切换事件
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                switch (observable.getValue().getText()) {
                    case "课程管理":
                        if(courses.size()==0)
                        initCourse();
                        break;
                    case "知识点管理":
                        if(knowledges.size()==0)
                            initKnowLedge();
                        break;
                    case "试题管理":
                        if(questions.size()==0)
                            initQuestion();
                        break;
                    default:
                        initCourse();
                        break;
                }
            }
        });
    }

//**************************课程模块**********************************************************

    /**
     * 初始化课程管理模块
     */
    private void initCourse() {
        columnName.setCellFactory(getCellFactory("cName"));
        columnNum.setCellFactory(getCellFactory("cNum"));
        columnScore.setCellFactory(getCellFactory("cScore"));
        //使用通用删除工场，精简代码
        columnDelete.setCellFactory(Common.getDeleteCellFactory(new Callback<Course, String>() {
            @Override
            public String call(Course param) {
                return null;
            }
        },courses,tableCourse));

        //todo 查询所有数据
        for (int i = 0; i < 20; i++) {
            Course course = new Course();
            course.setcName("课程名字" + i);
            course.setcNum("课程序号" + i);
            course.setcScore("课程学分" + i);
            courses.add(course);
        }
        tableCourse.setItems(courses);
        tableCourse.setEditable(true);
    }

    /**
     * 提供表格数据
     *
     * @param type 显示的属性为哪个
     * @return TableCell
     */
    private Callback<TableColumn<Course, String>, TableCell<Course, String>> getCellFactory(String type) {
        return new Callback<TableColumn<Course, String>, TableCell<Course, String>>() {
            @Override
            public TableCell<Course, String> call(TableColumn<Course, String> param) {
                TableCell<Course, String> tableCell = new TableCell<Course, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
//                        System.out.println("绘制:" + getIndex());
                        //todo 记录这里的问题，index进来顺序为0，-1，0，1...
                        if (!empty && this.getIndex() != -1 && courses.size() > this.getIndex()) {
                            //System.out.println(this.getIndex());
                            TextField textField = new TextField();
                            if ("cName".equals(type)) {
                                textField.setText(param.getTableView().getItems().get(this.getIndex()).getcName());
                            } else if ("cNum".equals(type)) {
                                textField.setText(param.getTableView().getItems().get(this.getIndex()).getcNum());
                            } else if ("cScore".equals(type)) {
                                textField.setText(param.getTableView().getItems().get(this.getIndex()).getcScore());
                            }
                            setGraphic(textField);
                            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                    //如果失去焦点，则改变数据
                                    if (!newValue) {
                                        Course course = courses.get(getIndex());
                                        String text = textField.getText();
                                        if ("cName".equals(type)) {
                                            boolean equals = course.getcName().equals(text.trim());
                                            if (!equals) {
                                                course.setcName(text);
                                                //设置标记为已更改
                                                course.setChange(true);
                                            }
                                        } else if ("cNum".equals(type)) {
                                            boolean equals = course.getcNum().equals(text.trim());
                                            if (!equals) {
                                                course.setcNum(text);
                                                //设置标记为已更改
                                                course.setChange(true);
                                            }
                                        } else if ("cScore".equals(type)) {
                                            boolean equals = course.getcScore().equals(text.trim());
                                            if (!equals) {
                                                course.setcScore(text);
                                                //设置标记为已更改
                                                course.setChange(true);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                };
                return tableCell;
            }
        };
    }

    public void addCourse(MouseEvent mouseEvent) {
        String trim1 = cName.getText().trim();
        String trim2 = cNum.getText().trim();
        String trim3 = cScore.getText().trim();
        if (trim1.length() == 0 || trim2.length() == 0 || trim3.length() == 0) {
            Common.setAlert("警告", "所有信息不能为空");
        } else {
            Course course = new Course();
            course.setcName(trim1);
            course.setcNum(trim2);
            course.setcScore(trim3);
            courses.add(course);
            //刷新数据，不然添加一行有时显示两行！！！
            tableCourse.refresh();
            Common.setInformationDialog("提示", "添加成功");
            //todo 改为数据库添加
        }
    }

    public void changeCourse(MouseEvent mouseEvent) {
        boolean isNull = true;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).isChange()) {
                //todo 更改数据库数据
                System.out.println("第" + i + "行改变了");
                isNull = false;
            }
        }
        if (isNull) {
            Common.setAlert("警告", "没有更改任何数据！");
        } else {
            Common.setInformationDialog("提示", "数据更改成功！");
        }
    }


//**************************知识点模块**********************************************************

    /**
     * 初始化知识点模块
     */
    private void initKnowLedge() {
        columnKContent.setCellFactory(getKnowledgeCellFactory("kContent"));
        columnKCourse.setCellFactory(getKnowledgeCellFactory("kCourse"));
        columnKDelete.setCellFactory(Common.getDeleteCellFactory(new Callback<Knowledge, String>() {
            @Override
            public String call(Knowledge param) {
                return null;
            }
        }, knowledges, tableKnowledge));

        //todo 查询所有数据
        for (int i = 0; i < 20; i++) {
            Knowledge knowledge= new Knowledge();
            knowledge.setkContent("知识点"+i);
            knowledge.setkCourse("课程名"+i);
            knowledges.add(knowledge);
        }
        tableKnowledge.setItems(knowledges);
        tableKnowledge.setEditable(true);
    }

    private Callback<TableColumn<Knowledge, String>, TableCell<Knowledge, String>> getKnowledgeCellFactory(String type) {
        return new Callback<TableColumn<Knowledge, String>, TableCell<Knowledge, String>>() {
            @Override
            public TableCell<Knowledge, String> call(TableColumn<Knowledge, String> param) {
                TableCell<Knowledge, String> tableCell = new TableCell<Knowledge, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && this.getIndex() != -1 && knowledges.size() > this.getIndex()) {
                            Label label = new Label();
                            if ("kContent".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getkContent());
                            } else if ("kCourse".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getkCourse());
                            }
                            // 只有增删，所以只需要显示
                            setGraphic(label);
                        }
                    }
                };
                return tableCell;
            }
        };
    }
    
    public void addKnowledge(MouseEvent mouseEvent) {
        String trim1 = kContent.getText().trim();
        String trim2 = kCourse.getText().trim();
        if (trim1.length() == 0 || trim2.length() == 0  ) {
            Common.setAlert("警告", "所有信息不能为空");
        } else {
            Knowledge knowledge = new Knowledge();
            knowledge.setkContent(trim1);
            knowledge.setkCourse(trim2);
            knowledges.add(knowledge);
            //刷新数据，不然添加一行有时显示两行！！！
            tableKnowledge.refresh();
            Common.setInformationDialog("提示", "添加成功");
            //todo 改为数据库添加
        }
    }


//**************************试题模块**********************************************************

    /**
     * 初始化试题模块
     */
    private void initQuestion() {
        columnQType.setCellFactory(getQuestionCellFactory("qType"));
        columnQContent.setCellFactory(getQuestionCellFactory("qContent"));
        columnAnswer.setCellFactory(getQuestionCellFactory("answer"));
        columnRightAnswer.setCellFactory(getQuestionCellFactory("rightAnswer"));
        columnQKContent.setCellFactory(getQuestionCellFactory("kContent"));
        columnQCourse.setCellFactory(getQuestionCellFactory("cNum"));
        columnQDelete.setCellFactory(Common.getDeleteCellFactory(new Callback<Question, String>() {
            @Override
            public String call(Question param) {
                return null;
            }
        }, questions, tableQuestion));

        //todo 查询所有数据
        for (int i = 0; i < 20; i++) {
            Question question= new Question();
            question.setAnswer("啦啦啦"+i);
            question.setcNum("啦啦啦"+i);
            question.setkContent("啦啦啦"+i);
            question.setqContent("啦啦啦"+i);
            question.setqType("啦啦啦"+i);
            question.setRightAnswer("啦啦啦"+i);
            questions.add(question);
        }
        tableQuestion.setItems(questions);
        tableQuestion.setEditable(true);
    }

    private Callback<TableColumn<Question, String>, TableCell<Question, String>> getQuestionCellFactory(String type) {
        return new Callback<TableColumn<Question, String>, TableCell<Question, String>>() {
            @Override
            public TableCell<Question, String> call(TableColumn<Question, String> param) {
                TableCell<Question, String> tableCell = new TableCell<Question, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && this.getIndex() != -1 && questions.size() > this.getIndex()) {
                            Label label = new Label();
                            if ("qType".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getqType());
                            } else if ("qContent".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getqContent());
                            }else if ("answer".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getAnswer());
                            }else if ("rightAnswer".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getRightAnswer());
                            }else if ("kContent".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getkContent());
                            }else if ("cNum".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getcNum());
                            }
                            // 只有增删，所以只需要显示
                            setGraphic(label);
                        }
                    }
                };
                return tableCell;
            }
        };
    }

    public void addQuestion(MouseEvent mouseEvent) {
        String trim1 = qType.getText().trim();
        String trim2 = qContent.getText().trim();
        String trim3 = qAnswer.getText().trim();
        String trim4 = qRightAnswer.getText().trim();
        String trim5 = qKnowledge.getText().trim();
        String trim6 = qCourse.getText().trim();
        if (trim1.length() == 0 || trim2.length() == 0 || trim3.length() == 0
                || trim4.length() == 0|| trim5.length() == 0|| trim6.length() == 0) {
            Common.setAlert("警告", "所有信息不能为空");
        } else {
            Question question= new Question();
            question.setAnswer(trim1);
            question.setcNum(trim2);
            question.setkContent(trim3);
            question.setqContent(trim4);
            question.setqType(trim5);
            question.setRightAnswer(trim6);
            questions.add(question);
            //刷新数据，不然添加一行有时显示两行！！！
            tableQuestion.refresh();
            Common.setInformationDialog("提示", "添加成功");
            //todo 改为数据库添加
        }

    }
}
