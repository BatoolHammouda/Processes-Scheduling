package application;


import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Simulator extends Thread implements Runnable {

	
	ArrayList<Proccess> io = new ArrayList<>();
	Proccess playing;
	Proccess ioProccess;
// declare all queues
	RRQueue queue1;
	RRQueue queue2;
	SRTF_Queue queue3;
	FCFS_Queue queue4;
	List<Proccess> proccesses;
	private int queueNum = 1;
	AnchorPane ap;
	Controller controller;
// declare the time quantum of each queue
	int q1, q2, time = 0;
// declare the alpha value
	double alpha;
	boolean hold = false;
	boolean done = false;

// declare the max arrival time
	int maxArrivalTime;
// declare the max number of CPU bursts
	int maxNoCPUBurst;
	private final double delay = 1;
// construct the Simulator object
	public Simulator(int q1, int q2, double alpha, List<Proccess> proccesses, Controller controller) {
		this.q1 = q1;
		this.q2 = q2;
		this.alpha = alpha;
		this.controller = controller;
		this.ap= controller.ap;
		this.proccesses = proccesses;
		queue1 = new RRQueue(proccesses, Integer.parseInt(controller.q1.getText().trim()), Integer.parseInt(controller.q2.getText().trim()), true);
		queue2 = new RRQueue(new ArrayList<Proccess>(), Integer.parseInt(controller.q1.getText().trim()), Integer.parseInt(controller.q2.getText().trim()), false);
		queue3 = new SRTF_Queue(new ArrayList<Proccess>(), time, this, Integer.parseInt(controller.alpha.getText().trim()));
		queue4 = new FCFS_Queue(new ArrayList<Proccess>(), time, this);
	}
	
	public String queue(int number){
		if(number == 1) 
			return queue1.toString1();
		
		else if(number == 2) 
			return queue2.toString();
		
		else if(number == 3)
			return queue3.toString();
		else
			return queue4.toString();
	}
	
	
	public void hold () {
		this.hold =true;
	}
//	public void assignProcessesToQueues() {
//		for(Proccess proccess : proccesses)
//		queue1.add(proccess);
//	}

// simulate the multilevel feedback queue scheduling algorithm
	public void simulate(String fileName) {
// read the file
		//List<Process> processes = readInput(fileName);
// assign the processes to the queues
		//assignProcessesToQueues(proccesses);
// run the simulationf
		//runSimulation();
	}



	@Override
	public void run() {
		queue1 = new RRQueue(proccesses, Integer.parseInt(controller.q1.getText().trim()), Integer.parseInt(controller.q2.getText().trim()), true);
		queue2 = new RRQueue(new ArrayList<Proccess>(), Integer.parseInt(controller.q1.getText().trim()), Integer.parseInt(controller.q2.getText().trim()), false);
		queue2.realProccesses = proccesses;
		queue3 = new SRTF_Queue(new ArrayList<Proccess>(), time, this, Integer.parseInt(controller.alpha.getText().trim()));
		queue4 = new FCFS_Queue(new ArrayList<Proccess>(), time, this);
		ArrayList<Proccess> p = new ArrayList<>();
		//System.out.println("Thread");
		synchronized (this) {
			
				boolean empty = false;
	          notify();
	         // System.out.println("notified");
	        //System.out.println(queue1.size());
		//Thread thread = new Thread();
		boolean enter = true;
		//keep running the simulation until all queues are empty
		while (true) {
			enter = true;
			int previuosTime = time;
			empty = false;
			//process processes in queue 1
			if (enter) {
					queue1.time = time;
					p = (ArrayList<Proccess>) queue1.RoundRobin(); // run and return the unfinished proccess
					
					if(queue1.played)
						playing = queue1.playing;
					
					time = queue1.time;
					
					if(p.size() > 0) {
						for(Proccess proccess : p) {
							proccess.arrivalTime = time;
							queue2.proccesses.add(proccess);
						}
						empty |= queue1.proccesses.size()==0;
					}
					
			
				
			}
				//if the process did not finish its CPU burst within 10 time-quanta, move it to queue 2
				if (enter) {

					queue2.time = time;
					p = (ArrayList<Proccess>) queue2.RoundRobin(); // run and return the unfinished proccess
					if(queue2.played) {
						enter = false;
						playing = queue2.playing;
					}
					time = queue2.time;
					
					if(p.size() > 0) 
						for(Proccess proccess : p) {
							proccess.arrivalTime = time;
							queue3.proccesses.add(proccess);
						}
					empty |= queue2.proccesses.size()==0;
					 
					
			}
				
				
			//process processes in queue 3
			else if (enter) {

				queue3.time = time;
			
				p = (ArrayList<Proccess>) queue3.SRTF(); // run and return the unfinished proccess
				if(queue3.played) {
					enter = false;
					playing = queue3.playing;
				}
				time = queue3.time;
				
				if(p.size() > 0) 
					for(Proccess proccess : p) {
						proccess.arrivalTime = time;
						queue4.proccesses.add(proccess);
					}
					empty |= queue3.proccesses.size()==0 | queue3.readyQueue.isEmpty();
				
			}
				
				//if the process did not finish its CPU burst within 10 time-quanta, move it to queue 4
				if (enter) {

					queue4.time = time;
					
					queue4.FCFS(); // run and return the unfinished proccess
					if(queue4.played) {
						enter = false;
						playing = queue4.playing;

					}
					time = queue4.time;
					
					if(p.size() > 0) 
						for(Proccess proccess : p) {
							proccess.arrivalTime = time;
							queue4.proccesses.add(proccess);
						}
						empty |= queue4.proccesses.size()==0;
					
				}
				
				if(hold) {
				synchronized (this) {
					
					
					try {
						this.wait();
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}}
			}
				if(this.playing != null)
				controller.runningProccess.setText(playing.running());
				else
					controller.runningProccess.setText("");
				try {
					this.wait((int) (1000*delay));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			updateQueus();
			if(empty && proccesses.isEmpty())
				break;

			
			
			if(previuosTime == time)
				time ++; // If There Is No Proccesses In Any Queue
			controller.time_txt.setText("time : "+time);
		}
		}
		updateQueus();
		done = true;
		
	}

	public void updateQueus() {
		//System.out.println("update");
		//controller.queue1.setText("hello");
		
		//updateQueue1();
		controller.queue1.setText(queue(1).toString().substring(1,queue(1).toString().length()-1));
		controller.queue2.setText(queue(2).toString().substring(1,queue(2).toString().length()-1));
		controller.queue3.setText(queue(3).toString().substring(1,queue(3).toString().length()-1));
		controller.queue4.setText(queue(4).toString().substring(1,queue(4).toString().length()-1));
	//queue1.realProccesses = new ArrayList<>();
	}




//print the Gantt chart
	public String printGanttChart(List<Proccess> proccesses) {
		StringBuilder sb = new StringBuilder();
		sb.append("Gantt chart: \n");
		for (Proccess proccess : proccesses) {
			sb.append(proccess.getPid() + " ");
		}
		return sb.toString();
	}

//print the CPU utilization
	private int printCPUUtilization(int time) {
		int totalWaitingTime = 0 ;
		for (Proccess proccess : proccesses) {
			totalWaitingTime += proccess.burst; // error
		}
		return totalWaitingTime / time;
	}

//print the average waiting time
	private void printAverageWaitingTime(List<Proccess> proccesses) {
		int totalWaitingTime = 0;
		for (Proccess proccess : proccesses) {
			totalWaitingTime += proccess.burst; // error
		}
		System.out.println("Average waiting time: " + totalWaitingTime / (double) proccesses.size());
	}}
