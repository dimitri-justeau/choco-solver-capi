package org.chocosolver.capi;

import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.objects.graphs.MultivaluedDecisionDiagram;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.ObjectHandle;
import org.graalvm.nativeimage.ObjectHandles;
import org.graalvm.nativeimage.c.function.CEntryPoint;

/**
 * C entrypoint API to manipulate Java Choco MDD objects.
 * @author Dimitri Justeau-Allaire.
 */
public class MultivaluedDecisionDiagramApi {

    private static final String API_PREFIX = "MDDApi_";

    private static ObjectHandles globalHandles = ObjectHandles.getGlobal();

    // Constructors

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "create_mdd_tuples")
    public static ObjectHandle createMDDTuples(IsolateThread thread, ObjectHandle intVarsHandle,
                                               ObjectHandle tuplesHandle, boolean compactOnce, boolean sortTuple) {
        IntVar[] intVars = globalHandles.get(intVarsHandle);
        int[][] tuples = globalHandles.get(tuplesHandle);
	Tuples tuplesObject = new Tuples(tuples, true);
        MultivaluedDecisionDiagram mdd = new MultivaluedDecisionDiagram(intVars, tuplesObject, compactOnce, sortTuple);
        ObjectHandle res = globalHandles.create(mdd);
        return res;
    }

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "create_mdd_transitions")
    public static ObjectHandle createMDDTransitions(IsolateThread thread, ObjectHandle intVarsHandle,
                                                    ObjectHandle transitionsHandle) {
        IntVar[] intVars = globalHandles.get(intVarsHandle);
        int[][] transitions = globalHandles.get(transitionsHandle);
        MultivaluedDecisionDiagram mdd = new MultivaluedDecisionDiagram(intVars, transitions);
        ObjectHandle res = globalHandles.create(mdd);
        return res;
    }

    // Methods

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "get_diagram")
    public static ObjectHandle getDiagram(IsolateThread thread, ObjectHandle mddHandle) {
        MultivaluedDecisionDiagram mdd = globalHandles.get(mddHandle);
        int[] diagram = mdd.getDiagram();
        ObjectHandle res = globalHandles.create(diagram);
        return res;
    }

}
