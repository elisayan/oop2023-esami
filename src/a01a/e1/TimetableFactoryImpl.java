package a01a.e1;

import java.util.*;
import java.util.stream.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class TimetableFactoryImpl implements TimetableFactory {

    private static <T> Set<T> addToSet(Set<T> s, T t){
        return concatSet(s, Set.of(t));
    }

    private static <T> Set<T> concatSet(Set<T> s, Set<T> s2){
        return Stream.concat(s.stream(), s2.stream()).collect(Collectors.toSet());
    }

    private static record TimetableData(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> data) implements Timetable {

        @Override
        public Timetable addHour(String activity, String day) {
            return new TimetableData(addToSet(activities, activity), addToSet(days, day),
                    (a, d) -> data.apply(a, d) + (activity.equals(a) && day.equals(d) ? 1 : 0));
        }

        @Override
        public int getSingleData(String activity, String day) {
            return data.apply(activity, day);
        }

        private int statistics(BiPredicate<String, String> predicate){
            int sum=0;
            
            for (String a : activities) {
                for (String d : days) {
                    if (predicate.test(a, d)) {
                        sum += getSingleData(a, d);
                    }
                }
            }

            return sum;
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            return statistics((a,d)->activities.contains(a) && days.contains(d));
        }
    
    }

    @Override
    public Timetable empty() {
        return new TimetableData(Set.of(), Set.of(), (a, d) -> 0);
    }

    @Override
    public Timetable single(String activity, String day) {
        return empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        return new TimetableData(concatSet(table1.activities(), table2.activities()),
                concatSet(table1.days(), table2.days()),
                (a, d) -> table1.getSingleData(a, d) + table2.getSingleData(a, d));
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return new TimetableData(table.activities(), table.days(),
                (a, d) -> Math.min(table.getSingleData(a, d), bounds.apply(a, d)));
    }

}
