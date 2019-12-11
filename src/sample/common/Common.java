package sample.common;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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

    public static void setInformationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void setConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public static  <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> getDeleteCellFactory(
            final Callback<S, T> paramCallback,
            final ObservableList data,
            final TableView tableView) {
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
                                    data.remove(getIndex());
                                    System.out.println("删除：" + getIndex());
                                    System.out.println("剩余：" + data.size());
                                    //必须刷新，不然最后几行时页面还是会缓存显示最后行数据
                                    tableView.refresh();
                                    //todo 删除数据
                                }
                            });
                        }
                    }
                };
            }
        };
    }
}
