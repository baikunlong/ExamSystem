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


//        columnDelete.setCellFactory(new Callback<TableColumn<Course, String>, TableCell<Course, String>>() {
//            @Override
//            public TableCell<Course, String> call(TableColumn<Course, String> param) {
//                TableCell<Course, String> tableCell = new TableCell<Course, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (!empty && this.getIndex() != -1 && courses.size() > this.getIndex()) {
//                            Button button = new Button("删除");
//                            button.setMinWidth(90);
//                            setGraphic(button);
//                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(MouseEvent event) {
//                                    courses.remove(getIndex());
//                                    System.out.println("删除：" + getIndex());
//                                    System.out.println("剩余：" + courses.size());
//                                    //必须刷新，不然最后几行时页面还是会缓存显示最后行数据
//                                    tableCourse.refresh();
//                                }
//                            });
//                        }
//                    }
//                };
//                return tableCell;
//            }
//        });


//    private Callback<TableColumn<Knowledge, String>, TableCell<Knowledge, String>> getDeleteCellFactory() {
//        return new Callback<TableColumn<Knowledge, String>, TableCell<Knowledge, String>>() {
//            @Override
//            public TableCell<Knowledge, String> call(TableColumn<Knowledge, String> param) {
//                TableCell<Knowledge, String> tableCell = new TableCell<Knowledge, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (!empty && this.getIndex() != -1 && knowledges.size() > this.getIndex()) {
//                            Button button = new Button("删除");
//                            button.setMinWidth(90);
//                            setGraphic(button);
//                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(MouseEvent event) {
//                                    knowledges.remove(getIndex());
//                                    System.out.println("删除：" + getIndex());
//                                    System.out.println("剩余：" + knowledges.size());
//                                    //必须刷新，不然最后几行时页面还是会缓存显示最后行数据
//                                    tableKnowledge.refresh();
//                                }
//                            });
//                        }
//                    }
//                };
//                return tableCell;
//            }
//        };
//    }