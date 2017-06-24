import java.math.BigInteger;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args){
		BigInteger slResult;
		BigInteger srResult;
		BigInteger pResult;
		
		NanoTimer slTimer = new NanoTimer();
		NanoTimer srTimer = new NanoTimer();
		NanoTimer pTimer = new NanoTimer();

		long value = 1;

	
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
		
		if(srResult.compareTo(slResult) == 0){
			System.out.println("RECURSIVE RESULT HAS THE SAME VALUE AS THE LOOP");
		}else{
			System.out.println("RECURSIVE RESULT STACKOVERFLOW ERROR");
		}
		
		if(pResult.compareTo(slResult) == 0){
			System.out.println("MTHREADED RESULT HAS THE SAME VALUE AS THE LOOP");
		}else{
			System.out.println("MTHREADED RESULT ERROR");
		}
		System.out.println("\n");
		System.out.println("Time elapsed for Single Threaded Loop Algorithm\t\t: " + slTimer.getFormattedTimeLapsed());
		System.out.println("Time elapsed for Single Threaded Recursive Algorithm\t: " + srTimer.getFormattedTimeLapsed());
		System.out.println("Time elapsed for Multi Threaded Algorithm\t\t: " + pTimer.getFormattedTimeLapsed());
		
//
//		slTimer.start();
//		
//		BigInteger slResult = SerialFactorial.factorial(value);
//		
//		slTimer.stop();
//		System.out.println("Result: " + slResult);
//		System.out.println("Time elapsed for Single Threaded Loop Algorithm\t\t: " + slTimer.getFormattedTimeLapsed());
//		
//		

//		
//		srTimer.start();
//		
//		BigInteger srResult = SerialFactorial2.factorial(value);
//		
//		srTimer.stop();
//		System.out.println("Result: " + srResult);
//		System.out.println("Time elapsed for Single Threaded Recursive Algorithm\t: " + srTimer.getFormattedTimeLapsed());
//
		
//		pTimer.start();
//		
//		BigInteger pResult = ParallelFactorial.factorial(value);
//		
//		pTimer.stop();
//		System.out.println("Result: " + pResult);		
//		System.out.println("Time elapsed for Multi Threaded Algorithm\t\t: " + pTimer.getFormattedTimeLapsed());

		
		
	}
}