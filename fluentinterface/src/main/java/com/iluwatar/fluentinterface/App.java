package com.iluwatar.fluentinterface;

import com.iluwatar.fluentinterface.fluentiterable.lazy.LazyFluentIterable;
import com.iluwatar.fluentinterface.fluentiterable.simple.SimpleFluentIterable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.String.valueOf;

public class App {

    public static void main(String[] args) {

        List<Integer> integerList = new ArrayList<Integer>() {{
            add(1);
            add(-61);
            add(14);
            add(-22);
            add(18);
            add(-87);
            add(6);
            add(64);
            add(-82);
            add(26);
            add(-98);
            add(97);
            add(45);
            add(23);
            add(2);
            add(-68);
            add(45);
        }};
        prettyPrint("The initial list contains: ", integerList);

        List<Integer> firstFiveNegatives = SimpleFluentIterable.from(integerList)
                .filter(negatives())
                .first(3)
                .asList();
        prettyPrint("The first three negative values are: ", firstFiveNegatives);


        List<Integer> lastTwoPositives = SimpleFluentIterable.from(integerList)
                .filter(positives())
                .last(2)
                .asList();
        prettyPrint("The last two positive values are: ", lastTwoPositives);

        SimpleFluentIterable.from(integerList)
                .filter(number -> number%2 == 0)
                .first()
                .ifPresent(evenNumber -> System.out.println(String.format("The first even number is: %d", evenNumber)));


        List<String> transformedList = SimpleFluentIterable.from(integerList)
                .filter(negatives())
                .map(transformToString())
                .asList();
        prettyPrint("A string-mapped list of negative numbers contains: ", transformedList);


        List<String> lastTwoOfFirstFourStringMapped = LazyFluentIterable.from(integerList)
                .filter(positives())
                .first(4)
                .last(2)
                .map(number -> "String[" + String.valueOf(number) + "]")
                .asList();
        prettyPrint("The lazy list contains the last two of the first four positive numbers mapped to Strings: ", lastTwoOfFirstFourStringMapped);

        LazyFluentIterable.from(integerList)
                .filter(negatives())
                .first(2)
                .last()
                .ifPresent(lastOfFirstTwo -> System.out.println(String.format("The last of the first two negatives is: %d", lastOfFirstTwo)));
    }

    private static Function<Integer, String> transformToString() {
        return integer -> "String[" + valueOf(integer) + "]";
    }
    private static Predicate<? super Integer> negatives() {
        return integer -> (integer < 0);
    }
    private static Predicate<? super Integer> positives() {
        return integer -> (integer > 0);
    }

    private static <TYPE> void prettyPrint(String prefix, Iterable<TYPE> iterable) {
        prettyPrint(", ", prefix, ".", iterable);
    }
    private static <TYPE> void prettyPrint(String prefix, String suffix, Iterable<TYPE> iterable) {
        prettyPrint(", ", prefix, suffix, iterable);
    }

    private static <TYPE> void prettyPrint(String delimiter, String prefix, String suffix, Iterable<TYPE> iterable) {
        StringJoiner joiner = new StringJoiner(delimiter, prefix, ".");
        Iterator<TYPE> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            joiner.add(iterator.next().toString());
        }

        System.out.println(joiner);
    }
}
