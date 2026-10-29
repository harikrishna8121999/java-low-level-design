package org.nailyourinterview.lld.file_system.search;

import lombok.AllArgsConstructor;
import org.nailyourinterview.lld.file_system.model.Entry;
import org.nailyourinterview.lld.file_system.model.File;

@AllArgsConstructor
public class ExtensionFilter implements SearchFilter {
    private final String extension;

    @Override
    public boolean matches(Entry entry) {
        return entry instanceof File file && file.getExtension().equals(extension);
    }
}
