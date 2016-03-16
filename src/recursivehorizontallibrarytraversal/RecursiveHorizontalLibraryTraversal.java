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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lefty G Balogh
 */
public class RecursiveHorizontalLibraryTraversal {

    
    /**
     * Recursively traverses all subdirectories in the given directory and prints its contents into a .CSV file.
     * It appends the level of depth where are file/directory set was found
     * @param path the directory to go through
     * @param level should be zero
     * @param targetFilePath the file name and path where the results will be saved
     *  
     */
    public static void readContents(String path, int level, String targetFilePath){
        level++;
        File base = new File(path);
        File[] filesAndDirectories = base.listFiles();        
        for (File file : filesAndDirectories) {
            System.out.println(file.getName());
        }        
        try {        
            FileWriter writer = new FileWriter(targetFilePath, true);            
            for (File file : filesAndDirectories) {
                writer.append(file.getName()+',');
            }
            writer.append(Integer.toString(level)+"\n");
            writer.flush();
            writer.close();            
            for (File file : filesAndDirectories) {
                if (file.isDirectory()){
                    readContents(file.getCanonicalPath(), level, targetFilePath);
                }
            }            
        } catch (IOException ex) {
            Logger.getLogger(RecursiveHorizontalLibraryTraversal.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static void main(String[] args) {
        String path = "/home/lefty/path0";
        String target = "/home/lefty/path0.csv";
        readContents(path, 0, target);        
        String path2 = "/home/lefty/Desktop";
        String target2 = "/home/lefty/desktop.csv";
        readContents(path2, 0, target2);
    }
    
}
