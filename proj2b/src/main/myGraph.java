package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class myGraph {
    static class Node{
        String[] item;
        public Node(String[] things) {
            item = things;
        }
    }
    HashMap<Integer, Node>nodeTable = new HashMap<>();
    HashMap<String, ArrayList<Integer>>wordToIndex = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> pointRelation = new HashMap<>();
    Set<String> result;
    public myGraph(String synsets, String hyponyms){
        In synsetsReader = new In(synsets);
        In hyponymsReader = new In(hyponyms);
        while (synsetsReader.hasNextLine()){
            String synsets_line = synsetsReader.readLine();
            String[] synsets_content = synsets_line.split(",");
            Node node = new Node(synsets_content[1].split(" "));
            nodeTable.put(Integer.parseInt(synsets_content[0]), node);
            for (String s : node.item){
                if (!wordToIndex.containsKey(s)){
                    ArrayList<Integer> synsets_intlist = new ArrayList<>();
                    synsets_intlist.add(Integer.parseInt(synsets_content[0]));
                    wordToIndex.put(s, synsets_intlist);
                }else {
                    wordToIndex.get(s).add(Integer.parseInt(synsets_content[0]));
                }
            }
        }
        while (hyponymsReader.hasNextLine()){
            String hyponyms_line = hyponymsReader.readLine();
            String[] hyponyms_content = hyponyms_line.split(",");
            ArrayList<Integer> hyponyms_intlist = new ArrayList<>();
            for(int i = 1; i < hyponyms_content.length; i++){
                hyponyms_intlist.add(Integer.parseInt(hyponyms_content[i]));
            }
            pointRelation.put(Integer.parseInt(hyponyms_content[0]), hyponyms_intlist);
        }
    }
    public String search_hyponyms(List<String> words){
        for (String word : words) {
            ArrayList<Integer> indexList = wordToIndex.get(word);
            if (indexList == null) {
                return "[]";
            }
            Set<String> currentSet = new HashSet<>();
            search_hyponyms_helper(indexList, currentSet);
            if (result == null) {
                result = currentSet;
            } else {
                result.retainAll(currentSet);
                if (result.isEmpty()) {
                    return "[]";
                }
            }
        }
        List<String> sorted = new ArrayList<>(result);
        Collections.sort(sorted);
        return sorted.toString();
    }
    public void search_hyponyms_helper(List<Integer> indexArray, Set<String> result){
        for (int index : indexArray) {
            Node relatedNode = nodeTable.get(index);
            if (relatedNode != null) {
                result.addAll(Arrays.asList(relatedNode.item));
            }
            List<Integer> children = pointRelation.get(index);
            if (children != null) {
                search_hyponyms_helper(children, result);
            }
        }
    }
}
