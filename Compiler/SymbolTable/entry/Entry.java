package SymbolTable.entry;

public interface Entry {
    String toString();

    default Integer compareTo(Entry entry){
        return this.toString().compareTo(entry.toString());
    }
}
