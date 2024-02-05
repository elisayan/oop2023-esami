package a02b.e1;

import java.util.*;

public class RulesEngineFactoryImpl implements RulesEngineFactory {

    @Override
    public <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input) {
        List<List<T>> output = new LinkedList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).equals(rule.get1())) {
                List<T> updateList = replacePosition(i, input, rule.get2());
                output.add(updateList);
            }
        }
        return output;
    }

    private <T> List<T> replacePosition(int index, List<T> source, List<T> newElement) {
        List<T> l = new LinkedList<>(source);
        l.remove(index);

        var it = newElement.listIterator(newElement.size());
        while (it.hasPrevious()) {
            l.add(index, it.previous());
        }
        return l;
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule) {
        // return new RulesEngine<T>() {
        //     List<T> list = new LinkedList<>();
        //     int count = 0;

        //     @Override
        //     public void resetInput(List<T> input) {
        //         this.list = input;
        //         count = 0;
        //     }

        //     @Override
        //     public boolean hasOtherSolutions() {
        //         return count < list.size();
        //     }

        //     @Override
        //     public List<T> nextSolution() {
        //         List<T> output = new LinkedList<>();
        //         for (T t : list) {
        //             if (t.equals(rule.get1())) {
        //                 for (T t2 : rule.get2()) {
        //                     output.add(t2);
        //                 }
        //             } else {
        //                 output.add(t);
        //             }
        //             count++;
        //         }
        //         return output;
        //     }

        // };

        return fromRules(List.of(rule));
    }

    private <T> boolean applicable(List<Pair<T,List<T>>> rules, List<T> input){
        for (Pair<T,List<T>> rule : rules) {
            if (input.contains(rule.get1())) {
                return true;
            }
        }
        return false;
    }

    private <T> List<List<T>> applyRules(List<Pair<T,List<T>>> rules, List<T> input) {
        List<List<T>> result = new LinkedList<>();
        if (!applicable(rules, input)) {
            result.add(input);
        } else{
            for (Pair<T,List<T>> rule  : rules) {
                for (List<T> updateInput : applyRule(rule, input)) {
                    for (List<T> recursiveResut : applyRules(rules, updateInput)) {
                        if (!result.contains(recursiveResut)) {
                            result.add(recursiveResut);
                        }
                    }
                }
            }
        }

        return result;
    }

    private <T> RulesEngine<T> fromRules(List<Pair<T, List<T>>> rules) {
        return new RulesEngine<T>() {
            private Iterator<List<T>> iterator = null;

            @Override
            public void resetInput(List<T> input) {
                iterator = applyRules(rules, input).iterator();
            }

            @Override
            public boolean hasOtherSolutions() {
                return iterator.hasNext();
            }

            @Override
            public List<T> nextSolution() {
                return iterator.next();
            }
            
        };
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> baseRule, Pair<T, List<T>> cascadeRule) {
        return fromRules(List.of(baseRule, cascadeRule));
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        return fromRules(List.of(rule1, rule2));
    }

}
