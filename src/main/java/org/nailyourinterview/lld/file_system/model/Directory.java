package org.nailyourinterview.lld.file_system.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Directory extends Entry {
    private final Map<String, Entry> children = new TreeMap<>();   // name -> entry, sorted for ls

    public Directory(String name) {
        super(name);
    }

    public Entry getChild(String name) {
        return children.get(name);
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public void addChild(Entry entry) {
        children.put(entry.getName(), entry);
        entry.setParent(this);
    }

    public void removeChild(String name) {
        Entry entry = children.remove(name);
        if (entry != null) entry.setParent(null);
    }

    public Collection<Entry> getChildren() {
        return children.values();
    }

    // Composite: a directory's size is the sum of its children
    @Override
    public int getSize() {
        return children.values().stream().mapToInt(Entry::getSize).sum();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
