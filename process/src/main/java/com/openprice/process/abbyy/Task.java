package com.openprice.process.abbyy;

import java.io.Reader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import lombok.Getter;

public class Task {

    public enum TaskStatus {
        Unknown, Submitted, Queued, InProgress, Completed, ProcessingFailed, Deleted, NotEnoughCredits
    }

    @Getter
    private TaskStatus status = TaskStatus.Unknown;

    @Getter
    private String id;

    @Getter
    private String downloadUrl;

    public Task(Reader reader) throws Exception {
        InputSource source = new InputSource();
        source.setCharacterStream(reader);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(source);

        NodeList taskNodes = doc.getElementsByTagName("task");
        Element task = (Element) taskNodes.item(0);

        parseTask(task);
    }

    private Task() {
    }

    /**
     * Read multiple tasks from server xml response
     */
    public static Task[] LoadTasks(Reader reader) throws Exception {
        // Read all text into string
        // String data = new Scanner(reader).useDelimiter("\\A").next();
        // Read full task information from xml
        InputSource source = new InputSource();
        source.setCharacterStream(reader);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(source);

        Vector<Task> result = new Vector<Task>();

        NodeList taskNodes = doc.getElementsByTagName("task");
        for (int i = 0; i < taskNodes.getLength(); i++) {
            Element taskEl = (Element) taskNodes.item(i);
            Task task = new Task();
            task.parseTask(taskEl);
            result.add(task);
        }
        return result.toArray(new Task[result.size()]);
    }

    public Boolean isTaskActive() {
        if (status == TaskStatus.Queued || status == TaskStatus.InProgress) {
            return true;
        }

        return false;
    }

    private void parseTask(Element taskElement) {
        id = taskElement.getAttribute("id");
        status = parseTaskStatus(taskElement.getAttribute("status"));
        if (status == TaskStatus.Completed) {
            downloadUrl = taskElement.getAttribute("resultUrl");
        }
    }

    private TaskStatus parseTaskStatus(String status) {
        if (status.equals("Submitted")) {
            return TaskStatus.Submitted;
        } else if (status.equals("Queued")) {
            return TaskStatus.Queued;
        } else if (status.equals("InProgress")) {
            return TaskStatus.InProgress;
        } else if (status.equals("Completed")) {
            return TaskStatus.Completed;
        } else if (status.equals("ProcessingFailed")) {
            return TaskStatus.ProcessingFailed;
        } else if (status.equals("Deleted")) {
            return TaskStatus.Deleted;
        } else if (status.equals("NotEnoughCredits")) {
            return TaskStatus.NotEnoughCredits;
        } else {
            return TaskStatus.Unknown;
        }
    }

}
