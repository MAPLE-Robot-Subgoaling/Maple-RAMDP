package taxi.abstraction1.state;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.oo.state.ObjectInstance;
import taxi.abstraction1.TaxiL1;
import utilities.MutableObject;

public class TaxiL1Passenger extends MutableObject {

	private final static List<Object> keys = Arrays.<Object>asList(
			TaxiL1.ATT_CURRENT_LOCATION,
			TaxiL1.ATT_IN_TAXI,
			TaxiL1.ATT_GOAL_LOCATION,
			TaxiL1.ATT_PICKED_UP_AT_LEAST_ONCE
			);
	
	public TaxiL1Passenger(String name, String currentLocation, String goalLocation) {
		this(name, (Object) currentLocation, (Object) goalLocation, false, false);
	}
	
	public TaxiL1Passenger(String name, String currentLocation, String goalLocation, boolean inTaxi){
		this(name, (Object) currentLocation, (Object) goalLocation, (Object) inTaxi, false);
	}
	
	public TaxiL1Passenger(String name, String currentLocation, String goalLocation, boolean inTaxi, boolean pickep){
		this(name, (Object) currentLocation, (Object) goalLocation, (Object) inTaxi, (Object) pickep);
	}
	
	private TaxiL1Passenger(String name, Object currentLocation, Object goalLocation, Object inTaxi,
			Object pickedUpOnce){
		this.set(TaxiL1.ATT_CURRENT_LOCATION, currentLocation);
		this.set(TaxiL1.ATT_IN_TAXI, inTaxi);
		this.set(TaxiL1.ATT_GOAL_LOCATION, goalLocation);
		this.set(TaxiL1.ATT_PICKED_UP_AT_LEAST_ONCE, pickedUpOnce);
		this.setName(name);
	}
	@Override
	public String className() {
		return TaxiL1.CLASS_L1PASSENGER;
	}

	@Override
	public TaxiL1Passenger copy() {
		return (TaxiL1Passenger) copyWithName(name());
	}

	@Override
	public ObjectInstance copyWithName(String objectName) {
		return new TaxiL1Passenger(
				objectName,
				get(TaxiL1.ATT_CURRENT_LOCATION),
				get(TaxiL1.ATT_GOAL_LOCATION),
				get(TaxiL1.ATT_IN_TAXI),
				get(TaxiL1.ATT_PICKED_UP_AT_LEAST_ONCE)
				);
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}
}