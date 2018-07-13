/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

/**
 * Request which will be processed by consumer.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Request {

	private Integer id;
	
	private String data;
	
	private Boolean connectionTimeout;
	
	public Request(Integer id, String data, Boolean connectionTimeout) {
		super();
		this.id = id;
		this.data = data;
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public String toString() {
		return String.format("[id=%d] [data=%s] [connectionTimeout=%s]", id, data, connectionTimeout);
	}

	public Boolean getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Boolean connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
