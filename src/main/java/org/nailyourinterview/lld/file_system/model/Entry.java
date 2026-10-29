package org.nailyourinterview.lld.file_system.model;

import lombok.Getter;
import lombok.Setter;

// One node in the tree. A file and a directory are both entries (Composite).
@Getter
public abstract class Entry {
    @Setter private String name;
    @Setter private Directory parent;   // null only for the root

    protected Entry(String name) {
        this.name = name;
    }

    public abstract int getSize();

    public abstract boolean isDirectory();

    // Walk up to the root: /docs/notes.txt
    public String getPath() {
        if (parent == null) return "/";
        String parentPath = parent.getPath();
        return (parentPath.equals("/") ? "" : parentPath) + "/" + name;
    }
}
