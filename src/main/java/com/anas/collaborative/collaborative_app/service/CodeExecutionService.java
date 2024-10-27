package com.anas.collaborative.collaborative_app.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CodeExecutionService {

    public String executeCode(String language, String code) throws IOException, InterruptedException {
        String className = "Main";
        if ("java".equalsIgnoreCase(language)) {
            // Attempt to detect the class name from the code
            String detectedClassName = detectClassName(code);
            if (detectedClassName != null) {
                className = detectedClassName;
            } else {
                return "Error: No public class found in Java code.";
            }
        }

        // Save the code to a file with the detected class name
        String directoryPath = saveCodeToFile(language, code, className);

        // Execute code based on the language
        if ("java".equalsIgnoreCase(language)) {
            return executeJavaCode(directoryPath, className);
        } else if ("python".equalsIgnoreCase(language)) {
            return executePythonCode(directoryPath);
        }

        return "Unsupported language!";
    }

    private String detectClassName(String code) {
        Pattern pattern = Pattern.compile("public class (\\w+)");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String executeJavaCode(String directoryPath, String className) throws IOException, InterruptedException {
        // Compile Java code
        String[] compileCommand = {
                "docker", "run", "--rm", "-v", directoryPath + ":/app", "-w", "/app", "openjdk:11", "javac", className + ".java"
        };
        Process compileProcess = new ProcessBuilder(compileCommand).start();
        compileProcess.waitFor();

        // Capture any compilation errors
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
            String compileErrors = errorReader.lines().collect(Collectors.joining("\n"));
            if (!compileErrors.isEmpty()) {
                return compileErrors;
            }
        }

        // Run Java code
        String[] runCommand = {
                "docker", "run", "--rm", "-v", directoryPath + ":/app", "-w", "/app", "openjdk:11", "java", className
        };
        Process runProcess = new ProcessBuilder(runCommand).start();

        // Capture output or errors from execution
        try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()))) {

            String output = outputReader.lines().collect(Collectors.joining("\n"));
            String errors = errorReader.lines().collect(Collectors.joining("\n"));
            return output.isEmpty() ? errors : output;
        }
    }

    private String executePythonCode(String directoryPath) throws IOException, InterruptedException {
        String[] command = {
                "docker", "run", "--rm", "-v", directoryPath + ":/app", "-w", "/app", "python:3.8", "python", "main.py"
        };
        Process process = new ProcessBuilder(command).start();

        try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            String output = outputReader.lines().collect(Collectors.joining("\n"));
            String errors = errorReader.lines().collect(Collectors.joining("\n"));
            return output.isEmpty() ? errors : output;
        }
    }

    private String saveCodeToFile(String language, String code, String className) throws IOException {
        Path dirPath = Paths.get("temp", UUID.randomUUID().toString());
        Files.createDirectories(dirPath);
        String filename = language.equalsIgnoreCase("java") ? className + ".java" : "main.py";
        Path filePath = dirPath.resolve(filename);
        Files.write(filePath, code.getBytes());
        return filePath.getParent().toAbsolutePath().toString();
    }
}
