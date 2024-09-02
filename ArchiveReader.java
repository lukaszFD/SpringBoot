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