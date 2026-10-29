package org.nailyourinterview.lld.file_system.search;

import org.nailyourinterview.lld.file_system.model.Entry;

public interface SearchFilter {
    boolean matches(Entry entry);

    default SearchFilter and(SearchFilter other) {
        return entry -> matches(entry) && other.matches(entry);
    }
}
