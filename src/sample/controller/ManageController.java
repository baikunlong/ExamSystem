package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.bean.BaseBean;
import sample.bean.Knowledge;
import sample.bean.Question;
import sample.common.Common;
import sample.bean.Course;

import java.net.URL;
import java.sql.*;
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
    private final ObservableList<Knowledge> knowledges = FXCollections.observableArrayList();
    private final ObservableList<Question> questions = FXCollections.observableArrayList();
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
    public TableColumn<Question, String> columnQType;
    public TableColumn<Question, String> columnQContent;
    public TableColumn<Question, String> columnAnswer;
    public TableColumn<Question, String> columnRightAnswer;
    public TableColumn<Question, String> columnQKContent;
    public TableColumn<Question, String> columnQDelete;
    public TableColumn<Question, String> columnQCourse;
    public ChoiceBox<String> qType;
    public TextField qContent;
    public TextField qAnswer;
    public TextField qRightAnswer;
    public ChoiceBox<String> qKnowledge;
    public ChoiceBox<String> qCourse;
    public ChoiceBox<String> cbKCourse;
    private Connection con;
    private ObservableList<String> cbKList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化数据库连接
        con = Common.initializeDB();

        //默认加载
        initCourse();
        //监听tab切换事件
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                //先放这里初始化，两个模块公用的数据
                //ChoiceBox使用方法参考地址：http://www.javafxchina.net/blog/2015/04/doc03_choicebox/
                cbKList = FXCollections.observableArrayList();
                for (int i = 0; i < courses.size(); i++) {
                    cbKList.add(courses.get(i).getcName());
                }

                //清数据
                courses.clear();
                knowledges.clear();
                questions.clear();
                initCourse();
                initKnowLedge();
                initQuestion();

                //先不考虑这个了
//                switch (observable.getValue().getText()) {
//                    case "课程管理":
////                        if (courses.size() == 0)
//                            initCourse();
//                        break;
//                    case "知识点管理":
////                        if (knowledges.size() == 0)
//                            initKnowLedge();
//                        break;
//                    case "试题管理":
////                        if (questions.size() == 0)
//                        //进试题模块之前要初始化知识点的数据
//                        initKnowLedge();
//                        initQuestion();
//                        break;
//                    default:
//                        initCourse();
//                        break;
//                }
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
        }, courses, tableCourse, con));

        //查询所有数据
        try {
            String sql = "SELECT * FROM `exam_system`.`course` LIMIT 0, 1000";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt(1));
                course.setcName(resultSet.getString(2));
                course.setcNum(resultSet.getString(3));
                course.setcScore(resultSet.getString(4));
                courses.add(course);
            }
            ps.close();
//            Common.setInformationDialog("提示", "总共查询到"+courses.size()+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
            Common.setAlert("警告", "获取数据失败");
        }

