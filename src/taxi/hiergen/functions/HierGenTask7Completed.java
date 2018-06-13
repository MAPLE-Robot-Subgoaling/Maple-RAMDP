package taxi.hiergen.functions;

import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import taxi.hiergen.task7.state.TaxiHierGenTask7Passenger;
import taxi.hiergen.TaxiHierGenState;

import static taxi.TaxiConstants.ATT_IN_TAXI;
import static taxi.TaxiConstants.PF_TASK_7;

public class HierGenTask7Completed extends PropositionalFunction {

    public HierGenTask7Completed(){
        super(PF_TASK_7, new String[]{});
    }

    @Override
    public boolean isTrue(OOState s, String... params) {
        TaxiHierGenState st = (TaxiHierGenState) s;
        for(String pname : params){
            TaxiHierGenTask7Passenger passenger = (TaxiHierGenTask7Passenger) st.object(pname);
            if (passenger == null) {
                // assume that if the passenger does not exist, we are in the imagined state, always terminal
                return true;
            }
            boolean inTaxi = (boolean) passenger.get(ATT_IN_TAXI);
            if(inTaxi)
                return true;
        }
        return false;
//        throw new RuntimeException("this is wrong, need to reimplement");
    }
}