class EtlReaderServiceTest extends Specification {

    EtlReaderService etlReaderService = Spy(EtlReaderService) // Użycie Spy z konkretnym typem
    AppClass app = Mock(AppClass) // Jasno określony typ Mocka
    ChecksumService checksumService = Mock(ChecksumService)
    EtlProcessService etlProcessService = Mock(EtlProcessService)

    def setup() {
        etlReaderService.app = app
        etlReaderService.checksumService = checksumService
        etlReaderService.etlProcessService = etlProcessService
    }

    def "should correctly process file and return data"() {
        given:
        FeedFile feedFileMock = Mock(FeedFile) // Jawnie określony typ mocka
        List<String> filePatterns = ["*.csv", "*.txt"] // Typ listy
        Long expectedChecksum = 987654321L

        app.getFeedFile() >> feedFileMock
        feedFileMock.getPatterns() >> filePatterns
        etlReaderService.readFile(_, _, _) >> ["row1", "row2", "row3"] // Zwraca listę stringów
        checksumService.calculateChecksum(_) >> expectedChecksum

        when:
        def result = etlReaderService.getDataFromFile(Mock(Class))

        then:
        result == ["row1", "row2", "row3"]
        1 * checksumService.calculateChecksum(_)
        1 * etlProcessService.updateEtlProcessControl(_, _, _, _, 3, expectedChecksum, _)
    }
}