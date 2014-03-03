import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

/* Michael Law 997376343

Exercise 2.1 Traffic Generator for Poisson traffic 

The file has the format: 
SeqNo       Time (in usec)      Size (in Bytes) 
 1              273                 30 
 2              934                 99 
 3              2293                27
 
 
usage: java TrafficGenerator <hostname>
if <hostname> is empty, "localhost" will be used as default

*/

class TrafficGenerator {  
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
            File fin = new File("poisson3.data"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			FileOutputStream fout =  new FileOutputStream("output_gen_ex31_poisson.txt");
			pout = new PrintStream (fout);
			
			
			int i = 0;
			float t1 = 0;
			
			//begin sending packets
			DatagramSocket socket = new DatagramSocket();
			
			long last_send_time = System.nanoTime();
			int SeqNo = 1;
			
			//store poisson data locally
			while ( (currentLine = bis.readLine()) != null) { 
				
				//Parse line and break up into elements
				StringTokenizer st = new StringTokenizer(currentLine); 
				String col1 = st.nextToken(); 
				String col2 = st.nextToken(); 
				String col3  = st.nextToken(); 
				
				//Convert each element to desired data type 
				int SeqNo2 	= Integer.parseInt(col1);
				float t2 	= Float.parseFloat(col2);  
				int Fsize 	= Integer.parseInt(col3);
				
				//find elapsed time since last packet
				float Etime = t2 - t1;
				long Etime_nano = (long) Etime*1000;
				t1 = t2;
			  
				//create packet
				byte [] data = new byte[Fsize];
				DatagramPacket packet = new DatagramPacket(data, Fsize, addr, 4444);
				
				/*
				Frame frame = new Frame(SeqNo, Etime_nano, packet); 
				frames.add(i++, frame);
				*/
				long time_to_wait = Etime_nano;
				long send_at_time = last_send_time + time_to_wait;
				long current_time;
			    do {
			        current_time = System.nanoTime();
			    } while ( current_time < send_at_time ); //we wait until current time has surpassed the send_at_time
			    
			    last_send_time = current_time;
			    
			    socket.send(packet);
			    pout.println(SeqNo+ "\t"+  time_to_wait + "\t" + packet.getLength());
			    SeqNo++;
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



