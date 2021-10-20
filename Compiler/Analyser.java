import SymbolTable.SymbolTable;
import SymbolTable.entry.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Analyser {
    private Map<String, Integer> mapEncode = new HashMap<String, Integer>();
    Map<Integer, String> mapDecode = new HashMap<Integer, String>();
    Map<Constant,String> mapPattern = new HashMap<>();
    FIP fip = new FIP();
    SymbolTable symbolTable = new SymbolTable();

    public Analyser(){
        try (BufferedReader reader = new BufferedReader(new FileReader(Parser.class.getResource("codare.txt").getFile()))) {
            reader.lines().forEach(line -> {
                mapEncode.put(line, mapEncode.size()+1);
                mapDecode.put(mapDecode.size()+1, line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // <numar> ::= ("+" | "-")? cifra_nenula { cifra } | "0"
        mapPattern.put(Constant.INT, "^([+-]?[1-9][0-9]*)|0$");
        // <string> ::= {(<litera> | <cifra> | <simbol> | "\n") }\{1, \}
        mapPattern.put(Constant.STRING, "^\"([a-zA-Z0-9_]|\\R)+\"$");
        // <const_bool> ::= "true" | "false"
        mapPattern.put(Constant.BOOL, "^(true|false)$");
        // <const_float> ::= <numar> "." {<cifra>}\{1, \}
        mapPattern.put(Constant.FLOAT, "^(([+-]?[1-9][0-9]*)|0)\\.[0-9]+$");
        // <caracter> ::= " ' " (litera | cifra | simbol) " ' "
        mapPattern.put(Constant.CHAR, "^\'[a-zA-Z0-9_]\'$");
    }

    public FIP getFip() {
        return fip;
    }

    public void setFip(FIP fip) {
        this.fip = fip;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Constant checkConstant(String atom){
        AtomicReference<Constant> c = new AtomicReference<>();
        Arrays.stream(Constant.values()).collect(Collectors.toList()).forEach(
                type -> {
                    if(checkPattern(atom,mapPattern.get(type))) {
                        c.set(type);
                        //System.out.println(atom + "-- " + type + "---"+ mapPattern.get(type));
                    }
                }
        );
        return c.get();
    }

    public Boolean checkPattern(String atom, String pattern){
        //System.out.println(atom + " ----" + pattern);
        Pattern beginPattern = Pattern.compile(pattern);
        Matcher beginMatcher = beginPattern.matcher(atom);
        boolean beginMatchFound = beginMatcher.matches();

        return beginMatchFound;
    }

    public Integer getCode(String atom){
        if(atom.equals("ID") || atom.equals("const") || !mapEncode.containsKey(atom))
            return -1;
        return mapEncode.get(atom);
    }

    //<ID> ::= <litera> {( <litera> | <cifra> | <simbol>)} \{0, 249\}
    public Boolean checkIdentifier(String atom){
        Pattern beginPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{0,249}$");
        Matcher beginMatcher = beginPattern.matcher(atom);
        boolean beginMatchFound = beginMatcher.matches();

        return beginMatchFound;
    }

    public static enum Constant{
        INT,
        FLOAT,
        CHAR,
        STRING,
        BOOL
    }

    public void analyse(List<String> atoms, List<Integer> lines){
        for (int i = 0; i < atoms.size(); i++) {
            Integer code = getCode(atoms.get(i));
            if(code != -1)
                fip.addFIP(code, -1);
            else
                if(checkIdentifier(atoms.get(i)))
                    fip.addFIP(
                            mapEncode.get("ID"),
                            symbolTable.poz(new IdentifierEntry(atoms.get(i)))
                    );
                else{
                    Constant type = checkConstant(atoms.get(i));
                    if(type != null)
                    {
                        //System.out.println(type);
                        fip.addFIP(
                                mapEncode.get("const"),
                                symbolTable.poz(
                                        switch (type) {
                                            case INT -> new IntEntry(Integer.parseInt(atoms.get(i)));
                                            case FLOAT -> new FloatEntry(Float.parseFloat(atoms.get(i)));
                                            case CHAR -> new CharEntry(atoms.get(i).charAt(1));
                                            case STRING -> new StringEntry(atoms.get(i));
                                            case BOOL -> new BoolEntry(Boolean.parseBoolean(atoms.get(i)));
                                        }
                        ));
                    }
                    else
                    {
                        throw new RuntimeException("Lexical Error at line " + lines.get(i));
                    }
                }
        }
    }
}
