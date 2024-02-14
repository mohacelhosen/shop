package com.store.shop.audio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Service
public class AudioService {
    private static final Logger logger = LoggerFactory.getLogger(AudioService.class);

    public byte[]  retrieveFileName(String searchKeyWord) throws IOException {
        String folderName = "audio";
        File audioFolder = new ClassPathResource(folderName).getFile();
        Path folderPath = audioFolder.toPath();


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
            searchKeyWord = searchKeyWord.trim();
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
            logger.info("Files with keyword '" + searchKeyWord + "':");
            for (Map.Entry<List<String>, Integer> entry : trackToken.entrySet()) {
                //collect only those files that match the maximum search token.
                if (maxMatch== entry.getValue()){
                    tokenMatchFiles.put(entry.getKey(), entry.getValue());
                }
                logger.info(entry.getKey() + ": " + entry.getValue() + " occurrences");
            }


            logger.info("\n\n🤔Return only that file whose name contains the fewest words \namong all the files that are equally maximum matched 🤔");
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
            String fileNameWithExtension = justFileName + ".mp3";
            logger.info("\nHighest occurrence of '" + searchKeyWord + "': " + fileNameWithExtension);

            Path originalFile = Path.of(folderName + File.separator + fileNameWithExtension);

            if (Files.exists(originalFile)) {
                logger.info("File exists: true");
                try {
                    return Files.readAllBytes(originalFile);

                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            } else {
                logger.info("File does not exist.");
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            return new byte[0];
        }
        return new byte[0];
    }
}
