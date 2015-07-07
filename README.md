# [Confitura 2015](http://tech.viacom.com/warsawsdc/confitura2015/)

## My choose

Duplicates removal may be based on different algorithms, so I've prepared a list of requirements:
- "provide the best solution" - since word best means not much, because of lack of any criteria, I chose my own one:
 - somebody else shall provide the implementation (you know, it's supported, maintained, almost guaranteed the enough good implementation of
   existing algorithm and more over somebody else already opened the door for me)
 - must be easy to apply: best - one liner
 - somehow interesting
 - low memory foot print (I have to be able to prove it on my own device)
 - relatively fast - coffee time cannot be crossed

Based on these criteria, I took into an account: bloom filters and native jdk 1.8 implementation.
I've chosen bloom filters instead of a native jdk 1.8 implementation because of following assumptions:
- some false positives in duplicate checking process are allowed (I've set the probability of such situation on: 0.01%),
  they are allowed because otherwise coffee time would be too long...
- good memory footprint
- performance

The stream is processed once, there is no need to make an order within and use complicated barrier (like in jdk native implementation)
Bloom filters time complexity is O(1)

On my device filtering collection of 90 000 002 elements (hope, that 90 000 002 is big enough) took about 6 minutes


## Duplicates

You have *very big* list of elements. Please provide best solution to detect and remove duplicated elements.

Please provide a solution and **comments** about its benefits and drawbacks. Please give us complexity (`O(n)`, `O(n^2)`, `O(ln(n))`, ...). Please think about custom classes like:

```java
class Person {
    String name;
    int age;
}
```

You can check contest bye-laws [here](http://tech.viacom.com/warsawsdc/confitura2015/Regulamin_konkurs_Viacom_programmer_adventure_2015.pdf).

Check out our Confitura 2015 site [here](http://tech.viacom.com/warsawsdc/confitura2015/)

We are hiring! Visit our [career site](http://tech.viacom.com/careers/).
