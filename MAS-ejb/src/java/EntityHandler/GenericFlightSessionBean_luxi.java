package SessionBean;

import Entity.Aircraft;
import Entity.Flight;
import Entity.GenericFlight;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Xi
 */
@Stateless
public class GenericFlightSessionBean implements GenericFlightSessionBeanLocal {
                    @PersistenceContext()
	EntityManager em;
	GenericFlight genericFlight;
                    Flight flight;
	Aircraft aircraft;

	public GenericFlightSessionBean(){
	}
        
          @Override
          public void addGenericFlight(Integer flightNo, Integer stopoverSNo)throws Exception{
		genericFlight=em.find(GenericFlight.class,flightNo);
		if(genericFlight!=null){
			throw new Exception("Flight exists");
		}
		genericFlight =new GenericFlight();
		genericFlight.create(flightNo,stopoverSNo);
		em.persist(genericFlight);
	}
        
}
