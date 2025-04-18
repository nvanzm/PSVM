package com.example.psvm.controllers;

import com.example.psvm.model.Message;
import static com.example.psvm.Startup.getScrumBoard;
import com.example.psvm.model.ScrumBoard;
import com.example.psvm.model.UserStory;
import com.example.psvm.model.WorkItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TypeController {
    private ScrumBoard scrumBoard;

    @FXML
    public void initialize() {
        this.scrumBoard = getScrumBoard();
    }

    @FXML
    private Label typeInfoLabel;

    public void setMessage(Message message) {
        WorkItem parentItem = message.getWorkItem();

        if (parentItem == null) {
            typeInfoLabel.setText("⚠️ WorkItem niet gevonden.");
            return;
        }

        String formattedText;

        switch (message.getItemType()) {
            case "epic":
                formattedText = String.format(
                        "Dit bericht betreft %s: %s.",
                        message.getTypeLabel(),
                        parentItem.getNaam()
                );
                break;

            case "user_story":
                formattedText = String.format(
                        "Dit bericht betreft %s: %s, die hoort bij de Epic: %s.",
                        message.getTypeLabel(),
                        parentItem.getNaam(),
                        scrumBoard.getEpicNameById(parentItem.getParentId())
                );
                break;

            case "taak":
                UserStory userStory = scrumBoard.getUserStoryById(parentItem.getParentId());
                String epicName = scrumBoard.getEpicNameById(userStory.getParentId());
                formattedText = String.format(
                        "Dit bericht betreft %s: %s, die hoort bij de User Story: %s. die hoort bij de Epic: %s.",
                        message.getTypeLabel(),
                        parentItem.getNaam(),
                        userStory.getNaam(),
                        epicName
                );
                break;

            default:
                formattedText = "⚠️ Onbekend werkitemtype.";
                break;
        }

        typeInfoLabel.setText(formattedText);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) typeInfoLabel.getScene().getWindow();
        stage.close();
    }
}
