package SessionBean;

import Entity.Aircraft;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AircraftSessionBean implements AircraftSessionBeanLocal {
	@PersistenceContext()
	EntityManager em;
	Aircraft aircraft;

	public AircraftSessionBean(){
	}

	@Override
	public void addAircraft(String registrationNo, String SerialNo, String Status,  String AirCraftType)throws Exception{
		aircraft=em.find(Aircraft.class,registrationNo);
		if(aircraft!=null){
			throw new Exception("Aircraft exists");
		}
		aircraft=new Aircraft();
		aircraft.create(registrationNo,SerialNo,Status,AirCraftType); ///new
		em.persist(aircraft);
	}

	@Override
	public void editAircraft(String registrationNo, String serialNo, String aircraftType)throws Exception{
		aircraft=em.find(Aircraft.class,registrationNo);
		if(aircraft==null){
			throw new Exception("Aircraft does not exist");
		}
		aircraft.setSerialNo(serialNo);
                                        aircraft.setAircraftType(aircraftType);
		em.flush();		
	}

	@Override
	public void removeAircraft(String registrationNo) throws Exception{
		aircraft=em.find(Aircraft.class,registrationNo);
		if(aircraft==null){
			throw new Exception("Aircraft does not exist");
		}
		em.remove(aircraft);
		em.flush();
	}

	@Override
	public Collection<Aircraft> getAircraftList(){
		Query q=em.createQuery("SELECT A FROM AIRCRAFT A");
		Collection aircrafts=new ArrayList();
		for(Object o:q.getResultList()){
			Aircraft A=(Aircraft) o;
			aircrafts.add(A);
		}
                    return aircrafts;
	}
}
