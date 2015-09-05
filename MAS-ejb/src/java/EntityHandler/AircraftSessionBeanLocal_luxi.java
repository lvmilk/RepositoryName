package SessionBean;

import Entity.Aircraft;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface AircraftSessionBeanLocal {

    public void addAircraft(String registrationNo, String SerialNo, String Status, String AirCraftType) throws Exception;

    public void removeAircraft(String registrationNo) throws Exception;

    public Collection<Aircraft> getAircraftList();

    public void editAircraft(String registrationNo, String SerialNo, String AirCraftType) throws Exception;
    
}
