package vsk.rahul.thread.waitnotify;

public class TestSenderReceiver {

	public static void main(String[] args) {
		Data data = new Data();
		
		Thread sender = new Thread(new Sender(data));
		Thread receiver = new Thread(new Receiver(data));
		
		sender.start();
		receiver.start();
	}

}