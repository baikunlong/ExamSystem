package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author baikunlong
 * @date 2019年12月9日20点56分
  设计一个可用于课程考试练习的程序。
    题型仅包括：单选题、多选题、判断题、填空题等四种题型。
    功能要求：
    ①课程管理：能用于多门课程的考试练习。课程信息至少包括：课程名称、课程号、学分。
    ②知识点管理：每门课程至少有3个知识点，可以实现知识点的增加和删除。
    ③试题管理：试题信息至少包括题型、题干、备选项若干（不超过5个）、所属知识点、所属课程号。
    ④练习者登录成功后，可以选择课程和各种题型的题目数量后，随机生成试卷开始练习。
    完成后显示实际得分（总分100，每道题目分数相同）并将答错的题目、答案和正确答案显示出来。
    ⑤试题应保存在文件或数据库。只有管理员才能操作课程管理、知识点管理、试题管理；普通用户只能练习。
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        primaryStage.setTitle("登录");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
