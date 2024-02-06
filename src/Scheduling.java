
import java.util.LinkedList;
import java.util.Queue;

public class Scheduling {
	//an integer to store the Waiting time of a process 
	static int waitingtime;
	// an integer to save the time that the last process ended at that will help us to set the start time for any new process
	static int lastFin = 0;
	// A timer which will start from zero and will still incrementing until the last process finish
	static int Timer = 0;
	// a total Waiting Time for all process which is basically = Timer(after finishing) - BurstTime - start time
	static int TotalWaiting = 0;
	// a total TurnAround time for all process which is basically = Timer(after finishing)  - start time
	static int TotalTurnaround = 0;
	// a counter that count number of processes
	static int counter = 0;
	// a Queue that contain each process that not finished at level 1 in MLQ
	static Queue<Job> Level2 = new LinkedList<>();
	// a Queue that contain each process that not finished at level 2 in MLQ
	static Queue<Job> Level3 = new LinkedList<>();
	// an array which used to store processes for RR10
	static Job[] tmp = new Job[30];


	public static void FCFS() {

		if (!ReadyQueue.ReadyQueue.isEmpty()){ // here we check for available process to prevent null exceptions

			Job e = ReadyQueue.ReadyQueue.poll(); 	// poll the process from ReadyQueue to use in algorithm

		if (Timer == 0)		// a condition to check if timer just started to print a start line
			System.out.print("||     ");

		counter++;		// a counter increment because a process have being added
		int bursttemp = e.BurstTime; // temporary integer
		System.out.print("P" + e.id + " " + Timer); // printing the process id with its start time

		for (int i = 0; i < bursttemp; i++) {
			System.out.print("==");
			e.BurstTime--;
			Timer++;
		}
		if (e.BurstTime == 0) {
			lastFin = Timer; // will be used to determine start time for later processes
			System.out.print(Timer + "  "); // printing the end time
			TotalTurnaround += Timer - e.StartTime;
			TotalWaiting += Timer - e.OriginBurst - e.StartTime;
			ReadyQueue.Memory += e.RequierdMemory; // freeing the memory from process
			e.State = JobState.Terminated; // change state to terminated
		}
	}
	}

	public static void MultiLevel() {
		counter++; 	// a counter increment because a process have being added

		if (!ReadyQueue.ReadyQueue.isEmpty()) { // here we check for available process to prevent null exceptions
			Job Process = ReadyQueue.ReadyQueue.poll(); // poll the process from ReadyQueue to use in algorithm

			if (Process.BurstTime > 8) { //check if burst time above time quantum
				Process.BurstTime = Process.BurstTime - 8;

				Timer += 8;
				Level2.add(Process); // and then added to level 2


			} else { // less than time quantum

				Timer += Process.BurstTime;
				lastFin = Timer; // will be used to determine start time for later processes
				waitingtime = Timer - Process.StartTime - Process.OriginBurst;
				TotalWaiting += waitingtime;
				TotalTurnaround += Timer - Process.StartTime;
				ReadyQueue.Memory += Process.RequierdMemory;// freeing the memory from process
				Process.State = JobState.Terminated;// change state to terminated
			}

			System.out.print("|____P" + Process.id + "____|"); // print process ID with finishing time
			System.out.print(Timer);
		}	
	}
	//	}
	public static void RR16() {


		while (!Level2.isEmpty()) { // iterate on each process on level 2

			Job Process = Level2.poll();


			if (Process.BurstTime > 16) {//check if burst time above time quantum

				Process.BurstTime = Process.BurstTime - 16;
				Timer += 16;
				Level3.add(Process); // and then added to level 3


			} else { // less than time quantum

				Timer += Process.BurstTime;
				lastFin = Timer; // will be used to determine start time for later processes
				waitingtime = Timer - Process.StartTime -Process.OriginBurst;
				TotalWaiting += waitingtime;;
				TotalTurnaround += Timer - Process.StartTime;
				ReadyQueue.Memory += Process.RequierdMemory; // freeing the memory from process
				Process.State = JobState.Terminated; // change state to terminated

			}

			System.out.print("|____P" + Process.id + "____|"); // print process ID with finishing time
			System.out.print(Timer);

		}
	}

	public static void FCFSMQ() {

		while (!Level3.isEmpty()) { // iterate on each process on level 3
			Job tmp = Level3.poll();
			Timer += tmp.BurstTime;
			lastFin = Timer;
			TotalTurnaround += Timer - tmp.StartTime;
			waitingtime = Timer - tmp.StartTime -tmp.OriginBurst;
			TotalWaiting += waitingtime;
			System.out.print("|____P" + tmp.id + "____|");
			System.out.print(Timer);
			ReadyQueue.Memory += tmp.RequierdMemory;

		}


	}

	public static void RR10() {

		if (counter == ReadingJobs.JobCounter) {// to see if all processes have been read to iterate regularly RR


			while (ReadyQueue.Memory != 1024) {// until there's process it will iterate like RR

				for (int i = 0; i < counter; i++) {// will iterate in each ready process
					if (tmp[i].State == JobState.Terminated || tmp[i].State == JobState.Waiting) {// if there's waiting process it will be changed to ready || an if it terminated will continue without doing anything
						if (tmp[i].State == JobState.Waiting) {
							tmp[i].State = JobState.Ready;
							continue;
						} else
							continue;
					}

					if (tmp[i].BurstTime > 10 && tmp[i].State == JobState.Ready) {// if it ready and have more than the time quantum
						tmp[i].BurstTime = tmp[i].BurstTime - 10;

						Timer += 10;

						System.out.print("|____P" + tmp[i].id + "____|");
						System.out.print(Timer);


					} else {

						Timer += tmp[i].BurstTime;
						lastFin = Timer;// will be used to determine start time for later processes
						TotalWaiting += Timer - tmp[i].OriginBurst- tmp[i].StartTime;
						tmp[i].BurstTime = 0;
						TotalTurnaround += Timer - tmp[i].StartTime;
						tmp[i].State = JobState.Terminated;// change state to terminated
						ReadyQueue.Memory += tmp[i].RequierdMemory;// freeing the memory from process
						System.out.print("|____P" + tmp[i].id + "____|");
						System.out.print(Timer);

					}

				}

			}
			return;
		}
		/*Same as above but it will start here actually until the counter equals the job counter then
		* it will enter the above process which will be kinda the same */

		for (int i = 0; i < counter; i++) {// will iterate in each ready process
////			
			if (tmp[i].State == JobState.Terminated || tmp[i].State == JobState.Waiting) continue;


			if (tmp[i].BurstTime > 10 && tmp[i].State == JobState.Ready) {
				tmp[i].BurstTime = tmp[i].BurstTime - 10;
				Timer += 10;
				tmp[i].State = JobState.Waiting;
				System.out.print("|____P" + tmp[i].id + "____|");
				System.out.print(Timer);


			} else {

				Timer += tmp[i].BurstTime;
				lastFin = Timer;
				TotalWaiting += Timer - tmp[i].OriginBurst - tmp[i].StartTime;
				tmp[i].BurstTime = 0;
				TotalTurnaround += Timer - tmp[i].StartTime;
				tmp[i].State = JobState.Terminated;
				ReadyQueue.Memory += tmp[i].RequierdMemory;
				System.out.print("|____P" + tmp[i].id + "____|");
				System.out.print(Timer);


			}


		}


	}
}

