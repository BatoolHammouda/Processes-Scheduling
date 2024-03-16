package application;
import java.util.*;
public class RRQueue {
	boolean firstQueue = false;
	//private LinkedList<Proccess> readyQueue ;
	private int timeQuantum;
	int time;
	public List<Proccess> realProccesses = new ArrayList<>();
	public List<Proccess> proccesses;
	List<Proccess> gant = new ArrayList<>();
	boolean played = false;
	Proccess playing = null;
	
	
	
	public RRQueue(List<Proccess> proccesses, int time , int timeQuantum , boolean firstQueue) {	
		this.firstQueue = firstQueue;
		this.proccesses = proccesses;
		this.time = time;
		this.timeQuantum = timeQuantum;	
	}
	
	
	
	
	private Proccess getminProccess() {
		if (firstQueue) {
			realProccesses = new ArrayList<>();
		}
		int index = 0;
		int min = proccesses.get(0).arrivalTime;
		for(int i = 0 ; i < proccesses.size(); ++i) {
			if(proccesses.get(i).arrivalTime <= time && firstQueue) {
				realProccesses.add(new Proccess(proccesses.get(i)));
			}
			if(proccesses.get(i).arrivalTime < min) {
				index=i;
				min = proccesses.get(i).arrivalTime;
			}
		}	if(proccesses.get(index).arrivalTime <= time) {
			Proccess p = proccesses.remove(index);
			return p;
		}
		else return null;
	}
	
	@Override
	public String toString() {
		return "RRQueue [" + (proccesses != null ? "proccesses=" + proccesses : "") + "]";
	}

	
	public String toString1() {
		return "RRQueue [" + (realProccesses != null ? "proccesses=" + realProccesses : "") + "]";
	}

	
	private int getmin() {
		if(proccesses.size() >0) {
		int index = 0;
		int min = proccesses.get(0).arrivalTime;
		for(int i = 0 ; i < proccesses.size(); ++i) {
			if(proccesses.get(i).arrivalTime < min) {
				index=i;
				min = proccesses.get(i).arrivalTime;
			}
		}
			
			return proccesses.get(index).arrivalTime;
		}
		else return 0;
	}
	
	
		public List<Proccess> RoundRobin() { // It will return the list of proccesses that will be passed to the next queue
			List<Proccess> notDone = new ArrayList<>();
			played = false;
			if(firstQueue)
				return RoundRobin1(notDone);
			else {updateQueue1();
				return RoundRobin2(notDone);}
			//return notDone;
		}
		
		private List<Proccess> RoundRobin2(List<Proccess> notDone) {
			
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
				
				 if(p.timeRemaining > timeQuantum) {
					 if(proccessArrived(time+timeQuantum) > 0) {
							proccesses.add(p);
							 time += proccessArrived(time+timeQuantum);
							 played = true;
							 //gant.add(new Proccess(p, started,time));
							 return notDone; 
						}
					 p.timeRemaining -= timeQuantum;
					 time+=timeQuantum;
					 gant.add(new Proccess(p , started,time));
					 notDone.add(p);	 	 
				 }
				 else if(p.timeRemaining > 0) {
					 if(proccessArrived(time+p.timeRemaining) > 0) {
						proccesses.add(p);
						 time += proccessArrived(time+p.timeRemaining);
						 played = true;
						 //gant.add(new Proccess(p, started,time));
						 return notDone; 
					}
					 time += p.timeRemaining;
					 p.timeRemaining = 0;
					 gant.add(new Proccess(p , started, time));
				 }
				 
					 p.nextBurst();
					 if(p.timeRemaining > 0) {
						 p.arrivalTime = time + p.ioBurst[p.burstIndex - 1];
						 proccesses.add(p); 
					}
				 
				 }
			updateQueue1();
			return notDone;
		}
		
		private List<Proccess> RoundRobin1(List<Proccess> notDone) {
			
			if(proccesses.size() > 0) {
				int started = time;
				Proccess p = getminProccess();
				
				
				if(p != null) {
					played = true; 
					playing = p;
					
				if(p.arrivalTime < time) 
					p.waitingTime += time - p.arrivalTime;
				
				 if(p.timeRemaining > timeQuantum) {
					 p.timeRemaining -= timeQuantum;
					 time+=timeQuantum;
					 notDone.add(p);
					 }
				 
				 else {	// If Time Quantum is 10 & Burst Time is 5 for example
					 time += p.timeRemaining;
					 p.timeRemaining = 0;
				 }
				 
				 p.nextBurst();
				 if(p.timeRemaining > 0) {
					 p.arrivalTime = time + p.ioBurst[p.burstIndex - 1];
					 proccesses.add(p);
				 }
				 gant.add(new Proccess(p , started,time));
					 //if(getmin() < time)
						//notDone = RoundRobin1(notDone); // If There Is Proccesses In The ReadyQueue
				 
			}else if(!played)
						played= false;
				}
			
			return notDone;
		}
		
	
		private int proccessArrived(int newTime) {
			int min = 0;
			boolean arrived = false;
			for(Proccess p : realProccesses) {
				if(p.arrivalTime <= newTime)
					if(!arrived) {
					min = p.arrivalTime;
					arrived = true;
					}
					else if(min > p.arrivalTime)
						min = p.arrivalTime;
			}
				
			return min;
		}
		
		public void updateQueue1() {
			
			for(int j = 0 ; j < realProccesses.size(); ++j) {boolean found = false;
				Proccess p = realProccesses.get(j);
				if(p.timeRemaining == 0) {
					realProccesses.remove(p);
					break;
				}
				
				for(int i = 0 ; i < proccesses.size(); ++i) {
					if(p.equals( proccesses.get(i) )) 
							found = true;
						
				}
				if(found)
					realProccesses.remove(p);

			}
		}

}
