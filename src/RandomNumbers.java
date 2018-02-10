import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class RandomNumbers {

    static String connect(int num, int min, int max) throws IOException {
        StringBuilder str = new StringBuilder();
        URL url = new URL("https://www.random.org/integers/?num=" + num + "&min=" + min + "&max="
                + max + "&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            str.append(line + " "); // puts spaces between the stream of numbers
        }

        reader.close();
        connection.disconnect();

        return str.toString();
    }

    static int[] getPrimes(String[] args) throws IOException, IllegalArgumentException {

        String values = connect(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));
        String[] intVals = values.split(" ");
        int[] primes = { 0, 0 };

        for (String v : intVals) {
            if (primes[1] != 0)
                break;

            int val = Integer.parseInt(v);
            if (val == 0)
                continue;

            if (isPrime(val)) {
                if (primes[0] == 0)
                    primes[0] = val;
                else {
                    primes[1] = val;
                }
            }
        }

        if (primes[1] == 0)
            throw new IllegalArgumentException("The arguments did not render at least 2 primes");

        return primes;
    }

    private static Boolean isPrime(int num) {
        if (num == 2)
            return true;

        for (int i = 2; i <= (int) Math.sqrt(num) + 1; i++)
            if (num % i == 0)
                return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        String[] vals = { "75", "11", "750" };
        int[] primes = getPrimes(vals);
        
        RSAImpl RSA = new RSAImpl(Integer.toString(primes[0]), Integer.toString(primes[1]));
        System.out.println("Your Public Key is: " + RSA.getExp());
        System.out.println("Your Private Key is: " + RSA.getD());
    }
}
