package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.common.Common;
import sample.bean.Course;

import java.net.URL;
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
    public TextField cName;
    public TextField cNum;
    public TextField cScore;
    public Button btnAdd;
    public Button btnChange;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellFactory(getCellFactory("cName"));
        columnNum.setCellFactory(getCellFactory("cNum"));
        columnScore.setCellFactory(getCellFactory("cScore"));

        columnDelete.setCellFactory(new Callback<TableColumn<Course, String>, TableCell<Course, String>>() {
            @Override
            public TableCell<Course, String> call(TableColumn<Course, String> param) {
                TableCell<Course, String> tableCell = new TableCell<Course, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && this.getIndex() != -1 && courses.size() > this.getIndex()) {
                            Button button = new Button("删除");
                            button.setMinWidth(90);
                            setGraphic(button);
                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    courses.remove(getIndex());
                                    System.out.println("删除：" + getIndex());
                                    System.out.println("剩余：" + courses.size());
                                    //必须刷新，不然最后几行时页面还是会缓存显示最后行数据
                                    tableCourse.refresh();
                                    //todo 删除数据
                                }
                            });
                        }
                    }
                };
                return tableCell;
            }
        });

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
                                            if(!equals){
                                                course.setcName(text);
                                                //设置标记为已更改
                                                course.setChange(true);
                                            }
                                        } else if ("cNum".equals(type)) {
                                            boolean equals = course.getcNum().equals(text.trim());
                                            if(!equals){
                                                course.setcNum(text);
                                                //设置标记为已更改
                                                course.setChange(true);
                                            }
                                        } else if ("cScore".equals(type)) {
                                            boolean equals = course.getcScore().equals(text.trim());
                                            if(!equals){
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
        String trim1= cName.getText().trim();
        String trim2 = cNum.getText().trim();
        String trim3 = cScore.getText().trim();
        if(trim1.length()==0||trim2.length()==0||trim3.length()==0){
            Common.setAlert("警告","所有信息不能为空");
        }else {
            Course course = new Course();
            course.setcName(trim1);
            course.setcNum(trim2);
            course.setcScore(trim3);
            courses.add(course);
            //刷新数据，不然添加一行有时显示两行！！！
            tableCourse.refresh();
            Common.setInformationDialog("提示","添加成功");
            //todo 改为数据库添加
        }
    }

    public void changeCourse(MouseEvent mouseEvent) {
        boolean isNull=true;
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).isChange()){
                //todo 更改数据库数据
                System.out.println("第"+i+"行改变了");
                isNull=false;
            }
        }
        if(isNull){
            Common.setAlert("警告","没有更改任何数据！");
        }else {
            Common.setInformationDialog("提示","数据更改成功！");
        }
    }
}
