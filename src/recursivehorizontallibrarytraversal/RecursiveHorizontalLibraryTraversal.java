/*
 * Code is prose.
 * 
 * Lefty G Balogh
 */
package recursivehorizontallibrarytraversal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lefty G Balogh
 */
public class RecursiveHorizontalLibraryTraversal {

    /**
     * Recursively traverses all subdirectories in the given directory and
     * prints its contents into a .CSV file. It appends the level of depth where
     * are file/directory set was found
     *
     * @param path the directory to go through
     * @param level should be zero
     * @param targetFilePath the file name and path where the results will be
     * saved
     *
     */
    public static void readShallowContentFirst(String path, int level, String targetFilePath) {
        level++;
        File base = new File(path);
        File[] filesAndDirectories = base.listFiles();
        for (File file : filesAndDirectories) {
            System.out.println(file.getName());
        }
        try {
            FileWriter writer = new FileWriter(targetFilePath, true);
            for (File file : filesAndDirectories) {
                writer.append(file.getName() + ',');
            }
            writer.append(Integer.toString(level) + "\n");
            writer.flush();
            writer.close();
            for (File file : filesAndDirectories) {
                if (file.isDirectory()) {
                    readShallowContentFirst(file.getCanonicalPath(), level, targetFilePath);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RecursiveHorizontalLibraryTraversal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Set<Integer> readDeepContentFirst(String path, Set<Integer> hashTracker, String targetFilePath) {

        File base = new File(path);
        File[] filesAndDirectories = base.listFiles();

        boolean containsFilesOnly = true;
        //filesAndDirectories[0].

        for (File file : filesAndDirectories) {

            if (file.isDirectory() && !hashTracker.contains(file.hashCode())) {
                System.out.println("Unvisited Directory:" + file);
                hashTracker.add(file.hashCode());
                appendToFile(targetFilePath, file);
                hashTracker = readDeepContentFirst(file.getPath(), hashTracker, targetFilePath);
            }

            if (file.isDirectory()) {
                containsFilesOnly = false;
            }
        }

        if (containsFilesOnly) {
            for (File file : filesAndDirectories) {
                hashTracker.add(file.hashCode());
                appendToFile(targetFilePath, file);
                System.out.println(file.getName());
            }

        }

        for (File file : filesAndDirectories) {
            if (!hashTracker.contains(file.hashCode())) {
                hashTracker.add(file.hashCode());
                appendToFile(targetFilePath, file);
                System.out.println(file.getName());
            }
        }

        return hashTracker;
    }

    public static void appendToFile(String targetFilePath, File visitedFileNode) {
        try {

            FileWriter writer = new FileWriter(targetFilePath, true);

            writer.append(visitedFileNode.getName() + ", ");
            if(visitedFileNode.isDirectory()){
                writer.append("\n");
            }
            writer.flush();
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(RecursiveHorizontalLibraryTraversal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        String path0 = "/home/lefty/path0";
        String desktop = "/home/lefty/Desktop";
        String path1 = "/home/lefty/path1";

        String horizontalTarget = "/home/lefty/horizontal.csv";
        String verticalTarget = "/home/lefty/vertical.csv";

        Set<Integer> hashTracker = new TreeSet<>();
        readDeepContentFirst(path0, hashTracker, verticalTarget);
        readShallowContentFirst(path0, 0, horizontalTarget);
    }

}
