import java.math.BigInteger;
import java.util.ArrayList;

public class ParallelFactorial2 {
	private long n;
	private BigInteger result;
	private int numOfThreads;
	private ArrayList threads;
	private ArrayList isFinished;
	private ArrayList<BigInteger> results;
	
	public BigInteger factorial(long n, int numOfThreads) {
		this.n = n;
		this.numOfThreads = numOfThreads;
		this.result = BigInteger.ONE;
		
		threads = new ArrayList();
		isFinished = new ArrayList();
		results = new ArrayList<BigInteger>();
		
		for(int i = 0; i < numOfThreads; i++) {			
			results.add(BigInteger.ONE);
			isFinished.add(false);
			ParallelThread temp = new ParallelThread(n-i, i);
			threads.add(temp);
			temp.start();
		}
		
		while(isFinished.contains(false)) {
			System.out.print("");
		}
		
		for(int i = 0; i < results.size(); i++) {
			this.result = this.result.multiply(results.get(i));
		}
		
		return result;
	}
	
	public class ParallelThread extends Thread{
		private long num;
		private int id;
		
		ParallelThread(long num, int id) {
			this.num = num;
			this.id = id;
		}
		
		public void run() {			
			while(num > 0) {
				//System.out.println("RUNNING " + num);
				results.set(id, results.get(id).multiply(BigInteger.valueOf(num)));
				num = num - numOfThreads;
			}
			
			System.out.println("FINISHED THREAD: " + id);
			isFinished.set(id, true);
		}
	}
}
