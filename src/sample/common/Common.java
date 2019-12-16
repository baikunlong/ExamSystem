package sample.common;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.bean.Course;
import sample.bean.Knowledge;
import sample.bean.Question;

import java.sql.*;

/**
 * @author baikunlong
 * @date 2019/12/11 20:38
 */
public class Common {
    /**
     * 警告框
     * @param title
     * @param content
     */
    public static void setAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 消息框
     * @param title
     * @param content
     */
    public static void setInformationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 确认框
     * @param title
     * @param content
     */
    public static Alert setConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
    }
    /**
     * 删除键工厂
     * @param <S>
     * @param <T>
     * @param paramCallback
     * @param data
     * @param tableView
     * @param con
     * @return
     */
    public static  <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> getDeleteCellFactory(
            final Callback<S, T> paramCallback,
            final ObservableList<S> data,
            final TableView tableView,
            final Connection con) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell<S, T> call(TableColumn<S, T> paramTableColumn) {
                return new TableCell<S, T>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && this.getIndex() != -1 && data.size() > this.getIndex()) {
                            Button button = new Button("删除");
                            button.setMinWidth(90);
                            setGraphic(button);
                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    //删除数据库数据
                                    //先判断删除哪个数据库,随意取其中一个对象即可判断
                                    S s = data.get(0);
                                    if(s instanceof Course){
                                        Course course = (Course) data.get(getIndex());
                                        deleteDb(data,course.getId(),getIndex(),con,"course");
                                    }else if(s instanceof Knowledge){
                                        Knowledge knowledge = (Knowledge) data.get(getIndex());
                                        deleteDb(data,knowledge.getId(),getIndex(),con,"knowledge");
                                    }else if(s instanceof Question){
                                        Question question = (Question) data.get(getIndex());
                                        deleteDb(data,question.getId(),getIndex(),con,"question");
                                    }
                                    //必须刷新，不然最后几行时页面还是会缓存显示最后行数据
                                    tableView.refresh();
                                }
                            });
                        }
                    }
                };
            }
        };
    }

    /**
     * 删除数据库里的数据
     * @param data 数据集合
     * @param id 要删除的id
     * @param index 要删除的下标
     * @param con 数据库连接
     * @param tableName 要删除的表名
     */
    private static void deleteDb(ObservableList data, int id, int index, Connection con, String tableName) {
        //todo 如果删除课程，要删除相关题目与知识点
        try {
            //这里tableName如果用参数化，会加单引号，然后报错
            String sql = "DELETE FROM `exam_system`."+tableName+" WHERE `id` = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            //此处打印了对象和 带入参数后的sql语句
            System.out.println(ps.toString());
            int i = ps.executeUpdate();
            if(i==1){
                data.remove(index);
                System.out.println("删除：" + index);
                System.out.println("剩余：" + data.size());
                //如果不加对话框，一直点删除，表格刷新不过来，就会崩，但是只要手速够快，一样能点崩
                Common.setInformationDialog("提示","删除成功");
            }else {
                throw new Exception("数据库错误");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            Common.setAlert("警告", "数据库错误");
        }

    }

    /**
     * 初始化数据连接
     * @return
     */
    public static Connection initializeDB() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/exam_system?serverTimezone=UTC", "root", "123456");
            System.out.println("Database connected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }


    public static void initCourse(Connection con,ObservableList<Course> courses){
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
    }
    public static void initKnowledges(Connection con,ObservableList<Knowledge> knowledges){
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
    }
    public static void initQuestions(Connection con,ObservableList<Question> questions){
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
    }

    /**
     * 判断两个字符串是否相等，忽略字符顺序
     * @param s
     * @param t
     * @return
     */
    public static boolean canPerm(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] counter = new int[256];
        for (char c : s.toCharArray()) {
            counter[c]++;
        }
        for (char c : t.toCharArray()) {
            if (--counter[c] < 0) {
                return false;
            }
        }
        return true;
    }
}
