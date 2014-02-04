import java.io.*;
import java.net.*;
public class Receiver {
  public static void main(String[] args) throws IOException {
    
    //get ip address of Receiver
    String receiver_hostname =  InetAddress.getLocalHost().getHostAddress();  
    System.out.println("Running at: "+receiver_hostname);
    
    DatagramSocket socket = new DatagramSocket(4444);
    byte[] buf = new byte[256];
    DatagramPacket p = new DatagramPacket(buf, buf.length);
    System.out.println("Waiting ..."); 
    socket.receive(p);
    String s = new String(p.getData(), 0, p.getLength());
    System.out.println(p.getAddress().getHostName() + ": " + s);
  }
}
