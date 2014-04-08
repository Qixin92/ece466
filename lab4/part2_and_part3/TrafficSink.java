import java.io.*;
import java.net.*;


/* Michael Law 997376343

*/
public class TrafficSink extends Thread{

    static int port;
    static int N;
    static int L;
    static int r;

    static boolean listening = true;



    public TrafficSink (int rp, int N, int L, int r) {
        this.port = rp;

        this.N = N;
        this.L = L;
        this.r = r;
        System.out.println("SeqNo seq_num size time(ms)");
    }

    // convert byte array to integer
    public static int fromByteArray(byte [] value, int start, int length) {
        int Return = 0;
        for (int i=start; i< start+length; i++) {
            Return = (Return << 8) + (value[i] & 0xff);
        }
        return Return;
    }

    public void run() {

        PrintStream pout = null;
        
        try {

            DatagramSocket socket = new DatagramSocket(port);
            FileOutputStream fout =  new FileOutputStream("output_sink.txt");
            pout = new PrintStream (fout);
    
            long packet_size_in_B = L/N;
            int packet_size = (int) packet_size_in_B;
            byte[] buf = new byte[packet_size];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            
            long last_receive_time = 0;
            while (listening) {
                socket.receive(packet);
                            
                //get seqno from packet
                int seq_num = fromByteArray(packet.getData(), 2, 2);

                //time to record is the current received time minus the last recieved packet time
                long got_packet_time = System.nanoTime();
                long time_to_record = got_packet_time - last_receive_time;
                last_receive_time = got_packet_time;
                
                //time in microseconds
                time_to_record = time_to_record/1000;
                
                //we do this to the first packet in both generator and sink's
                if (seq_num == 1) time_to_record = System.nanoTime() /1000;
                
                

                //update at key
                Integer key = new Integer(seq_num);
                TrafficEstimator.map.put(key, TrafficEstimator.map.get(key).setReceive(time_to_record));

                //we're done once we have N packets
                if (seq_num == N ) {
                    break;
                }

                
            }


            //fix seqno 1's entry
            TrafficEstimator.map.put(new Integer(1), TrafficEstimator.map.get(new Integer(1)).fix());

            //get culmulative timestamps
            for (int i=2; i<=N; i++) {
                Timestamp ts = TrafficEstimator.map.get(new Integer(i-1));
                TrafficEstimator.map.put(new Integer(i), TrafficEstimator.map.get(new Integer(i)).add(ts));

            }

            //print out map
            for (int i=1; i<=N; i++) {
                Timestamp ts = TrafficEstimator.map.get(new Integer(i));
                System.out.println(i+" "+ts.print());

                pout.println(i+ "\t"+  ts.send + "\t" + ts.receive); 
            }


        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();   
        }
        catch (IOException e) {
            e.printStackTrace();   
        }
        finally {
             
            
        }
    
        pout.close();
    }
}
