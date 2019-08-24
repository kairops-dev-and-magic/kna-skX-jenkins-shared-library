package online.kairops.kna.skX.pipelines

import online.kairops.kna.skX.IStepExecutor
import online.kairops.kna.skX.ioc.ContextRegistry

/**
 * Example class (without proper implementation) for using the MsBuild tool for building .NET projects.
 */
class MvnPipeline extends AbstractCommonPipeline {

    public final static String DEFAULT_BUILD_GOAL = "package"
    public final static String DEFAULT_CODE_ANALISIS_GOAL = "verify sonar:sonar"
    public final static String DEFAULT_PUBLISH_GOAL = "deploy"
    public final static String DEFAULT_PUBLISH_REGISTRY = "ARTIFACTORY"
    public final static String DEFAULT_DEPLOY_GOAL = "deploy"
    public final static String DEFAULT_DEPLOY_ENVIRONMENT = "DEV"
    public final static String DEFAULT_TEST_GOAL = "test"
    public final static String DEFAULT_TEST_TYPE = "UNIT"

    private String _settingsPath
    private String _pomPath
    private String _profile

    MvnPipeline(String settingsPath, String pomPath, String profile) {
        this._settingsPath = settingsPath
        this._pomPath = pomPath
        this._profile = profile
    }

    @Override
    int build() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.mvn(DEFAULT_BUILD_GOAL + " -P " + _profile )
        if (returnStatus != 0) {
            steps.error("Some error")
        }
        return returnStatus
    }

    @Override
    int publish(String registryType, String credentials) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.mvn(DEFAULT_PUBLISH_GOAL, registryType, credentials)
        if (returnStatus != 0) {
            steps.error("Some error")
        }
        return returnStatus
    }

    @Override
    int deploy(String environment) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.mvn(DEFAULT_DEPLOY_GOAL + " -P " + _profile)
        if (returnStatus != 0) {
            steps.error("Some error")
        }
        return returnStatus
    }

    @Override
    int test(String type) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.mvn(DEFAULT_TEST_GOAL + " -P " + _profile)
        if (returnStatus != 0) {
            steps.error("Some error")
        }
        return returnStatus
    }
}
