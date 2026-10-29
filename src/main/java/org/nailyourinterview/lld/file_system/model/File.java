package org.nailyourinterview.lld.file_system.model;

public class File extends Entry {
    private final StringBuilder content = new StringBuilder();

    public File(String name) {
        super(name);
    }

    public String read() {
        return content.toString();
    }

    public void write(String text) {
        content.setLength(0);
        content.append(text);
    }

    public void append(String text) {
        content.append(text);
    }

    public String getExtension() {
        int dot = getName().lastIndexOf('.');
        return dot < 0 ? "" : getName().substring(dot + 1);
    }

    @Override
    public int getSize() {
        return content.length();
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
