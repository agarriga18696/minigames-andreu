package cat.andreu.jovia.games;

import io.github.agarriga18696.andreuutils.core.StringUtils;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Loads and validates Jovia game packages.
 *
 * @author Andreu
 * @version 1.0
 */
public final class GamePackageLoader {

    // ----------------------------------------
    // CONSTANTS
    // ----------------------------------------

    private static final String PACKAGE_EXTENSION = ".jovia";

    private static final String MANIFEST_PATH = "manifest.json";

    private static final int SUPPORTED_FORMAT_VERSION = 1;

    private static final int MAX_MANIFEST_SIZE = 64 * 1024;

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final ObjectMapper objectMapper;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates a game package loader.
     */
    public GamePackageLoader() {
        objectMapper =
                new ObjectMapper();
    }

    // ----------------------------------------
    // LOADING
    // ----------------------------------------

    /**
     * Loads a Jovia game package.
     *
     * @param source Path of the Jovia package.
     * @return Loaded and validated game package.
     * @throws GamePackageException If the package cannot be loaded
     *                              or is invalid.
     */
    public GamePackage load(
            Path source
    ) throws GamePackageException {

        validateSource(source);

        Path normalizedSource =
                source
                        .toAbsolutePath()
                        .normalize();

        try (
                ZipFile zipFile =
                        new ZipFile(
                                normalizedSource.toFile()
                        )
        ) {
            GameManifest manifest =
                    loadManifest(
                            zipFile
                    );

            validateManifest(
                    manifest
            );

            validateRequiredEntry(
                    zipFile,
                    manifest.icon(),
                    "Game icon"
            );

            validateRequiredEntry(
                    zipFile,
                    manifest.game(),
                    "Game definition"
            );

            try {
                return new GamePackage(
                        manifest.id(),
                        manifest.type(),
                        manifest.name(),
                        manifest.icon(),
                        manifest.game(),
                        normalizedSource
                );
            } catch (IllegalArgumentException exception) {
                throw new GamePackageException(
                        "Invalid game package metadata.",
                        exception
                );
            }

        } catch (GamePackageException exception) {
            throw exception;

        } catch (IOException exception) {
            throw new GamePackageException(
                    "Could not read Jovia game package.",
                    exception
            );
        }
    }

    // ----------------------------------------
    // SOURCE VALIDATION
    // ----------------------------------------

    private void validateSource(
            Path source
    ) throws GamePackageException {

        if (source == null) {
            throw new GamePackageException(
                    "Game package path cannot be null."
            );
        }

        if (!Files.isRegularFile(source)) {
            throw new GamePackageException(
                    "Game package does not exist or is not a file."
            );
        }

        String fileName =
                source
                        .getFileName()
                        .toString()
                        .toLowerCase(
                                Locale.ROOT
                        );

        if (!fileName.endsWith(PACKAGE_EXTENSION)) {
            throw new GamePackageException(
                    "File is not a Jovia game package."
            );
        }
    }

    // ----------------------------------------
    // MANIFEST
    // ----------------------------------------

    private GameManifest loadManifest(
            ZipFile zipFile
    ) throws GamePackageException,
            IOException {

        ZipEntry manifestEntry =
                zipFile.getEntry(
                        MANIFEST_PATH
                );

        if (manifestEntry == null || manifestEntry.isDirectory()) {
            throw new GamePackageException(
                    "Game package does not contain manifest.json."
            );
        }

        byte[] manifestData;

        try (
                InputStream inputStream =
                        zipFile.getInputStream(
                                manifestEntry
                        )
        ) {
            manifestData = inputStream.readNBytes(
                    MAX_MANIFEST_SIZE + 1
            );
        }

        if (manifestData.length > MAX_MANIFEST_SIZE) {
            throw new GamePackageException(
                    "Game manifest is too large."
            );
        }

        try {
            return objectMapper.readValue(
                    manifestData,
                    GameManifest.class
            );
        } catch (JacksonException exception) {
            throw new GamePackageException(
                    "Invalid manifest.json.",
                    exception
            );
        }
    }

    private void validateManifest(
            GameManifest manifest
    ) throws GamePackageException {

        if (manifest == null) {
            throw new GamePackageException(
                    "Game manifest cannot be empty."
            );
        }

        if (manifest.formatVersion()
                != SUPPORTED_FORMAT_VERSION) {

            throw new GamePackageException(
                    "Unsupported Jovia package format version: "
                            + manifest.formatVersion()
            );
        }

        validatePackagePath(
                manifest.icon(),
                "Game icon path"
        );

        validatePackagePath(
                manifest.game(),
                "Game definition path"
        );
    }

    // ----------------------------------------
    // PACKAGE CONTENT
    // ----------------------------------------

    private void validateRequiredEntry(
            ZipFile zipFile,
            String path,
            String description
    ) throws GamePackageException {

        ZipEntry entry =
                zipFile.getEntry(path);

        if (entry == null
                || entry.isDirectory()) {

            throw new GamePackageException(
                    description
                            + " was not found in the package: "
                            + path
            );
        }
    }

    private void validatePackagePath(
            String path,
            String description
    ) throws GamePackageException {

        if (StringUtils.isNullOrBlank(path)) {
            throw new GamePackageException(
                    description
                            + " cannot be blank."
            );
        }

        if (path.startsWith("/")
                || path.startsWith("\\")
                || path.contains("\\")
                || path.contains("../")
                || path.contains("/..")
                || path.equals("..")) {

            throw new GamePackageException(
                    description
                            + " is invalid: "
                            + path
            );
        }
    }
}