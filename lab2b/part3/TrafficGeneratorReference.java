import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

/* Michael Law 997376343

Exercise 1.1 Create and Test Reference Traffic Generator

The file has the format: 
SeqNo       Time (in usec)      Size (in Bytes) 
 1              273                 30 
 2              934                 99 
 3              2293                27

*/

class TrafficGeneratorReference {  
	public static void main (String[] args) { 
		
		String currentLine = null; 
		PrintStream pout = null;
		ArrayList<Frame> frames = new ArrayList<Frame>();
		
		String hostname = "localhost";
		float T_msec;
		int N_packets;
		int L_bytes;
		int port = 4445; //change to 4444 if sending to tokenbucket, 4445 for sink
	
		try {  
			
			//process input arguments
			hostname = args[0];
	        InetAddress addr = InetAddress.getByName(hostname);	
			T_msec = Float.parseFloat(args[1]);
			N_packets = Integer.parseInt(args[2]);
			L_bytes = Integer.parseInt(args[3]);

			//file to write to
			FileOutputStream fout =  new FileOutputStream("output.txt");
			pout = new PrintStream (fout);
			
			//initialization
			DatagramSocket socket = new DatagramSocket();
			int i = 0;
			int SeqNo = 1;
			long time_to_wait = (long) T_msec*1000*1000;
			long last_T_time = System.nanoTime();
			long last_send_time = 0;
			long current_time;
			while(true) {
			    
			   
			    //send N packets of size L bytes, back to back 
			    for (i = 0; i<N_packets; i++) {
			        
			        byte [] data = new byte[L_bytes];
			        DatagramPacket packet = new DatagramPacket(data, L_bytes, addr, port); 
			        
			        long make_packet_time = System.nanoTime();
			        long time_to_record = make_packet_time - last_send_time;
			        
			        if (SeqNo == 1) time_to_record = 0; 
			        last_send_time = make_packet_time;

                    time_to_record = time_to_record/1000; //nano to microseconds
			        pout.println(SeqNo+ "\t"+  time_to_record + "\t" + L_bytes);
			      
			        
			        socket.send(packet);
			        SeqNo++;
			    }
			    
			    //ensure we send every T msecs
			    long send_at_time = last_T_time + time_to_wait;
			    
			    do {
			        current_time = System.nanoTime();
			    } while ( current_time < send_at_time ); //we wait until current time has surpassed the send_at_time
			    last_T_time = current_time;
			    
			}
			
			
			
		} catch (IOException e) {  
			// catch io errors from FileInputStream or readLine()  
			System.out.println("IOException: " + e.getMessage());  
		} finally {  
			// Close files   
			 
			
				pout.close();
			
			
		}
		 
	}  
}



