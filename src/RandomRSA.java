import java.util.*;
import java.net.URL;
import java.io.*;

/**
 * Created by joytang on 12/24/16.
 *
 * Apache CommonCodec: Version: GnuPG v1.4.11 (MingW32)
 *
 *iQEcBAABAgAGBQJUW4J/AAoJEIb9x+KhEmLL6a4H/jT4Mt0SVqnHUCTCVlVNQj1/
 *QdUZ7BGsMd8O2bUn8rKFujG/DKHXOeXlnP90nrPuXk9CjNeEoBF3MU0/F71izUHS
 *G/d6FB5lgf/F+5u7Or1bk9739MAhPyjZhTRq4vvsU4EL9CBiOxJyQ3mvmqHU5ugs
 *qsVVqV74az7M8PVU++JhXYlVfTIVpL35PgwSyoVAlU30AqhtRd4pHZdop26M+JZC
 *wKCApA7JuMOamPo5pZ1tsYlyG6OIs40btBJaUR9jDRitEN+1rCGoKdJVrhxykIxx
 *osCstDiDzmAJ2hq+wclYpqcdJSwUJfndpImkZ4TfJnJOUrzA3LcYx6wobKQQItk=
 *=4Z99
 */

public class RandomRSA {

    RandomRSA() {
        _p = 1;
        _q = 1;
        _d = 1;
        _e = 1;
        _N = 1;
        _rsa = new ArrayList<>(2);
    }

    /** Gets HTTP API from Random.org. */
    public class GetRandom {

        public void main(String[] args) {
            try {
                URL url = new URL("https://api.random.org/json-rpc/1/invoke");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                String strTemp;
                while (null != (strTemp = br.readLine())) {
                    String str = gson.fromJson(strTemp, String.class);
                    System.out.println(str);
                }
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /** Returns next lowest prime number if m is not prime. */
    public int prime(int m) {
        for (int i = 2; i <= m / 2; i += 1) {
            if (m % i == 0) {
                m -= 1;
            }
        }
        return m;
    }

    /** Int method to compute the gcd of int m and int n. */
    public int gcd(int m, int n) {
        if (n == 0) {
            return m;
        } else {
            return gcd(n, m % n);
        }
    }

    /** Computing _d using the ecgd algorithm. */
    public int[] egcd(int x, int y) {
        int[] result = new int[3];
        int floordiv;
        if (y == 0) {
            result[0] = x;
            result[1] = 1;
            result[2] = 0;
        } else {
            floordiv = x / y;
            result = egcd(y, x % y);
            int temp = result[1] - floordiv * result[2];
            result[1] = result[2];
            result[2] = temp;
        }
        return result;
    }

    /** Returns random prime number between 101 and 9973. */
    public int randomPrime() {
        GetRandom roc = GetRandom.getRandomOrgClient(b918b800-cdce-4ace-a4df-8569bd649dc2);
        int[] random = roc.generateIntegers(1, 101, 9973, false);
        return prime(random[0]);
    }

    /** Returns RSA key pair. */
    public ArrayList<List<Integer>> rsa() {
        _p = randomPrime();
        _q = randomPrime();
        GetRandom roc = GetRandom.getRandomOrgClient(b918b800-cdce-4ace-a4df-8569bd649dc2);
        int[] randoms = roc.generateIntegers(10, 2, 11, false);
        int index = 0;
        while (_e == 1) {
            if (gcd(randoms[index], (_p - 1) * (_q - 1)) == 1) {
                _e = randoms[index];
            }
            index += 1;
        }
        _N = _p * _q;
        _d = egcd(_e, (_p - 1) * (_q - 1))[1];
        ArrayList<Integer> publicKey = new ArrayList<>(2);
        publicKey.add(_N);
        publicKey.add(_e);
        ArrayList<Integer> privateKey = new ArrayList<>(1);
        privateKey.add(_d);
        _rsa.add(publicKey);
        _rsa.add(privateKey);
        return _rsa;
    }

    /** Prime int p. */
    private int _p;

    /** Prime int q. */
    private int _q;

    /** Private key d. */
    private int _d;

    /** Part of public key: int e relatively prime to (p - 1)(q - 1). */
    private int _e;

    /** Part of public key: int N. */
    private int _N;

    /** ArrayList of int Lists (publicKey, privateKey) representing an RSA key pair. */
    private ArrayList<List<Integer>> _rsa;

}