//        模拟数据
//        for (int i = 0; i < 20; i++) {
//            Course course = new Course();
//            course.setcName("课程名字" + i);
//            course.setcNum("课程序号" + i);
//            course.setcScore("课程学分" + i);
//            courses.add(course);
//        }

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
                        //这里的问题，index进来顺序为0，-1，0，1...
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
            try {
                String sql = "INSERT INTO `exam_system`.`course`(`cName`, `cNum`, `cScore`) VALUES (?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, course.getcName());
                ps.setString(2, course.getcNum());
                ps.setString(3, course.getcScore());
                ps.executeUpdate();
                setId(course, ps,courses,tableCourse);
            } catch (SQLException e) {
                e.printStackTrace();
                Common.setAlert("警告", "添加失败，请注意课程名称和课程号是唯一的！！！");
            }
        }
    }

    public void changeCourse(MouseEvent mouseEvent) {
        int changeCount = 0;
        try {
            String sql = "UPDATE `exam_system`.`course` SET `cName` = ?, `cNum` = ?, `cScore` = ? WHERE `id` = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);
                if (course.isChange()) {
                    // 更改数据库数据
                    ps.setString(1, course.getcName());
                    ps.setString(2, course.getcNum());
                    ps.setString(3, course.getcScore());
                    ps.setInt(4, course.getId());
                    int count = ps.executeUpdate();
                    if (count == 1) {
                        changeCount++;
                    }
                    //状态改回未更改
                    course.setChange(false);
//                    System.out.println("第" + i + "行改变了");
                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Common.setAlert("警告", "数据库错误");
        }
        if (changeCount == 0) {
            Common.setAlert("警告", "没有更改任何数据！");
        } else {
            Common.setInformationDialog("提示", "总共更新了" + changeCount + "条数据");
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
        }, knowledges, tableKnowledge, con));

        //todo 查询所有数据
        //查询所有数据
        try {
            String sql = "SELECT * FROM `exam_system`.`knowledge` LIMIT 0, 1000";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Knowledge knowledge = new Knowledge();
                knowledge.setId(resultSet.getInt(1));
                knowledge.setkContent(resultSet.getString(2));
                knowledge.setkCourse(resultSet.getString(3));
                knowledges.add(knowledge);
            }
            ps.close();
//            Common.setInformationDialog("提示", "总共查询到"+courses.size()+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
            Common.setAlert("警告", "获取数据失败");
        }

//        for (int i = 0; i < 20; i++) {
//            Knowledge knowledge = new Knowledge();
//            knowledge.setkContent("知识点" + i);
//            knowledge.setkCourse("课程名" + i);
//            knowledges.add(knowledge);
//        }
        tableKnowledge.setItems(knowledges);
        tableKnowledge.setEditable(true);

        cbKCourse.setItems(cbKList);
        cbKCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String value = cbKCourse.getValue();
                System.out.println(value);
            }
        });
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
//        String trim2 = kCourse.getText().trim();
        String trim2 = cbKCourse.getValue();
        if (trim2 == null || trim1.length() == 0 || trim2.length() == 0) {
            Common.setAlert("警告", "所有信息不能为空");
        } else {
            Knowledge knowledge = new Knowledge();
            knowledge.setkContent(trim1);
            knowledge.setkCourse(trim2);
            //数据库添加
            try {
                String sql = "INSERT INTO `exam_system`.`knowledge`(`kContent`, `kCourse`) VALUES (?, ?)";
                //要返回id需要指定Statement.RETURN_GENERATED_KEYS
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, knowledge.getkContent());
                ps.setString(2, knowledge.getkCourse());
                ps.executeUpdate();
                setId(knowledge, ps,knowledges,tableKnowledge);
            } catch (Exception e) {
                e.printStackTrace();
                Common.setAlert("警告", "添加失败，请注意课程名称和课程号是唯一的！！！");
            }
        }
    }


//**************************试题模块**********************************************************

    /**
     * 初始化试题模块
     */
    private void initQuestion() {
        //初始化下拉框
        ObservableList<String> qtypes = FXCollections.observableArrayList("单选题", "多选题", "判断题", "填空题");
        qType.setItems(qtypes);

        ObservableList<String> kContents = FXCollections.observableArrayList();
        for (int i = 0; i < knowledges.size(); i++) {
            kContents.add(knowledges.get(i).getkContent());
        }
        qKnowledge.setItems(kContents);
        qCourse.setItems(cbKList);


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
        }, questions, tableQuestion, con));

        //查询所有数据
        try {
            String sql = "SELECT * FROM `exam_system`.`question` LIMIT 0, 1000";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Question question = new Question();
                question.setId(resultSet.getInt(1));
                question.setqType(resultSet.getString(2));
                question.setqContent(resultSet.getString(3));
                question.setAnswer(resultSet.getString(4));
                question.setkContent(resultSet.getString(5));
                question.setcNum(resultSet.getString(6));
                question.setRightAnswer(resultSet.getString(7));
                questions.add(question);
            }
            ps.close();
