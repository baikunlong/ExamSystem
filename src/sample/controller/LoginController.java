package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.common.Common;
import sample.bean.User;

import java.io.IOException;

/**
 * @author baikunlong
 */
public class LoginController{


    public TextField uName;
    public PasswordField pwd;
    public Button btnLogin;

    public void login() throws IOException {
        //todo 改为查询数据库的用户
        //模拟管理员登录
        User user = new User("admin", "admin",0);
//        if(uName.getText().equals(user.getuName())&&pwd.getText().equals(user.getPwd())){
        if(user.getRole()==0){

            if(user.getRole()==0){
                System.out.println("欢迎管理员");
                // 创建新的stage
                Stage secondStage = new Stage();
                //注意文件位置，不然会异常java.lang.NullPointerException: Location is required.
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/manage.fxml"));
                Scene secondScene = new Scene(root, 900, 600);
                secondStage.setTitle("管理员");
                secondStage.setResizable(false);
                secondStage.setScene(secondScene);
                secondStage.show();
                //关闭登录窗口
                Stage stage = (Stage)btnLogin.getScene().getWindow();
                stage.close();
            }else {
                System.out.println("欢迎普通用户");
            }
        }else {
            Common.setAlert("错误","用户名或密码错误");
        }
    }
}
