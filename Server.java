import java.io.*;
import java.net.*;
/*
	Author: Vijeenrosh P.W <hsorhteeniv@gmail.com>
	Institution : Govt.Enggg College , Thrissur
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
			isr = new InputStreamReader(new BufferedInputStream(sock.getInputStream()),"US-ASCII");
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


class Server {
	public static void main(String argv[]) {
		try {
			ServerSocket ssock = new ServerSocket(Integer.parseInt(argv[0]));
			Socket csock = null;			
			OutputStreamWriter osw = null;
			System.out.println("\n\n\n                       X Chatter Server \n              A Java Based Command Line chat Utility \n\n");			
			String message = " Welcome Guest " + "\n\n" + (char)13;
			while(true) {
				csock = ssock.accept();
				osw = new OutputStreamWriter(new BufferedOutputStream(csock.getOutputStream()),"US-ASCII");				
				osw.write(message);
				osw.flush();				
				ReadSocket rs = new ReadSocket(csock);
				rs.start();
			
				while(true) {
					//System.out.print(">>>");					
					message = IO.readString();
					message = "\n" + argv[1]+"  :  " + message + "\n" + (char)13;
					osw.write(message);
					osw.flush();			
				}
			}			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}

