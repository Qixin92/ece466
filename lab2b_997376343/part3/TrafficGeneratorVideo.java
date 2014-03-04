import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

/* Michael Law 997376343

Exercise 3.3 Video Trace file

The file has the format: 
SeqNo       Time (in msec)    Frame Type        Size (in Bytes) 
 1              0               I               513
 2              33.33           B               136 
 3              66.66           P               152
 
 
usage: java TrafficGeneratorVideo <hostname>

*/

class TrafficGeneratorVideo {  
	public static void main (String[] args) { 
		
		BufferedReader bis = null; 
		String currentLine = null; 
		PrintStream pout = null;
		String hostname = "localhost";
		int port = 4444;
		
		//parse file
		try {  
			
			//get host
			hostname = args[0];
			InetAddress addr = InetAddress.getByName(hostname);	
			
			//file handler
            File fin = new File("movietrace.data"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			FileOutputStream fout =  new FileOutputStream("output_gen_ex31_video.txt");
			pout = new PrintStream (fout);
			

			//socket to send on
			DatagramSocket socket = new DatagramSocket();

			//intial varibales
			int i = 0;
			int max_packet_size = 65507;
			long last_send_time = System.nanoTime();
			
			/*
			send each frame every 33 nanoseconds
			elapsed time is always 33 ms for the video traffic
			Lets generate traffic at a factor of 1000 times faster
			*/
			float Etime = 33;  //33 microseconds
			//long time_to_wait = (long) Etime*1000; 
			long time_to_wait = (long) Etime*1000000; 


			while ( (currentLine = bis.readLine()) != null) { 
				
				//Parse line and break up into elements
				StringTokenizer st = new StringTokenizer(currentLine); 
				String col1 = st.nextToken(); 
				String col2 = st.nextToken(); 
				String col3  = st.nextToken(); 
				String col4  = st.nextToken(); 
				
				//Convert each element to desired data type 
				int SeqNo 	= Integer.parseInt(col1);
				float t2 	= Float.parseFloat(col2);  
				int Fsize 	= Integer.parseInt(col4);
				
				//spin until time to send
			  	long send_at_time = last_send_time + time_to_wait;
			  	long current_time;
			    do {
			        current_time = System.nanoTime();
			    } while ( current_time < send_at_time );
			    last_send_time = current_time;

				//send the frame as packets up to max_packet_size
				while (Fsize > 0) {
					int cur_Fsize = Fsize % max_packet_size;
					if (cur_Fsize == 0 ) {
						cur_Fsize = max_packet_size;
					}
					//System.out.println(SeqNo+ ":current partial frame szie: "+cur_Fsize);
					byte [] data = new byte[cur_Fsize];
					DatagramPacket packet = new DatagramPacket(data, cur_Fsize, addr, port);
					socket.send(packet);
					Fsize = Fsize - cur_Fsize;
				}
				
			} 
			
			
		} catch (IOException e) {  
			// catch io errors from FileInputStream or readLine()  
			System.out.println("IOException: " + e.getMessage());  
		} finally {  
			// Close files   
			if (bis != null) { 
				try { 
					bis.close(); 
					pout.close();
				} catch (IOException e) { 
					System.out.println("IOException: " +  e.getMessage());  
				} 
			} 
		}
		 
	}  
}



