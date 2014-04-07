import java.io.*;
import java.net.*;
import java.util.concurrent.*;
/* Michael Law 997376343

Traffic Estimator
    send packets to and receive packets from the black box

*/
public class TrafficEstimator {

    public static ConcurrentHashMap<Integer,Timestamp> map = new ConcurrentHashMap<Integer, Timestamp> ();

    public static void main(String[] args) throws IOException {
        
    //get our own IP address
    String receiver_hostname =  InetAddress.getLocalHost().getHostAddress();  
    System.out.println("Running at: "+receiver_hostname);


    //BLACKBOX at localhost:4444
    String hostname = args[0]; // localhost
    InetAddress addr = InetAddress.getByName(hostname); 
    int port = Integer.parseInt(args[1]); // 4444
    int return_port = 5555; //sink's port

    int N = Integer.parseInt(args[2]);
    int L = Integer.parseInt(args[3]);
    int r = Integer.parseInt(args[4]);

    //tell the generator to send traffic to blackbox
    new TrafficGenerator(addr, port, N, L, r, return_port).start();

    //receive traffic from blackbox and compute timestamps
    new TrafficSink(return_port,N, L, r).start();



    }
}



