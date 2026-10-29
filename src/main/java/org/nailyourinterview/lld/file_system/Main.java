package org.nailyourinterview.lld.file_system;

import org.nailyourinterview.lld.file_system.search.ExtensionFilter;
import org.nailyourinterview.lld.file_system.search.MinSizeFilter;
import org.nailyourinterview.lld.file_system.search.NameContainsFilter;
import org.nailyourinterview.lld.file_system.service.FileSystem;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        System.out.println("--- 1. Build the tree ---");
        fs.mkdir("/home/alice/docs");
        fs.mkdir("/home/bob");
        fs.mkdir("/tmp");

        System.out.println("--- 2. Write and read files ---");
        fs.writeFile("/home/alice/docs/resume.txt", "Alice - Java developer");
        fs.writeFile("/home/alice/docs/notes.md", "# LLD\n");
        fs.appendFile("/home/alice/docs/notes.md", "- Composite pattern\n");
        fs.createFile("/home/bob/todo.txt");
        fs.writeFile("/tmp/cache.log", "cache miss x 3");
        System.out.print("cat notes.md:\n" + fs.readFile("/home/alice/docs/notes.md"));

        System.out.println("--- 3. Relative paths: cd, pwd, ls ---");
        fs.cd("/home/alice");
        System.out.println("pwd = " + fs.pwd());
        fs.ls("docs");
        fs.cd("../bob");
        System.out.println("pwd = " + fs.pwd());
        fs.writeFile("todo.txt", "buy milk");
        fs.ls(".");

        System.out.println("--- 4. Move and rename ---");
        fs.move("/home/alice/docs/resume.txt", "/home/bob");
        fs.move("/home/bob/resume.txt", "/home/bob/cv.txt");
        fs.tree("/");

        System.out.println("--- 5. Search ---");
        System.out.println("*.txt          -> " + fs.search("/", new ExtensionFilter("txt")));
        System.out.println(">= 10 B        -> " + fs.search("/", new MinSizeFilter(10)));
        System.out.println("'o' in name+md -> " + fs.search("/home",
                new NameContainsFilter("o").and(new ExtensionFilter("md"))));

        System.out.println("--- 6. Invalid operations ---");
        fs.readFile("/home/carol/a.txt");
        fs.createFile("/home/bob/todo.txt");
        fs.mkdir("/home/bob/todo.txt/x");
        fs.cd("/tmp/cache.log");
        fs.readFile("/home");
        fs.move("/home", "/home/alice");
        fs.delete("/home");
        fs.delete("/");

        System.out.println("--- 7. Delete a directory ---");
        fs.cd("/");
        fs.delete("/home/alice");
        fs.delete("/tmp");
        fs.tree("/");
    }
}
