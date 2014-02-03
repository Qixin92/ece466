import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;
import TokenBucket.TokenBucket;
/* Michael Law 997376343

Exercise 3.1 Running Token Bucket
 
usage: java TrafficShaper

*/

class TrafficShaper {  
	public static void main (String[] args) { 
		
		//listen on port 4444, 
		//send to localhost
		//send to port 4445
		//max size of packet received is 1024
		//buffer capacity is 100*1024 bytes,
		//token bucket has 10000 tokens
		//token bucket has rate 5000 tokens/sec
		//records packet arrivals to bucket.txt
		  
		TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 1024, 100*1024, 10000, 5000, "bucket.txt");
		new Thread(lb).start();
		
		 
	}  
}



