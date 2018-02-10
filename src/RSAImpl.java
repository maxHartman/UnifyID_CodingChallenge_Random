import java.math.BigInteger;
import java.util.Random;

public class RSAImpl {

    private BigInteger       p, q, n, e, d, lambda;
    private static final BigInteger ONE = BigInteger.ONE;

    public RSAImpl(String p, String q) {
        this.p = new BigInteger(p);
        this.q = new BigInteger(q);
        n = (this.p).multiply(this.q);
        lambda = lcm((this.p).subtract(ONE), (this.q).subtract(ONE));
        e = findE(lambda);
        d = e.modInverse(lambda);
    }

    
    public String getD() {
        return d.toString();
    }
    
    public String getN() {
        return n.toString();
    }
    
    public String getExp() {
        return e.toString(); 
    }
    
    public BigInteger encrypt(BigInteger m) {
        return m.modPow(e, n);
    }
    
    public BigInteger decrypt(BigInteger em) {
        return em.modPow(d, n);
    }
    
    
    private static BigInteger findE(BigInteger lambda) {
        BigInteger e = BigInteger.probablePrime(256, new Random());
        while (lambda.compareTo(e) > 0 && lambda.gcd(e).compareTo(BigInteger.ONE) > 0) {
            e = e.nextProbablePrime();
        }
       
        return e;
    }

    private static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.divide(a.gcd(b)).multiply(b);
    }

}
