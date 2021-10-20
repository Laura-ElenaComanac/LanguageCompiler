package SymbolTable;

import SymbolTable.entry.Entry;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    List<Entry> entries = new ArrayList<>();
    SymbolTableNode root;

    // Searches position and Inserts node in BST
    public Integer pozRec(SymbolTableNode node, Entry entry, SymbolTableNode parent){
        if(node == null) {
            entries.add(entry);
            if(parent == null){
                root = new SymbolTableNode(entries.size()-1, entry);
            }
            else{
                if(entry.compareTo(parent.entry)<0)
                    parent.setLeftChild(new SymbolTableNode(entries.size()-1, entry));
                else
                    parent.setRightChild(new SymbolTableNode(entries.size()-1, entry));
            }
            return entries.size()-1;
        }
        if(entry.compareTo(node.entry)==0){
            return node.position;
        }
        if(entry.compareTo(node.entry)<0)
            return pozRec(node.leftChild, entry, node);
        return pozRec(node.rightChild, entry, node);
    }

    public Integer poz(Entry entry){
        return pozRec(root, entry, null);
    }

    @Override
    public String toString() {
        String text = "";
        for (int i = 0; i <entries.size() ; i++) {
            text+= entries.get(i) + " " + i + "\n";
        }
        return text;
    }
}
