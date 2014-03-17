import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

/* Michael Law 997376343

Exercise 3.3 Ethernet Trace file

The file has the format: 
Time (in seconds)      Size (in Bytes) 
0.001340  				1090 
0.001508   				174 
0.004176   				162

usage: java TrafficGeneratorVideo <hostname>

*/
class TrafficGeneratorEthernet {  
	public static void main (String[] args) { 
		
		BufferedReader bis = null; 
		String currentLine = null; 
		PrintStream pout = null;
		ArrayList<Frame> frames = new ArrayList<Frame>();
		String hostname = "localhost";
		
		
		//parse file
		try {  
			
			//get host
			hostname = args[0];
			InetAddress addr = InetAddress.getByName(hostname);	
			
			//file handler
            File fin = new File("BC-pAug89-small.TL"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			FileOutputStream fout =  new FileOutputStream("output.txt");
			pout = new PrintStream (fout);
			
			
			int i = 0;
			float t1 = 0;
			
			//store poisson data locally
			while ( (currentLine = bis.readLine()) != null) { 
				
				//Parse line and break up into elements
				StringTokenizer st = new StringTokenizer(currentLine); 
				String col1 = st.nextToken(); 
				String col2 = st.nextToken(); 
			
				
				//Convert each element to desired data type 
				float t2 	= Float.parseFloat(col1);  
				int Fsize 	= Integer.parseInt(col2);
				
				//find elapsed time since last packet
				float Etime = t2 - t1;
				long Etime_nano = (long) (Etime*1000*1000*1000);
				t1 = t2;
			  
				//add packet
				byte [] data = new byte[Fsize];
				DatagramPacket packet = new DatagramPacket(data, Fsize, addr, 4444);
				Frame frame = new Frame(1,  Etime_nano, packet); 
				frames.add(i++, frame);
				
			} 
			
			//begin sending packets
			DatagramSocket socket = new DatagramSocket();
            
            long last_send_time = System.nanoTime();
			for (i=0; i< frames.size(); i++) {
			
			    //elapsed time in nano seconds
			    Frame frame = frames.get(i);
			    long time_to_wait = frame.Etime;
			    
			    //send_at_time is the previous packet's send time (t0) + the elapsed time for packet
			    long send_at_time = last_send_time + time_to_wait;
			    long current_time;
			    do {
			        current_time = System.nanoTime();
			    } while ( current_time < send_at_time ); //we wait until current time has surpassed the send_at_time
	    
	            //record the new last_send_time and send the packet
			    last_send_time = current_time;
			    socket.send(frame.packet);
			    
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



