package online.kairops.kna.skX.pipelines;

import online.kairops.kna.skX.IStepExecutor;
import online.kairops.kna.skX.ioc.ContextRegistry;
import online.kairops.kna.skX.ioc.IContext;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Pipeline test class
 */
public class CommonPipelineTest {

    private IContext _context;
    private IStepExecutor _steps;
    private IPipeline _pipeline;
    private Map<String, String> _sonarConfig;
    private final String SONAR_FAKE_PATH = ".";


    @Before
    public void setup() {
        _context = mock(IContext.class);
        _steps = mock(IStepExecutor.class);

        when(_context.getStepExecutor()).thenReturn(_steps);

        ContextRegistry.registerContext(_context);

        _pipeline = new CommonPipeline();

        _sonarConfig = new HashMap<String, String>() {{
            put(CommonPipeline.CODE_ANALYSIS_TYPE_KEY, CommonPipeline.CodeAnalysisTypes.SONAR.getType());
            put(CommonPipeline.CODE_ANALYSIS_SONAR_PATH_KEY, SONAR_FAKE_PATH);
        }};

    }


    @Test
    public void codeAnalisys_callsShStep() {

        // prepare
        Map<String,String> config = new HashMap<>();

        // execute
        _pipeline.codeAnalysis(_sonarConfig);

        // verify
        verify(_steps).sh(SONAR_FAKE_PATH + CommonPipeline.CODE_ANALYSIS_SONAR_COMMAND);
    }

    @Test
    public void codeAnalisys_ShStepReturnsStatusNotEqualsZero_callsErrorStep() {

        // prepare
        Map<String,String> emptyConfig = new HashMap<>();

        // execute
        _pipeline.codeAnalysis(emptyConfig);

        // verify
        verify(_steps).error(anyString());
    }
}
