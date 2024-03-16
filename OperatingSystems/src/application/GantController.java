package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class GantController {

	Controller controller;
	private Simulator simulator;
	@FXML
	TextArea gant1_txt;
	@FXML
	TextArea gant2_txt;
	@FXML
	TextArea gant3_txt;
	@FXML
	TextArea gant4_txt;
	@FXML
	public void initialize() {
		
	}
	
	
	public void tableCreater() {
		this.simulator = controller.simulator;
		ArrayList<Proccess> gant1 = (ArrayList<Proccess>) simulator.queue1.gant;
		ArrayList<Proccess> gant2 = (ArrayList<Proccess>) simulator.queue2.gant;
		ArrayList<Proccess> gant3 = (ArrayList<Proccess>) simulator.queue3.gant;
		ArrayList<Proccess> gant4 = (ArrayList<Proccess>) simulator.queue4.gant;
		StringBuilder sb = new StringBuilder();
		Collections.sort(gant1);
		Collections.sort(gant2);
		Collections.sort(gant3);
		Collections.sort(gant4);
		
		for(Proccess p : gant1)
			sb.append(p.toGant());
		gant1_txt.setText(sb.toString());
		
		sb = new StringBuilder();
		for(Proccess p : gant2)
			sb.append(p.toGant());
		gant2_txt.setText(sb.toString());
		
		sb = new StringBuilder();
		for(Proccess p : gant3)
			sb.append(p.toGant());
		gant3_txt.setText(sb.toString());
		
		sb = new StringBuilder();
		for(Proccess p : gant4)
			sb.append(p.toGant());
		gant4_txt.setText(sb.toString());
		
		gant1_txt.setText(sb.toString());
	}
	
}
