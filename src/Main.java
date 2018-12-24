import java.util.*;

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

    public boolean IsStraight(List<Integer> Roll){
        Boolean Straight = true;
        List<Integer> StraightChecker = new ArrayList<Integer>();
        for(int j = 0; j<Roll.size(); j++){ //Iterate through rolled dice
            if(StraightChecker.contains(Roll.get(j)) ){
                Straight = false;
            }
            else{
                StraightChecker.add(Roll.get(j));
            }

        }
        return Straight;
    }

    public Boolean IsThreeDoubles(List<Integer> RollCount) {
        int doubleCount = 0;
        boolean ret = false;
        for(int i = 1; i<RollCount.size(); i++){
            if(RollCount.get(i) == 2){
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
        List<Integer> MasterList =  new ArrayList<Integer>(Collections.nCopies(7,0));
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
        System.out.println(MasterList);
        /////////////////////////////////////////////////////////////////////
        // Calculate Score
        if(IsStraight(Roll)){
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
                PointsEarned += MasterList.get(1) * 1000 * Math.pow(2, MasterList.get(1) - 3);
            }

            //Logic for Five's Rolled
            if (MasterList.get(5) < 3) {
                PointsEarned += MasterList.get(5) * 50;
            }

            //Logic for Every other Number
            for (int i = 2; i < MasterList.size(); i++) {
                if (MasterList.get(i) >= 3) {
                    PointsEarned += i * 100 * Math.pow(2, MasterList.get(i) - 3);
                }
            }
        }

         MasterList.set(0,MasterList.get(0)+PointsEarned);

        System.out.println(MasterList.get(0));
        return MasterList;
    }

    public static void main(String[] args){
        Main MainObj =  new Main();
        List<Integer> PlayerRoll = MainObj.Roll(6);
        /*
        List<Integer> PlayerRoll =  new ArrayList<Integer>();
        PlayerRoll.add(0,1);
        PlayerRoll.add(1,2);
        PlayerRoll.add(2,3);
        PlayerRoll.add(3,4);
        PlayerRoll.add(4,5);
        PlayerRoll.add(5,6);
        */

        MainObj.CalculateScore(PlayerRoll, 0);


    }
}
