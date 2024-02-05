package a01c.e1;

import java.util.*;
import java.util.stream.*;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private static record TimeSheetImpl(Set<String> activities, Set<String> days,
            Map<Pair<String, String>, Integer> hoursMap, boolean allowed) implements TimeSheet {

        @Override
        public int getSingleData(String activity, String day) {
            if (hoursMap.containsKey(new Pair<>(activity, day))) {
                return hoursMap.get(new Pair<>(activity, day));
            }
            return 0;
        }

        @Override
        public boolean isValid() {
            return allowed;
        }

    }

    @Override
    public TimeSheet ofRawData(List<Pair<String, String>> data) {
        Set<String> activities = data.stream().map(Pair::get1).collect(Collectors.toSet());
        Set<String> days = data.stream().map(Pair::get2).collect(Collectors.toSet());
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        for (Pair<String, String> string : data) {
            hours.put(string, hours.getOrDefault(string, 0) + 1);
        }

        return new TimeSheetImpl(activities, days, hours, true);
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities) {
        Set<String> activities = data.stream().map(Pair::get1).collect(Collectors.toSet());
        Set<String> days = data.stream().map(Pair::get2).collect(Collectors.toSet());
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        boolean allowed = true;

        for (Pair<String, String> string : data) {
            for (var entry : boundsOnActivities.entrySet()) {
                if (entry.getKey().equals(string.get1())) {
                    if (hours.getOrDefault(string, 0) < entry.getValue()) {
                        hours.put(string, hours.getOrDefault(string, 0) + 1);
                    } else {
                        allowed = false;
                    }
                }
            }

        }

        return new TimeSheetImpl(activities, days, hours, allowed);
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays) {
        Set<String> activities = data.stream().map(Pair::get1).collect(Collectors.toSet());
        Set<String> days = data.stream().map(Pair::get2).collect(Collectors.toSet());
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        boolean allowed = true;

        for (Pair<String, String> string : data) {
            for (var entry : boundsOnDays.entrySet()) {
                if (entry.getKey().equals(string.get2())) {
                    if (hours.getOrDefault(string, 0) <= entry.getValue()) {
                        hours.put(string, hours.getOrDefault(string, 0) + 1);
                    } else {
                        allowed = false;
                    }
                }
            }

        }

        return new TimeSheetImpl(activities, days, hours, allowed);
    }

    @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities,
            Map<String, Integer> boundsOnDays) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withBounds'");
    }

}
