import java.nio.file.Path;


public class TraversalPathIssue {
    public void copyWorkingBlobFileToOriginal(String storage, String container, String fileName) {

        Path sourceFile = SecurityUtils.validateAndBuildPath(
                SecurityUtils.validateStorage(storage),
                SecurityUtils.validateContainer(container),
                SecurityUtils.sanitizeFileName(fileName)
        );
        Path targetFile = Path.of(SecurityUtils.validateStorage(storage),
                SecurityUtils.validateContainer(container),
                SecurityUtils.validateStorageFolder(storage),
                SecurityUtils.sanitizeFileName(fileName));
    }

}
