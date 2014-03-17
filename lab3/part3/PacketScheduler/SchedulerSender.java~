package PacketScheduler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.lang.Math.*;

//Removes and sends packets from buffers to a given address and port.
public class SchedulerSender implements Runnable {	
	/**
	 * senderActiveUntil holds the time (in ns) when next packet can be sent, i.e. time when last sending ends.
	 * NOTE: this is not the actual time it takes to send packet, 
	 * it is the time it would take to send packet at given link capacity.
	 */
	private long senderActiveUntil;
	
	// destination port
	private int destPort;
	// destination address
	private InetAddress destAddress;
	// socket used to send packets
	private DatagramSocket socket;
	// buffers from which packets are sent
	private Buffer[] buffers;
	// link capacity at which packet scheduler operates (pbs)
	private long linkCapacity;

	/**
	 * Constructor. Creates socket.
	 * @param buffers Buffers from which packets are sent.
	 * @param destAddress IP address to which packets are sent.
	 * @param destPort Port to which packets are sent.
	 * @param linkCapacity Link capacity at which FIFO scheduler operates (bps).
	 */
	public SchedulerSender(Buffer[] buffers, InetAddress destAddress, int destPort, long linkCapacity) {
		this.senderActiveUntil = 0l;
		this.buffers = buffers;
		this.destAddress = destAddress;
		this.destPort = destPort;
		this.linkCapacity = linkCapacity;
		
		try {
			socket = new DatagramSocket();
		} 
		catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Send packet using socket.
	 * @param packet Packet to send.
	 * @param startTime Time when sending of this packet was started.
	 */
	public synchronized void sendPacket(DatagramPacket packet, long startTime) {
		try {
			// change destination of packet (do forwarding)
			packet.setAddress(destAddress);
			packet.setPort(destPort);
			
			// time it would take to send packet with given link capacity
			long sendingTime = (long)((((float)packet.getLength()*8)/linkCapacity)*1000000000);
			
			socket.send(packet);
			
			// time before next packet can be sent (simulate link capacity)
			long timeToWait = sendingTime - (System.nanoTime() - startTime);
			
			// set when next packet can be sent
			senderActiveUntil = startTime+timeToWait;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Remove packets form buffers and send them.
	 * This method is invoked when starting a thread for this class.
	 */
	public void run() {
		// assign number of packets to serve based on weight
		int []flowSize = new int[3];
		flowSize[0] = 8; 
		flowSize[1] = 6; 
		flowSize[2] = 2;
		
		int []weight = new int[3];
		//weight[0] = 1;
		weight[0] = 3;
		weight[1] = 1; 
		weight[2] = 1;

		int totalWeight=0;
		for (int i=0;i<3;i++){
			totalWeight += weight[i];
		}
		

		double []share = new double[3];
		int Cap = 10;
		//find f
		int f = 0;
		for (int i=0; i<Cap; i++) {
		    int result = Math.min(flowSize[0], i*weight[0]) + Math.min(flowSize[1], i*weight[1]) + Math.min(flowSize[2], i*weight[2]);
		    if (result == Cap) {
		        System.out.println(result);
		        f = i;
		        break;
		    }
		    
		}
		System.out.println(f);
		
		//find shares using f
		share[0] = Math.min(f, flowSize[0]);
		share[1] = Math.min(f, flowSize[1]);
		share[2] = Math.min(f, flowSize[2]);
		System.out.println("SHARES at i "+0+" is " +share[0]);
		System.out.println("SHARES at i "+1+" is " +share[1]);
		System.out.println("SHARES at i "+2+" is " +share[2]);
		
		while(true) {
			DatagramPacket packet = null;	
			// number of empty buffers
			int noEmpty = 0;
			// go through each buffer
			for (int i=0;i<buffers.length;i++){
				int sent=0;
				
				while(sent<share[i]){
					// get time when next packet can be sent 
					long startTime = System.nanoTime();
					long nextSendOK = senderActiveUntil;
	
					// if no packet is in transmission look for next packet to send
					if (System.nanoTime() >= nextSendOK) {
						//Check if there is a packet in queue.
						//If there is send packet, remove it form queue.
						//If there is no packet increase noEmpty that keeps 
						//track of # of empty queues 

						if ((packet = buffers[i].peek()) != null) {
							sendPacket(packet, startTime);
							buffers[i].removePacket();
							sent++;
						}
						else {
							noEmpty++;
							break;
						}
				
					}
					else {
						// wait until it is possible to send
						long timeToWait = nextSendOK-startTime;
						try {
							Thread.sleep(timeToWait/1000000, (int)timeToWait%1000000);
						} 
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
				}
			}
			// there are no packets in buffers to send. Wait for one to arrive to buffer.
			// (busy wait)
			if (noEmpty == buffers.length) {
				boolean anyNotEmpty = false;
				for (int i=0; i<buffers.length; i++) {
					if (buffers[i].getSize()>0) {
						anyNotEmpty = true;
					}
				}
				while(!anyNotEmpty) {
					for (int i=0; i<buffers.length; i++) {
						if (buffers[i].getSize()>0) {
							anyNotEmpty = true;
						}
					}
				}
			}	
		}
	}
}
