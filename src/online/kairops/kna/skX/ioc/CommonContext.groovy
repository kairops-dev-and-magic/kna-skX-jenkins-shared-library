package online.kairops.kna.skX.ioc

import online.kairops.kna.skX.IStepExecutor
import online.kairops.kna.skX.StepExecutor

class CommonContext implements IContext, Serializable {
    private _steps

    CommonContext(steps) {
        this._steps = steps
    }

    @Override
    IStepExecutor getStepExecutor() {
        return new StepExecutor(this._steps)
    }
}
