package os;
import os.*;
public class MemoryWord {
	private String key;
	private Object data;

	 public MemoryWord(String key, Object s) {
			this.key = key;
			this.data=s;
		}
	 
	 public String toString() {
		 return key+""+ data+"";
	 }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object value) {
		this.data = value;
	}

	
	 
}
