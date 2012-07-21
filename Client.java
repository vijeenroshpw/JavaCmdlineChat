import java.io.*;
import java.net.*;
/*
	Author :  Vijeenrosh P.W <hsorhteeniv@gmail.com>
	Institution : Govt.Engg College , Thrissur
*/
class IO {
	public static String readString() {
		String data = "";		
		try {		
			DataInputStream din = new DataInputStream(new BufferedInputStream(System.in));
			data = din.readLine();
		} catch(Exception e) {
		}
		return data;
	}
}
class ReadSocket extends Thread {
	private Socket sock;
	private InputStreamReader isr;	
	private StringBuffer ibuffer;	
	public ReadSocket(Socket sock) {
		this.sock = sock;
		try {		
			isr = new InputStreamReader(new BufferedInputStream(sock.getInputStream()));
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	public synchronized void readSocket() throws Exception {
		int c;
		ibuffer = new StringBuffer();
		while( (c = isr.read()) != 13)
			ibuffer.append((char)c);
			
	}
	public void run() {
		while(true) {		
			try {
				readSocket();
				System.out.print(ibuffer);
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}

}

class Client {
	public static void main(String argv[]) {
		try {
			Socket sock = new Socket(argv[0],Integer.parseInt(argv[1]));
			OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(sock.getOutputStream()));
			System.out.println("\n\n\n                       X Chatter Client \n              A Java Based Command Line chat Utility \n\n");			
			String message = " Hello , Server \n\n" + (char)13;
			osw.write(message);
			osw.flush();			
			ReadSocket rs = new ReadSocket(sock);
			rs.start();
			while(true) {
				//System.out.print(">>>");				
				message = IO.readString();
				message = "\n" + argv[2] +"  :  " +  message +"\n"+ (char)13;
				osw.write(message);
				osw.flush();
			}

		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
