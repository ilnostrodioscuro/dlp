/*
 * By Ray Murdorf
 * Licensed under CC-BY-NC-SA 4.0
 */

import java.util.*;
import java.math.BigInteger;

class DiscreteLogs {
    static int minv(int g, int p) { // returns modular inverse of g in F*p (AKA finds x where g^x + p^(some n) = 1)
        if(p == 1) return 0;
        int prime = p, x = 1, y = 0, quotient, temp;
        while(g > 1) {
            quotient = g / prime;
            temp = prime;
            prime = g % prime;
            g = temp;
            temp = y;
            y = x - quotient * y;
            x = temp;
        }
        return (x < 0) ? x + p : x;
    }
    static int order(int _g, int _p) { // returns the order of g in F*p
        TreeSet<Integer> factors = factors(_p - 1);
        Iterator<Integer> fact = factors.iterator();
        int temp;
        BigInteger g = new BigInteger(Integer.toString(_g)), p = new BigInteger(Integer.toString(_p)), power;
        while(fact.hasNext()) {
            temp = fact.next();
            power = g.pow(temp);
            System.out.println(power.remainder(p));
            if(power.remainder(p).equals(new BigInteger("1"))) {
                System.out.printf("Order of %d in F*%d is %d %n", _g, _p, temp);
                return temp;
            }
        }
        return -1;
    }
    static TreeSet<Integer> factors(int p) { // returns a sorted treeset of all factors of p
        TreeSet<Integer> factors = new TreeSet<>();
        System.out.printf("Factors of %d: ", p);
        for(int i = 1; i <= p; i++) {
            if(p % i == 0) {
                System.out.print(i + " ");
                factors.add(i);
            }
        }
        System.out.println();
        return factors;
    }
    static int dlp(int _g, int _h, int _p) {
        int iter = 0, i = 0;
        BigInteger[] list1 = new BigInteger[_p], list2 = new BigInteger[_p];
        BigInteger g, h, p, o, n, a;
        g = new BigInteger(Integer.toString(_g)); // eqn: g^x = h (mod p)
        h = new BigInteger(Integer.toString(_h));
        p = new BigInteger(Integer.toString(_p));
        o = new BigInteger(Integer.toString(order(_g, _p))); // order of g in F*p
        n = new BigInteger(Integer.toString(1 + (int) Math.floor(Math.sqrt(o.intValue()))));
        System.out.println("n = " + n);
        if(o.equals(new BigInteger("-1"))) return -1;
        a = (new BigInteger(Integer.toString(minv(_g, _p)))).pow(n.intValue()).remainder(p);
        System.out.println("List2 iterating on " + a);
        while(true) {
            if(iter >= _p) { // stops on the pth iteration if no collisions are found
                System.out.println("Error: no collisions found");
                return -1;
            }
            list1[iter] = g.pow(iter).remainder(p); // updates with g^i (mod p)
            list2[iter] = h.multiply(a.pow(iter)).remainder(p); // updates with h * g^(-n * i)
            System.out.printf("List1[%d] = %d, list2[%d] = %d %n", iter, list1[iter], iter, list2[iter]);
            for(i = 0; i <= iter; i++) { // checks updated lists for collisions
                if(list1[i].equals(list2[iter])) {
                    System.out.printf("Collision found at indexes list1[%d] and list2[%d]: %d %n", i, iter, list1[i].intValue());
                    return (new BigInteger(Integer.toString(i))).add((n.multiply(new BigInteger(Integer.toString(iter))))).remainder(p).intValue(); // calculates & returns x
                }
            }
            iter++;
        }
    }
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int g, h, p;
        System.out.print("g? ");
        g = kb.nextInt();
        System.out.print("h? ");
        h = kb.nextInt();
        System.out.print("p? ");
        p = kb.nextInt();
        System.out.printf("Log_%d %d mod %d = %d", g, h, p, dlp(g, h, p));
        kb.close();
    }
}