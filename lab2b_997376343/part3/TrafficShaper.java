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
		
		//Exercise 3.1
		//TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 1024, 100*1024, 10000, 5000, "bucket_video.txt");
		
		//lab2a
		//Exercise 3.2 poisson and 3.3 Video traffic and Ethernet
		//using 65507 as max packet size
		//TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 100*65507, 65508, 5000, "bucket_poisson.txt");

        //lab2b
        //experiment 1 - 100 tokens (100 bytes), with a rate of 250000 tokens/s --> 250000 bytes/sec --> 2 Mbps
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 100*65507, 100, 250000, "output_bucket_exp1.txt");
        
        //experiment 2
        //size changed from 100 to 500,
        //rate at 125000 tokens/s --> 1Mbps 
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 100*65507, 500, 125000, "output_bucket_exp2.txt");
        
        //experiment 3
        //size of bucket changed to 100
        //rate at 100000 tokens/s --> 0.8 Mbps 
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 100*65507, 100, 100000, "output_bucket_exp3.txt");
        
        //excercise 2.4, finding the max
        //100000 is the size of the token bucket 
        //64 Mbps
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 800000000, 10000, 8000000, "output_bucket_max.txt");
        
        //excercise 3.1 
        //poisson
        //size = 985
        //rate = 126050.. increased to 200000
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 6550700, 1500, 300000, "output_bucket_ex31_poisson.txt");
        
        //exercise 3.1 video
        //rate 1473000 to 4473000
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 65507000, 634344,  4473000, "output_bucket_ex31_video.txt");
        
        
        //exercise 3.1 ethernet
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 655070000, 1518,  1000000, "output_bucket_ex31_etheret.txt");
        
        //excercise 3.1 
        //poisson
        //size = 985
        //rate = 126050.. increased to 200000
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 6550700, 5000, 126050, "output_bucket_ex32_poisson.txt");
        
        //exercise 3.2 video
        //burst 
        //TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 655070000, 50543440,  1473000, "output_bucket_ex32_video.txt");
        
        
        //exercise 3.2 ethernet
        TokenBucket lb = new TokenBucket (4444, "localhost", 4445, 65507, 655070000, 166980,  175060, "output_bucket_ex32_ethernet.txt");
        
		new Thread(lb).start();
		
		 
	}  
}



