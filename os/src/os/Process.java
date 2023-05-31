package os;
import java.util.*;
import java.io.*;
import java.lang.*;
import os.*;
public class Process {
	
	private  int  processID;
    private int requiredMemorySize;
	private PCB pcb;
	private ArrayList<String> programCode;
	private  static int counter= 1;
	private static ArrayList<Process> finder=new ArrayList<Process>();;
	
	public Process()  {
		
		this.processID=counter;
		counter++;
		this.pcb=new PCB(this.processID);
		//this.filePath=path;
		programCode= new ArrayList<String>();
		this.requiredMemorySize=programCode.size();
		finder.add(this);
		
	}

	public ArrayList<String> getProgramCode(String filePath) throws Exception {
	FileReader fr = new FileReader(filePath);
	BufferedReader br = new BufferedReader(fr);
	String s;
	while ((s = br.readLine()) != null) {
		programCode.add(s);
	}
	br.close();
	fr.close();
	return programCode;
	}


	public int getProcessID() {
		return processID;
	}
	
	public PCB getPcb() {
		return pcb;
	}


	public int getRequiredMemorySize() {
		return requiredMemorySize;
	}

	public void setRequiredMemorySize(int requiredMemorySize) {
		this.requiredMemorySize = requiredMemorySize;
	}
	
	public static Process Findprocess(int processID) {
		for (int i=0;i<finder.size();i++) {
			if (finder.get(i).getProcessID()==processID) {
				return finder.get(i);
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		Process p= new Process();
		
	}
}
