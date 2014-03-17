import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;
import PacketScheduler.PacketScheduler;
/* Michael Law 997376343

usage: java TrafficScheduler

*/

class TrafficScheduler {  
	public static void main (String[] args) { 
     /* 
     * Create a new packet scheduler. 
     * Scheduler listens on UDP port 4444 for incoming *packets 
     * and sends outgoing packets to localhost:4445. 
     * Transmission rate of scheduler is 2Mbps. The scheduler 
     * has 2 queues, and accepts packets of maximum size 1024 
     * bytes. 
     * Capacity of first queue is 100*1024 bytes and capacity 
     * if second queue is 200*1024 bytes. 
     * Arrivals of packets are recorded to file ps.txt. 
     */ 
     
     PacketScheduler ps = new PacketScheduler(4444, "localhost", 4445, 
        2000000,    //transmission rate of scheduler is 2 Mbps
        2,          //2 queues
        1024,       //max packet size of 1024 B
        new long [] {100*1024, 200*1024}, //first queue is 100*1024 Bytes, second queue capacity is 200*1024 Bytes
        "ps.txt"); 
        
        
    //Exercise 1.2 Implement a FIFO Scheduler
        // buffer 100kB
        //capacity of link to 10Mbps
     PacketScheduler ps2 = new PacketScheduler(4444, "localhost", 4445, 
        1000000,    //transmission rate of scheduler is 1 Mbps
        1,          //1 queues
        2024,       //packet size of 2024 so we don't lose any packets due to its packet size
        new long [] {100*1024},  //buffer size of 100 kB
        "output_scheduler_ex13_N9.txt"); 
        
     // start packet scheduler 
     new Thread(ps2).start(); 
		
		 
	}  
}



