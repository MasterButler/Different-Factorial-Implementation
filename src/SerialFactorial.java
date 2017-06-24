import java.math.BigInteger;
import java.util.Scanner;

public class SerialFactorial {

	public static BigInteger factorial(long n){
		BigInteger result = BigInteger.ONE;
		
		for(long i = 2; i <= n; i++){
			result = result.multiply(BigInteger.valueOf(i));
		}
		
		return result; 
	}
	
}
