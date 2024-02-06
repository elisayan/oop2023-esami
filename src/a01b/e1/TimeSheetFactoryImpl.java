package a01b.e1;

import java.util.*;
import java.util.function.*;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private static record TimeSheetImpl(List<String> activities, List<String> days, BiFunction<String, String, Integer> fun) implements TimeSheet{

        @Override
        public int getSingleData(String activity, String day) {
            return activities.contains(activity) && days.contains(day) ? fun().apply(activity, day) : 0;
        }

        @Override
        public Map<String, Integer> sumsPerActivity() {
            Map<String, Integer> sums = new HashMap<>();
            for (String a : activities) {
                int sum=0;
                for (String d : days) {
                    sum+=fun.apply(a, d);
                }
                sums.put(a, sum);
            }
            return sums;
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            Map<String, Integer> sums = new HashMap<>();
            for (String d : days) {
                int sum=0;
                for (String a : activities) {
                    sum+=fun.apply(a, d);
                }
                sums.put(d, sum);
            }
            return sums;
        }
    
        
    }

    private List<String> createActivities(int numActivities){
        List<String> activities = new LinkedList<>();
        for (int i = 1; i <= numActivities; i++) {
            activities.add("act"+i);   
        }
        return activities;
    }

    private List<String> createDays(int numDays){
        List<String> activities = new LinkedList<>();
        for (int i = 1; i <= numDays; i++) {
            activities.add("day"+i);   
        }
        return activities;
    }

    @Override
    public TimeSheet flat(int numActivities, int numNames, int hours) {
        List<String> activities = createActivities(numActivities);
        List<String> days = createDays(numNames);
        return new TimeSheetImpl(activities, days, (a,d)->hours);                      
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetImpl(activities, days, (a, d) -> data.get(activities.indexOf(a)).get(days.indexOf(d)));
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        List<String> activities = createActivities(numActivities);
        List<String> days = createDays(numDays);
        return new TimeSheetImpl(activities, days, (a, d) -> {
            int hours = 0;
            for (Pair<Integer, Integer> pair : data) {
                if (pair.get1().equals(activities.indexOf(a)) && pair.get2().equals(days.indexOf(d))) {
                    hours++;
                }
            }
            return hours;
        });
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return new TimeSheetImpl(activities, days, (a, d) -> {
            Pair<String, String> pair = new Pair<>(a, d);
            return data.getOrDefault(pair, 0);
        });
    }

}
