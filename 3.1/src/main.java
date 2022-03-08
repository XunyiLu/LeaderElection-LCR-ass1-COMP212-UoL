import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.Scanner;


public class main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("-----------------------------------------------------------");
        System.out.println("| Input the number of processors in a ring （larger than 2）|");
        System.out.println("------------------------------------------------------------");
        int num_processor;
        num_processor = input.nextInt();
        System.out.println("--------------------------------------------");
        System.out.println("| Input the ID assignment setting in a ring|");
        System.out.println("|          0: Descending order             |");
        System.out.println("|           1: Ascending order             |");
        System.out.println("|           2: Random order                |");
        System.out.println("--------------------------------------------");
        int mode;
        mode = input.nextInt();
        List<Processor> processes = new LinkedList<>();
        Random r = new Random();

        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i = 0; i < 3*num_processor; i++){
            arr.add(i);
        }
        Collections.shuffle(arr);

        //Build a List，contain n processors，ranged from 0 to 3*n
        for (int i = 0; i < num_processor; i++) {
            processes.add(new Processor(arr.get(i)));
        }

        /*Range the processors List
        1. Des
        2. Asc
        3.Random
        */
        Processor temp;
        switch (mode) {
            case 0:// Des
                // Construct the Ring structure
                //realize every Processor in processesList is Des
                for (int j = num_processor-1; j >= 0; j--) {
                    for (int i = num_processor - 1; i > 0; i--) {
                        if (processes.get(i).getMyID() > processes.get(i - 1).getMyID()) {
                            temp = processes.get(i);
                            processes.set(i, processes.get(i - 1));
                            processes.set(i - 1, temp);
                        }
                    }
                }

                /*
                give a startRound to every processor.
                for simplicity
                u1 wakes up in round 1
                u2 wakes up in round 2
                ......
                */
                for (int m = 0; m < num_processor; m++) {
                    processes.get(m).setStartRound(m + 1);
                }

                break;
            case 1: //Asc
                //repeat the steps stated in case 0
                for (int j = 0; j < num_processor - 1; j++) {
                    for (int i = num_processor - 1; i > 0; i--) {
                        if (processes.get(i).getMyID() < processes.get(i - 1).getMyID()) {
                            temp = processes.get(i);
                            processes.set(i, processes.get(i - 1));
                            processes.set(i - 1, temp);
                        }
                    }
                }

                for (int m = 0; m < num_processor; m++) {
                    processes.get(m).setStartRound(m + 1);
                }
                break;
            case 2:  //Random

                for (int m = 0; m < num_processor; m++) {
                    processes.get(m).setStartRound(m + 1);
                }

                break;
            default:
                break;
        }

        //struct the ring structure
        System.out.println("Structure of the ring.");
        for(int i = 0; i < num_processor; i++){
            System.out.print(processes.get(i).getMyID() + " -> ");
        }
        System.out.println(processes.get(0).getMyID());



        int round;
        int leader = -1;
        int message = 0;
        for (round = 1; round < 100000; round++){
            //send
            processes.get(0).setReceiveID(processes.get(processes.size() - 1).getSendID());
            for (int i = 1; i < processes.size(); i++) {
                processes.get(i).setReceiveID(processes.get(i - 1).getSendID());
            }

            //receive
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).getStartRound() <= round) {
                    //receiveID > myID， set sendID to receiveID
                    if (processes.get(i).getReceiveID() > processes.get(i).getMyID()) {
                        processes.get(i).setSendID(processes.get(i).getReceiveID());
                    } else if (processes.get(i).getReceiveID() == processes.get(i).getMyID()) {
                        leader = processes.get(i).getInID();
                        break;
                    } else {
                        processes.get(i).setSendID(processes.get(i).getMyID());
                    }
                }
                message++;
            }

            if(leader > 0)
                break;

        }

        for(int i = 0; i < num_processor; i++){
            processes.get(i).setLeaderID(leader);
            round++;
            message++;
        }
        System.out.println("Leader = " + leader);
        System.out.println("Round = " + round);
        System.out.println("#Message = " + message);

    }
}
