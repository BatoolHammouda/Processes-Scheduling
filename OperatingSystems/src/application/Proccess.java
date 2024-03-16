package application;


class Proccess implements Comparable<Proccess>{
	private int pid;
	public int arrivalTime;
	public int timeRemaining = 0;
	private int numberOfPreemptions;
	public int waitingTime;
	public int burst;
	int [] cpuBurst;
	int [] ioBurst;
	int finished;
	int started;
	int numOfPreemptions = 0;
	int burstIndex = 0;
	
	public Proccess(Proccess p, int started, int finished) {
		this.pid = p.pid;
		this.finished = finished;
		this.started = started;
	}

	public Proccess(int pid, int arrivalTime, int [] cpuBurst, int [] ioBurst) {
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		// initially the process has not been executed
		this.cpuBurst = cpuBurst;
		this.ioBurst = ioBurst;
		
		this.timeRemaining = cpuBurst[0];
		this.burst = timeRemaining;
		this.numberOfPreemptions = 0;
		this.waitingTime = 0;
	}

	public Proccess(Proccess p) {
		this.pid = p.pid;
		this.timeRemaining = p.timeRemaining;
	}

	
	public Proccess() {
		// TODO Auto-generated constructor stub
	}

	// run the process
	public void run(int time) {
		// if the process has not been executed yet, update its waiting time
		if (timeRemaining == waitingTime) {
			waitingTime = time - arrivalTime;
		}
		
		// update the time remaining
		timeRemaining = Math.max(0, timeRemaining - time);
		// update the number of preemptions
		if (timeRemaining > 0) {
			numberOfPreemptions++;
		}
	}

	@Override
	public String toString() {
		return "Proccess [pid=" + pid + ", timeRemaining=" + timeRemaining + "]";
	}
	
	
	public String toGant() {
		return "Proccess [pid=" + pid + ", start=" + started + ", Finished="+ finished+"]";
	}

	
	public String io() {
		return "Proccess [pid=" + pid + "io="+ ioBurst[burstIndex]+"]";
	}
	
	public String running() {
		return "pid=" + pid ;
	}


	public int getPid() {
		return pid;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	public int getNumberOfPreemptions() {
		return numberOfPreemptions;
	}
	
	public void nextBurst() {
		int io =0;
		if(this.ioBurst.length >0)
			io = this.ioBurst[burstIndex];
		burstIndex++;
		if(burstIndex < cpuBurst.length)
			timeRemaining = io + cpuBurst[burstIndex] ;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	@Override
	public boolean equals(Object obj) {
		
		return ((Proccess)obj).pid == this.pid;
	}

	@Override
	public int compareTo(Proccess o) {
		if(this.started > o.started)
			return 1;
		else if(this.started < o.started)
			return -1;
		else return 0;
	}
	
	
}