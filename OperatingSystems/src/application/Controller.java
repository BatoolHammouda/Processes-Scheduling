package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;

public class Controller {
	@FXML
	TableView<String> tv1;
	@FXML
	TableView<String> tv2;
	@FXML
	TableView<String> tv3;
	@FXML
	TableView<String> tv4;
	@FXML
	TextArea queue1;
	@FXML
	TextArea queue2;
	@FXML
	TextArea queue3;
	@FXML
	TextArea queue4;
	@FXML
	TextField runningProccess;
	@FXML
	TextField time_txt;
	@FXML
	TextField ioProccess;
	@FXML
	AnchorPane ap;
	@FXML
	Button secondNext;
	@FXML
	Button next;
	@FXML
	Button pause;
	@FXML
	Button play;
	@FXML
	TextField numOfProccesses;
	@FXML
	TextField maxArrival;
	@FXML
	TextField maxNumCpuBurst;
	@FXML
	TextField minIO;
	@FXML
	TextField maxIO;
	@FXML
	TextField minCPU;
	@FXML
	TextField maxCPU;
	@FXML
	TextField q1;
	@FXML
	TextField q2;
	@FXML
	TextField alpha;
	@FXML
	Label labelError1;
	@FXML
	Label labelError2;
	@FXML
	Label labelError3;
	@FXML
	Label labelError4;
	@FXML
	Label labelError5;
	@FXML
	Label labelError6;
	@FXML
	Label labelError7;
	@FXML
	Label labelError8;
	@FXML
	Label labelError9;
	@FXML
	Label labelError10;
	@FXML
	Simulator simulator;
	LinkedList<Proccess> proccesses = new LinkedList<>();
	StringBuilder sb = new StringBuilder();
	boolean done = false;
	/////////////////////
	private Stage stage;
	private Scene scene;
	//private Parent root;
	
	
	@FXML
	private void initialize() {
		pause = new Button();
		
		//runningProccess = new TextField();
		//ioProccess = new TextField();
	}
	
	
	@FXML
	public void next(ActionEvent event) {
		boolean accessGranted = true;
		if(numOfProccesses.getText() == null || !isNum(numOfProccesses.getText().trim())) {
			numOfProccesses.setText("");
			labelError1.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		if(maxArrival.getText() == null || !isNum(maxArrival.getText().trim())) {
			maxArrival.setText("");
			labelError2.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		if(maxNumCpuBurst.getText() == null || !isNum(maxNumCpuBurst.getText().trim()) || Integer.parseInt(maxNumCpuBurst.getText().trim()) <= 0) {
			maxNumCpuBurst.setText("");
			labelError3.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		
		if(minIO.getText() == null || !isNum(minIO.getText().trim())) {
			minIO.setText("");
			labelError4.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		
		if(maxIO.getText() == null || !isNum(maxIO.getText().trim())) {
			maxIO.setText("");
			labelError5.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		
		if(minCPU.getText() == null || !isNum(minCPU.getText().trim())) {
			minCPU.setText("");
			labelError6.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		
		
		if(maxCPU.getText() == null || !isNum(maxCPU.getText().trim())) {
			maxCPU.setText("");
			labelError7.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			accessGranted = false;
		}
		if(accessGranted) {
			generateFile();
			switchToScene2(event);
			showfile(sb.toString());
		}
	}
	
	public void generateFile() {
		sb = new StringBuilder();
		int numOfprossecces = Integer.parseInt(numOfProccesses.getText().trim());
		int maxArrivalTime = Integer.parseInt(maxArrival.getText().trim());
		int maxBurst = Integer.parseInt(maxNumCpuBurst.getText().trim());
		int minio = Integer.parseInt(minIO.getText().trim());
		int maxio = Integer.parseInt(maxIO.getText().trim());
		int mincpu = Integer.parseInt(minCPU.getText().trim());
		int maxcpu = Integer.parseInt(maxCPU.getText().trim());
		for(int i = 0; i < numOfprossecces; ++i) {
			sb.append(i + " ");
			int arrival = (int) (Math.random()*maxArrivalTime);
			sb.append(arrival+" ");
			int burst = getInRange(1, maxBurst);
			for(int j = 0 ; j < burst; ++j) {
				int cpu;
				int num = (j+1) ;
				if(num == burst) {
					cpu = getInRange(mincpu, maxcpu);
					sb.append(cpu + "\n");
					break;
				}
				cpu = getInRange(mincpu, maxcpu);
				sb.append(cpu + " ");
				int io = getInRange(minio , maxio);
				sb.append(io + " ");	
			}
			
		}
		try {
			FileWriter f = new FileWriter("data.txt");
			f.write(sb.toString());
			f.flush();
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	
	public int getInRange(int min , int max) {
		int result = (int) (Math.random()*max);
		while(result < min) {
			result = (int) (Math.random()*max);
		}
		return result;
	}
	
	public void switchToScene2(ActionEvent event) {
		try {	
			FXMLLoader fxmlLoader = new FXMLLoader();
			
	        fxmlLoader.setLocation(getClass().getResource("Scene2.fxml"));
			Parent root = fxmlLoader.load();fxmlLoader.setController(this);
			stage = (Stage) ((Node)event.getSource()).getScene().getWindow() ;
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("Multi Level Queu");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void showfile(String text) {
		BorderPane p2 = new BorderPane();
		TextArea TA = new TextArea();
		p2.setCenter(TA);

		TA.setPadding(new Insets(8, 8, 8, 8));
		TA.setText("File Content");
		TA.setText(text);

		Stage newStage = new Stage();
		newStage.setTitle("WORKLOAD FILE");

		Scene scene3 = new Scene(p2, 550, 480);
		newStage.setScene(scene3);
		newStage.show();
	}
	public boolean isNum(String num){
		try {
			Integer.parseInt(num);
			}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	//									BEGIN SIMULATOR
	//
	//
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	
		
//		public void assignProcessesToQueues() {
//			for (Proccess proccess : proccesses) {
//				simulator.queue1.add(proccess);
//			}
//		}
		
		@FXML
		public void secondNext(ActionEvent event) {
			boolean accessGranted = true;
			if(q1.getText() == null || !isNum(q1.getText().trim())) {
				q1.setText("");
				accessGranted = false;
				labelError8.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			}
			if(q2.getText() == null || !isNum(q2.getText().trim())) {
				q2.setText("");
				accessGranted = false;
				labelError9.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			}
			if(alpha.getText() == null || !isNum(alpha.getText().trim())) {
				alpha.setText("");
				accessGranted = false;
				labelError10.setText("YOU SHOULD ENTER VALID INTEGER VALUE!");
			}
			if(accessGranted) {
				switchTosimulator(event);
				
			}
			
		}
		
		@FXML
		public void switchTosimulator(ActionEvent event) {
			readFile();
			
			//simulator.setTextFields(Q1, Q2, Q3, runningProccess, ioProccess);
			//simulator.updateQueus();
			//Thread thread = new Thread(simulator);
			//thread.start();
			//queue1.setText("switching...");
			//simulator.start();
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setController(this);
		        fxmlLoader.setLocation(getClass().getResource("simulator.fxml"));
				Parent root = fxmlLoader.load();
				stage = (Stage) ((Node)event.getSource()).getScene().getWindow() ;
				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				//System.out.println(queue1.getText());
				stage.setTitle("Multi Level Queu Simulator");
				stage.setScene(scene);
				stage.show();
				////////////////
				simulator = new Simulator( Integer.parseInt(q1.getText().trim()), Integer.parseInt(q2.getText().trim())
						, Integer.parseInt(alpha.getText().trim()), proccesses, this);//pause ,ap);
			//	simulator.assignProcessesToQueues();
				
				simulator.start();simulator.updateQueus();
				//////////////////
				//queue1 = new TextArea();
				//runningProccess.setText("initialize");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void readFile() {
			try {
				Scanner input = new Scanner(new File("data.txt"));
				while(input.hasNextLine()) {
					String [] tokens = input.nextLine().trim().split(" ");
					int id = Integer.parseInt(tokens[0].trim());
					int arrivalTime = Integer.parseInt(tokens[1].trim());
					int [] cpu = new int [tokens.length-2];
					int [] io = new int [tokens.length-3];
					for(int i = 2 ; i < tokens.length/2 ; ++i) {
						int num = (i+1) * 2 ;
						if(num >= tokens.length || num + 1 >= tokens.length) {
							cpu[i-2] = Integer.parseInt(tokens[i+1].trim());
							break;
						}
						cpu[i-2] = Integer.parseInt(tokens[i].trim());
						io[i-2] = Integer.parseInt(tokens[i+1].trim());
					}
					this.proccesses.add(new Proccess(id, arrivalTime, cpu, io));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(proccesses);
		}
		
		@FXML
		public void thirdNext(ActionEvent event) {
			if(simulator.done) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				
				fxmlLoader.setLocation(getClass().getResource("GantChart.fxml"));
				Parent root;
				
				root = fxmlLoader.load();
				GantController gantController = fxmlLoader.getController();
				gantController.controller = this;
				gantController.tableCreater();
				stage = (Stage) ((Node)event.getSource()).getScene().getWindow() ;
				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

				stage.setTitle("Results");
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		
		
		
		@FXML
		public void play(ActionEvent event) {synchronized (simulator) {
			play.setDisable(true);
			play.setVisible(false);
			simulator.hold = false;
			pause.setDisable(false);
			pause.setVisible(true);
			

				simulator.notifyAll();	
			}
		}
	
		@FXML
		public void hold(ActionEvent event) {	
			
			synchronized (event) {
			play.setDisable(false);
			play.setVisible(true);
			
			pause.setDisable(true);
			pause.setVisible(false);}
				simulator.hold();
			
			System.out.println("hold!!");
			
			}
}