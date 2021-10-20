package SymbolTable.entry;

public class BaseEntry<T> implements Entry {
    T baseEntry;

    public T getBaseEntry() {
        return baseEntry;
    }

    public void setBaseEntry(T baseEntry) {
        this.baseEntry = baseEntry;
    }

    public BaseEntry(T baseEntry) {
        this.baseEntry = baseEntry;
    }

    @Override
    public String toString(){
        return baseEntry.toString();
    }
}
