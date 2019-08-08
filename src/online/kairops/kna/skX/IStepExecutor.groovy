package online.kairops.kna.skX

/**
 * Interface for calling any necessary Jenkins steps. This will be mocked in unit tests.
 */
interface IStepExecutor {

    int sh(String command)

    int mvn(String goal)

    int docker(String command)

    void error(String message)

    int sonarScanner(String sonarScannerIntallationPath)
}
