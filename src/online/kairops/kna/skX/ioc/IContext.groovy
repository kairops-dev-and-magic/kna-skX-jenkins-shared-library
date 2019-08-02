package online.kairops.kna.skX.ioc

import online.kairops.kna.skX.IStepExecutor

interface IContext {
    IStepExecutor getStepExecutor()
}
