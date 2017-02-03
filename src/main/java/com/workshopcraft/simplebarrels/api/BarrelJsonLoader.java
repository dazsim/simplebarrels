package com.workshopcraft.simplebarrels.api;

import java.io.*;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URISyntaxException;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonObject;

import com.workshopcraft.simplebarrels.SimpleBarrels;

class BarrelJsonLoader {
    private static String[] directoryListing;
    private static final String MOD_ASSETS_PATH = "assets/simplebarrels/";
    private static final String BARRELS_JSON_PATH = MOD_ASSETS_PATH + "barrels/";

    BarrelJsonLoader() {}

    static List<JsonObject> load() {

        // Load a list of all files in our barrels configuration folder.
        try {
            directoryListing = getDirectoryListing(BarrelJsonLoader.class, BARRELS_JSON_PATH);
        } catch (Exception e) {
            SimpleBarrels.logger.error("Unable to read directory: " + e.getMessage());
        }

        // Create a list to hold the JSON loaded from each file in the folder.
        List<JsonObject> barrelGroups = new ArrayList<JsonObject>();

        // Iterate through our directory listing and load their JSON into our List.
        try {
            for (String fileName : directoryListing) {
                if (!fileName.endsWith(".json")) continue; // Only load json files. Skip to next file.

                JsonElement json = importJsonFile(fileName);

                if (json == null) continue; // Import failed. Skip to next file.
                if (!json.isJsonObject()) continue; // contents of the file are invalid. Skip to next file.

                barrelGroups.add(json.getAsJsonObject());
            }
        } catch (JsonSyntaxException e) {
            SimpleBarrels.logger.error("Invalid JSON Syntax: " + e.getMessage());
        } catch (JsonIOException e) {
            SimpleBarrels.logger.error("Error loading JSON: " + e.getMessage());
        }

        return barrelGroups;
    }

    private static JsonElement importJsonFile(String fileName) {
        Gson gson = new Gson();
        InputStream stream = JsonElement.class.getResourceAsStream("/" + BARRELS_JSON_PATH + fileName);
        Reader streamReader  = new InputStreamReader(stream);
        return gson.fromJson(streamReader, JsonElement.class);
    }

    /**
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     *
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException
     * @throws IOException
     */
    private static String[] getDirectoryListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            /* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
            /*
             * In case of a jar file, we can't actually find a directory.
             * Have to assume the same jar as clazz.
             */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
            /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { //filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory name
                        entry = entry.substring(0, checkSubdir);
                    }
                    if (!entry.isEmpty()) {
                        result.add(entry);
                    }
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }
}
