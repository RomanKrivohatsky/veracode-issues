import java.nio.file.Path;


public class TraversalPathIssue {
    public Path copyWorkingBlobFileToOriginal(String storage, String container, String fileName) {

        Path sourceFile = SecurityUtils.validateAndBuildPath(
                storage,
                container,
                fileName
        );
        Path targetFile = Path.of(SecurityUtils.validateStorage(storage),
                SecurityUtils.validateContainer(container),
                SecurityUtils.validateStorageFolder(storage),
                SecurityUtils.sanitizeFileName(fileName));
        return sourceFile;
    }
}
