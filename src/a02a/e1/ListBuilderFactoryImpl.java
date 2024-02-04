package a02a.e1;

import java.util.*;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private class ListBuilderImpl<T> implements ListBuilder<T> {

        private final List<T> list;

        public ListBuilderImpl(List<T> list) {
            this.list = list;
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            List<T> combinedList = new ArrayList<>(this.list);
            combinedList.addAll(list);
            return new ListBuilderImpl<>(combinedList);
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            return add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            List<T> replacedList = new ArrayList<>();
            for (T t2 : list) {
                if (t2.equals(t)) {
                    replacedList.addAll(lb.build());
                } else {
                    replacedList.add(t2);
                }
            }
            return new ListBuilderImpl<>(replacedList);
        }

        @Override
        public ListBuilder<T> reverse() {
            List<T> reverseList = new ArrayList<>(list);
            Collections.reverse(reverseList);
            return new ListBuilderImpl<>(reverseList);
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
        List<T> output = new ArrayList<>();

        output.add(start);

        for (ListBuilder<T> list : builderList) {
            output.addAll(list.build());
        }
        output.add(stop);

        return new ListBuilderImpl<>(output);
    }

}
