package org.nailyourinterview.lld.file_system.search;

import lombok.AllArgsConstructor;
import org.nailyourinterview.lld.file_system.model.Entry;

@AllArgsConstructor
public class NameContainsFilter implements SearchFilter {
    private final String text;

    @Override
    public boolean matches(Entry entry) {
        return entry.getName().contains(text);
    }
}
