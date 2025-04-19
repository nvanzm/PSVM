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

/**
 * The TypeController class is responsible for controlling the interaction between
 * the user interface and the backend logic related to different types of work items
 * (Epic, User Story, Task). It manages the display of information about these work
 * items on the UI and handles specific user actions.
 */
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
                        "Bericht %s\n    Content: %s \n↓\n%s: %s\n    Beschrijving: %s",
                        message.getId(),
                        message.getText(),
                        message.getTypeLabel(),
                        parentItem.getNaam(),
                        parentItem.getBeschrijving()
                );
                break;

            case "user_story":
                formattedText = String.format(
                        "Bericht %s\n    Content: %s \n↓\n%s: %s\n    Beschrijving: %s \n↓\nde Epic: %s. \n    Beschrijving: %s.",
                        message.getId(),
                        message.getText(),
                        message.getTypeLabel(),
                        parentItem.getNaam(),
                        parentItem.getBeschrijving(),
                        scrumBoard.getEpicNameById(parentItem.getParentId()),
                        scrumBoard.getEpicdDescById(parentItem.getParentId())
                );
                break;

            case "taak":
                UserStory userStory = scrumBoard.getUserStoryById(parentItem.getParentId());
                String epicName = scrumBoard.getEpicNameById(userStory.getParentId());
                String epicDesc = scrumBoard.getEpicdDescById(userStory.getParentId());

                formattedText = String.format(
                        "Bericht %s\n    Content: %s \n↓\n%s: %s\n    Beschrijving: %s \n↓\nde User Story: %s. \n    Beschrijving: %s.\n↓\nde Epic: %s. \n    Beschrijving: %s.",
                        message.getId(),
                        message.getText(),
                        message.getTypeLabel(),
                        parentItem.getNaam(),
                        parentItem.getBeschrijving(),
                        scrumBoard.getUserStoryNameById(parentItem.getParentId()),
                        scrumBoard.getUserStoryDescById(parentItem.getParentId()),
                        epicName,
                        epicDesc
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
