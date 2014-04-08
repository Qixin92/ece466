// Tzuo Shuin Yew 997266499
import java.io.*; 
import java.util.*;
import java.net.*;

// probe packet
public class probe {  
    private int seqNo = 0;
    private long sendTime = 0;
    private long receiveTime = 0;
    
    // constructor
    public probe(int _seqNo, long _sendTime) {
		seqNo = _seqNo;
		sendTime = _sendTime;
    }
    // set sequence #
	public void setSeqNo(int input) {
		seqNo = input;
	}
	// get value of sequence #
    public int getSeqNo() {
		return seqNo;
	}
	// record value of time sent
	public void setSendTime(long input) {
		sendTime = input;
	}
	// return value of time sent
    public long getSendTime() {
		return sendTime;
	}
	// record value of time received
	public void setReceiveTime(long _receiveTime) {
		receiveTime = _receiveTime;
	}
	// return value of time received
    public long getReceiveTime() {
		return receiveTime;
	}
}
