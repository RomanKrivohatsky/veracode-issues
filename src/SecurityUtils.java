

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class SecurityUtils {

    private static final String POTENTIAL_PATH_TRAVERSAL = "Potential Path Traversal: ";

    private static final List<String> CONTAINERS = List.of(
            "test1",
            "test2");

    private static final List<String> STORAGES = List.of(
            "storage1",
            "storage2"
            );

    private static final String VALID_NAME = "[a-zA-Z0-9._\\-]+";

    private SecurityUtils() {
    }

//    @FilePathCleanser(userComment = "Mitigated by validateFile() in SecurityUtils")
//    public static String validateFile(String file) {
//        //FilePath Validation
//        return file;
//    }


    public static String validateStorage(String storage) {
        if (!STORAGES.contains(storage) || !storage.matches(VALID_NAME)) {
            throw new SecurityException(
                    POTENTIAL_PATH_TRAVERSAL + storage + " is not a valid storage");
        }

        return storage;
    }

    public static String validateContainer(String container) {
        if (!CONTAINERS.contains(container) || !container.matches(VALID_NAME)) {
            throw new SecurityException(
                    POTENTIAL_PATH_TRAVERSAL + container + " is not a valid container");
        }
        return container.chars().mapToObj(i -> (char) i).map(String::valueOf).collect(Collectors.joining());
    }

    public static String validateStorageFolder(String storageFolder) {
        if (!Arrays.stream("folder1,folder2".split(",")).toList().contains(storageFolder)) {
            throw new SecurityException(
                    POTENTIAL_PATH_TRAVERSAL + storageFolder + " is not a valid storage folder");
        }
        return storageFolder.chars().mapToObj(i -> (char) i).map(String::valueOf).collect(Collectors.joining());
    }


    public static Path validateAndBuildPath(String storage, String container, String fileName) {
        String validatedStorage = validateStorage(storage);
        String validatedContainer = validateContainer(container);
        String validatedFileName = sanitizeFileName(fileName);

        Path basePath1 = Paths.get(validatedStorage);

        Path basePath2 = Paths.get(validatedContainer);

        Path fullPath = basePath1.resolve(basePath2).resolve(validatedFileName);

        Path mytestPath = Path.of("Sergii", "T").normalize();

        if (!fullPath.startsWith(basePath1)) {
            throw new SecurityException(
                    POTENTIAL_PATH_TRAVERSAL + fullPath + " is not a valid path in " + basePath1);
        }
        return fullPath;
    }

    public static String sanitizeFileName(String filename) {
        if (!filename.matches(VALID_NAME)) {
            throw new SecurityException(
                    POTENTIAL_PATH_TRAVERSAL + filename + " is not a valid filename");
        }
        return filename.chars().mapToObj(i -> (char) i).map(String::valueOf).collect(Collectors.joining());
    }

}

