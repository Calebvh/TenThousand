import java.awt.*;
import java.util.*;
import java.util.List;


public class Main {

    public List Roll(int NumDice){
        Random rand = new Random();
        List<Integer> Outcome = new ArrayList<Integer>();

        for(int i = 0; i<NumDice; i++){
            Outcome.add(i, rand.nextInt(6)+1);
        }

        Collections.sort(Outcome);

        System.out.println("Your Roll: " +  Arrays.toString(Outcome.toArray()));

        return Outcome;
    }

    public boolean IsStraight(List<Integer> MasterRollList){
        Boolean Straight = true;
        for(int j = 1; j<MasterRollList.size()-1; j++){ //Iterate through rolled dice
            if(MasterRollList.get(j) != 1){
                Straight =false;
            }

        }
        return Straight;
    }

    public Boolean IsThreeDoubles(List<Integer> MasterRollList) {
        int doubleCount = 0;
        boolean ret = false;
        for(int i = 1; i<MasterRollList.size()-1; i++){
            if(MasterRollList.get(i) == 2){
                doubleCount++;
            }
        }

        if(doubleCount == 3){
            ret = true;
        }
        return ret;
    }

    public List CalculateScore(List<Integer> Roll, int CurrentRollScore){
        int PointsEarned = 0;
        ///////////////////////////////////////////////////////////////////
        //Number Arrays
        List<Integer> MasterList =  new ArrayList<Integer>(Collections.nCopies(8,0));
        MasterList.set(0,CurrentRollScore);

        for(int i = 0; i<Roll.size(); i++){
           switch (Roll.get(i)){
               case 1:
                   MasterList.set(1,MasterList.get(1)+1);
                   break;
               case 2:
                   MasterList.set(2,MasterList.get(2)+1);
                   break;
               case 3:
                   MasterList.set(3,MasterList.get(3)+1);
                   break;
               case 4:
                   MasterList.set(4,MasterList.get(4)+1);
                   break;
               case 5:
                   MasterList.set(5,MasterList.get(5)+1);
                   break;
               case 6:
                   MasterList.set(6,MasterList.get(6)+1);
                   break;
           }
        }
        /////////////////////////////////////////////////////////////////////
        // Calculate Score
        if(IsStraight(MasterList)){
            MasterList.set(0, MasterList.get(0)+5000);
        }
        else if(IsThreeDoubles(MasterList))   {
            MasterList.set(0, MasterList.get(0)+500);
        }
        else {
            //Logic for One's Rolled
            if (MasterList.get(1) < 3) {
                PointsEarned += MasterList.get(1) * 100;
            } else if (MasterList.get(1) >= 3) {
                PointsEarned += 1000 * Math.pow(2, MasterList.get(1) - 3);
            }

            //Logic for Five's Rolled
            if (MasterList.get(5) < 3) {
                PointsEarned += MasterList.get(5) * 50;
            }

            //Logic for Every other Number
            for (int i = 2; i < MasterList.size()-1; i++) {
                if (MasterList.get(i) >= 3) {
                    PointsEarned += i * 100 * Math.pow(2, MasterList.get(i) - 3);
                }
                else if(i != 5){
                    MasterList.set(7, MasterList.get(7)+MasterList.get(i));
                }
            }
        }

         MasterList.set(0,MasterList.get(0)+PointsEarned);
        return MasterList;
    }

    public boolean RollAgain(List<Integer> CurrentRoll){
        boolean retval = false;
        System.out.println("Your total score for this turn is: "+ CurrentRoll.get(0));
        System.out.println("You have "+ CurrentRoll.get(7)+" Dice to Roll");

        System.out.println("Would you like to Roll Again?(y/n): ");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String n = reader.next(); // Scans the next token of the input as an int.
        //once finished
        //reader.close();
        System.out.println(n);
        if(n.equals("y")){retval=true;}
        return retval;
    }

    public void PlayerRoll(Player Player, int PrevRollScore, int DiceLeft){
        System.out.println("#######################################");
        System.out.println(Player.Name + "'s Roll" + "  " + Player.GotFirstThousand);
        System.out.println("Current Score:" + Player.Score);
        List<Integer> Roll = Roll(DiceLeft);
        List<Integer> RollOutcome = CalculateScore(Roll, PrevRollScore);

        if(PrevRollScore < RollOutcome.get(0)) {
            boolean NewRoll = RollAgain(RollOutcome);
            if (NewRoll) {
                if(RollOutcome.get(7) == 0){
                    PlayerRoll(Player, RollOutcome.get(0), 6);
                }
                else{
                    PlayerRoll(Player, RollOutcome.get(0), RollOutcome.get(7));
                }
            } else {
                if(!Player.GotFirstThousand && RollOutcome.get(0) >=1000){
                    Player.GotFirstThousand = true;
                    Player.Score = RollOutcome.get(0) - 1000;
                }
                else if(Player.GotFirstThousand){
                    Player.Score += RollOutcome.get(0);
                }
            }
        }
        else{
            System.out.println("Sorry you get no points this turn");
        }
    }

    public static void main(String[] args){

        Main MainObj =  new Main();
        Player Player1 = new Player("Caleb van Haaren");
        Player Player2 = new Player("Here is a new  Player");
        Player Player3 = new Player("Here is the branch branch branch branch player");
        int Turn = 0;
        while(true) {
            if(Turn%2 == 0){
                MainObj.PlayerRoll(Player1, 0, 6);
            }
            else{
                MainObj.PlayerRoll(Player2, 0, 6);
            }
            Turn++;
        }
    }
}
