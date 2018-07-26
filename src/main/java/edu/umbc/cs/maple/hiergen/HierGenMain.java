package edu.umbc.cs.maple.hiergen;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.debugtools.DPrint;
import burlap.debugtools.RandomFactory;
import burlap.mdp.core.oo.state.OOVariableKey;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.model.FullModel;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import edu.umbc.cs.maple.hiergen.CAT.*;
import edu.umbc.cs.maple.state.hashing.bugfix.BugfixHashableStateFactory;
import edu.umbc.cs.maple.taxi.Taxi;
import edu.umbc.cs.maple.taxi.TaxiVisualizer;
import edu.umbc.cs.maple.taxi.stategenerator.HierGenTrajectorySource;
import edu.umbc.cs.maple.utilities.BurlapConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static edu.umbc.cs.maple.hiergen.CATScan.DEBUG_CODE_CATSCAN;

public class HierGenMain {

    public static final String DIRECTORY_PATH_HIERGEN_OUTPUT = "./output_hiergen/";
    public static final String DIRECTORY_PATH_TRAJECTORY = "trajectories/";
    public static final String DIRECTORY_PATH_ACTION_MODEL_TREES = "trees/";
    public static final String DIRECTORY_PATH_CATS = "cats/";
    public static final String DIRECTORY_PATH_ARFF = "arff/";
    public static final String FILE_PREFIX_TRAJECTORY = "trajectory";
    public static final String FILE_PREFIX_CAT = "cat";
    public static final String FILE_ARFF = "data.arff";


    public static void generateTrajectories(long seed, String stateType, String pathToTrajectories, int trajectoryCount) {

        HashableStateFactory hsf = new BugfixHashableStateFactory(false);

        boolean differentSeedsEachTime = true;
        HierGenTrajectorySource trajectorySource = new HierGenTrajectorySource(seed, stateType, differentSeedsEachTime);

        Taxi test = new Taxi();
        OOSADomain domain = test.generateDomain();

        System.out.println("Generating trajectories");
        double gamma = 0.95;
        List<Episode> episodes = TrajectoryGenerator.generateQLearnedTrajectories(trajectorySource, trajectoryCount, domain, gamma, hsf);

        Episode.writeEpisodes(episodes, pathToTrajectories, FILE_PREFIX_TRAJECTORY);

    }

    public static void main(String[] args) {

        String pathToTrajectories = DIRECTORY_PATH_HIERGEN_OUTPUT + DIRECTORY_PATH_TRAJECTORY;
        String pathToTrees = DIRECTORY_PATH_HIERGEN_OUTPUT + DIRECTORY_PATH_ACTION_MODEL_TREES;
        String pathToARFF = DIRECTORY_PATH_HIERGEN_OUTPUT + DIRECTORY_PATH_ARFF + FILE_ARFF;
        String pathToCATs = DIRECTORY_PATH_HIERGEN_OUTPUT + DIRECTORY_PATH_CATS;

        DPrint.toggleCode(DEBUG_CODE_CATSCAN, true);

        int trajectoryCount = 50;

        long seed = 7777777L;//20948304976L;
//        String stateType = "small-3passengers";
//        String stateType = "medium-3passengers";
        String stateType = "random-4d-2p";

        System.out.println("Running HierGen algorithm on seed: " + seed);

        RandomFactory.getMapped(BurlapConstants.DEFAULT_RNG_INDEX).setSeed(seed);

//        generateTrajectories(seed, stateType, pathToTrajectories, trajectoryCount);

//        List<Episode> trajectories = Episode.readEpisodes(pathToTrajectories);
//
//        Episode one = trajectories.get(0);
//        System.out.println(one.stateSequence.get(one.stateSequence.size()-1));
//        System.out.println(one.rewardSequence.get(one.rewardSequence.size()-1));
//        System.out.println(one.actionSequence.get(one.actionSequence.size()-1));
//
//        EpisodeSequenceVisualizer v = new EpisodeSequenceVisualizer(TaxiVisualizer.getVisualizer(5, 5), new Taxi().generateDomain(), trajectories);
//        v.setDefaultCloseOperation(v.EXIT_ON_CLOSE);
//        v.initGUI();
//
//        System.out.println("Learning the action models");
//        CreateActionModels.createModels(pathToTrees, pathToARFF, trajectories);
//
//        Map<String, Map<String, VariableTree>> actionModels = CreateActionModels.readTreeFiles(pathToTrees);
//
//        SADomain domain = new Taxi().generateDomain();
////        domain.setModel(null); // for Causal Annotation, we assume access to the model
//        FullModel model = (FullModel) domain.getModel();
//
//        for (int i = 0; i < trajectories.size(); i++) {
//            Episode trajectory = trajectories.get(i);
//            System.out.println("Causally annotating the trajectory " + (i+1) + " / " + trajectories.size());
//            CATrajectory cat = new CATrajectory();
//            cat.annotateTrajectory(trajectory, actionModels, model);
//            String filename = pathToCATs + FILE_PREFIX_CAT + "_" + i;
//            cat.write(filename);
//        }

//        int count = trajectoryCount;
        int count = 3;
        ArrayList<CATrajectory> cats = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String filename = pathToCATs + FILE_PREFIX_CAT + "_" + i;
            CATrajectory cat = CATrajectory.read(filename);
            cats.add(cat);
        }

        HierBuilder.start(cats);

//        System.out.println("Running the main HierGenAlgorithm");
//        HierGenTask root = HierGenAlgorithm.generate(actionModels, cats);
//        System.out.println(root);

    }
}
