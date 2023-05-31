package os;

import java.util.*;
import java.io.*;

public class Mutex {
	private boolean available;
	private Queue<Integer> waiting;
	private int ownerID;

	public Mutex() {
		this.available = true;
		this.waiting = new LinkedList();
		this.ownerID = -1;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Queue<Integer> getWaiting() {
		return waiting;
	}

	public void setWaiting(Queue<Integer> waiting) {
		this.waiting = waiting;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int processID) {
		this.ownerID = processID;
	}

	public void semWait(int p, Scheduler s) {
		if (this.getAvailable()) {
			this.setOwnerID(p);
			this.setAvailable(false);
			System.out.println("locked resource");
			System.out.println("Mutex waiting queue:");
			Scheduler.printQueue(waiting);
			System.out.println("ownerID: " + ownerID);
		} else {
			this.getWaiting().add(p);
			s.addProcessToBlockedQueue(p);
			if ((int) Integer.parseInt(s.getMemory()[0].getData() + "") == p) {
				// p.getPcb().setState(ProcessState.blocked);
				s.getMemory()[1].setData(ProcessState.blocked);

			} else if ((int) Integer.parseInt(s.getMemory()[20].getData() + "") == p) {
				// .p..getPcb().setState(ProcessState.blocked);
				s.getMemory()[21].setData(ProcessState.blocked);

			}

		}
	}

	public void semSignal(int processID, Scheduler s) throws IOException {

		if (this.getOwnerID() == processID) {
			if (this.getWaiting().isEmpty()) {
				this.setAvailable(true);
				this.setOwnerID(-1);
				System.out.println("unlocked resource");
				System.out.println("waiting queue:");
				Scheduler.printQueue(waiting);
			} else {
				this.setAvailable(true);
				System.out.println("unlocked resource ");
				System.out.println("Mutex waiting queue:");
				Scheduler.printQueue(waiting);
				int processDequeuedID = this.getWaiting().remove();
				this.setOwnerID(processDequeuedID);
				System.out.println("ownerID: " + ownerID);
				for (int i = 0; i < s.getBlockedQueue().size(); i++) {
					if (processDequeuedID != s.getBlockedQueue().peek()) {
						int tmp = s.getBlockedQueue().remove();
						s.getBlockedQueue().add(tmp);
					} else {
						s.getBlockedQueue().remove();
						s.getReadyQueue().add(processDequeuedID);
					}
				}

				if (processDequeuedID == (int) Integer.parseInt(s.getMemory()[0].getData() + "")) {
					s.getMemory()[1].setData(ProcessState.ready);
				} else {
					if (processDequeuedID == (int) Integer.parseInt(s.getMemory()[20].getData() + "")) {
						s.getMemory()[21].setData(ProcessState.ready);
					} else {
						System_Call systemCall = new System_Call(s);
						Object[] tmp = new Object[20];
						tmp = systemCall.readFileDisk("Disk.txt");
						tmp[1] = "READY";
						FileWriter fw = new FileWriter("Disk.txt", false);
						fw.flush();
						for (int i = 0; i < tmp.length; i++) {
							systemCall.writeFileDisk("Disk", tmp[i] + "\n");
						}
					}
				}

			}
		}
	}

}
