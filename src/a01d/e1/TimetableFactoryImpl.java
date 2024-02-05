package a01d.e1;

import java.util.*;
import java.util.stream.*;

import a01d.e1.Timetable.Day;

public class TimetableFactoryImpl implements TimetableFactory {

    private static class BookingSlot {
        private final String room;
        private final String course;
        private final Timetable.Day day;
        private final int hour;
        
        public BookingSlot(String room, String course, Day day, int hour) {
            this.room = room;
            this.course = course;
            this.day = day;
            this.hour = hour;
        }

        public String getRoom() {
            return room;
        }

        public String getCourse() {
            return course;
        }

        public Timetable.Day getDay() {
            return day;
        }

        public int getHour() {
            return hour;
        }
        
    }

    private static class TimetableImpl implements Timetable{

        private final Set<BookingSlot> data;

        public TimetableImpl(Set<BookingSlot> data) {
            this.data = data;
        }

        @Override
        public Set<String> rooms() {
            return data.stream().map(BookingSlot::getRoom).collect(Collectors.toSet());
        }

        @Override
        public Set<String> courses() {
            return data.stream().map(BookingSlot::getCourse).collect(Collectors.toSet());
        }

        @Override
        public List<Integer> hours() {
            List<Integer> hoursList = new LinkedList<>();
            for (BookingSlot bookingSlot : data) {
                if (!(hoursList.contains(bookingSlot.getHour()))) {
                    hoursList.add(bookingSlot.getHour());
                }
            }
            Collections.sort(hoursList);

            return hoursList;
        }

        @Override
        public Timetable addBooking(String room, String course, Day day, int hour, int duration) {
            Set<BookingSlot> newData = new HashSet<>(data);
            for (int i = 0; i < duration; i++) {
                data.add(new BookingSlot(room, course, day, hour + i));
            }
            return new TimetableImpl(newData);
        }

        @Override
        public Optional<Integer> findPlaceForBooking(String room, Day day, int duration) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findPlaceForBooking'");
        }

        @Override
        public Map<Integer, String> getDayAtRoom(String room, Day day) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getDayAtRoom'");
        }

        @Override
        public Optional<Pair<String, String>> getDayAndHour(Day day, int hour) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getDayAndHour'");
        }

        @Override
        public Map<Day, Map<Integer, String>> getCourseTable(String course) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getCourseTable'");
        }
    
        
    }

    @Override
    public Timetable empty() {
        return new TimetableImpl(Set.of());
    }  
        
}
