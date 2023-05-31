package os;
import java.io.*;
import java.util.*;
import os.*;

public class Scheduler {
	private Queue<Integer> readyQueue;
	private Queue<Integer> blockedQueue;
	private MemoryWord[] memory;
	private int timeSlice;
	private  Mutex userInput;
	private  Mutex userOutput;
	private  Mutex fileAccess;
	private int counter;
	private int runningID;
	private int arrivalTiming1;
	private int arrivalTiming2;
	private int arrivalTiming3;
	private int timeTaken;
	private int counter1;
	private int counter2;
	private String path1;
	private String path2;
	private String path3;
	
	
	
	public Scheduler( int timing1, int timing2, int timing3, int timeslice,String path1, String path2, String path3) {
		
		readyQueue = new LinkedList();
        blockedQueue = new LinkedList();
        this.memory= new MemoryWord[40];
        this.timeSlice=timeslice;
        this.userOutput = new Mutex();
		this.userInput = new Mutex();
		this.fileAccess = new Mutex();
		this.counter=0;
		this.runningID=-1;
		this.arrivalTiming1=timing1;
		this.arrivalTiming2=timing2;
		this.arrivalTiming3=timing3;
		this.timeTaken=0;
		this.counter1=0;
		this.counter2=0;
		
		this.path1 = path1;
		this.path2 = path2;
		this.path3 = path3;
	}
	
	
	public void runScheduler() throws Exception{		if (this.timeSlice==0) {
			System.out.print("Time slice has to be longer tha 0");
			return;
		}
		this.initializeMemory();
		System_Call systemCall = new System_Call(this);
		boolean finished= false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		Interpreter I= new Interpreter();
		
		while(!finished) {
			
			
			System.out.println(flag1+""+flag2+""+flag3);
			System.out.println("cycle: " + counter);
			//admission
			if (runningID != -1) {
				if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) 
					System.out.println("processRunning: " + ((int) Integer.parseInt(memory[0].getData() + "")));
					else {
					if ((int) Integer.parseInt(memory[20].getData() + "") == runningID) 
						System.out.println("processRunning: " + ((int) Integer.parseInt(memory[20].getData() + "")));
					}
				}
			else {
				System.out.println("No Process running");
			}
			
			if (counter==arrivalTiming1) {
			System.out.println("Process 1 arrived");
			Process p1= new Process();
			ArrayList<String> instructions= p1.getProgramCode(path1);
			p1.getPcb().setState(ProcessState.ready);
			p1.getPcb().setProgramCounter(0);
			Object temp = null;
			String nestedFlag = "false";
			//finding space for process
			boolean foundSpace=this.allocate(p1,temp,nestedFlag,instructions);
			if (foundSpace != true) {
				this.allocateSwap(systemCall, p1, temp, nestedFlag, instructions);
			}
			readyQueue.add(p1.getProcessID());
				
		}
			if (counter==arrivalTiming2) {
				System.out.println("Process 2 arrived");
				Process p2= new Process();
				ArrayList<String> instructions= p2.getProgramCode(path2);
				p2.getPcb().setState(ProcessState.ready);
				p2.getPcb().setProgramCounter(0);
				Object temp = null;
				String nestedFlag = "false";
				//finding space for process
				boolean foundSpace=this.allocate(p2,temp,nestedFlag,instructions);
				if (foundSpace != true) {
					this.allocateSwap(systemCall, p2, temp, nestedFlag, instructions);
				}
				readyQueue.add(p2.getProcessID());
			}
			
			if (counter==arrivalTiming3) {
				System.out.println("Process 3 arrived");
				Process p3= new Process();
				ArrayList<String> instructions= p3.getProgramCode(path3);
				p3.getPcb().setState(ProcessState.ready);
				p3.getPcb().setProgramCounter(0);
				Object temp = null;
				String nestedFlag = "false";
				//finding space for process
				boolean foundSpace=this.allocate(p3,temp,nestedFlag,instructions);
				if (foundSpace != true) {
					this.allocateSwap(systemCall, p3, temp, nestedFlag, instructions);
				}
				readyQueue.add(p3.getProcessID());
			}
			
			//start scheduling
			if (!(flag1&& flag2 && flag3)) {
				if (runningID==-1) {
					//dispatch
					
					if (!readyQueue.isEmpty()) {
						
						runningID=readyQueue.remove();
						Process p=Process.Findprocess(runningID);
						if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
							p.getPcb().setState(ProcessState.running);
							systemCall.writeToMemory(p.getPcb().getState(), 1);
							System.out.println("A new process dispatched.");
							System.out.println("process running: " + runningID);
							System.out.print("Ready Queue: ");
							this.printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
							
						}
						else if ((int) Integer.parseInt(memory[20].getData() + "") == runningID) {
							p.getPcb().setState(ProcessState.running);
							systemCall.writeToMemory(p.getPcb().getState(), 21);
							System.out.println("A new process dispatched.");
							System.out.println("process running: " + runningID);
							System.out.print("Ready Queue: ");
							this.printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						}
						else {
							this.Swap(systemCall);
							if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
							p.getPcb().setState(ProcessState.running);
							systemCall.writeToMemory(p.getPcb().getState(), 1);
							}
							else {
								p.getPcb().setState(ProcessState.running);
								systemCall.writeToMemory(p.getPcb().getState(), 21);
							}
							System.out.println("A new process dispatched.");
							System.out.println("process running: " + runningID);
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						}
					}else {
						this.getReadyQueue().add(this.getBlockedQueue().remove());	
					}
							
				}else {
					//runningID not -1
						if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
							preemption(systemCall);
						}
						else if ((int) Integer.parseInt(memory[20].getData() + "") == runningID) {
							preemption(systemCall);
						}
						else {
							this.Swap(systemCall);
							
							if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
								preemption(systemCall);
							}
							else {
								preemption(systemCall);
							}
							
						}
					}
				//exceuting
				if (runningID!=-1) {
					if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
						
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 10;//have to update in pcb
						String nextInstruction = (String) systemCall.readFromMemory(x);
						System.out.println("Process " + runningID + " currently executing " + nextInstruction);
						I.execute(nextInstruction, this, runningID);
						if (((String) systemCall.readFromMemory(6)).equals("false")) {
							int y = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 1;
							systemCall.writeToMemory(y , 2); //have to update in pcb
							System.out.println("PC UPDATED");
						}
						
						this.setTimeTaken(this.getTimeTaken() + 1);
						if (( systemCall.readFromMemory(1)).equals(ProcessState.blocked)) { //set in pcb
							runningID = -1;
							this.setTimeTaken(0);
							
						}
						System.out.println("Queues after executing: ");
						System.out.print("Ready Queue: ");
						printQueue(readyQueue);
						System.out.println();
						System.out.print("Blocked Queue: ");
						printQueue(blockedQueue);
						System.out.println();
					}
					else if ((int) Integer.parseInt(systemCall.readFromMemory(20) + "") == runningID){
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 30;//have to update in pcb
						String nextInstruction = systemCall.readFromMemory(x) + "";
						System.out.println("Process " + runningID + " currently executing " + nextInstruction);
						I.execute(nextInstruction, this, runningID);
						if (((String) systemCall.readFromMemory(26)).equals("false")) {
							int y = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 1;//have to update in pcb
							systemCall.writeToMemory(y , 22);
							System.out.println("PC UPDATED");
						}
						this.setTimeTaken(this.getTimeTaken() + 1);
						if (( systemCall.readFromMemory(21)).equals(ProcessState.blocked)) {//have to update in pcb
							runningID = -1;
							this.setTimeTaken(0);
						}
						System.out.println("Queues after executing: ");
						System.out.print("Ready Queue: ");
						printQueue(readyQueue);
						System.out.println();
						System.out.print("Blocked Queue: ");
						printQueue(blockedQueue);
						System.out.println();
					}
					else {
						this.Swap(systemCall);
						if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
							int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 10;
							String nextInstruction = (String) systemCall.readFromMemory(x);
							System.out.println("Process " + runningID + " currently executing " + nextInstruction);
							I.execute(nextInstruction, this, runningID);
							if (((String) systemCall.readFromMemory(6)).equals("false")) {
								int y = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 1;
								systemCall.writeToMemory(y, 2);
								System.out.println("PC UPDATED");
							}
							this.setTimeTaken(this.getTimeTaken() + 1);
							if (( systemCall.readFromMemory(1)).equals(ProcessState.blocked)) {
								runningID = -1;
								this.setTimeTaken(0);
							}
							System.out.println("Queues after executing: ");
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						} else {
							int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 30;
							String nextInstruction = "" + systemCall.readFromMemory(x);
							System.out.println("Process " + runningID + " currently executing " + nextInstruction);
							I.execute(nextInstruction, this, runningID);
							if (((String) systemCall.readFromMemory(26)).equals("false")) {
								int y = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 1;
								systemCall.writeToMemory(y , 22);
								System.out.println("PC UPDATED");
							}
							this.setTimeTaken(this.getTimeTaken() + 1);
							if (( systemCall.readFromMemory(21)).equals(ProcessState.blocked)) {
								runningID = -1;
								this.setTimeTaken(0);
							}
							System.out.println("Queues after executing: ");
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						}
					}
				}
				
				
			}
			
			//checking if done
			if (runningID!=-1) {
				if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
					if (runningID == 1) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "");
						if (memory[x + 10].getData() == null || (memory[x + 10].getData() + "").equals("null")) {
							flag1 = true;
							systemCall.writeToMemory(ProcessState.finished, 1);
							System.out.println("Process 1 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
							//clearMemory(0);
						}
					} 
					
					else if (runningID == 2) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "");
						if (memory[x + 10].getData() == null || (memory[x + 10].getData() + "").equals("null")) {
							flag2 = true;
							systemCall.writeToMemory(ProcessState.finished, 1);
							System.out.println("Process 2 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
							//clearMemory(0);
						}
					}
					else if (runningID == 3) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "");
						if (memory[x + 10].getData() == null || (memory[x + 10].getData() + "").equals("null")) {
							flag3 = true;
							systemCall.writeToMemory(ProcessState.finished, 1);
							System.out.println("Process 3 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
							//clearMemory(0);
						}
					}
					
				}
				
				else if (runningID == (int) Integer.parseInt(systemCall.readFromMemory(20) + "")) {
					if (runningID == 1) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "");
						if (memory[x + 30].getData() == null || (memory[x + 30].getData() + "").equals("null")) {
							flag1 = true;
							systemCall.writeToMemory(ProcessState.finished, 21);
							System.out.println("Process 1 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
							//clearMemory(20);
						}
					} else if (runningID == 2) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "");
						if (memory[x + 30].getData() == null || (memory[x + 30].getData() + "").equals("null")) {
							flag2 = true;
							systemCall.writeToMemory(ProcessState.finished, 21);
							System.out.println("Process 2 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
							//clearMemory(20);
						}
					} else if (runningID == 3) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "");
						if (memory[x + 30].getData() == null || (memory[x + 30].getData() + "").equals("null")) {
							flag3 = true;
							systemCall.writeToMemory(ProcessState.finished, 21);
							System.out.println("Process 3 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
							//clearMemory(20);
						}
					}
				}
			
			}
			
			if (flag1 && flag2 && flag3) {
				finished = true;
				System.out.println("All proccesses done executing");
			}
			
			if (this.memory[0].getData() != null || !(memory[0].getData() + "").equals("null")) {
				counter1++;
			}
			if (this.memory[20].getData() != null || !(memory[20].getData() + "").equals("null")) {
				counter2++;
			}
			System.out.println("Memory after cycle " + counter + ":");
			this.printMemory();
			this.printDisk(systemCall);
			System.out.println();
			System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><>");
			System.out.println();
			counter++;
		}
		
		
		
			
	}
	
	


	public void initializeMemory() {
		for (int i = 0; i < 40; i++) {
			this.memory[i] = new MemoryWord("", null);
		}
		
		this.memory[0].setKey("pID:");
		this.memory[1].setKey("pState:");
		this.memory[2].setKey("PC:");
		this.memory[3].setKey("startAddress:");
		this.memory[4].setKey("endAddress:");
		this.memory[5].setKey("temp:");
		this.memory[6].setKey("nestedFlag:");
		this.memory[7].setKey("var1:");
		this.memory[8].setKey("var2:");
		this.memory[9].setKey("var3:");
		for (int i = 10; i < 20; i++) {
			this.memory[i].setKey("instruction" + (i - 9)+":");
		}
		this.memory[20].setKey("pID:");
		this.memory[21].setKey("pState:");
		this.memory[22].setKey("PC:");
		this.memory[23].setKey("startAddress:");
		this.memory[24].setKey("endAddress:");
		this.memory[25].setKey("temp:");
		this.memory[26].setKey("nestedFlag:");
		this.memory[27].setKey("var1:");
		this.memory[28].setKey("var2:");
		this.memory[29].setKey("var3:");
		for (int j = 30; j < 40; j++) {
			this.memory[j].setKey("instruction" + (j - 29)+":");
		}
	}
	
	public void printDisk(System_Call systemCall) {
		System.out.println("Disk after cycle " + counter + ":");
		String[] disk = systemCall.readFileDisk("Disk.txt");
		System.out.print("{");
		for (int i = 0; i < disk.length; i++) {
			if (i != disk.length - 1) {
				System.out.print(disk[i] + ", ");
			} else {
				System.out.println(disk[i] + "}");
			}
		}
	}
	
	public void clearMemory(int startAddress) {
		for (int i = 0; i < 20; i++) {
			this.memory[i + startAddress].setData(null);
		}
	}

	public void Swap(System_Call systemCall) throws IOException {
		//System_Call systemCall = new System_Call(this);
		String[] temp = new String[20];
		temp = systemCall.readFileDisk("Disk.txt");
		FileWriter fw = new FileWriter("Disk.txt", false);
		int swappedInID=Integer.parseInt(temp[0]);
		int swappedOutID = (int) Integer.parseInt(memory[0].getData() + "");
		fw.flush();
		System.out.println(counter1+counter2);
		if (counter1>counter2) {
			
			for (int i=0;i<20;i++) {
				systemCall.writeFileDisk("Disk", memory[i].getData() + "\n");
			}
			counter1 = 0;
			//this.clearMemory(0);
			
			for (int i=0;i<20;i++) {
				if (i==0) {
					this.memory[i].setData(Integer.parseInt(temp[i]));
				}
				if (i == 3) {
					this.memory[i].setData(0);
				} else if (i == 4) {
					this.memory[i].setData(20);
				} else {
					this.memory[i].setData(temp[i]);
				}
			}
			//this.printMemory();
		
		}
		else {
			swappedOutID = (int) Integer.parseInt(memory[20].getData() + "");
			for (int i=20;i<40;i++) {
				systemCall.writeFileDisk("Disk", memory[i].getData() + "\n");
			}
			counter1 = 0;
			//this.clearMemory(0);
			for (int i=0;i<20;i++) {
				if (i==0) {
					this.memory[i+20].setData(Integer.parseInt(temp[i]));
				}
				if (i == 3) {
					this.memory[i + 20].setData(20);
				} else if (i == 4) {
					this.memory[i + 20].setData(40);
				} else {
					this.memory[i + 20].setData(temp[i]);
				}
			}
			//this.printMemory();
		}
		System.out.println("Swap occurred!");
		System.out.println("Process " + swappedInID + " swapped in");
		System.out.println("Process " + swappedOutID + " swapped out");
		
	}
	
	public boolean allocate(Process p,Object temp,String nestedFlag,ArrayList<String> instructions) {

		if (memory[0].getData() == null || (memory[0].getData() + "").equals("null")) {
			p.getPcb().setStartAddress(0);
			p.getPcb().setEndAddress(19);
			this.memory[0].setData(p.getProcessID());
			this.memory[1].setData(p.getPcb().getState());
			this.memory[2].setData(p.getPcb().getProgramCounter());
			this.memory[3].setData(p.getPcb().getStartAddress());
			this.memory[4].setData(p.getPcb().getEndAddress());
			this.memory[5].setData(temp);
			this.memory[6].setData(nestedFlag);
			for (int i = 0; i < instructions.size(); i++) {
				this.memory[i + 10].setData(instructions.get(i));
			}
			return true;
		}
		else if (memory[20].getData() == null || (memory[20].getData() + "").equals("null")) {
			p.getPcb().setStartAddress(20);
			p.getPcb().setEndAddress(39);
			this.memory[20].setData(p.getProcessID());
			this.memory[21].setData(p.getPcb().getState());
			this.memory[22].setData(p.getPcb().getProgramCounter());
			this.memory[23].setData(p.getPcb().getStartAddress());
			this.memory[24].setData(p.getPcb().getEndAddress());
			this.memory[25].setData(temp);
			this.memory[26].setData(nestedFlag);
			for (int i = 0; i < instructions.size(); i++) {
				this.memory[i + 30].setData(instructions.get(i));
			}
			return true;
		}
		else {
			return false;
		}
	}

	public void allocateSwap(System_Call systemCall,Process p,Object temp,String nestedFlag,ArrayList<String> instructions) throws IOException {
		int swappedOutID = (int) Integer.parseInt(memory[0].getData() + "");
		if (counter1 > counter2) {
			for (int i = 0; i < 20; i++) {
						systemCall.writeFileDisk("Disk", memory[i].getData() + "\n");
					
			}
			counter1 = 0;
			this.clearMemory(0);
			p.getPcb().setStartAddress(0);
			p.getPcb().setEndAddress(19);
			this.memory[0].setData(p.getProcessID());
			this.memory[1].setData(p.getPcb().getState());
			this.memory[2].setData(p.getPcb().getProgramCounter());
			this.memory[3].setData(p.getPcb().getStartAddress());
			this.memory[4].setData(p.getPcb().getEndAddress());
			this.memory[5].setData(temp);
			this.memory[6].setData(nestedFlag);
			for (int i = 0; i < instructions.size(); i++) {
				this.memory[i + 10].setData(instructions.get(i));
			}
			
			//this.printMemory();
		} else {
			swappedOutID = (int) Integer.parseInt(memory[20].getData() + "");
			for (int i = 20; i < 40; i++) {
				systemCall.writeFileDisk("Disk", memory[i].getData() + "\n");
			}
			counter2 = 0;
			this.clearMemory(20);
			p.getPcb().setStartAddress(20);
			p.getPcb().setEndAddress(39);
			this.memory[20].setData(p.getProcessID());
			this.memory[21].setData(p.getPcb().getState());
			this.memory[22].setData(p.getPcb().getProgramCounter());
			this.memory[23].setData(p.getPcb().getStartAddress());
			this.memory[24].setData(p.getPcb().getEndAddress());
			this.memory[25].setData(temp);
			this.memory[26].setData(nestedFlag);
			for (int i = 0; i < instructions.size(); i++) {
				this.memory[i + 30].setData(instructions.get(i));
			}
		}
		System.out.println("Swap occurred!");
		System.out.println("Process"+p.getProcessID() +" swapped in");
		System.out.println("Process " + swappedOutID + " swapped out");
	}
	
	public void preemption(System_Call systemCall) throws IOException {
		Process p= Process.Findprocess(runningID);
		
		if (timeTaken==timeSlice) {
			if (readyQueue.isEmpty()) {
				this.setTimeTaken(0);
				System.out.println("Time slice done. No processes to be admitted.");
			}
			
			else {
				
				//preemption
				this.setTimeTaken(0);
				p.getPcb().setState(ProcessState.ready);
				systemCall.writeToMemory(p.getPcb().getState(), 1);
				readyQueue.add(p.getProcessID());
				System.out.println("Time slice done. Process " + runningID
						+ " preempted and moved to ready queue.");
				runningID=readyQueue.remove();
				if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
					p.getPcb().setState(ProcessState.running);
					systemCall.writeToMemory(p.getPcb().getState(), 1);
				} else if ((int) Integer.parseInt(memory[20].getData() + "") == runningID) {
					p.getPcb().setState(ProcessState.running);
					systemCall.writeToMemory(p.getPcb().getState(), 21);
				} else {
					this.Swap(systemCall);
					if ((int) Integer.parseInt(memory[0].getData() + "") == runningID) {
						p.getPcb().setState(ProcessState.running);
						systemCall.writeToMemory(p.getPcb().getState(), 1);
					} else {
						p.getPcb().setState(ProcessState.running);
						systemCall.writeToMemory(p.getPcb().getState(), 21);
					}
				}
				System.out.println("process " + runningID + " dispatched.");
				System.out.print("Ready Queue: ");
				printQueue(readyQueue);
				System.out.println();
				System.out.print("Blocked Queue: ");
				printQueue(blockedQueue);
				System.out.println();
			}
		}
	}
	
	public void addProcessToReadyQueue(int p) {
        readyQueue.add(p);
        System.out.println("Process " + p + " added to the ready queue");
    }
	
	public void addProcessToBlockedQueue(int p) {
        blockedQueue.add(p);
        System.out.println("Process " + p + " added to the blocked queue");
    }
	
	public MemoryWord[] getMemory() {
		return memory;
	}

	public void setMemory(MemoryWord[] memory) {
		this.memory = memory;
	}
	
	public static void printQueue(Queue<Integer> Q) {
		for (int s : Q) {
			System.out.print("Process " + s + ", ");
		}
	}
	
	public void printMemory() {
		System.out.print("{");
		for (int i = 0; i < 40; i++) {
			if (i != 39) {
				System.out.print(memory[i] + ", ");
				if (i == 9 || i == 19 || i == 29) {
					System.out.println();
				}
			} else {
				System.out.println(memory[i] + "}");
			}
		}
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	
	public Queue<Integer> getReadyQueue() {
		return readyQueue;
	}


	public void setReadyQueue(Queue<Integer> readyQueue) {
		this.readyQueue = readyQueue;
	}


	public Queue<Integer> getBlockedQueue() {
		return blockedQueue;
	}


	public void setBlockedQueue(Queue<Integer> blockedQueue) {
		this.blockedQueue = blockedQueue;
	}


	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}


	public Mutex getUserInput() {
		return userInput;
	}


	public void setUserInput(Mutex userInput) {
		this.userInput = userInput;
	}


	public Mutex getUserOutput() {
		return userOutput;
	}


	public void setUserOutput(Mutex userOutput) {
		this.userOutput = userOutput;
	}


	public Mutex getFileAccess() {
		return fileAccess;
	}


	public void setFileAccess(Mutex fileAccess) {
		this.fileAccess = fileAccess;
	}


	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}


	public int getRunningID() {
		return runningID;
	}


	public void setRunningID(int runningID) {
		this.runningID = runningID;
	}


	public int getArrivalTiming1() {
		return arrivalTiming1;
	}


	public void setArrivalTiming1(int arrivalTiming1) {
		this.arrivalTiming1 = arrivalTiming1;
	}


	public int getArrivalTiming2() {
		return arrivalTiming2;
	}


	public void setArrivalTiming2(int arrivalTiming2) {
		this.arrivalTiming2 = arrivalTiming2;
	}


	public int getArrivalTiming3() {
		return arrivalTiming3;
	}


	public void setArrivalTiming3(int arrivalTiming3) {
		this.arrivalTiming3 = arrivalTiming3;
	}


	public int getTimeTaken() {
		return timeTaken;
	}


	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}


	public int getCounter1() {
		return counter1;
	}


	public void setCounter1(int counter1) {
		this.counter1 = counter1;
	}


	public int getCounter2() {
		return counter2;
	}


	public void setCounter2(int counter2) {
		this.counter2 = counter2;
	}


	public String getPath1() {
		return path1;
	}


	public void setPath1(String path1) {
		this.path1 = path1;
	}


	public String getPath2() {
		return path2;
	}


	public void setPath2(String path2) {
		this.path2 = path2;
	}


	public String getPath3() {
		return path3;
	}


	public void setPath3(String path3) {
		this.path3 = path3;
	}


	public static void main(String[] args) throws Exception {
		
		Process p1= new Process();
		Process p2= new Process();
		Process p3= new Process();
		Scheduler s= new Scheduler(1,2,3,4,"Program_1.txt","Program_1.txt","Program_1.txt");
		//s.readyQueue.add(p3);
		//s.readyQueue.add(p2);
		//s.readyQueue.add(p1);
		s.printQueue(s.readyQueue);
		s.runScheduler();
	}
}
