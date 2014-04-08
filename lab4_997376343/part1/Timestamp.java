import java.io.*;
import java.net.*;
import java.lang.*;


public class Timestamp {
    public long send=0;
    public long receive=0;

     /* constructor */
    public Timestamp(long send) {
        this.send = send;
    }
    public Timestamp setReceive(long receive) {
        this.receive = receive;
        return this;
    }

    public String print() {
        return " "+String.valueOf(send) + " " + String.valueOf(receive);
    }

    public Timestamp fix () {
        this.receive = this.receive - this.send;
        this.send = 0;
        return this;
    }

    public Timestamp add(Timestamp ts) {
        this.send = this.send + ts.send;
        this.receive = this.receive + ts.receive;
        return this;
    }
}