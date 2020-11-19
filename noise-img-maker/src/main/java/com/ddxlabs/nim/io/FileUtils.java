package com.ddxlabs.nim.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> stringListByLocalPath(String filePath) throws IOException {
        List<String> stringList = new ArrayList<String>();
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        try (FileReader fileReader = new FileReader(file);
             BufferedReader br = new BufferedReader(fileReader);
        ) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (!"".equals(sCurrentLine.trim())) {
                    stringList.add(sCurrentLine);
                }
            }
            return stringList;
        }
    }
}
