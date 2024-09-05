class EtlReaderServiceTest extends Specification {

    def etlReaderService = new EtlReaderService() // Możesz użyć Mock() lub Spy() w zależności od tego, jak bardzo chcesz symulować interakcje
    def app = Mock(AppClass) // Załóżmy, że masz obiekt app, który musisz zmockować
    def checksumService = Mock(ChecksumService) // Twój serwis do obliczania sumy kontrolnej
    def etlProcessService = Mock(EtlProcessService) // Serwis do aktualizacji statusów

    def setup() {
        etlReaderService.app = app
        etlReaderService.checksumService = checksumService
        etlReaderService.etlProcessService = etlProcessService
    }

    def "should correctly process file and return data"() {
        given:
        def feedFileMock = Mock(FeedFile)
        def filePatterns = ["*.csv", "*.txt"]
        def controlId = 12345
        def mockData = ["row1", "row2", "row3"] // przykładowe dane, które mają zostać przetworzone
        def expectedChecksum = 987654321L

        app.getFeedFile() >> feedFileMock
        feedFileMock.getPatterns() >> filePatterns
        etlReaderService.readFile(_, _, _) >> mockData
        checksumService.calculateChecksum(mockData) >> expectedChecksum

        when:
        def result = etlReaderService.getDataFromFile(Mock(Class))

        then:
        result == mockData
        1 * checksumService.calculateChecksum(mockData)
        1 * etlProcessService.updateEtlProcessControl(_, _, _, _, mockData.size(), expectedChecksum, _)
    }

    def "should handle empty data case"() {
        given:
        def feedFileMock = Mock(FeedFile)
        def filePatterns = ["*.csv", "*.txt"]
        def controlId = 12345
        def mockData = [] // Pusta lista danych

        app.getFeedFile() >> feedFileMock
        feedFileMock.getPatterns() >> filePatterns
        etlReaderService.readFile(_, _, _) >> mockData

        when:
        def result = etlReaderService.getDataFromFile(Mock(Class))

        then:
        result == null
        0 * checksumService.calculateChecksum(_)
        1 * etlProcessService.updateEtlProcessControl(_, _, _, _, 0, _, _) // Sprawdzamy, że liczba danych wynosi 0
    }

    def "should handle exceptions during file processing"() {
        given:
        def feedFileMock = Mock(FeedFile)
        def filePatterns = ["*.csv", "*.txt"]
        def controlId = 12345

        app.getFeedFile() >> feedFileMock
        feedFileMock.getPatterns() >> filePatterns
        etlReaderService.readFile(_, _, _) >> { throw new Exception("Error during file processing") }

        when:
        def result = etlReaderService.getDataFromFile(Mock(Class))

        then:
        result == null
        1 * etlProcessService.updateEtlProcessControl(_, _, _, _, _, _, _) // Sprawdzamy, że metoda aktualizacji statusu została wywołana po wystąpieniu wyjątku
    }
}