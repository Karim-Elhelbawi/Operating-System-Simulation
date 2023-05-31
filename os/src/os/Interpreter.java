package os;
import os.*;
import java.util.*;
import java.io.*;
public class Interpreter {
		
	public Interpreter() {
		
	}
	
	@SuppressWarnings({ "static-access", "static-access" })
	public void execute(String instruction,Scheduler s,int processID) throws Exception{
		System_Call systemCall= new System_Call(s);
		String[] parced=instruction.split(" ");
		String line=parced[0];
		 	
		switch(line) {
		
		 case "print": //print x
			 if ((int) Integer.parseInt(s.getMemory()[0].getData() + "") == processID) {
					for (int i = 7; i <= 9; i++) {
						if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (parced[1].equals(splittedValue[0])) {
								String printedValue = "";
								for (int j = 1; j < splittedValue.length; j++) {
									printedValue += splittedValue[j] + " ";
								}
								systemCall.printOnScreen(printedValue);
							}
						}
					}
				} else {
					if ((int) Integer.parseInt(s.getMemory()[20].getData() + "") == processID) {
						for (int i = 27; i <= 29; i++) {
							if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
								String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
								if (parced[1].equals(splittedValue[0])) {
									String printedValue = "";
									for (int j = 1; j < splittedValue.length; j++) {
										printedValue += splittedValue[j] + " ";
									}
									systemCall.printOnScreen(printedValue);
								}
							}
						}
					}
				}
			 break;
			 
