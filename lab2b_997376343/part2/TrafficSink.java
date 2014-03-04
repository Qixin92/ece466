import java.io.*;
import java.net.*;


/* Michael Law 997376343

Exercise 2.2 Traffic Sink

The output file TrafficSinkOutput.txt has the format: 
SeqNo       Size (in bytes)      arrival time since last packet (in usec) 
 1              273                 30 
 2              934                 99 
 3              2293                27
 
 
usage: java TrafficSink <port>
if <port> is empty, 4444 will be used as default

*/
public class TrafficSink {
  public static void main(String[] args) throws IOException {
    
    PrintStream pout = null;
    
    //get ip address of TrafficSink
    String receiver_hostname =  InetAddress.getLocalHost().getHostAddress();  
    System.out.println("Running at: "+receiver_hostname);
    
    //default port
    int port = 4445;
    try {
        port = Integer.parseInt(args[0]);
    }
    finally {
        System.out.println(port);    
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buf = new byte[65507];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("Waiting ..."); 
        
        boolean listening = true;
        
        FileOutputStream fout =  new FileOutputStream("output_sink_max.txt");
	    pout = new PrintStream (fout);
			
	    int SeqNo = 1;
	    long last_receive_time = 0;
        while (listening) {
            socket.receive(packet);
                        
            //time to record is the current received time minus the last recieved packet time
            long got_packet_time = System.nanoTime();
            long time_to_record = got_packet_time - last_receive_time;
            if (SeqNo == 1) time_to_record = 0; 
            last_receive_time = got_packet_time;
            
            //time in microseconds
            time_to_record = time_to_record/1000;
            pout.println(SeqNo+ "\t"+  packet.getLength() + "\t" + time_to_record); 
            SeqNo++;
        }
    }
    
    pout.close();
    //System.out.println(p.getAddress().g);
    /*
    String s = new String(p.getData(), 0, p.getLength());
    System.out.println(p.getAddress().getHostName() + ": " + s);
    */
  }
}
