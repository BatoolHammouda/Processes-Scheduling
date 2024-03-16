package application;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class SRTF_Queue {

	
	public LinkedList<Proccess> readyQueue ;
	//private int timeQuantum;
	int time;
	private Simulator simulator;
	public List<Proccess> proccesses;
	List<Proccess> gant = new ArrayList<>();
	boolean played = false;
	int alpha;
	Proccess playing = null;
	
	
	@Override
	public String toString() {
		return "SRTF_Queue [" + (proccesses != null ? "proccesses=" + proccesses : "") + "]";
	}

	public SRTF_Queue(List<Proccess> proccesses, int time , Simulator simulator, int alpha) {
		this.proccesses = proccesses;
		this.time = time;
		//this.timeQuantum = timeQuantum;	
		this.simulator = simulator;
		this.alpha = alpha ;
	}
	
	public List<Proccess> SRTF(){
		played = false ;
		return this.SRTF(new ArrayList<>());
	}
	
	private List<Proccess> SRTF(List<Proccess> notDone) {
		
		if(proccesses.size() > 0) {
			int started = time;
			
			Proccess p = getminProccess();
			if(p != null) 
				played = true;
	
			else 
				 return notDone;
			
			// Terminate If A Proccess Entered Queue1
			
			playing = p;
			
			if(p.arrivalTime < time) 
				p.waitingTime += time - p.arrivalTime;
			
			 while(p != null && p.timeRemaining > 0) {
				 p.timeRemaining -= 1;
				 time+=1;
				 
				 updateReadyQueue();
				 
				
				 
				 if(proccessArrived(time)) {
					 readyQueue.add(p);
					 proccessArrived(time);
					 played = true;
					 gant.add(new Proccess(p, started,time));
					 return notDone; 
				}
				 
				 if(getMin() < p.timeRemaining) {
					 gant.add(new Proccess(p , started,time));
					 p.numOfPreemptions++;
					 if(p.numOfPreemptions < alpha)
						 readyQueue.add(p);
					 else
						 notDone.add(p);
					 return notDone;
					 //p = getminProccess();
					 //started = time;
				 }
				 
				 if(p.timeRemaining == 0) {
					 p.nextBurst();
					 if(p.timeRemaining > 0) {
						 p.arrivalTime = time + p.ioBurst[p.burstIndex - 1];
						 readyQueue.add(p);
					}
					 return notDone;
					 		 }
			 }
				 
				 
			 
			 }
		return notDone;
	}
	
	public Proccess getminProccess() {
		int min = Integer.MAX_VALUE;
		Proccess proccess = null;
		for(Proccess p : readyQueue) 
			if(p.timeRemaining < min) {
				min = p.timeRemaining;
				proccess = p;
			}
		if(proccess != null)
		this.proccesses.remove(proccess);
		return proccess;
	}
	
	public int getMin() {
		
		int min = Integer.MAX_VALUE;		
		for(Proccess p : readyQueue) 
			if(p.timeRemaining < min) 
				min = p.timeRemaining;

		return min;		
	}
	
	
	public void updateReadyQueue() {
		for (Proccess p : proccesses)
			if(p.arrivalTime <= time) {
				readyQueue.add(p);
				proccesses.remove(p);
			}
	}
	
	
	
	private boolean proccessArrived(int newTime) {
		int min = 0;
		boolean arrived = false;
		for(Proccess p : simulator.queue1.proccesses) {
			if(p.arrivalTime <= newTime)
				if(!arrived) {
				min = p.arrivalTime;
				arrived = true;
				}
				else if(min > p.arrivalTime)
					min = p.arrivalTime;
		}
		
		for(Proccess p : simulator.queue2.proccesses) {
			if(p.arrivalTime <= newTime)
				if(!arrived) {
				min = p.arrivalTime;
				arrived = true;
				}
				else if(min > p.arrivalTime)
					min = p.arrivalTime;
		}
		return min > 0;
	}
}
