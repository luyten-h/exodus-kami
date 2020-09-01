package me.zeroeightsix.kami.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Files {
    public static String[] readFileAllLines(String path) {
        try {
            List lines = java.nio.file.Files.readAllLines(Paths.get(path));
            String[] array = new String[lines.size()];
            lines.toArray(array);
            return array;
        } catch (IOException var3) {
            return null;
        }
    }

    public static void writeFile(String path, String[] lines) {
        try {
            FileWriter fw = new FileWriter(path);
            String[] var3 = lines;
            int var4 = lines.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String l = var3[var5];
                fw.write(l + "\r");
            }

            fw.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }
}