package online.kairops.kna.skX.pipelines;

import online.kairops.kna.skX.IStepExecutor;
import online.kairops.kna.skX.ioc.ContextRegistry;
import online.kairops.kna.skX.ioc.IContext;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Pipeline test class
 */
public class DockerPipelineTest {
    private IStepExecutor _steps;
    private IPipeline _pipeline;

    @Before
    public void setup() {
        IContext _context = mock(IContext.class);
        _steps = mock(IStepExecutor.class);

        when(_context.getStepExecutor()).thenReturn(_steps);

        ContextRegistry.registerContext(_context);

        _pipeline = new DockerPipeline();
    }

    @Test
    public void build_callsDockerStep() {
        // execute
        _pipeline.build();

        // verify
        verify(_steps).docker(DockerPipeline.BUILD_GOAL);
    }

    @Test
    public void build_DockerStepReturnsStatusNotEqualsZero_callsErrorStep() {

        // prepare
        when(_steps.docker(DockerPipeline.BUILD_GOAL)).thenReturn(-1);

        // execute
        _pipeline.build();

        // verify
        verify(_steps).error(anyString());
    }
}