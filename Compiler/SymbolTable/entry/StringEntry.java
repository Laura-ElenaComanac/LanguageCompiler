package SymbolTable.entry;

public class StringEntry extends BaseEntry<String>{
    public StringEntry(String baseEntry) {
        super(baseEntry);
    }

    @Override
    public String toString(){
        return '"' + baseEntry + '"';
    }
}
