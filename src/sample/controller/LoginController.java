package sample.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.bean.User;
import sample.common.Common;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author baikunlong
 */
public class LoginController implements Initializable {


    public TextField uName;
    public PasswordField pwd;
    public Button btnLogin;
    public Button defaultAccount;
    private Connection con;

    public void login() throws IOException {
        User user = queryUser(uName.getText(),pwd.getText());

        //模拟管理员登录
//        User user = new User("admin", "admin",0);
//        User user = new User("admin", "admin",1);
//        if(uName.getText().equals(user.getuName())&&pwd.getText().equals(user.getPwd())){
        if(user!=null){
            // 创建新的stage
            Stage secondStage = new Stage();
            Parent root=null;
            if(user.getRole()==0){
                System.out.println("欢迎管理员");
                //注意文件位置，不然会异常java.lang.NullPointerException: Location is required.
                root = FXMLLoader.load(getClass().getResource("../fxml/manage.fxml"));
                secondStage.setTitle("管理员");

            }else {
                System.out.println("欢迎普通用户");
                root = FXMLLoader.load(getClass().getResource("../fxml/exam.fxml"));
                secondStage.setTitle("用户");
            }
            Scene secondScene = new Scene(root, 900, 600);
            secondStage.setResizable(false);
            secondStage.setScene(secondScene);
            secondStage.show();
            //关闭登录窗口
            Stage stage = (Stage)btnLogin.getScene().getWindow();
            stage.close();

            //当窗口关闭时，打开登录窗口
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    stage.show();
                }
            });
        }else {
            Common.setAlert("错误","用户名或密码错误");
        }
    }

    private User queryUser(String uName,String pwd) {
        User user = null;
        try {
            String sql = "SELECT * FROM `exam_system`.`user` where uName=? and pwd=? LIMIT 0, 1000";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,uName);
            ps.setString(2,pwd);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                 user= new User(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
//                System.out.println(user.toString());
            }
            ps.close();
//            Common.setInformationDialog("提示", "总共查询到"+courses.size()+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
            Common.setAlert("警告", "获取数据失败");
        }
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = Common.initializeDB();
        defaultAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Common.setInformationDialog("账户信息","管理员默认账户：admin/admin\n用户默认账户：bkl/123");
            }
        });
    }
}
