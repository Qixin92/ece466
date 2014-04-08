// Tzuo Shuin Yew 997266499
import java.io.*;
import java.util.*;
import java.net.*;

// threaded version of sink from Lab 2a
public class sink extends Thread{
	private int inPort = 0; // receive on this port from black box
	private int N = 0; // number of packets of the train
	private int L = 0; // packet size of train (bytes)
	private int r = 0; // avg rate of train (kbps)
	
	// constructor function
	public sink(int _inPort, int _N, int _L, int _r) {
		super("Starting sink...");
		this.inPort = _inPort;
		this.N = _N;
		this.L = _L;
		this.r = _r;
	}
	
	// convert byte array to integer
	public static int fromByteArray(byte [] value, int start, int length) {
		int Return = 0;
		for (int i=start; i< start+length; i++)	{
			Return = (Return << 8) + (value[i] & 0xff);
		}
		return Return;
	}
	
	// main thread function
	public void run() {
		try {
			// prepare socket and buffer
			int packetSize = L/N;
			DatagramSocket socket = new DatagramSocket(inPort);
			byte[] buf = new byte[packetSize];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			System.out.println("Waiting for packets from black box...");
			socket.setSoTimeout(15000);
			// start receiving packets
			int i=0;
			while (i < N) {
				try{
					socket.receive(packet);
					String recv = new String(packet.getData());
					// get sequence number from byte array
					int seqNo = fromByteArray(packet.getData(), 2, 2);
					long time = System.nanoTime();
					// record receipt time on probe packet (in microseconds)
					estimator.probeList.get(i).setReceiveTime((time-estimator.startTime)/1000);
					i++;
				} catch (SocketTimeoutException e) {
					System.out.println("timed out");
					break;
				}
			}
			System.out.println("Done receiving "+i+" packets. Writing to file...");
			// now print the sequence #s, send times, and receive times to a text file
			BufferedWriter output = new BufferedWriter(new FileWriter("sink.txt"));
			for (i=0; i<estimator.probeList.size(); i++){
				long sendTime = estimator.probeList.get(i).getSendTime();
				long receiveTime = estimator.probeList.get(i).getReceiveTime();
				output.write((i+1)+"\t"+sendTime+"\t"+receiveTime);
				output.newLine();
			}
			// close output file & socket
			output.close();
			socket.disconnect();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
