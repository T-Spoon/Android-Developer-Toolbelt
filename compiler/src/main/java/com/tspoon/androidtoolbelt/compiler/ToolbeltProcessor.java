package com.tspoon.androidtoolbelt.compiler;

import com.google.auto.service.AutoService;
import com.tspoon.androidtoolbelt.compiler.writer.MemoryServiceWriter;
import com.tspoon.androidtoolbelt.compiler.writer.ServiceHolderWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ToolbeltProcessor extends AbstractProcessor {

    public static final int NUMBER_OF_SERVICES = 15;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Registry.init(processingEnv.getMessager(), processingEnv.getTypeUtils(), processingEnv.getElementUtils(), processingEnv.getFiler());

        for (int i = 1; i <= NUMBER_OF_SERVICES; i++) {
            MemoryServiceWriter serviceWriter = new MemoryServiceWriter(i);
            try {
                JavaFileObject object = Registry.get().getFiler().createSourceFile(serviceWriter.getFileName());
                Writer writer = object.openWriter();
                serviceWriter.writeSource(writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        ServiceHolderWriter holderWriter = new ServiceHolderWriter();
        try {
            JavaFileObject object = Registry.get().getFiler().createSourceFile(holderWriter.getFileName());
            Writer writer = object.openWriter();
            holderWriter.writeSource(writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

}
