package SessionBean;

import Entity.Flight;
import Entity.GenericFlight;
import Entity.Aircraft;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FlightSessionBean implements FlightSessionBeanLocal {
	@PersistenceContext()
	EntityManager em;
	GenericFlight genericFlight;
                    Flight flight;
	Aircraft aircraft;

	public FlightSessionBean(){

	}

	@Override
	public void addFlight(String registrationNo,String flightStatus, String date, Integer flightNo, Integer stopoverSNo) throws Exception{
		aircraft=em.find(Aircraft.class,registrationNo);
		if(aircraft==null){
			throw new Exception("Aircraft does not exist");
		}
		genericFlight=aircraft.findFlight(flightNo);
		if(genericFlight!=null){
			throw new Exception("Flgiht exists");
		}
		genericFlight=new GenericFlight();
		genericFlight.create(flightNo,stopoverSNo);
                                        flight=new Flight();
                                        flight.create(flightStatus,date);
		flight.setGenericFlight(genericFlight);
		aircraft.getFlight().add(flight);
		aircraft.setFlight(aircraft.getFlight());
		em.persist(flight);
	}

//	@Override
//	public void editFlight(){
//	}
	
                    @Override
                    public void deleteFlight(String date, Integer flightNo, String registrationNo) throws Exception{
                                         aircraft = em.find(Aircraft.class, registrationNo);
                                         if (aircraft == null) {
                                             throw new Exception("Aircraft does not exist");
                                         }
                                         genericFlight = aircraft.findFlight(flightNo);
                                         if (genericFlight == null) {
                                             throw new Exception("Generic Flight does not exist");
                                         }
                                         flight=em.find(Flight.class, flightNo);
                                         if (flight == null) {
                                             throw new Exception("Flight does not exist");
                                         }
                                         
                                         aircraft.getFlight().remove(aircraft);
                                         aircraft.setFlight(aircraft.getFlight());
                                         em.remove(flight);
                                         em.flush();
                 }

	@Override
	public Collection<Flight> getFlightList(){
		Query q=em.createQuery("SELECT F FROM FLIGHT F");
		Collection flights=new ArrayList();
		for(Object o:q.getResultList()){
			Flight F=(Flight) o;
			flights.add(F);
		}
                     return flights;

}
}