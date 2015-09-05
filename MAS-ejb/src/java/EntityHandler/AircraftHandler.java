package EntityHandler;

import Entity.Aircraft;
import Entity.AircraftType;
import Exception.MASException;
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
        return true;
    }

    @Override
    public ArrayList<AircraftType> findAircraftType(String type){
        return null;
    }

    @Override
    public boolean hasAircraftType(String type){
        return false;
    }

    @Override
    public void addAircraft(Aircraft af){
        
    }

    @Override
    public boolean deleteAircraft(Aircraft af){
        return false;
    }

    @Override
    public ArrayList<AircraftType> findAircraft(String registrationNumber){
        return null;
    }

    @Override
    public boolean hasAircraft(String registrationNumber){
        return false;
    }

    @Override
    public void updateAircraftType(AircraftType at) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AircraftType findAircraftType(long id) throws MASException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AircraftType> listAircraftType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateAircraft(Aircraft af) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Aircraft findAircraft(long id) throws MASException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AircraftType> listAircraft() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
