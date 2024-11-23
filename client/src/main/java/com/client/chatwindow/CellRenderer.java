package com.client.chatwindow;

import com.messages.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CellRenderer implements Callback<ListView<User>, ListCell<User>> {

    private final String currentUserName;
    private final ObservableList<User> userList;

    public CellRenderer(String currentUserName) {
        this.currentUserName = currentUserName;
        this.userList = FXCollections.observableArrayList();
    }

    @Override
    public ListCell<User> call(ListView<User> p) {
        ListCell<User> cell = new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setGraphic(null);
                setText(null);
                if (user != null && !empty) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getName());

                    ImageView statusImageView = new ImageView();
                    Image statusImage = new Image(getClass().getClassLoader()
                            .getResource("images/" + user.getStatus().toString().toLowerCase() + ".png").toString(), 16, 16, true, true);
                    statusImageView.setImage(statusImage);

                    ImageView pictureImageView = new ImageView();
                    Image image = new Image(getClass().getClassLoader()
                            .getResource("images/" + user.getPicture().toLowerCase() + ".png").toString(), 50, 50, true, true);
                    pictureImageView.setImage(image);

                    hBox.getChildren().addAll(statusImageView, pictureImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }

    // Cập nhật danh sách người dùng, loại bỏ người dùng hiện tại
    public void updateUserList(ObservableList<User> allUsers) {
        userList.clear();
        allUsers.stream()
                .filter(user -> !user.getName().equals(currentUserName))
                .forEach(userList::add);
    }

    public ObservableList<User> getUserList() {
        return userList;
    }
}
