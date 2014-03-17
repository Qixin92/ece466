import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

/* Michael Law 997376343

TrafficGeneratorPoissonRescale

The file has the format: 
SeqNo       Time (in usec)      Size (in Bytes) 
 1              273                 30 
 2              934                 99 
 3              2293                27
 
 
usage: TrafficGeneratorPoissonRescale


where N = 1 (low load), N=5 (medium load), N=9 (high load).

*/

class TrafficGeneratorPoissonRescale {  
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
			
			//get N
			int N = Integer.parseInt(args[1]);
			System.out.println("N is: "+N);
			
			//file handler
            File fin = new File("poisson3.data"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			FileOutputStream fout =  new FileOutputStream("output_gen_poisson_ex1_N9.txt");
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
				
				
				//lab 3, rescaling to N
                //long time_to_wait = Etime_nano;
                long time_to_wait = Etime_nano/N;
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