//            Common.setInformationDialog("提示", "总共查询到"+courses.size()+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
            Common.setAlert("警告", "获取数据失败");
        }

//        for (int i = 0; i < 20; i++) {
//            Question question = new Question();
//            question.setAnswer("啦啦啦" + i);
//            question.setcNum("啦啦啦" + i);
//            question.setkContent("啦啦啦" + i);
//            question.setqContent("啦啦啦" + i);
//            question.setqType("啦啦啦" + i);
//            question.setRightAnswer("啦啦啦" + i);
//            questions.add(question);
//        }
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
                            } else if ("answer".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getAnswer());
                            } else if ("rightAnswer".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getRightAnswer());
                            } else if ("kContent".equals(type)) {
                                label.setText(param.getTableView().getItems().get(this.getIndex()).getkContent());
                            } else if ("cNum".equals(type)) {
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
        String trim1 = qType.getValue();
        String trim2 = qContent.getText().trim();
        String trim3 = qAnswer.getText().trim();
        String trim4 = qRightAnswer.getText().trim();
        String trim5 = qKnowledge.getValue();
        String trim6 = qCourse.getValue();
        if (trim1 == null || trim5 == null || trim6 == null || trim1.length() == 0 || trim2.length() == 0 || trim3.length() == 0
                || trim4.length() == 0 || trim5.length() == 0 || trim6.length() == 0) {
            Common.setAlert("警告", "所有信息不能为空");
        } else {
            //校验格式是否正确
            if ("单选题".equals(trim1) || "多选题".equals(trim1)) {
                int i = trim3.indexOf("A.");
                int i2 = trim3.indexOf("B.");
                int i3 = trim3.indexOf("C.");
                int i4 = trim3.indexOf("D.");
                if (i == -1 || i2 == -1 || i3 == -1 || i4 == -1) {
                    Common.setAlert("警告", "答案备选项格式不正确，请看使用说明");
                    return;
                }
                System.out.println("i = " + trim3.substring(i, i2));
                System.out.println("i2 = " + trim3.substring(i2, i3));
                System.out.println("i3 = " + trim3.substring(i3, i4));
                System.out.println("i4 = " + trim3.substring(i4));
            }
            if ("判断题".equals(trim1)&&!"对".equals(trim4)&&!"错".equals(trim4)) {
                Common.setAlert("警告", "正确格式不正确，判断题只有对错，请看使用说明");
                return;
            }

            Question question = new Question();
            question.setqType(trim1);
            question.setqContent(trim2);
            question.setAnswer(trim3);
            question.setRightAnswer(trim4);
            question.setkContent(trim5);
            question.setcNum(trim6);
            //数据库添加
            try {
                String sql = "INSERT INTO `exam_system`.`question`(`qType`, `qContent`, `answer`, `kContent`, `cNum`, `rightAnswer`) VALUES (?,?,?,?,?,?)";
                //要返回id需要指定Statement.RETURN_GENERATED_KEYS
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, question.getqType());
                ps.setString(2, question.getqContent());
                ps.setString(3, question.getAnswer());
                ps.setString(4, question.getkContent());
                ps.setString(5, question.getcNum());
                ps.setString(6, question.getRightAnswer());
                ps.executeUpdate();
                setId(question, ps,questions,tableQuestion);
            } catch (Exception e) {
                e.printStackTrace();
                Common.setAlert("警告", "添加失败，请注意课程名称和课程号是唯一的！！！");
            }
        }
    }

    private void setId(BaseBean baseBean, PreparedStatement ps, ObservableList data, TableView tableView) throws SQLException {
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            //一定要把ID设置了，不然修改删除时id都是为0
            baseBean.setId(generatedKeys.getInt(1));
            data.add(baseBean);
            //刷新数据，不然添加一行有时显示两行！！！
            tableView.refresh();
            Common.setInformationDialog("提示", "添加成功");
        } else {
            Common.setAlert("警告", "数据库错误");
        }
        ps.close();
    }
}
