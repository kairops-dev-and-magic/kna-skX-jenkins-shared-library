package online.kairops.kna.skX

class StepExecutor implements IStepExecutor {
    private _steps

    StepExecutor(steps) {
        this._steps = steps
    }

    @Override
    int mvn(String goal) {
        this._steps.sh returnStatus: true, script: "mvn clean ${goal}"
    }

    @Override
    int docker(String command) {
        this._steps.sh returnStatus: true, script: "${command}"
    }

    @Override
    void error(String message) {
        this._steps.error(message)
    }
}
