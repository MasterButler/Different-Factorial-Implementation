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
		
		BigInteger c2Result;
		BigInteger c4Result;
		BigInteger c8Result;
		
		NanoTimer slTimer = new NanoTimer();
		NanoTimer srTimer = new NanoTimer();
		NanoTimer pTimer = new NanoTimer();
		NanoTimer c2Timer = new NanoTimer();
		NanoTimer c4Timer = new NanoTimer();
		NanoTimer c8Timer = new NanoTimer();
		
		Runtime runtime;
		
		double initialMemoryUsed;
		double slMemoryUsed;
		double srMemoryUsed;
		double pMemoryUsed;
		double c2MemoryUsed;
		double c4MemoryUsed;
		double c8MemoryUsed;
		
		long value = 1000000;
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
		
		
		System.out.println("STARTING 2 thread MULTI-THREADED LOOP ALGORITHM");
		c2Timer.start();
		c2Result = new ParallelFactorial2().factorial(value, 2);
		c2Timer.stop();
		System.out.println("FINISHED 2 thread MULTI-THREADED LOOP ALGORITHM\n\n");
		
		c2MemoryUsed = computeMemoryUsed() - (slMemoryUsed + srMemoryUsed + initialMemoryUsed);
		
		System.out.println("STARTING 4 thread MULTI-THREADED LOOP ALGORITHM");
		c4Timer.start();
		c4Result = new ParallelFactorial2().factorial(value, 4);
		c4Timer.stop();
		System.out.println("FINISHED 4 cothreadre MULTI-THREADED LOOP ALGORITHM\n\n");
		
		c4MemoryUsed = computeMemoryUsed() - (c2MemoryUsed + slMemoryUsed + srMemoryUsed + initialMemoryUsed);
		
		System.out.println("STARTING 8 thread MULTI-THREADED LOOP ALGORITHM");
		c8Timer.start();
		c8Result = new ParallelFactorial2().factorial(value, 8);
		c8Timer.stop();
		System.out.println("FINISHED 8 thread MULTI-THREADED LOOP ALGORITHM\n\n");
		
		c8MemoryUsed = computeMemoryUsed() - (c4MemoryUsed + c2MemoryUsed + slMemoryUsed + srMemoryUsed + initialMemoryUsed);
		
		
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
		
		if(answer.compareTo(c2Result) == 0){
			System.out.println("\tMulti Threaded 2 thread  : Correct");
		}else{
			System.out.println("\tMulti Threaded 2 thread  : Wrong");
		}
		
		if(answer.compareTo(c4Result) == 0){
			System.out.println("\tMulti Threaded 4 thread  : Correct");
		}else{
			System.out.println("\tMulti Threaded 4 thread  : Wrong");
		}
		
		if(answer.compareTo(c8Result) == 0){
			System.out.println("\tMulti Threaded 8 thread  : Correct");
		}else{
			System.out.println("\tMulti Threaded 8 thread  : Wrong");
		}
		
		
		System.out.println("Performance Speed:");
		System.out.println("\tSingle Threaded Loop     : " + slTimer.getFormattedTimeLapsed());
		System.out.println("\tSingle Threaded Recursion: " + srTimer.getFormattedTimeLapsed());
		System.out.println("\tMulti Threaded 2 thread  : " + c2Timer.getFormattedTimeLapsed());
		System.out.println("\tMulti Threaded 4 thread  : " + c4Timer.getFormattedTimeLapsed());
		System.out.println("\tMulti Threaded 8 thread  : " + c8Timer.getFormattedTimeLapsed());
		
		DecimalFormat df = new DecimalFormat("#0.000");
		System.out.println("Memory Usage");
		System.out.println("\tSingle Threaded Loop     : " + df.format(slMemoryUsed) + " KB");
		System.out.println("\tSingle Threaded Recursion: " + df.format(srMemoryUsed) + " KB");
		System.out.println("\tMulti Threaded 2 thread  : " + df.format(c2MemoryUsed) + " KB");
		System.out.println("\tMulti Threaded 4 thread  : " + df.format(c4MemoryUsed) + " KB");
		System.out.println("\tMulti Threaded 8 thread  : " + df.format(c8MemoryUsed) + " KB");
		
	}
}