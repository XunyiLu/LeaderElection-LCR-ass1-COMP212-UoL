import java.util.*;

public class Processor {
    private int myID;
    private int sendID;
    private int inID;
    private boolean status; //true = leader ; false = unknown
    private int leaderID;
    private int startRound;
    private int receiveID;
    private boolean ifInterface;
    private List<SubProcessor> sub;
    private int messageTotal;
    private int roundTotal;

    public Processor(int uid){
        this.myID = uid;
        this.sendID = uid;
        this.inID = uid;
        this.status = false;
        this.leaderID = uid;
        this.startRound = 0;
        this.receiveID = uid;
        this.leaderID = -1;
        this.ifInterface = false;
        this.roundTotal = 0;
        this.messageTotal = 0;
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
    public void setIfInterface(boolean n){ifInterface = n;}
    public boolean getIfInterface(){return ifInterface;}
    public void setMessage(int n){messageTotal = n;}
    public int getMessage(){return messageTotal;}
    public void setRound(int n){roundTotal = n;}
    public int getRound(){return roundTotal;}

    public void generator(){
        List<SubProcessor> subRing = new LinkedList<>();
        Random r = new Random();
        int num = r.nextInt(7);// Range [3,10)
        num = num + 3;// subRing's number of processor >= 3

        //assign myID for subRIng Range {3,4,5,.....num}
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < num; i++){
            arr.add(i+1);
        }
        //Mix up
        Collections.shuffle(arr);
//        System.out.println("This is the subRing");
        for(int i = 0; i < num; i++){
            subRing.add(new SubProcessor(arr.get(i)));
//            System.out.print(subRing.get(i).getMyID() + " -> ");
        }
        //assign a startRound number to each subProcessor
        for(int i = 0; i < num; i++){
            subRing.get(i).setStartRound(i+1);
        }

        int round;
        int leader = -1;
        int message = 0;
        for (round = 1; round < 100000; round++){
            //send
            subRing.get(0).setReceiveID(subRing.get(subRing.size() - 1).getSendID());
            for (int i = 1; i < subRing.size(); i++) {
                subRing.get(i).setReceiveID(subRing.get(i - 1).getSendID());
            }

            //receive
            for (int i = 0; i < subRing.size(); i++) {
                if (subRing.get(i).getStartRound() <= round) {
                    //receiveID > myID， set sendID to receiveID
                    if (subRing.get(i).getReceiveID() > subRing.get(i).getMyID()) {
                        subRing.get(i).setSendID(subRing.get(i).getReceiveID());
                    } else if (subRing.get(i).getReceiveID() == subRing.get(i).getMyID()) {
                        leader = subRing.get(i).getInID();
                        break;
                    } else {
                        subRing.get(i).setSendID(subRing.get(i).getMyID());
                    }
                }
                message++;
            }

            if(leader > 0)
                break;

        }

        for(int i = 0; i < num; i++){
//            subRing.get(i).setLeaderID(leader);
            round++;
            message++;
        }
        myID = leader;
        messageTotal = message;
        roundTotal = round;
        sub = subRing;

    }
}