import java.net.*;

public class Frame {
    
    public Frame(int SeqNo, long Etime, DatagramPacket packet) {
        this.SeqNo = SeqNo;
        this.Etime = Etime;
        this.packet = packet;
        
        
    }				
				
    //sequence number
	public int SeqNo;
	
	//Etime is the elasped time (in nanoseconds) before this packet gets sent since the last packet
	public long Etime;
	
	//packet to send
	public DatagramPacket packet;


}
