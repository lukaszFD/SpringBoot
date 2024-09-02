public class ArchiveReader {
    public byte[] readFirstZipFile(Path zipFile) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    Path resolvedPath = Path.of("output_directory").resolve(entry.getName()).normalize();
                    if (!resolvedPath.startsWith(Path.of("output_directory"))) {
                        throw new IOException("Potential Zip Slip attack detected: " + entry.getName());
                    }
                    return zipInputStream.readAllBytes();
                }
            }
        }
        return null;
    }
}

public class ArchiveReader {
    private static final long MAX_UNCOMPRESSED_SIZE = 100 * 1024 * 1024; // 100 MB limit
    
    public byte[] readFirstZipFile(Path zipFile) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry entry;
            long uncompressedSize = 0;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    
                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        uncompressedSize += bytesRead;
                        if (uncompressedSize > MAX_UNCOMPRESSED_SIZE) {
                            throw new IOException("Uncompressed data size exceeds the limit, potential Zip Bomb attack.");
                        }
                    }
                    return outputStream.toByteArray();
                }
            }
        }
        return null;
    }
}