package a02a.e1;

import java.util.*;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private static class ListBuilderImpl<T> implements ListBuilder<T>{

        private List<T> list = new LinkedList<>();

        public ListBuilderImpl(List<T> list) {
            this.list = list;
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            List<T> addedList = new LinkedList<>(this.list);
            addedList.addAll(list);
            return new ListBuilderImpl<T>(addedList);
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            return add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            List<T> replacedList = new LinkedList<>();
            for (T element : list) {
                if (element.equals(t)) {
                    replacedList.addAll(lb.build());
                } else{
                    replacedList.add(element);
                }
            }
            return new ListBuilderImpl<>(replacedList);
        }

        @Override
        public ListBuilder<T> reverse() {
            List<T> reversedList = new LinkedList<>(list);
            Collections.reverse(reversedList);
            return new ListBuilderImpl<>(reversedList);
        }

        @Override
        public List<T> build() {
            return list;
        }
    
        
    }

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl<>(List.of());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return new ListBuilderImpl<>(List.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        List<T> list = new LinkedList<>();
        list.add(start);
        for (ListBuilder<T> t : builderList) {
            list.addAll(t.build());
        }
        list.add(stop);
        return new ListBuilderImpl<>(list);
    }

}
