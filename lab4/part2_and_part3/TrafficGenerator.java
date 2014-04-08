import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

/* Michael Law 997376343

TrafficGenerator



*/

class TrafficGenerator extends Thread {  

	static InetAddress addr;
	static int blackbox_port;
	static int N;
	static int L;
	static int r;
	static int return_port;


	public TrafficGenerator (InetAddress addr, int port, int N, int L, int r, int rp) {
		this.addr = addr;
		this.blackbox_port = port;

		this.N = N;
		this.L = L;
		this.r = r;
		System.out.println("N = "+N+" packets in train");
		System.out.println("L = "+L+" bytes total");
		System.out.println("r = "+r+" kbps average rate");
		System.out.println("****************************");

		this.return_port = rp;
	}

	public static byte[] toByteArray(int value) {
		byte[] Result = new byte[4];
		Result[3] = (byte) ((value >>> (8*0)) & 0xFF);
		Result[2] = (byte) ((value >>> (8*1)) & 0xFF); 
		Result[1] = (byte) ((value >>> (8*2)) & 0xFF); 
		Result[0] = (byte) ((value >>> (8*3)) & 0xFF);
		return Result;
	}

	public void run () { 
		
		try {  
			DatagramSocket socket = new DatagramSocket();


			long r_in_Bps = r * 1000 / 8; //kbps * 1000 / 8
			long packet_size_in_B = L/N;
			long interval_in_nsec = (packet_size_in_B *1000 *1000 *1000)/ r_in_Bps;

			System.out.println(packet_size_in_B + " B per packet");
			System.out.println(interval_in_nsec +" nanosecond interval");
			System.out.println("for "+N+" packets");

			int packet_size = (int) packet_size_in_B;
			long last_send_time = System.nanoTime();

			long timestamp = 0;
			int i = 1;
			for (i=1; i<= N; i++){

				byte [] data = new byte[packet_size];

				//add return_port to first two bytes
				System.arraycopy(toByteArray(return_port),2,data,0,2);

				//add seqno to the next two bytes
				System.arraycopy(toByteArray(i),2,data,2,2);
				DatagramPacket packet = new DatagramPacket(data, packet_size, addr, blackbox_port);
				

				long send_at_time = last_send_time + interval_in_nsec;
				long current_time;
			    do {
			        current_time = System.nanoTime();
			    } while ( current_time < send_at_time ); //we wait until current time has surpassed the send_at_time
			    last_send_time = current_time;


			    if (i == 1) {
			    	timestamp = System.nanoTime() /1000;
			    }
			    else {
			    	timestamp = last_send_time - timestamp;
			    	timestamp = timestamp /1000;
			    }
			    TrafficEstimator.map.put(new Integer(i), new Timestamp(timestamp));
			    timestamp = last_send_time;
				socket.send(packet);
			}
			System.out.println("DONE");


		}
		catch (SocketException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally {

		}
		 
	}  
}



