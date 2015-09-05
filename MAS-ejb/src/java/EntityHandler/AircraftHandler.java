package EntityHandler;

import Entity.Aircraft;
import Entity.AircraftType;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author victor/lucy
 */
public class AircraftHandler implements AircraftHandlerInterface{
    @PersistenceContext()
    EntityManager em;
    Aircraft aircraft;

    @Override
    public void addAircraftType(AircraftType at){
        
    }

    @Override
    public boolean deleteAircraftType(AircraftType at){
        
    }

    @Override
    public ArrayList<AircraftType> findAircraftType(String type){
        
    }

    @Override
    public boolean hasAircraftType(String type){
        
    }

    @Override
    public void addAircraft(Aircraft af){
        
    }

    @Override
    public boolean deleteAircraft(Aircraft af){
        
    }

    @Override
    public ArrayList<AircraftType> findAircraft(String registrationNumber){
        
    }

    @Override
    public boolean hasAircraft(String registrationNumber){
        
    }
}
