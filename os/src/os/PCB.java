package os;
import java.util.*;


public class PCB {
	private int id;
	private ProcessState state;
	private int programCounter;
	private int startAddress;
	private int endAddress;
	//private int addressPCB;
	

	public PCB(int id) {
		this.id=id;
		//this.state = ProcessState.created;
		//this.programCounter=0;
		this.startAddress=startAddress;
		this.endAddress=endAddress;
		
	}
	



	public int getId() {
		return id;
	}


	public ProcessState getState() {
		return state;
	}


	public void setState(ProcessState state) {
		this.state = state;
	}


	public int getProgramCounter() {
		return programCounter;
	}

	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}


	public int getStartAddress() {
		return startAddress;
	}
	
	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}

	public int getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(int endAddress) {
		this.endAddress = endAddress;
	}

/*	
	public int getAddressPCB() {
		return addressPCB;
	}

	public void setAddressPCB(int addressPCB) {
		this.addressPCB = addressPCB;
	}*/

	
}
