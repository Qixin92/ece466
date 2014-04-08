// Tzuo Shuin Yew 997266499
import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;

// threaded version of generator from Lab 2a
public class generator extends Thread {
	private InetAddress addr = null; // address of black box
	private int outPort = 0; // send to this port on the black box
	private int inPort = 0; // receive on this port from black box
	private int N = 0; // number of packets of the train
	private int L = 0; // packet size of train (bytes)
	private int r = 0; // avg rate of train (kbps)
	
	// constructor function
	public generator (InetAddress _addr,int _outPort,int _inPort,int _N,int _L,int _r) {
		super("Starting generator...");
		this.addr = _addr;
		this.outPort = _outPort;
		this.inPort = _inPort;
		this.N = _N;
		this.L = _L;
		this.r = _r;
	}
	
	// convert integer to byte array
	public static byte[] toByteArray(int value) {
		byte[] Result = new byte[4];
		Result[3] = (byte) ((value >>> (8*0)) & 0xFF);
		Result[2] = (byte) ((value >>> (8*1)) & 0xFF); 
		Result[1] = (byte) ((value >>> (8*2)) & 0xFF); 
		Result[0] = (byte) ((value >>> (8*3)) & 0xFF);
		return Result;
	}
	
	// main thread function
	public void run() {
		try {
			int packetSize = L/N;
			// multiply by 1e6 as r is in kbps and we want ns
			long interval = (packetSize * 8 * 1000000)/r;
			long time = System.nanoTime();
			long recordedTime;
			long lastTime;
			int i; // counter
			// send packets one-by-one
			for (i=0;i<N;i++) {
				byte[] buf = new byte[packetSize];
				// put estimator's input port # into the first 2 bytes
				System.arraycopy(toByteArray(inPort),2,buf,0,2);
				// put current sequence # in next 2 bytes of buf
				System.arraycopy(toByteArray(i),2,buf,2,2);
		
				DatagramPacket packet = new DatagramPacket(buf,buf.length,addr,outPort);
				DatagramSocket socket = new DatagramSocket();

				lastTime = System.nanoTime();

				if (i == 0) {
					estimator.startTime = lastTime;
					recordedTime = 0;
				}
				else {
					recordedTime = lastTime - estimator.startTime;
				}
				// record sending time (in microseconds)
				probe p = new probe(i,recordedTime/1000);
				estimator.probeList.add(p);
				//System.out.println("Added Packet " + i);

				socket.send(packet);

				while(time - lastTime < interval)
					time = System.nanoTime();
			}
			System.out.println("Sent "+i+" packets");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
