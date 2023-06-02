package fr.amu.iut.cc3;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @FXML
    Pane Radar;
    @FXML
    TextField comp1;
    @FXML
    TextField comp2;
    @FXML
    TextField comp3;
    @FXML
    TextField comp4;
    @FXML
    TextField comp5;
    @FXML
    TextField comp6;
    @FXML
    Label labErr;
    Circle point1 = new Circle();
    Circle point2 = new Circle();
    Circle point3 = new Circle();
    Circle point4 = new Circle();
    Circle point5 = new Circle();
    Circle point6 = new Circle();
    ObservableList<Circle> tabPoint = FXCollections.observableArrayList();
    Collection<Line> ligneTrace = new ArrayList<Line>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comp1.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointHandle(keyEvent,1, comp1.getText(), point1));
        comp2.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointHandle(keyEvent,2, comp2.getText(), point2));
        comp3.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointHandle(keyEvent,3, comp3.getText(), point3));
        comp4.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointHandle(keyEvent,4, comp4.getText(), point4));
        comp5.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointHandle(keyEvent,5, comp5.getText(), point5));
        comp6.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> pointHandle(keyEvent,6, comp6.getText(), point6));
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    public void pointHandle(KeyEvent event, int comp, String number, Circle point) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (number.matches("[0-9]+")) {
                double nb = Integer.valueOf(number);
                if (nb >= 0 && nb <= 20) {
                    point.setRadius(5);
                    point.setFill(Color.BLACK);
                    if (Radar.getChildren().contains(point)) {
                        point.setCenterX(getXRadarChart(nb, comp));
                        point.setCenterY(getYRadarChart(nb, comp));
                    }
                    else {
                        Radar.getChildren().add(point);
                        point.setCenterX(getXRadarChart(nb, comp));
                        point.setCenterY(getYRadarChart(nb, comp));
                        tabPoint.add(point);
                    }
                }
                else {
                    labErr.setText("Erreur de saisie : \n"+"Les valeurs doivent être entre 0 et 20");
                }
            }
        }
    }
    @FXML
    public void viderButton(){
        labErr.setText("");
        comp1.setText("");
        comp2.setText("");
        comp3.setText("");
        comp4.setText("");
        comp5.setText("");
        comp6.setText("");
        if (Radar.getChildren().contains(point1))
            Radar.getChildren().remove(point1);
        if (Radar.getChildren().contains(point2))
            Radar.getChildren().remove(point2);
        if (Radar.getChildren().contains(point3))
            Radar.getChildren().remove(point3);
        if (Radar.getChildren().contains(point4))
            Radar.getChildren().remove(point4);
        if (Radar.getChildren().contains(point5))
            Radar.getChildren().remove(point5);
        if (Radar.getChildren().contains(point6))
            Radar.getChildren().remove(point6);
        tabPoint.removeAll(tabPoint);
        Radar.getChildren().removeAll(ligneTrace);
    }

    @FXML
    public void Tracer(){
        for (int i=0; i < tabPoint.size(); i+=1){
            Line tmpLine = new Line();
            tmpLine.setStyle("-fx-stroke: black;");
            Radar.getChildren().add(tmpLine);
            ligneTrace.add(tmpLine);
            tmpLine.startXProperty().bind(tabPoint.get(i).centerXProperty());
            tmpLine.startYProperty().bind(tabPoint.get(i).centerYProperty());
            if (i+1 == tabPoint.size()){
                tmpLine.endXProperty().bind(tabPoint.get(0).centerXProperty());
                tmpLine.endYProperty().bind(tabPoint.get(0).centerYProperty());
            }
            else {
                tmpLine.endXProperty().bind(tabPoint.get(i+1).centerXProperty());
                tmpLine.endYProperty().bind(tabPoint.get(i+1).centerYProperty());
            }
        }
    }
}
