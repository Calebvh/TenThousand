import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public List Roll(){
        Random rand = new Random();
        List<Integer> Outcome = new ArrayList<Integer>();

        for(int i = 0; i<6; i++){
            Outcome.add(i, rand.nextInt(6)+1);
        }

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

    public Boolean IsThreeDoubles(List<Integer> Roll) {
        List<Integer> DoubleChecker1 = new ArrayList<Integer>();
        List<Integer> DoubleChecker2 = new ArrayList<Integer>();
        List<Integer> AlreadyDouble = new ArrayList<Integer>();
        boolean ret = false;
        int DoubleCount = 0;
        for (int j = 0; j < Roll.size(); j++) { //Iterate through rolled dice
            if (DoubleChecker1.contains(Roll.get(j)) &&
                    DoubleChecker2.contains(Roll.get(j)) &&
                    !AlreadyDouble.contains(Roll.get(j))) {
                DoubleCount++;
                AlreadyDouble.add(Roll.get(j));
            } else if (DoubleChecker1.contains(Roll.get(j)) &&
                    !DoubleChecker2.contains(Roll.get(j))) {
                DoubleChecker2.add(Roll.get(j));
            } else {
                DoubleChecker1.add(Roll.get(j));
            }
        }

        if(DoubleCount == 3){
            ret = true;
        }

        return ret;
    }

    public int CalculateScore(List<Integer> Roll){
        int PointsEarned = 0;
        ///////////////////////////////////////////////////////////////////
        //Special Cases
        //Straight
        Boolean Straight = IsStraight(Roll);
        Boolean Doubles = IsThreeDoubles(Roll);

        if(Straight){
            PointsEarned = 5000;
        }
        else if(Doubles){
            PointsEarned = 500;
        }
        else{
            for(int i = 6; i>=1; i--){ //For Loop to focus on each number on the dice i.e 1-6
                int numcount = 0;
                for(int j = 0; j<6; j++){ //Iterate through rolled dice
                    if(Roll.get(j) == i){
                        numcount++;
                    }
                }
                if(numcount>= 3){
                    PointsEarned += (i*100) * numcount;
                }
                else if(i == 5 ){
                    PointsEarned += numcount*50;
                }
                else if(i == 1){
                    PointsEarned += numcount*100;
                }
            }
        }

        System.out.println(PointsEarned);
        return PointsEarned;
    }

    public static void main(String[] args){
        Main MainObj =  new Main();
        List<Integer> PlayerRoll = MainObj.Roll();
        //int[] a = new int[] {0, 0, 0, 0};
        MainObj.CalculateScore(PlayerRoll);


    }
}
