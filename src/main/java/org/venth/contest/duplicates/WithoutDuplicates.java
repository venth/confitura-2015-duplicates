package org.venth.contest.duplicates;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

import java.util.stream.Stream;

/**
 * @author Venth on 06/07/2015
 */
public class WithoutDuplicates {

    private final Stream<Person> people;
    private final BloomFilter<Person> filter;

    public WithoutDuplicates(Stream<Person> people) {
        this(people, new FilterBuilder(100000000, 0.001).buildBloomFilter());
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
