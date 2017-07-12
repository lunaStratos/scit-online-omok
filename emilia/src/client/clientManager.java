package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;
import vo.account;
import vo.makeRoom;

public class clientManager implements Callable<Object> {

	private ObjectOutputStream output;
	private ObjectInputStream input;

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}

	public clientManager(ObjectOutputStream output, ObjectInputStream input) {
		super();
		// TODO Auto-generated constructor stub
		this.output = output;
		this.input = input;
	}


	public void send(Object dat) {

		try {
			output.writeObject(dat);
			output.reset();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		Object data = null;
		int state = 0;
		String name = null;
		String message = null;
		while (true) {
			try {
				data = input.readObject();
				return data;
			} catch (Exception e) {
			}
		}

	}

}
