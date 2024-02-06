import java.io.*;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Select Algorithm to use:");
		System.out.println("1) FCFS");
		System.out.println("2) RR10");
		System.out.println("3) MLFQ");
		int Selection = input.nextInt();
		ReadingJobs test = new ReadingJobs("C:\\Users\\majoo\\OneDrive\\سطح المكتب\\job.txt");
		ReadyQueue Ready = new ReadyQueue(Selection);

 		test.start();
		Ready.start();

		
	}

}

