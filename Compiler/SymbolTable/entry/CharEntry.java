package SymbolTable.entry;

public class CharEntry extends BaseEntry<Character> {
    public CharEntry(Character baseEntry) {
        super(baseEntry);
    }

    @Override
    public String toString() {
        return "'" + baseEntry + "'";
    }
}
