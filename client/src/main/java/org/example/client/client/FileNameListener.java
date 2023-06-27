package org.example.client.client;

import org.example.exceptions.FileLoadingException;
import org.example.exceptions.NoAccessToFileException;
import org.example.models.StudyGroup;
import org.example.xmlUtils.XmlFileHandler;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Stack;

/**A class that opens and reads a file*/
public class FileNameListener {
    private String fileName;
    private Stack<StudyGroup> groups;

    public String getFileName(){
        return fileName;
    }

    public DataInOutStatus listener(){
        OutStream.outputIntoCLI("Type name of file.");
        try {
            this.fileName = InputClireader.getInputReader().nextLine();
        }catch (NoSuchElementException e) {
            OutStream.outputIntoCLI("You pressed ctrl + D");
            return DataInOutStatus.FAILED;
        }
        return DataInOutStatus.SUCCESSFULLY;
    }

    public DataInOutStatus reader(){
        try {
            File file1 = new File(fileName);
            XmlFileHandler read  = new XmlFileHandler();
            read.load(file1);
            groups = read.get();
        }catch (NullPointerException e) {
            return DataInOutStatus.FAILED;
        } catch (FileLoadingException e) {
            throw new RuntimeException(e);
        } catch (NoAccessToFileException e) {
            throw new RuntimeException(e);
        }
        return DataInOutStatus.SUCCESSFULLY;
    }

    public Stack<StudyGroup> getGroups() {
        return groups;
    }
}
