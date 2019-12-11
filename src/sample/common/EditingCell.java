package sample.common;


import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import sample.bean.Course;

/**
 * EditingCell
 *
 * @author xiongchao
 * @date 2019/8/12 9:47
 */
public class EditingCell extends TableCell<Course, String> {

    private TextField textField;

    public EditingCell() {
    }

    @Override
    public void startEdit() {
        System.out.println("开始");
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        System.out.println("结束");

        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 1);
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0,
                 Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}

