import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Integer> mapEncode = new HashMap<String, Integer>();
        Map<Integer, String> mapDecode = new HashMap<Integer, String>();
        String program = "";
        List<String> foundAtoms = new LinkedList<>();
        List<String> foundAtomsFinal = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Parser.class.getResource("codare.txt").getFile()))) {
            reader.lines().forEach(line -> {
                mapEncode.put(line, mapEncode.size());
                mapDecode.put(mapDecode.size(), line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (Map.Entry<String,Integer> entry : mapEncode.entrySet())
//        {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//        }

        try (BufferedReader reader = new BufferedReader(new FileReader(Parser.class.getResource("date.txt").getFile()))) {
            program = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(program);

        Pattern beginPattern = Pattern.compile("^#include <iostream>\\Rusing namespace std;\\Rint main\\(\\)\\{\\R(.|\\R)+\\R\\}$");
        Matcher beginMatcher = beginPattern.matcher(program);
        boolean beginMatchFound = beginMatcher.find();

        if (beginMatchFound) {
            System.out.println("Antet existent!");
        } else {
            System.out.println("Antet inexistent!");
        }

        program = program.replaceAll("^#include <iostream>\\Rusing namespace std;\\Rint main\\(\\)\\{\\R", "");
        program = program.replaceAll("\\R\\}$", "");
        program = program.replaceAll("\\R", "");

        //System.out.println(program);

        List<String> arrOfStr = Arrays.stream(program.split("((?=;)|(?<=;))")).collect(Collectors.toList());
        //arrOfStr.forEach(System.out::println);


        //String searchSplit = "<=|==|>=|!=|<|!|/|%|\\[|\\]|\\{|\\}|;|\\*|,|\\s|=|\\+|-|";
        String searchSplit = "!|=|\\*|\\s|,|<|>|/|%|\\[|\\]|\\{|\\}|\\+|-|;|\\(|\\)";
        for (int i = 0; i < arrOfStr.size(); i++) {
            List<String> values = Arrays.stream(arrOfStr.get(i).split("((?="+searchSplit+")|(?<="+searchSplit+"))")).collect(Collectors.toList());

            for (int j = 0; j < values.size(); j++) {
                foundAtoms.add(values.get(j));
            }
        }

//        for (String atom : foundAtoms) {
//            System.out.println(atom);
//        }

        for (int i = 0; i <foundAtoms.size() ; i++) {
            if(foundAtoms.get(i).equals("cin") && foundAtoms.get(i+1).equals(">") && foundAtoms.get(i+2).equals(">") || foundAtoms.get(i).equals("cout") && foundAtoms.get(i+1).equals("<") && foundAtoms.get(i+2).equals("<")) {
                foundAtomsFinal.add(foundAtoms.get(i) + foundAtoms.get(i + 1) + foundAtoms.get(i + 2));
                i+=2;
            }
            else
                if((foundAtoms.get(i).equals("<") || foundAtoms.get(i).equals(">") || foundAtoms.get(i).equals("=") || foundAtoms.get(i).equals("!")) && foundAtoms.get(i+1).equals("=")){
                    foundAtomsFinal.add(foundAtoms.get(i) + foundAtoms.get(i + 1));
                    i++;
                }
                else{
                    foundAtomsFinal.add(foundAtoms.get(i));
                }
        }

        System.out.println("Atomi:\n");
        for (String atom : foundAtomsFinal) {
            System.out.println(atom);
        }

//            if(foundMap.containsKey(key)){
//                foundMap.get(key).add(value);
//            } else {
//                List<String> list = new ArrayList<>();
//                list.add(value);
//                foundMap.put(key, list);
//            }


        //Arrays.stream(arrOfStr).collect(Collectors.toList()).forEach(System.out::println);

    }
}
