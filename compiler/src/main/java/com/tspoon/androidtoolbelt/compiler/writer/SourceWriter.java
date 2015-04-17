package com.tspoon.androidtoolbelt.compiler.writer;

import java.io.IOException;
import java.io.Writer;

public interface SourceWriter {

    static final String PACKAGE = "com.tspoon.androidtoolbelt.component.service";

    String getFileName();

    void writeSource(Writer writer) throws IOException;
}

