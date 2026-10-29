package org.nailyourinterview.lld.file_system.service;

import org.nailyourinterview.lld.file_system.model.Directory;
import org.nailyourinterview.lld.file_system.model.Entry;
import org.nailyourinterview.lld.file_system.model.File;
import org.nailyourinterview.lld.file_system.search.SearchFilter;

import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private final Directory root = new Directory("");
    private Directory current = root;   // working directory for relative paths

    // ----- directories -----

    // Like mkdir -p: create every missing directory on the path
    public boolean mkdir(String path) {
        Directory dir = path.startsWith("/") ? root : current;
        for (String part : path.split("/")) {
            if (part.isEmpty() || part.equals(".")) continue;
            if (part.equals("..")) {
                dir = dir.getParent() == null ? dir : dir.getParent();
                continue;
            }
            Entry child = dir.getChild(part);
            if (child == null) {
                child = new Directory(part);
                dir.addChild(child);
            } else if (!child.isDirectory()) {
                System.out.println("mkdir: " + child.getPath() + " is a file.");
                return false;
            }
            dir = (Directory) child;
        }
        System.out.println("mkdir " + dir.getPath());
        return true;
    }

    public boolean cd(String path) {
        Directory dir = findDirectory(path, "cd");
        if (dir == null) return false;
        current = dir;
        return true;
    }

    public String pwd() {
        return current.getPath();
    }

    public void ls(String path) {
        Entry entry = find(path, "ls");
        if (entry == null) return;
        if (!entry.isDirectory()) {
            System.out.println(line(entry));
            return;
        }
        System.out.println("ls " + entry.getPath());
        ((Directory) entry).getChildren().forEach(child -> System.out.println("  " + line(child)));
    }

    // ----- files -----

    public boolean createFile(String path) {
        Directory parent = findDirectory(parentOf(path), "touch");
        String name = nameOf(path);
        if (parent == null || !isValidName(name)) return false;
        if (parent.hasChild(name)) {
            System.out.println("touch: " + parent.getChild(name).getPath() + " already exists.");
            return false;
        }
        File file = new File(name);
        parent.addChild(file);
        System.out.println("touch " + file.getPath());
        return true;
    }

    // Overwrite. Create the file first if it is missing.
    public boolean writeFile(String path, String text) {
        Entry entry = resolve(path);
        if (entry == null) {
            if (!createFile(path)) return false;
            entry = resolve(path);
        }
        File file = asFile(entry, "write");
        if (file == null) return false;
        file.write(text);
        System.out.println("write " + file.getPath() + " (" + file.getSize() + " B)");
        return true;
    }

    public boolean appendFile(String path, String text) {
        File file = asFile(find(path, "append"), "append");
        if (file == null) return false;
        file.append(text);
        System.out.println("append " + file.getPath() + " (" + file.getSize() + " B)");
        return true;
    }

    public String readFile(String path) {
        File file = asFile(find(path, "cat"), "cat");
        return file == null ? null : file.read();
    }

    // ----- delete and move -----

    // Delete a file, or a directory with everything under it (rm -r)
    public boolean delete(String path) {
        Entry entry = find(path, "rm");
        if (entry == null) return false;
        if (entry == root) {
            System.out.println("rm: cannot delete the root.");
            return false;
        }
        if (isSameOrInside(current, entry)) {
            System.out.println("rm: " + entry.getPath() + " holds the working directory.");
            return false;
        }
        System.out.println("rm " + entry.getPath());
        entry.getParent().removeChild(entry.getName());
        return true;
    }

    // mv: into an existing directory, or to a new path (rename)
    public boolean move(String from, String to) {
        Entry source = find(from, "mv");
        if (source == null) return false;
        if (source == root) {
            System.out.println("mv: cannot move the root.");
            return false;
        }

        Entry target = resolve(to);
        Directory newParent;
        String newName;
        if (target != null && target.isDirectory()) {
            newParent = (Directory) target;
            newName = source.getName();
        } else {
            newParent = findDirectory(parentOf(to), "mv");
            newName = nameOf(to);
            if (newParent == null || !isValidName(newName)) return false;
        }

        if (isSameOrInside(newParent, source)) {
            System.out.println("mv: cannot move " + source.getPath() + " inside itself.");
            return false;
        }
        if (newParent.hasChild(newName)) {
            System.out.println("mv: " + newParent.getChild(newName).getPath() + " already exists.");
            return false;
        }

        String oldPath = source.getPath();
        source.getParent().removeChild(source.getName());
        source.setName(newName);
        newParent.addChild(source);
        System.out.println("mv " + oldPath + " -> " + source.getPath());
        return true;
    }

    // ----- search -----

    public List<String> search(String path, SearchFilter filter) {
        List<String> found = new ArrayList<>();
        Entry start = find(path, "find");
        if (start != null) collect(start, filter, found);
        return found;
    }

    // Depth-first walk of the tree
    private void collect(Entry entry, SearchFilter filter, List<String> found) {
        if (filter.matches(entry)) found.add(entry.getPath());
        if (entry instanceof Directory dir) {
            dir.getChildren().forEach(child -> collect(child, filter, found));
        }
    }

    public void tree(String path) {
        Entry start = find(path, "tree");
        if (start != null) printTree(start, "");
    }

    private void printTree(Entry entry, String indent) {
        String name = entry == root ? "/" : entry.getName() + (entry.isDirectory() ? "/" : "");
        System.out.println(indent + name + "  (" + entry.getSize() + " B)");
        if (entry instanceof Directory dir) {
            dir.getChildren().forEach(child -> printTree(child, indent + "  "));
        }
    }

    // ----- path helpers -----

    // Absolute (/a/b) or relative to the working directory (a/b, ../b, ./b).
    // Returns null if any part is missing.
    private Entry resolve(String path) {
        Entry entry = path.startsWith("/") ? root : current;
        for (String part : path.split("/")) {
            if (part.isEmpty() || part.equals(".")) continue;
            if (part.equals("..")) {
                entry = entry.getParent() == null ? entry : entry.getParent();
                continue;
            }
            if (!(entry instanceof Directory dir)) return null;
            entry = dir.getChild(part);
            if (entry == null) return null;
        }
        return entry;
    }

    private Entry find(String path, String command) {
        Entry entry = resolve(path);
        if (entry == null) System.out.println(command + ": " + path + ": no such file or directory.");
        return entry;
    }

    private Directory findDirectory(String path, String command) {
        Entry entry = find(path, command);
        if (entry == null) return null;
        if (!entry.isDirectory()) {
            System.out.println(command + ": " + entry.getPath() + " is not a directory.");
            return null;
        }
        return (Directory) entry;
    }

    private File asFile(Entry entry, String command) {
        if (entry == null) return null;
        if (entry.isDirectory()) {
            System.out.println(command + ": " + entry.getPath() + " is a directory.");
            return null;
        }
        return (File) entry;
    }

    // Walk up from 'entry'. True if we meet 'ancestor' on the way.
    private boolean isSameOrInside(Entry entry, Entry ancestor) {
        for (Entry e = entry; e != null; e = e.getParent()) {
            if (e == ancestor) return true;
        }
        return false;
    }

    private String parentOf(String path) {
        int slash = path.lastIndexOf('/');
        if (slash < 0) return ".";
        return slash == 0 ? "/" : path.substring(0, slash);
    }

    private String nameOf(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    private boolean isValidName(String name) {
        if (name.isEmpty() || name.equals(".") || name.equals("..")) {
            System.out.println("Invalid name: '" + name + "'.");
            return false;
        }
        return true;
    }

    private String line(Entry entry) {
        return (entry.isDirectory() ? "d " + entry.getName() + "/" : "f " + entry.getName())
                + "  " + entry.getSize() + " B";
    }
}
