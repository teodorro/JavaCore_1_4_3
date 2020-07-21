package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TaskLoad {
    private final String ZIP_PATH = "D://Games//savegames//gameprogress.zip";
    private final String PATH = "D://Games//savegames";

    public void start(){
        unzipProgresses();
        GameProgress progress = readProgress();
        printProgress(progress);
    }

    private void printProgress(GameProgress progress) {
        System.out.println(progress.toString());
    }

    private GameProgress readProgress() {
        File dir = new File(PATH);
        File[] files = dir.listFiles(file -> file.getPath().substring(file.getPath().lastIndexOf(".")).equals(".dat"));
        try (FileInputStream fis = new FileInputStream(files[0].getPath());
            ObjectInputStream ois = new ObjectInputStream(fis)){
            return (GameProgress) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void unzipProgresses() {
        try (ZipInputStream zis = new ZipInputStream(
                new FileInputStream(ZIP_PATH))){
            ZipEntry entry;
            String name;
            while((entry = zis.getNextEntry()) != null){
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(PATH + "//" + name);
                for (int c = zis.read(); c != -1; c = zis.read())
                    fos.write(c);
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
