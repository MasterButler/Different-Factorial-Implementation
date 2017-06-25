import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Driver {
	private static final long MEGABYTE = 1024L * 1024L;
	private static final long KILOBYTE = 1024L;
	
    public static double bytesToMegabytes(long bytes) {
        return 1.0 * bytes / MEGABYTE;
    }
	
    public static double bytesToKilobytes(long bytes) {
        return 1.0 * bytes / KILOBYTE;
    }
    
    /*
     * The code has been borrowed from http://www.vogella.com/tutorials/JavaPerformance/article.html
     * The program will only be used for testing purposes and will not be used for other monetary means 
     * whatsoever.
     */
	public static double computeMemoryUsed(){
		// Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc(); 
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("Used memory is bytes: " + memory);
//        System.out.println("Used memory is kilobytes: " + bytesToKilobytes(memory));
//        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
        return bytesToKilobytes(memory);
	}
	
	public static void main(String[] args){	
		BigInteger slResult;
		BigInteger srResult;
		BigInteger pResult;
		BigInteger answer;
		
		BigInteger cResult;
		
		NanoTimer slTimer = new NanoTimer();
		NanoTimer srTimer = new NanoTimer();
		NanoTimer pTimer = new NanoTimer();
		NanoTimer cTimer = new NanoTimer();
		
		Runtime runtime;
		
		double initialMemoryUsed;
		double slMemoryUsed;
		double srMemoryUsed;
		double pMemoryUsed;
		double cMemoryUsed;
		
		long value = 100000;
		answer = Factorial.of(value);

		initialMemoryUsed = computeMemoryUsed();
		
		System.out.println("STARTING SINGLE-THREADED LOOP ALGORITHM");
		slTimer.start();
		slResult = SerialFactorial.factorial(value);
		slTimer.stop();
		System.out.println("FINISHED SINGLE-THREADED LOOP ALGORITHM\n\n");
		
		slMemoryUsed = computeMemoryUsed() - initialMemoryUsed;

		
		System.out.println("STARTING SINGLE-THREADED RECURSIVE ALGORITHM");
		srTimer.start();
		try{
			srResult = SerialFactorial2.factorial(value);	
		}catch(StackOverflowError e){
			srResult = BigInteger.ZERO;
		}
		srTimer.stop();
		System.out.println("FINISHED SINGLE-THREADED RECURSIVE ALGORITHM\n\n");
		
		srMemoryUsed = computeMemoryUsed() - (slMemoryUsed + initialMemoryUsed);
		
		System.out.println("STARTING MULTI-THREADED LOOP ALGORITHM");
		pTimer.start();
		pResult = ParallelFactorial.factorial(value);
		pTimer.stop();
		System.out.println("FINISHED MULTI-THREADED LOOP ALGORITHM\n\n");
		
		pMemoryUsed = computeMemoryUsed() - (slMemoryUsed + srMemoryUsed + initialMemoryUsed);
		
		
		System.out.println("STARTING CUSTOM MULTI-THREADED LOOP ALGORITHM");
		cTimer.start();
		cResult = new ParallelFactorial2().factorial(value, 4);
		cTimer.stop();
		System.out.println("FINISHED CUSTOM MULTI-THREADED LOOP ALGORITHM\n\n");
		
		cMemoryUsed = computeMemoryUsed() - (slMemoryUsed + srMemoryUsed + pMemoryUsed + initialMemoryUsed);
		
		
		System.out.println("CORRECTNESS: ");
		if(answer.compareTo(slResult) == 0){
			System.out.println("\tSingle Threaded Loop     : Correct");
		}else{
			System.out.println("\tSingle Threaded Loop     : Wrong");
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
		
		if(answer.compareTo(cResult) == 0){
			System.out.println("\tMulti Threaded Custom Solution  : Correct" + String.valueOf(cResult));
		}else{
			System.out.println("\tMulti Threaded Custom Solution  : Wrong" + String.valueOf(cResult));
		}
		
		
		System.out.println("Performance Speed:");
		System.out.println("\tSingle Threaded Loop     : " + slTimer.getFormattedTimeLapsed());
		System.out.println("\tSingle Threaded Recursion: " + srTimer.getFormattedTimeLapsed());
		System.out.println("\tMulti Threaded Solution  : " + pTimer.getFormattedTimeLapsed());
		System.out.println("\tMulti Threaded Custom Solution  : " + cTimer.getFormattedTimeLapsed());
		
		DecimalFormat df = new DecimalFormat("#0.000");
		System.out.println("Memory Usage");
		System.out.println("\tSingle Threaded Loop     : " + df.format(slMemoryUsed) + " KB");
		System.out.println("\tSingle Threaded Recursion: " + df.format(srMemoryUsed) + " KB");
		System.out.println("\tMulti Threaded Solution  : " + df.format(pMemoryUsed) + " KB");
		System.out.println("\tMulti Threaded Custom Solution  : " + df.format(cMemoryUsed) + " KB");
		
	}
}