import java.math.BigInteger;
import java.text.DecimalFormat;

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
	public static double computeMemoryUsedBefore(){
		// Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc(); 
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        return bytesToKilobytes(memory);
	}
	
	public static double computeMemoryUsedAfter(){
		// Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
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
		
		long value = 100000;
		answer = Factorial.of(value);

		System.out.println("STARTING SINGLE-THREADED LOOP ALGORITHM");
		initialMemoryUsed = computeMemoryUsedBefore();
		
		slTimer.start();
		slResult = SerialFactorial.factorial(value);
		slTimer.stop();
		
		slMemoryUsed = computeMemoryUsedAfter() - initialMemoryUsed;
		System.out.println("FINISHED SINGLE-THREADED LOOP ALGORITHM\n\n");

		
		System.out.println("STARTING SINGLE-THREADED RECURSIVE ALGORITHM");
		initialMemoryUsed = computeMemoryUsedBefore();
		srTimer.start();
		try{
			srResult = SerialFactorial2.factorial(value);	
		}catch(StackOverflowError e){
			srResult = BigInteger.ZERO;
		}
		srTimer.stop();
		
		srMemoryUsed = computeMemoryUsedAfter() - initialMemoryUsed;
		System.out.println("FINISHED SINGLE-THREADED RECURSIVE ALGORITHM\n\n");
		
		
		System.out.println("STARTING 2 thread MULTI-THREADED LOOP ALGORITHM");
		initialMemoryUsed = computeMemoryUsedBefore();
		
		c2Timer.start();
		c2Result = new ParallelFactorial2().factorial(value, 2);
		c2Timer.stop();
		
		c2MemoryUsed = computeMemoryUsedAfter() - initialMemoryUsed;
		System.out.println("FINISHED 2 thread MULTI-THREADED LOOP ALGORITHM\n\n");
		
		
		System.out.println("STARTING 4 thread MULTI-THREADED LOOP ALGORITHM");
		initialMemoryUsed = computeMemoryUsedBefore();
		
		c4Timer.start();
		c4Result = new ParallelFactorial2().factorial(value, 4);
		c4Timer.stop();
		
		c4MemoryUsed = computeMemoryUsedAfter() - initialMemoryUsed;
		System.out.println("FINISHED 4 cothreadre MULTI-THREADED LOOP ALGORITHM\n\n");
		
		System.out.println("STARTING 8 thread MULTI-THREADED LOOP ALGORITHM");
		initialMemoryUsed = computeMemoryUsedBefore();
		
		c8Timer.start();
		c8Result = new ParallelFactorial2().factorial(value, 8);
		c8Timer.stop();
		
		c8MemoryUsed = computeMemoryUsedAfter() - initialMemoryUsed;
		System.out.println("FINISHED 8 thread MULTI-THREADED LOOP ALGORITHM\n\n");
		
		
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