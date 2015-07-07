package org.venth.contest.duplicates;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

import java.util.stream.Stream;

/**
 * Duplicates removal may be based on different algorithms, so I've prepared a list of requirements:
 * - "provide the best solution" - since word best means not much, because of lack of any criteria, I chose my own one:
 *  - somebody else shall provide the implementation (you know, it's supported, maintained, almost guaranteed the enough good implementation of
 *    existing algorithm and more over somebody else already opened the door for me)
 *  - must be easy to apply: best - one liner
 *  - somehow interesting
 *  - low memory foot print (I have to be able to prove it on my own device)
 *  - relatively fast - coffee time cannot be crossed
 *
 * Based on these criteria, I took into an account: bloom filters and native jdk 1.8 implementation.
 * I've chosen bloom filters instead of a native jdk 1.8 implementation because of following assumptions:
 * - some false positives in duplicate checking process are allowed (I've set the probability of such situation on: 0.01%),
 *   they are allowed because otherwise coffee time would be too long...
 * - good memory footprint
 * - performance
 *
 * The stream is processed once, there is no need to make an order within and use complicated barrier (like in jdk native implementation)
 * Bloom filters time complexity is O(1)
 *
 * On my device filtering collection of 90 000 002 elements (hope, that 90 000 002 is big enough) took about 6 minutes
 *
 * @author Venth on 06/07/2015
 */
public class WithoutDuplicates {

    private final Stream<Person> people;
    private final BloomFilter<Person> filter;

    public WithoutDuplicates(Stream<Person> people) {
        this(people, new FilterBuilder(100000000, 0.0001).buildBloomFilter());
    }

    public WithoutDuplicates(Stream<Person> people, BloomFilter<Person> filter) {
        this.people = people;
        this.filter = filter;
    }


    public static Stream<Person> applyOn(Stream<Person> people) {
        return new WithoutDuplicates(people).stream();
    }

    public Stream<Person> stream() {
        return people
                .filter(person -> !filter.contains(person))
                .map(person -> {
                    filter.add(person);
                    return person;
                });
    }
}
