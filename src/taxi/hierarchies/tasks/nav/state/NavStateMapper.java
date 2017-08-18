package taxi.hierarchies.tasks.nav.state;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.auxiliary.StateMapping;
import burlap.mdp.core.state.State;
import taxi.hierarchies.tasks.nav.TaxiNavDomain;

public class NavStateMapper implements StateMapping {

	@Override
	public State mapState(State s) {
		TaxiNavState st = (TaxiNavState) s;
		
		int tx = (int) st.getTaxiAtt(TaxiNavDomain.ATT_X);
		int ty = (int) st.getTaxiAtt(TaxiNavDomain.ATT_Y);
		TaxiNavAgent taxi = new TaxiNavAgent(TaxiNavDomain.CLASS_TAXI, tx, ty);
		
		List<TaxiNavLocation> locations = new ArrayList<>();
		for(String locName : st.getLocations()){
			int lx = (int) st.getLocationAtt(locName, TaxiNavDomain.ATT_X);
			int ly = (int) st.getLocationAtt(locName, TaxiNavDomain.ATT_Y);
			locations.add(new TaxiNavLocation(locName, lx, ly));
		}
		List<TaxiNavWall> walls = new ArrayList<>();
		for(String wallName : st.getWalls()){
			int startX = (int) st.getWallAtt(wallName, TaxiNavDomain.ATT_START_X);
			int startY = (int) st.getWallAtt(wallName, TaxiNavDomain.ATT_START_Y);
			int length = (int) st.getWallAtt(wallName, TaxiNavDomain.ATT_LENGTH);
			boolean isHorizontal = (boolean) st.getWallAtt(wallName, TaxiNavDomain.ATT_IS_HORIZONTAL);
			walls.add(new TaxiNavWall(wallName, startX, startY, length, isHorizontal));
		}
		
		return new TaxiNavState(taxi, locations, walls);
	}
}

