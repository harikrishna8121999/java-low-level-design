package org.nailyourinterview.lld.file_system.search;

import lombok.AllArgsConstructor;
import org.nailyourinterview.lld.file_system.model.Entry;

@AllArgsConstructor
public class MinSizeFilter implements SearchFilter {
    private final int minSize;

    @Override
    public boolean matches(Entry entry) {
        return !entry.isDirectory() && entry.getSize() >= minSize;
    }
}
