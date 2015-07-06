package org.venth.contest.duplicates;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;

/**
 * @author Venth on 06/07/2015
 */
public class WithoutDuplicatesTest {

    @Test
    public void empty_collection_contains_none_duplicates() {
        //given an empty collection of people
        Collection<Person> emptyCollection = Collections.emptyList();

        //when duplicates are removed from the empty collection
        Collection<Person> changedMatrix = WithoutDuplicates.applyOn(emptyCollection.stream())
                .collect(Collectors.toList());

        //then collection is still empty
        assertThat(changedMatrix, is(emptyCollectionOf(Person.class)));
    }

    @Test
    public void one_person_collection_remains_one_person() {
        //given Neo :)
        Person neo = new Neo();

        //and in the matrix is only Neo
        Collection<Person> matrix = Collections.singletonList(neo);

        //when duplicates are removed from the matrix
        Collection<Person> changedMatrix = WithoutDuplicates.applyOn(matrix.stream())
                .collect(Collectors.toList());

        //then people collection still contains Neo
        assertThat(changedMatrix, contains(neo));
    }

    @Test
    public void collection_with_unique_elements_remains_unchanged() {
        //given Neo and Oracle in the matrix
        Neo neo = new Neo();
        Oracle oracle = new Oracle();
        Collection<Person> matrix = Arrays.asList(neo, oracle);

        //when duplicates are removed from the matrix
        Collection<Person> changedMatrix = WithoutDuplicates.applyOn(matrix.stream())
                .collect(Collectors.toList());

        //then Neo and Oracle remained
        assertThat(changedMatrix, contains(neo, oracle));
    }

    @Test
    public void only_first_duplicate_remains() {
        //given 20 Mr Smiths in the matrix
        Person mrSmith = new MrSmith();
        Collection<Person> matrix = Stream.generate(MrSmith::new).limit(20).collect(Collectors.toList());

        //when duplicates are removed from the matrix
        Collection<Person> changedMatrix = WithoutDuplicates.applyOn(matrix.stream())
                .collect(Collectors.toList());

        //then only one Mr Smith remained
        assertThat(changedMatrix, contains(mrSmith));
    }

    @Test
    public void unique_and_first_duplicate_remains() {
        //given Neo and Oracle
        Neo neo = new Neo();
        Oracle oracle = new Oracle();

        //and 20 Mr Smiths in the matrix
        Person mrSmith = new MrSmith();

        Collection<Person> matrix = Stream.concat(
                Arrays.asList(neo, oracle).stream(),
                Stream.generate(MrSmith::new).limit(20)
            ).collect(Collectors.toList());

        //when duplicates are removed from the matrix
        Collection<Person> changedMatrix = WithoutDuplicates.applyOn(matrix.stream())
                .collect(Collectors.toList());

        //then Neo, Oracle and one Mr Smith remained
        assertThat(changedMatrix, contains(neo, oracle, mrSmith));
    }
}

class Neo extends Person {
    public Neo() {
        super("Neo", 30);
    }
}

class MrSmith extends Person {
    public MrSmith() {
        super("Mr Smith", 30);
    }
}

class Oracle extends Person {
    public Oracle() {
        super("Oracle", 0);
    }
}
