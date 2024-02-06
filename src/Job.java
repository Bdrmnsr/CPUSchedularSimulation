public class Job {
public int id;
public JobState State;
public int BurstTime;
public int OriginBurst;
public int RequierdMemory;
public int StartTime;
public int EndTime;


public Job(int id,int BurstTime,int RequierdMemory) {
	this.id = id;
	this.BurstTime = BurstTime;
	this.RequierdMemory = RequierdMemory;
	this.OriginBurst = BurstTime;
	
}

}
