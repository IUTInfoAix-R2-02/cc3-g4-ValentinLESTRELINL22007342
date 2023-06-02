package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @FXML
    Pane radar;
    @FXML
    TextField comp1 = new TextField();
    @FXML
    TextField comp2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comp1.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointInRadar(keyEvent, 1, comp1.getText()));
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    private void pointInRadar(KeyEvent event, int competence, String number){
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (number.matches("[0-9]+")) {
                double nb = Integer.valueOf(number);
                if (nb >= 0 && nb <= 20){
                    Circle pointGraphe = new Circle();
                    pointGraphe.setCenterY(getYRadarChart(nb, competence));
                    pointGraphe.setCenterX(getXRadarChart(nb, competence));
                    pointGraphe.setStyle("-fx-background-color: black");
                    pointGraphe.setRadius(10.00);
                }
            }
        }
    }

}
