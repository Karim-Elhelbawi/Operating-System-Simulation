package os;
import java.io.*;
import java.util.*;
import os.*;

public class System_Call {
	Scheduler scheduler;
	
	public System_Call(Scheduler s) {
		this.scheduler=s;
	}
	
	public  String[] readFileDisk(String filepath) {
		  try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
	            String line;
	            StringBuilder program = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                program.append(line).append(System.lineSeparator());
	            }
	            return program.toString().split(System.lineSeparator());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	
	public void writeFile(String name, String data) throws IOException {
		FileWriter file = new FileWriter(name + ".txt");
		file.write(data + "");
		file.close();
	}
	
	public String readFile(String path) throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String s;
		String result = "";
		while ((s = br.readLine()) != null) {
			result += s;
		}
		br.close();
		fr.close();
		return result;
	}
	
	public void writeFileDisk(String name,String data)throws IOException {
		FileWriter file = new FileWriter(name + ".txt", true);
		file.write(data + "");
		file.close();
	}
	
	public void printOnScreen(String message) {
		 System.out.println("Printing to screen: " + message);
	}
	
	public String takeInput() {
		System.out.print("Please enter a value: ");
		Scanner sc = new Scanner(System.in);
		String result = sc.nextLine();
		return result;
	}
	
	public Object readFromMemory(int location) {
		return scheduler.getMemory()[location].getData();
	}
	
	public void writeToMemory(Object data,int location) {
		scheduler.getMemory()[location].setData(data);
	}
}
