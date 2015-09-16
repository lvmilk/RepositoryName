package EntityHandler;

import Entity.Aircraft_;
import Entity.AircraftType_;
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
    Aircraft_ aircraft;

    @Override
    public void addAircraftType(AircraftType_ at){

    }

    @Override
    public boolean deleteAircraftType(AircraftType_ at){
        return true;
    }

    @Override
    public ArrayList<AircraftType_> findAircraftType(String type){
        return null;
    }

    @Override
    public boolean hasAircraftType(String type){
        return false;
    }

    @Override
    public void addAircraft(Aircraft_ af){
        
    }

    @Override
    public boolean deleteAircraft(Aircraft_ af){
        return false;
    }

    @Override
    public ArrayList<AircraftType_> findAircraft(String registrationNumber){
        return null;
    }

    @Override
    public boolean hasAircraft(String registrationNumber){
        return false;
    }

    @Override
    public void updateAircraftType(AircraftType_ at) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AircraftType_ findAircraftType(long id) throws MASException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AircraftType_> listAircraftType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateAircraft(Aircraft_ af) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Aircraft_ findAircraft(long id) throws MASException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AircraftType_> listAircraft() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
