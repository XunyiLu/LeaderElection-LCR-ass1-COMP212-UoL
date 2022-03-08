import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SubProcessor {
    private int myID;
    private int sendID;
    private int inID;
    private boolean status; //true = leader ; false = unknown
    private int leaderID;
    private int startRound;
    private int receiveID;



    public SubProcessor(int uid){
        this.myID = uid;
        this.sendID = uid;
        this.inID = uid;
        this.status = false;
        this.leaderID = uid;
        this.startRound = 0;
        this.receiveID = uid;
        this.leaderID = -1;

    }

    public int getMyID(){return myID; }
    public void setMyID(int n){myID = n;}
    public int getSendID(){return sendID;}
    public void setSendID(int n){sendID = n;}
    public int getInID(){return inID;}
    public void setStartRound(int n){startRound = n;}
    public int getStartRound(){return startRound;}
    public void setReceiveID(int n){receiveID = n;}
    public int getReceiveID(){return receiveID;}
    public void setLeaderID(int n){leaderID = n;}



}