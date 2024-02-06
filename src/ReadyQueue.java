import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;

public class ReadyQueue extends Thread {
	private int selection;// Variable that holds the user choice of an algorithm
	public static int Memory;//available memory in the ready queue 
	public static int TotalMemory;// Total memory of all jobs
	/*a queue initialized to holds jobs that will be added from the JobList to the readyQueue
	 * made static to be used in Scheduling class*/
	public static Queue<Job> ReadyQueue= new LinkedList<Job>();
	
	public ReadyQueue(int selection) {
		Memory = 1024;
		this.selection = selection;
	}

public void takingJob(){

		while(!ReadingJobs.JobList.isEmpty() ) { // iterate on each Job object on Joblist || make sure that there's an object to work with

		if((Memory - ReadingJobs.JobList.get(0).RequierdMemory) >=0){// check if there is a space to add new process in the readyQueue

			Job tmp = ReadingJobs.JobList.remove(0);//Stores the first job in the jobList in an object variable and removes it from the jobList
			tmp.State = JobState.Ready;//Changes the job state to ready before entering the readyQueue
			
			Memory = Memory - tmp.RequierdMemory;//Updates remaining memory in the readyqueue
			TotalMemory += tmp.RequierdMemory;//Adds to the sum of total memory 
			ReadyQueue.add(tmp);//Adds job to the readyQueue
			switch(selection){// upon our selection the switch will select which algorithm to start
				case 1:
					continue;//Repeat until the memory is full and then execute FCFS in else
				case 2:

					Scheduling.tmp[Scheduling.counter++] = ReadyQueue.poll();//Retrieves first job of readyQueue and stores it in RR10 array
					Scheduling.RR10();//Calls RR10 scheduling algorithm
					break;


				case 3:

					Scheduling.MultiLevel(); break; //Calls MLFQ Scheduling algorithm

				default:
					System.out.println("enter valid selection.");//Handles invalid input of user choice

			}

		}
		else {//if there is no space to add new process in the readyQueue
			if(selection == 1) {
				Scheduling.FCFS();// if the memory filled it will call the method here to free memory

			}


			if(selection == 2) {
				while ((Memory - ReadingJobs.JobList.get(0).RequierdMemory) <= 0) {// if the memory filled it will call the method until the memory freed
					for (int i = 0; i < Scheduling.counter; i++) {
						if (Scheduling.tmp[i].State == JobState.Waiting)
							Scheduling.tmp[i].State = JobState.Ready;
						else continue;
					}
					Scheduling.RR10();
				}
				for (int i = 0; i < Scheduling.counter; i++)
					if (Scheduling.tmp[i].State == JobState.Ready)
						Scheduling.tmp[i].State = JobState.Waiting;

			}
			if(!Scheduling.Level2.isEmpty()) Scheduling.RR16();// if the memory filled it will call the method until the memory freed
			if(!Scheduling.Level3.isEmpty()) Scheduling.FCFSMQ();// if the memory filled it will call the method until the memory freed
		}

		}
		if (selection == 1) while (!ReadyQueue.isEmpty()) Scheduling.FCFS();// to do the remaining processes
		if(selection == 2) Scheduling.RR10();// to do the remaining processes
	if(!Scheduling.Level2.isEmpty()) Scheduling.RR16();// to do the remaining processes
	if(!Scheduling.Level3.isEmpty()) Scheduling.FCFSMQ();// to do the remaining processes

}

	@Override
public void run() {
		try {
			Thread.sleep(1042);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	takingJob();
		if(Scheduling.counter != 0)// to prevent dividing by zero
			System.out.println("\n\n"+" "+Scheduling.TotalWaiting + "------------------------"+ Scheduling.TotalTurnaround) ;
			System.out.println("\n"+"The average waiting time is: "+Scheduling.TotalWaiting/(Scheduling.counter*1.0) +" "+"\n"+"The average turnaround time is: "+ Scheduling.TotalTurnaround/(Scheduling.counter*1.0));

	}
		
}

