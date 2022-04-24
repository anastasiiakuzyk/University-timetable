package sample.actions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.ResourceBundle;

import DataTables.SqlActions.SqlClient;
import TableViews.TableViewer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.AdminController;
import sample.Commands;
import sample.actions.ObserverForRozklad.IObservable;
import sample.actions.ObserverForRozklad.IObserver;
import sample.actions.ObserverForRozklad.ObserverParams;
import sample.actions.ObserverForRozklad.RozkladBoxFiller;
import sample.animations.Shake;

import static sample.AdminController.editMode.ADD;
import static sample.AdminController.editMode.DELETE;

public class EditRozkladController
    implements IObservable
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> numberBox;

    @FXML
    private ComboBox<?> dayBox;

    @FXML
    private ComboBox<?> audienceBox;

    @FXML
    private ComboBox<?> groupBox;

    @FXML
    private ComboBox<?> subjectBox;

    @FXML
    private ComboBox<?> teacherBox;

    @FXML
    private Button editBtn;

    @FXML
    private Label editLabel;

    private List<ComboBox> combos = new ArrayList<>();

    @FXML
    void initialize() {
        combos.add(subjectBox);
        combos.add(teacherBox);
        combos.add(groupBox);
        combos.add(numberBox);
        combos.add(dayBox);
        combos.add(audienceBox);
        RozkladBoxFiller filler = new RozkladBoxFiller(this, combos);

        filler.fill();

        for (ComboBox box : combos)
        {
            box.setOnAction(event ->{
                notifyObservers();
                filler.fill();
            });
        }

        if(AdminController.currMode == ADD)
        {
            editLabel.setText("Додати пару");
            editBtn.setText("Додати");
        }
        else if(AdminController.currMode == DELETE)
        {
            editLabel.setText("Видалити пару");
            editBtn.setText("Видалити");
        }
        editBtn.setOnAction(event ->
        {
            filler.register(AdminController.currMode);
            TableViewer.updateTable(AdminController.currTable);
            Commands.closeWindow(editBtn);
        });
    }


    List<IObserver> listeners = new ArrayList<>();

    @Override
    public void addObserver(IObserver observer) {
        listeners.add(observer);
    }

    @Override
    public void notifyObservers() {

        String teacher = teacherBox.getSelectionModel().getSelectedItem() == null ? ""
                :teacherBox.getSelectionModel().getSelectedItem().toString();
        String group = groupBox.getSelectionModel().getSelectedItem() == null ? ""
                :groupBox.getSelectionModel().getSelectedItem().toString();
        String subject = subjectBox.getSelectionModel().getSelectedItem() == null ? ""
                :subjectBox.getSelectionModel().getSelectedItem().toString();
        String audience = audienceBox.getSelectionModel().getSelectedItem() == null ? ""
                :audienceBox.getSelectionModel().getSelectedItem().toString();
        String day = dayBox.getSelectionModel().getSelectedItem() == null ? ""
                :dayBox.getSelectionModel().getSelectedItem().toString();
        String pair = numberBox.getSelectionModel().getSelectedItem() == null ? ""
                :numberBox.getSelectionModel().getSelectedItem().toString();
        ObserverParams observerParams = new ObserverParams(teacher,group, subject, audience, day, pair);

        for (IObserver listener : listeners)
        {
            listener.saveChanges(observerParams);
        }
    }

}
