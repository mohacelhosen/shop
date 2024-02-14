package com.store.shop.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SearchFileByTokenSystem {
    public static void main(String[] args) {
        String folderName = "data";
        Path folderPath = Path.of(folderName);

        // Read all files in the folder and tokenize them
        try (Stream<Path> files = Files.list(folderPath)) {
            List<String> fileList = files
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(file -> file.substring(0, file.indexOf(".")))
                    .toList();

            List<List<String>> fileListToken = new ArrayList<>();

            for (String singleFile : fileList) {
                List<String> fileNameToken = Arrays.stream(singleFile.split(" ")).map(String::toLowerCase).toList();
                fileListToken.add(fileNameToken);
            }

            // Tokenize the search string
            String searchKeyWord = "morning test".trim();
            List<String> searchKeyWordTokens = Arrays.stream(searchKeyWord.split(" ")).map(String::toLowerCase).toList();

            // Track occurrences of the search keyword in each file
            Map<List<String>, Integer> trackToken = new HashMap<>();

            for (List<String> singleFileToken : fileListToken) {
                int count = 0;
                for (String token : singleFileToken) {
                    if (searchKeyWordTokens.contains(token)) {
                        count++;
                    }
                }
                trackToken.put(new ArrayList<>(singleFileToken), count);
            }

            // Find the file with the highest occurrence of the keyword
            int maxMatch= Collections.max(trackToken.values());



            Map<List<String>, Integer> tokenMatchFiles = new HashMap<>();
            System.out.println("Files with keyword '" + searchKeyWord + "':");
            for (Map.Entry<List<String>, Integer> entry : trackToken.entrySet()) {
                //collect only those files that match the maximum search token.
                if (maxMatch== entry.getValue()){
                    tokenMatchFiles.put(entry.getKey(), entry.getValue());
                }
                System.out.println(entry.getKey() + ": " + entry.getValue() + " occurrences");
            }


            System.out.println("\n\n🤔Return only that file whose name contains the fewest words \namong all the files that are equally maximum matched 🤔");
            long minimumToken = Long.MAX_VALUE;
            List<String> fileNameInArray = new ArrayList<>();
            for(Map.Entry<List<String>, Integer> singleMapItem:tokenMatchFiles.entrySet()){
                System.out.println(singleMapItem.getKey());
                long count = singleMapItem.getKey().size();
                if (count<minimumToken) {
                    minimumToken=count;
                    fileNameInArray.clear();
                    fileNameInArray.addAll(singleMapItem.getKey());
                }
            }

            // Construct the original file name from the List<String> tokens:
            String originalFileName = String.join(" ", fileNameInArray);

            String justFileName = originalFileName.trim();
            String fileNameWithExtension = justFileName + ".txt";
            System.out.println("\nHighest occurrence of '" + searchKeyWord + "': " + fileNameWithExtension);

            Path originalFile = Path.of(folderName + File.separator + fileNameWithExtension);

            if (Files.exists(originalFile)) {
                System.out.println("File exists: true");
                try {
                    List<String> allLines = Files.readAllLines(originalFile);
                    System.out.println("File content:");
                    allLines.forEach(System.out::println);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("File does not exist.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
