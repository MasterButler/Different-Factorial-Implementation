import java.math.BigInteger;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args){	
		BigInteger slResult;
		BigInteger srResult;
		BigInteger pResult;
		BigInteger answer;
		
		NanoTimer slTimer = new NanoTimer();
		NanoTimer srTimer = new NanoTimer();
		NanoTimer pTimer = new NanoTimer();
		
		Runtime runtime;
		
		long slMemoryUsed;
		long srMemoryUsed;
		long pMemoryUsed;
		
		long value = 200000;
		answer = Factorial.of(value);

		System.out.println("STARTING SINGLE-THREADED LOOP ALGORITHM");
		slTimer.start();
		slResult = SerialFactorial.factorial(value);
		slTimer.stop();
		System.out.println("FINISHED SINGLE-THREADED LOOP ALGORITHM\n\n");
		

		
		System.out.println("STARTING SINGLE-THREADED RECURSIVE ALGORITHM");
		srTimer.start();
		try{
			srResult = SerialFactorial2.factorial(value);	
		}catch(StackOverflowError e){
			srResult = BigInteger.ZERO;
		}
		srTimer.stop();
		System.out.println("FINISHED SINGLE-THREADED RECURSIVE ALGORITHM\n\n");
		
		
		
		System.out.println("STARTING MULTI-THREADED LOOP ALGORITHM");
		pTimer.start();
		pResult = ParallelFactorial.factorial(value);
		pTimer.stop();
		System.out.println("FINISHED MULTI-THREADED LOOP ALGORITHM\n\n");
		
		
		System.out.println("CORRECTNESS: ");
		if(answer.compareTo(slResult) == 0){
			System.out.println("\tSingle Threaded Loop     : Correct" + slResult);
		}else{
			System.out.println("\tSingle Threaded Loop     : Wrong" + slResult);
		} 	
		
		if(answer.compareTo(srResult) == 0){
			System.out.println("\tSingle Threaded Recursion: Correct");
		}else{
			System.out.println("\tSingle Threaded Recursion: Wrong");
		}
		
		if(answer.compareTo(pResult) == 0){
			System.out.println("\tMulti Threaded Solution  : Correct");
		}else{
			System.out.println("\tMulti Threaded Solution  : Wrong");
		}
		
		System.out.println("Performance Speed:");
		System.out.println("\tSingle Threaded Loop     : " + slTimer.getFormattedTimeLapsed());
		System.out.println("\tSingle Threaded Recursion: " + srTimer.getFormattedTimeLapsed());
		System.out.println("\tMulti Threaded Solution  : " + pTimer.getFormattedTimeLapsed());
		
		
	}
}