		 case "printFromTo": //printfromto a b
			 String var1 = parced[1];
				String var2 = parced[2];
				int start = -1;
				int end = -1;
				if ((int) Integer.parseInt(s.getMemory()[0].getData() + "") == processID) {
					for (int i = 7; i < 10; i++) {
						if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
							String[] varImMem = (s.getMemory()[i].getData() + "").split(" ");
							if (varImMem[0].equals(var1)) {
								start = Integer.parseInt(varImMem[1]);
							}
							if (varImMem[0].equals(var2)) {
								end = Integer.parseInt(varImMem[1]);
							}
						}
					}
				} else {
					for (int i = 27; i < 30; i++) {
						if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
							String[] varImMem = (s.getMemory()[i].getData() + "").split(" ");
							if (varImMem[0].equals(var1)) {
								start = Integer.parseInt(varImMem[1]);
							}
							if (varImMem[0].equals(var2)) {
								end = Integer.parseInt(varImMem[1]);
							}
						}
					}
				}
				while (start <= end) {
					systemCall.printOnScreen(start + "");
					start++;
				}
			
			 break;
		
		 case "assign": 
			String temp=parced[2];
			if (temp.equals("input")) {
				if ((int) Integer.parseInt(s.getMemory()[0].getData() + "") == processID) {
					if (s.getMemory()[5].getData() != null && !(s.getMemory()[5].getData() + "").equals("null")) {
						String dataType;
						String y = (String) systemCall.readFromMemory(5);
						if (y.matches("\\d+")) {
							dataType = "Integer";
						} else {
							dataType = "String";
						}
						if (dataType.equals("Integer")) {
							int x = (int) Integer.parseInt(y);
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(parced[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(parced[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						} else {
							String x = y;
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(parced[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(parced[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						}

					} else {
						systemCall.writeToMemory(systemCall.takeInput(), 5);
						systemCall.writeToMemory("true", 6);
					}
				} else if ((int) Integer.parseInt(s.getMemory()[20].getData() + "") == processID) {
					if (s.getMemory()[25].getData() != null && !(s.getMemory()[25].getData() + "").equals("null")) {
						String dataType;
						String y = (String) systemCall.readFromMemory(25);
						if (y.matches("\\d+")) {
							dataType = "Integer";
						} else {
							dataType = "String";
						}
						if (dataType.equals("Integer")) {
							int x = (int) Integer.parseInt(y);
							int index = -1;
							boolean found = false;
							for (int i = 27; i <= 29; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(parced[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {

									systemCall.writeToMemory(parced[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 25);
							systemCall.writeToMemory("false", 26);

						} else {
							String x = y;
							int index = -1;
							boolean found = false;
							for (int i = 27; i <= 29; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(parced[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(parced[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 25);
							systemCall.writeToMemory("false", 26);

						}

					} else {
						systemCall.writeToMemory(systemCall.takeInput(), 25);
						systemCall.writeToMemory("true", 26);
					}
				}
			} else if (temp.equals("readFile")) {
				if ((int) Integer.parseInt(s.getMemory()[0].getData() + "") == processID) {
					if (s.getMemory()[5] != null && !(s.getMemory()[5].getData() + "").equals("null")) {
						String dataType;
						String y = (String) systemCall.readFromMemory(5);
						if (y.matches("\\d+")) {
							dataType = "Integer";
						} else {
							dataType = "String";
						}
						if (dataType.equals("Integer")) {
							int x = (int) Integer.parseInt(y);
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(parced[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(parced[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						} else {
							String x = y;
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(parced[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(parced[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						}
					} else {
						Object fileName = null;
						for (int i = 7; i <= 9; i++) {
							if (s.getMemory()[i].getData() != null
									&& !(s.getMemory()[i].getData() + "").equals("null")) {
								String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
								if (parced[3].equals(splittedValue[0])) {
									fileName = splittedValue[1];
									
								}
							}
						}
						this.createfile((String)fileName);
						systemCall.writeToMemory(systemCall.readFile(fileName + ".txt"), 5);
						systemCall.writeToMemory("true", 6);
					}
				} else {
					if ((int) Integer.parseInt(s.getMemory()[20].getData() + "") == processID) {
						if (s.getMemory()[25] != null && !(s.getMemory()[25].getData() + "").equals("null")) {
							String dataType;
							String y = (String) systemCall.readFromMemory(25);
							if (y.matches("\\d+")) {
								dataType = "Integer";
							} else {
								dataType = "String";
							}
							if (dataType.equals("Integer")) {
								int x = (int) Integer.parseInt(y);
								int index = -1;
								boolean found = false;
								for (int i = 27; i <= 29; i++) {
									if (s.getMemory()[i].getData() != null
											&& !(s.getMemory()[i].getData() + "").equals("null")) {
										String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
										if (parced[1].equals(splittedValue[0])) {
											systemCall.writeToMemory(parced[1] + " " + x, i);
											found = true;
										}
									} else {
										index = i;
									}
								}
								if (!found) {
									if (index != -1) {
										systemCall.writeToMemory(parced[1] + " " + x, index);
									}
								}
								systemCall.writeToMemory(null, 25);
								systemCall.writeToMemory("false", 26);

							} else {
								String x = y;
								int index = -1;
								boolean found = false;
								for (int i = 27; i <= 29; i++) {
									if (s.getMemory()[i].getData() != null
											&& !(s.getMemory()[i].getData() + "").equals("null")) {
										String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
										if (parced[1].equals(splittedValue[0])) {
											systemCall.writeToMemory(parced[1] + " " + x, i);
											found = true;
										}
									} else {
										index = i;
									}
								}
								if (!found) {
									if (index != -1) {
										systemCall.writeToMemory(parced[1] + " " + x, index);
									}
								}
								systemCall.writeToMemory(null, 25);
								systemCall.writeToMemory("false", 26);

							}
						} else {
							Object fileName = null;
							for (int i = 27; i <= 29; i++) {
								if (s.getMemory()[i].getData() != null
										&& !(s.getMemory()[i].getData() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (parced[3].equals(splittedValue[0])) {
										fileName = splittedValue[1];
										
									}
								}
							}
							this.createfile((String)fileName);
							systemCall.writeToMemory(systemCall.readFile(fileName + ".txt"), 5);
							systemCall.writeToMemory("true", 26);
						}
					}
				}

			}
		
		 	
		 	break;
		 
		 case"readFile": //readFile a
			 String fileName= parced[1];
			 String[] data= systemCall.readFileDisk(fileName);
			 break;
			 
		 case"writeFile": //write x y
			 	String temp1 = parced[1];
				String temp2 = parced[2];
				Object a = "";
				Object b = "";
				if ((int) Integer.parseInt(s.getMemory()[0].getData() + "") == processID) {
					for (int i = 7; i <= 9; i++) {
						if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (temp1.equals(splittedValue[0])) {
								for (int j = 1; j < splittedValue.length; j++) {
									a += splittedValue[j] + " ";
								}
							}
						}
					}
					for (int i = 7; i <= 9; i++) {
						if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (temp2.equals(splittedValue[0])) {
								for (int j = 1; j < splittedValue.length; j++) {
									b += splittedValue[j] + " ";
								}
							}
						}
					}
				} else {
					if ((int) Integer.parseInt(s.getMemory()[20].getData() + "") == processID) {
						for (int i = 27; i <= 29; i++) {
							if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
								String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
								if (temp1.equals(splittedValue[0])) {
									for (int j = 1; j < splittedValue.length; j++) {
										a += splittedValue[j] + " ";
									}
								}
							}
						}
						for (int i = 27; i <= 29; i++) {
							if (s.getMemory()[i].getData() != null && !(s.getMemory()[i].getData() + "").equals("null")) {
								String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
								if (temp2.equals(splittedValue[0])) {
									for (int j = 1; j < splittedValue.length; j++) {
										b += splittedValue[j] + " ";
									}
								}
							}
						}
					}
				}
				systemCall.writeFile(a + "", b + "");
			 
			 break;
		
		 case"semWait":
			 Process p=Process.Findprocess(processID);
			 String type=parced[1];
			 	switch(type) {
			 	case("userOutput"):
			 		
			 		s.getUserOutput().semWait(processID, s);
			 		break;
			 	case("userInput"):
			 		s.getUserInput().semWait(processID, s);
			 		break;
			 	case("file"):
			 		s.getFileAccess().semWait(processID, s);
			 		break;
			 	}
			 	
			 break;
			 
		 case "semSignal":
			 Process p1=Process.Findprocess(processID);
			 String type1=parced[1];
			 	switch(type1) {
			 	case("userOutput"):
			 		s.getUserOutput().semSignal(processID, s);
			 		break;
			 	case("userInput"):
			 		s.getUserInput().semSignal(processID, s);
			 		break;
			 	case("file"):
			 		s.getFileAccess().semSignal(processID, s);
			 		break;
			 	}
			 break;
		}
	
	}
	public void createfile(String fileName) throws IOException {
		 String projectDirectory = System.getProperty("user.dir");
	        
	        // Specify the file name and path within the project directory
	        String filePath = projectDirectory + File.separator + fileName+".txt";
	        
	        // Create the file object
	        File file = new File(filePath);
	        
	        try {
	            // Create a new file
	            boolean created = file.createNewFile();
	            if (created) {
	                System.out.println("Text file created successfully.");
	            } else {
	                System.out.println("Text file already exists.");
	            }
	        } catch (IOException e) {
	            System.out.println("An error occurred while creating the text file.");
	            e.printStackTrace();
	        }
	}
	
	public static void main(String[] args) throws Exception {
		Interpreter I= new Interpreter();
		I.createfile("i");
	}
	
}
