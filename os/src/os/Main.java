package os;
import os.*;
import java.util.*;
import java.io.*;
public class Main {
public static void main(String[] args) throws Exception {
		
		//Process p1= new Process();
		//Process p2= new Process();
		//Process p3= new Process();
		Scheduler s= new Scheduler(0,1,2,2,"Program_1.txt","Program_2.txt","Program_3.txt");
		//s.readyQueue.add(p3);
		//s.readyQueue.add(p2);
		//s.readyQueue.add(p1);
		//s.printQueue(s.readyQueue);
		
		s.runScheduler();
	}
}
