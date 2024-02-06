import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadingJobs extends Thread{
	public String filename;
	/*an array initialized to holds jobs that will be read in the file
	 * made static to be used in ReadyQueue class*/
	public static ArrayList<Job> JobList = new ArrayList<Job>();
	/*Counter to keep track of job numbers maybe deleted later*/
	static int JobCounter;
	
	public ReadingJobs(String filename) {
		this.filename = filename;
		
	}
	
	public void readFileAndAnalyse(String filename)  {
		File f = new File(filename);
		Scanner text = null;
		try {
			text = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		while(text.hasNextLine()) { //Loop through each line in the text file
			String a = text.nextLine(); //Store each line in the variable
			String[] Line = a.split("[^0-9]"); // Formatting the line to take only the numbers as substrings
			
				Job tmp = new Job(Integer.parseInt(Line[0]),Integer.parseInt(Line[1]),Integer.parseInt(Line[2]));//Converts the substring into an integer and initlizes the job object
				tmp.State = JobState.Waiting;//Changes the state of the job to waiting before adding to the job list
				
				JobList.add(tmp);//Add the job to JobList
				JobCounter++;//increase the counter for number of jobs
				}
			for (Job x : JobList) {// print all jobs in job list with their id, burstTime, and required memory
				System.out.println(x.id + " " + x.BurstTime + " " + x.RequierdMemory);
			}
			
			}
	
	public void run() { // Start a new Thread
		readFileAndAnalyse(filename);
	}
		}
