/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

import java.net.ConnectException;

/**
 * Dao to put the data in configured repository.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Dao {

	public Response process(Request request) throws ConnectException {
		Response response = new Response();
		response.setData(request.getData());
		response.setId(request.getId());
		
		if(!request.getConnectionTimeout()) {
			String msg = String.format("Connection is not available try to post %s again.", request.toString());
			System.out.println(msg);
			throw new ConnectException(msg);
		}
		
		if(request.getId().equals(1111)) {
			request.setId(request.getId() + 2);
			throw new ConnectException("Deliberately retrying again.");
		}
		
		System.out.println(String.format("%s processed successfully.", request.toString()));
		
		return response;
	}
}
