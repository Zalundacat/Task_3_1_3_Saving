import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress[] gamer = {
                new GameProgress(45, 56, 6, 4756),
                new GameProgress(4, 10, 3, 364),
                new GameProgress(59, 100, 37, 634)
        };

        String savingPath = "C:\\Users\\alex\\Desktop\\Games\\savegames\\";
        String zipName = "Gamers.zip";
        String zipPath = savingPath + zipName;

        List<String> saving = new ArrayList<>();

        savingGamers(gamer, savingPath, saving);
        zipFIles(zipPath, saving);
        deleteGamers(saving);
    }

    public static void savingGamers(GameProgress[] gameProgress, String savePath, List<String> saving) {
        for (int i = 0; i < gameProgress.length; i++) {
            String fileName = "save" + (i+1) + ".dat";
            String savingPath = savePath + fileName;
            saveGame(savingPath, gameProgress[i]);
            saving.add(savingPath);
            System.out.println("Файл  \"" + savingPath + "\" успешно создан");
        }
    }

    public static void saveGame(String savingPath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(savingPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage(
            ));
        }
    }

    public static void deleteGamers(List<String> saving) {
        for (int i = 0; i < saving.size(); i++) {
            String savingPath = saving.get(i);
            System.out.println("Файл \"" + savingPath + "\" успешно удален");
            File delete = new File(savingPath);
            if (!delete.delete()) {
                System.out.println("Ошибка в удалении файла \"" + savingPath + "\"");
            }
        }
    }

    public static void zipFIles(String zipPath, List<String> saving) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (String save : saving) {
                File zipFile = new File(save);
                try (FileInputStream fis = new FileInputStream(zipFile)) {
                    ZipEntry zipEntry = new ZipEntry(zipFile.getName());
                    zout.putNextEntry(zipEntry);
                    byte[] b = new byte[fis.available()];
                    fis.read(b);
                    zout.write(b);
                    zout.closeEntry();
                    System.out.println("Файл \"" + save + "\" успешно добавлен в архив " + "\"" + zipPath + "\"");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}