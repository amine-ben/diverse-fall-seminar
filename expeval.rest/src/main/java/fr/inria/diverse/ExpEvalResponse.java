package fr.inria.diverse;

import com.google.gson.JsonElement;

public class ExpEvalResponse {
	
	private Status status;
	private String message;
	private JsonElement data;
	
	public ExpEvalResponse(User user, String message, Status status) {
		this.setStatus(status);
		this.setMessage(message);
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public JsonElement getData() {
		return data;
	}

	public void setData(JsonElement data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpEvalResponse other = (ExpEvalResponse) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public enum Status {		
		SUCCESS (0),	
	    ERROR (1);
	  
	    private int status;     
	    private  Status (int status) {
	    	this.status = status;
	    }
	    
	}
	 
}
