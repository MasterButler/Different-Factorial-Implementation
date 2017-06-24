import java.math.BigInteger;
import java.util.Scanner;

public class SerialFactorial2 {

	public static BigInteger factorial(long n){
		BigInteger result = BigInteger.ONE;
		
		if(n == 1){
			return BigInteger.ONE;
		}else{
			return BigInteger.valueOf(n).multiply(factorial(n-1));
		}
	}
	
}
