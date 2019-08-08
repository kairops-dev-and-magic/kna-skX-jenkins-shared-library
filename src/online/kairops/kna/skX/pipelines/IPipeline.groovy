package online.kairops.kna.skX.pipelines

/**
 * Interface for calling any necessary Jenkins steps. This will be mocked in unit tests.
 */
interface IPipeline {

    // Static Code analysis including check vulnerabilities
    int codeAnalysis(Map<String,String> analysis)

    // Compile, Unit Test and package
    int build()

    // Artifactory / Harbor / Chart Museum
    int publish(String registryType, String credentials)

    // DEV / TEST / COMPLIANCE-UAT
    int deploy(String environment)

    // SMOKE / INTEGRATION / COMPLIANCE
    int test(String type)

}
