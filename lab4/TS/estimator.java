// Tzuo Shuin Yew 997266499
import java.io.*; 
import java.util.*;
import java.net.*;

public class estimator {  
	public static LinkedList<probe> probeList = new LinkedList<probe>();
	public static long startTime = 0;
	public static void main (String[] args) throws IOException,InterruptedException {
		// set params from command line args
		InetAddress addr = InetAddress.getByName("localhost"); // addr of black box
		int outPort = 4444; // send to this port on the black box
		int inPort = 4445; // receive on this port from black box
		int N = Integer.parseInt(args[0]); // number of packets of the train
		int L = Integer.parseInt(args[1]); // packet size of train (bytes)
		int r = Integer.parseInt(args[2]); // avg rate of train (kbps)
		System.out.println("Number of Packets: " + N);
		System.out.println("Size of train (bytes): " + L);
		System.out.println("Sending rate (kbps): " + r);
		// create threads
		generator g = new generator(addr, outPort, inPort, N, L, r);
		sink s = new sink(inPort, N, L, r);
		
		// start packet scheduler (fork threads)
		new Thread(g).start();
		new Thread(s).start();
	}
}
