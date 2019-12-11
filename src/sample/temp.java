//      ManageController
//          可编辑，但是好像就只能编辑，没其他功能
//        columnName.setCellFactory(TextFieldTableCell.forTableColumn());

//        columnName.setCellValueFactory(new PropertyValueFactory<>("cName"));
//        columnNum.setCellValueFactory(new PropertyValueFactory<>("cNum"));
//        columnScore.setCellValueFactory(new PropertyValueFactory<>("cScore"));

//      //使用自定义Cell
//        Callback<TableColumn<Course, String>,
//                TableCell<Course, String>> cellFactory
//                = (TableColumn<Course, String> p) -> new EditingCell();
//        columnName.setCellFactory(cellFactory);
//        //当同行切换文本框时会触发
//        columnName.setOnEditCommit(
//                (TableColumn.CellEditEvent<Course, String> t) -> {
//                    Course course = t.getTableView().getItems().get(
//                            t.getTablePosition().getRow());
//                    course.setcName(t.getNewValue());
//                });
//        columnNum.setCellFactory(cellFactory);
//        columnNum.setOnEditCommit(
//                (TableColumn.CellEditEvent<Course, String> t) -> {
//                    Course course = t.getTableView().getItems().get(
//                            t.getTablePosition().getRow());
//                    course.setcNum(t.getNewValue());
//                });
//        columnScore.setCellFactory(cellFactory);
//        columnScore.setOnEditCommit(
//                (TableColumn.CellEditEvent<Course, String> t) -> {
//                    Course course = t.getTableView().getItems().get(
//                            t.getTablePosition().getRow());
//                    course.setcScore(t.getNewValue());
//                });