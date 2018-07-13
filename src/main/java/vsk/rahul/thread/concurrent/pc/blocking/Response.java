/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

/**
 * Response created once the request is processed.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Response {

	private Integer id;
	
	private String data;

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
