package online.kairops.kna.skX.pipelines;

import online.kairops.kna.skX.ioc.ContextRegistry;
import online.kairops.kna.skX.ioc.IContext;
import online.kairops.kna.skX.IStepExecutor;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Pipeline test class
 */
public class MvnPipelineTest {
    private IContext _context;
    private IStepExecutor _steps;
    private IPipeline _pipeline;

    @Before
    public void setup() {
        _context = mock(IContext.class);
        _steps = mock(IStepExecutor.class);

        when(_context.getStepExecutor()).thenReturn(_steps);

        ContextRegistry.registerContext(_context);

        _pipeline = new MvnPipeline();
    }

    @Test
    public void build_callsMvnStep() {
        // execute
        _pipeline.build();

        // verify
        verify(_steps).mvn(MvnPipeline.BUILD_GOAL);
    }

    @Test
    public void build_MvnStepReturnsStatusNotEqualsZero_callsErrorStep() {

        // prepare
        when(_steps.mvn(MvnPipeline.BUILD_GOAL)).thenReturn(-1);

        // execute
        _pipeline.build();

        // verify
        verify(_steps).error(anyString());
    }

    @Test
    public void qa_callsMvnStep() {
        // execute
        _pipeline.qa();

        // verify
        verify(_steps).mvn(MvnPipeline.QA_GOAL);
    }

    @Test
    public void qa_MvnStepReturnsStatusNotEqualsZero_callsErrorStep() {

        // prepare
        when(_steps.mvn(MvnPipeline.QA_GOAL)).thenReturn(-1);

        // execute
        _pipeline.qa();

        // verify
        verify(_steps).error(anyString());
    }
}
