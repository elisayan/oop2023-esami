package a02c.e1;

import java.util.*;

public class ReplacersFactoryImpl implements ReplacersFactory {

    @Override
    public <T> Replacer<T> noReplacement() {
        return new Replacer<T>() {

            @Override
            public List<List<T>> replace(List<T> input, T t) {
                return new LinkedList<>();
            }

        };
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
        return new Replacer<T>() {

            @Override
            public List<List<T>> replace(List<T> input, T t) {
                if (!input.contains(t)) {
                    return new LinkedList<>();
                }

                List<T> output = new LinkedList<>(input);
                int firstIndex = input.indexOf(t);

                output.add(firstIndex, t);

                return List.of(output);
            }

        };
    }

    @Override
    public <T> Replacer<T> translateLastWith(List<T> target) {
        return new Replacer<T>() {

            @Override
            public List<List<T>> replace(List<T> input, T t) {
                if (!input.contains(t)) {
                    return new LinkedList<>();
                }
                List<T> output = new LinkedList<>(input);
                int lastIndex = -1;
                for (int i = 0; i < output.size(); i++) {
                    if (output.get(i).equals(t)) {
                        lastIndex = i;
                    }
                }

                if (lastIndex != -1) {
                    output.remove(lastIndex);
                    output.addAll(lastIndex, target);
                }

                return List.of(output);
            }

        };
    }

    @Override
    public <T> Replacer<T> removeEach() {
        return new Replacer<T>() {

            @Override
            public List<List<T>> replace(List<T> input, T t) {
                // if (!input.contains(t)) {
                //     return new LinkedList<>();
                // }
                // List<List<T>> output = new LinkedList<>();
                // List<Integer> indexList = new LinkedList<>();
                // for (int i = 0; i < input.size(); i++) {
                //     if (input.get(i).equals(t)) {
                //         indexList.add(i);
                //     }
                // }
                // for (Integer j : indexList) {
                //     List<T> innerList = new LinkedList<>(input);
                //     for (int i = 0; i < innerList.size(); i++) {
                //         if (i == j) {
                //             innerList.remove(i);
                //         }
                //     }
                //     output.add(innerList);
                // }

                // return output;

                if (!input.contains(t)) {
                    return new LinkedList<>();
                }
            
                List<List<T>> output = new LinkedList<>();
                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).equals(t)) {
                        List<T> innerList = new LinkedList<>(input.subList(0, i));
                        innerList.addAll(input.subList(i + 1, input.size()));
                        output.add(innerList);
                    }
                }
            
                return output;
            }

        };
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(List<T> sequence) {
        return new Replacer<T>() {

            @Override
            public List<List<T>> replace(List<T> input, T t) {
                if (!input.contains(t)) {
                    return new LinkedList<>();
                }

                List<List<T>> output = new LinkedList<>();
                int c = 0;
                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).equals(t) && c < sequence.size()) {
                        List<T> innerList = new LinkedList<>(input);
                        innerList.set(i, sequence.get(c++));
                        output.add(innerList);
                    }
                }

                return output;
            }

        };
    }

}
