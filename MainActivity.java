package com.odev.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

     TextView resultTv;//textview definition showing result
     double enteredNumber;//the first number to be entered will be kept here for processing with the next number
     CalculationStatueEnum calculationStatueEnum;// will  use the enum structure here and  keep the type of operation, so we will minimize the confusion.
     Button dotBtn;//We keep the dot button here in order to enable it when a fractional number can be entered and disable it when it cannot be entered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//definitions
    }

    void init()
    {
        //definitions
        resultTv = findViewById(R.id.resultTv);
        calculationStatueEnum = CalculationStatueEnum.DONE;
        dotBtn = findViewById(R.id.decimal);

    }

    public void onNumberClick(View view) {
        Button button = (Button) view;//here we get the view of the clicked button
        //If our status was previously selected as done, we empty our text and set it to empty to start the new process
        if (calculationStatueEnum == CalculationStatueEnum.DONE) {
            resultTv.setText("");//resulttext cleaning
            calculationStatueEnum = CalculationStatueEnum.EMPTY;//make our status empty
        }
        if (button.getText().equals(".") || resultTv.getText().toString().contains("."))//If a point has been entered in the clicked button point or result before, we disable our point button
            dotBtn.setEnabled(false);// enable false so that it cannot put a dot
        resultTv.append(button.getText() + "");//  add the number or dot inside the button that was clicked to the result text
    }

    public void onCalculateClick(View view) {
        if(!resultTv.getText().toString().equals("")){//check in order not to get an error if another operation is pressed without entering a number after one operation is pressed
            enteredNumber = Double.parseDouble(resultTv.getText().toString());//If the resulttv is not empty, we assign the number in it to the enteredNumber value
        }
        resultTv.setText("");//clean result
        dotBtn.setEnabled(true);//enable true for dot

        switch (view.getId()) {//with the switch structure, we detect the id of the clicked button and act accordingly
            case R.id.clear:
                calculationStatueEnum = CalculationStatueEnum.CLEAR;//If the id of the clicked button is clear,  make our status clear and call our function
                clear();//clear function
                break;
            case R.id.add://If the id of the button is add, we do add to use our status in the next operation
                calculationStatueEnum = CalculationStatueEnum.ADD;
                break;
            case R.id.factorial://If the button's id is factorial, call our function that will calculate factorial
                calculateFactorial();// function that does the factorial calculation
                break;


            case R.id.multiply://If the id of the button is multiply,  make MULTI to use our status in the next operation
                calculationStatueEnum = CalculationStatueEnum.MULTI;
                break;
            case R.id.subtract://If the id of the button is subtract,  make SUB to use our status in the next operation
                calculationStatueEnum = CalculationStatueEnum.SUB;
                break;
            case R.id.divide://If the id of the button is divide,  make DIV to use our status in the next operation
                calculationStatueEnum = CalculationStatueEnum.DIV;
                break;
        }
    }

    void calculateFactorial()
    {
        int fact=1;// define a variable to store the total value
        for(int i = 1; i<= enteredNumber; i++){//adds the values starting from 1 up to the entered value by multiplying it by the sum
            fact=fact*i;
        }
        resultTv.setText(fact + "");// show the result on the result TV to do another operation
        calculationStatueEnum = CalculationStatueEnum.DONE;//since the process is over, we are pulling our status to done

    }

    public void onIsPrime(View view)
    {
        if(resultTv.getText().toString().equals("")){//If resutltv is empty,  do not continue from here to avoid errors
            return;
        }

        int nc=0,flag=0;
        int numberCheck=Integer.parseInt( resultTv.getText().toString());
        nc=numberCheck/2;
        if(numberCheck==0||numberCheck==1){//If the number is 1 or 0, we can say that it is not directly prime
            System.out.println(numberCheck+" is not prime number");
        }else{
            for(int i=2;i<=nc;i++){//as long as the number is greater than 2,  loop will continue
                if(numberCheck%i==0){// looking at the remainder of our numbercheck variable from the i,if it is 0 it is not prime
                    Toast.makeText(this,"No, Its Not",Toast.LENGTH_LONG).show();// message part
                    flag=1;// make the flag 1 for use in the loop
                    break;
                }
            }
            if(flag==0)  {  Toast.makeText(this,"Yes, Its Prime",Toast.LENGTH_LONG).show(); }//If the flag is 0, that is, if the remainder of  i is not 0, then we can say that it is prime
        }
    }
    public void Merhaba(View view){
        Toast.makeText(this, "Merhabaaaaa", Toast.LENGTH_SHORT).show();
    }

    public void onEqualClick(View view) {
        dotBtn.setEnabled(true);//doing enable true here as a point value can be entered after the equals event
        double enteredSecondNumber= calculationStatueEnum== CalculationStatueEnum.MULTI ? 1:0;//in case of multiplication with the if structure,  make 1 in order to avoid an operation error and 0 in other cases


            if(!resultTv.getText().toString().equals("")){//if resutltv not empty
                enteredSecondNumber = Double.parseDouble(resultTv.getText().toString());// get the second entered number from resulttv

            }

        switch (calculationStatueEnum) {// here we will act according to the type of status
            case ADD://we are adding with the previous value
                enteredNumber += enteredSecondNumber;
                break;
            case SUB://we are subtracting with the previous value
                enteredNumber -= enteredSecondNumber;
                break;
            case DIV://we are dividing with the previous value
                enteredNumber /= enteredSecondNumber;
                break;
            case MULTI://we are multiplying with the previous value
                enteredNumber *= enteredSecondNumber;
                break;
            default://in case of  error situation, we equate it to the same value
                enteredNumber = enteredSecondNumber;
        }
        if(enteredNumber %1==0){  // If the remainder is 0 from dividing the result by 1, convert it to int and thus recover it from the decimal structure
            int intNumber= (int)enteredNumber;
            resultTv.setText(intNumber + "");
        }else{
            resultTv.setText(enteredNumber + "");// show the result on resulttv
        }


        calculationStatueEnum = CalculationStatueEnum.DONE;//since the process ends here, we are pulling our status to DONE
    }

    void clear(){
        resultTv.setText("");//pulling result to its initial value 0
        calculationStatueEnum = CalculationStatueEnum.EMPTY;// change our status to empty, which means no action
        dotBtn.setEnabled(true);//Since a point can be placed, we are doing enable dot true
    }


}