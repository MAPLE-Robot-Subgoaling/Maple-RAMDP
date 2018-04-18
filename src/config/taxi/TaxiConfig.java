package config.taxi;

import burlap.debugtools.RandomFactory;
import config.output.OutputConfig;
import config.planning.PlanningConfig;
import config.rmax.RmaxConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import taxi.state.TaxiState;
import taxi.stateGenerator.TaxiStateFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class TaxiConfig {
    public String state;
    public List<String> agents;
    public int episodes;
    public int max_steps;
    public int trials;
    public double gamma;

    public StochasticTaxiConfig stochastic;
    public PlanningConfig planning;
    public RmaxConfig rmax;
    public OutputConfig output;

    public static TaxiConfig load(String conffile) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(TaxiConfig.class));
        InputStream input = new FileInputStream(new File(conffile));

        TaxiConfig config = (TaxiConfig) yaml.load(input);

        long seed = config.stochastic.seed;
        if (seed == 0) {
            seed = System.nanoTime();
            System.err.println("Warning: using a randomly generated RNG seed: " + seed);
        }
        RandomFactory.seedMapped(0, seed);
        System.out.println("Using seed: " + config.stochastic.seed);

        return config;
    }

    public TaxiState generateState() {
        switch (state) {
            case "classic":
                return TaxiStateFactory.createClassicState();
            case "classic-2passengers":
                return TaxiStateFactory.createClassicState(2);
            case "tiny":
                return TaxiStateFactory.createTinyState();
            case "small":
                return TaxiStateFactory.createSmallState();
            case "small-2passengers":
                return TaxiStateFactory.createSmallState(2);
            case "medium":
                return TaxiStateFactory.createMediumState();
            case "medium-2passengers":
                return TaxiStateFactory.createMediumState(2);
            case "3depots":
                return TaxiStateFactory.createThreeDepots();
            case "3depots-2passengers":
                return TaxiStateFactory.createThreeDepots(2);
            default:
                throw new RuntimeException("ERROR: invalid state passed to generateState in TaxiConfig: " + state);
        }
    }
}
