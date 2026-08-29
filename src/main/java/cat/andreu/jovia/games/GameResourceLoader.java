package cat.andreu.jovia.games;

import io.github.agarriga18696.andreuutils.core.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Loads resources stored inside a Jovia game package.
 *
 * @author Andreu
 * @version 1.0
 */
public final class GameResourceLoader {

    /**
     * Loads a resource from a game package.
     *
     * @param gamePackage Game package.
     * @param path        Resource path inside the package.
     * @param maxSize     Maximum allowed resource size in bytes.
     * @return Resource contents.
     * @throws GamePackageException If the resource cannot be loaded.
     */
    public byte[] load(
            GamePackage gamePackage,
            String path,
            int maxSize
    ) throws GamePackageException {

        if (gamePackage == null) {
            throw new IllegalArgumentException(
                    "Game package cannot be null."
            );
        }

        if (StringUtils.isNullOrBlank(path)) {
            throw new IllegalArgumentException(
                    "Resource path cannot be blank."
            );
        }

        if (maxSize <= 0) {
            throw new IllegalArgumentException(
                    "Maximum resource size must be greater than zero."
            );
        }

        try (
                ZipFile zipFile =
                        new ZipFile(
                                gamePackage
                                        .source()
                                        .toFile()
                        )
        ) {
            ZipEntry entry =
                    zipFile.getEntry(
                            path
                    );

            if (entry == null || entry.isDirectory()) {

                throw new GamePackageException(
                        "Resource was not found in the game package: "
                                + path
                );
            }

            byte[] data;

            try (
                    InputStream inputStream =
                            zipFile.getInputStream(
                                    entry
                            )
            ) {
                data =
                        inputStream.readNBytes(
                                maxSize + 1
                        );
            }

            if (data.length > maxSize) {
                throw new GamePackageException(
                        "Game resource is too large: "
                                + path
                );
            }

            return data;

        } catch (GamePackageException exception) {
            throw exception;

        } catch (IOException exception) {
            throw new GamePackageException(
                    "Could not read game resource: "
                            + path,
                    exception
            );
        }
    }
}