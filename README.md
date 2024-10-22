# Discrete Log Problem
Given a set F*_p, calculates the log base g of h, **OR** calculates x, given g^x = h (mod p)
- Generally accounts for large powers with BigInteger instead of int, so limit is quite high
- Spits out most of the work; N, n, order of g in the set, contents of the list, and where the first collision is found
- Only finds the first collision, but by the nature of the DLP all results are essentially x +- m * (order of g in F*_p) for all integers m
