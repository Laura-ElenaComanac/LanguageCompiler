package SymbolTable;

import SymbolTable.entry.Entry;

public class SymbolTableNode {
    Integer position;
    Entry entry;
    SymbolTableNode leftChild;
    SymbolTableNode rightChild;

    public SymbolTableNode(Integer position, Entry entry) {
        this.position = position;
        this.entry = entry;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public SymbolTableNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(SymbolTableNode leftChild) {
        this.leftChild = leftChild;
    }

    public SymbolTableNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(SymbolTableNode rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "SymbolTableNode{" +
                "position=" + position +
                ", entry=" + entry +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}
