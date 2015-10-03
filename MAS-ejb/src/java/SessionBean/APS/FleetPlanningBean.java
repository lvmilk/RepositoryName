package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Entity.APS.CabinClass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lu Xi
 */
@Stateless
public class FleetPlanningBean implements FleetPlanningBeanLocal {

    @PersistenceContext
    EntityManager em;
    AircraftType aircraftType;
    Aircraft aircraft;

    public FleetPlanningBean() {
    }

    public void addCabin(AircraftType aircraftType, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) {
        AircraftType AirType = em.find(AircraftType.class, aircraftType.getType());
        System.out.println("Aircraft Type found is " + aircraftType.getType());
        Collection<CabinClass> cabinSet = new ArrayList<CabinClass>();
        AirType.setCabinList(cabinSet);

        if (suiteNo > 0) {
            CabinClass suite = new CabinClass();
            suite.setCabinName("Suite");
            suite.setSeatCount(suiteNo);
            suite.setAircraftType(AirType);
//            em.persist(suite);
            AirType.getCabinList().add(suite);
        }

        if (fcSeatNo > 0) {
            CabinClass First = new CabinClass();
            First.setCabinName("First Class");
            First.setSeatCount(fcSeatNo);
            First.setAircraftType(AirType);
//            em.persist(First);

            AirType.getCabinList().add(First);
        }

        if (bcSeatNo > 0) {
            CabinClass biz = new CabinClass();
            biz.setCabinName("Business Class");
            biz.setSeatCount(bcSeatNo);
            biz.setAircraftType(AirType);
//            em.persist(biz);
            AirType.getCabinList().add(biz);
        }

        if (pecSeatNo > 0) {
            CabinClass Pecon = new CabinClass();
            Pecon.setCabinName("Premium Economic Class");
            Pecon.setSeatCount(pecSeatNo);
            Pecon.setAircraftType(AirType);
//            em.persist(Pecon);
            cabinSet.add(Pecon);
        }

        if (ecSeatNo > 0) {
            CabinClass econ = new CabinClass();
            econ.setCabinName("Economic Class");
            econ.setSeatCount(ecSeatNo);
            econ.setAircraftType(AirType);
//            em.persist(econ);
            AirType.getCabinList().add(econ);
        }
//        AirType.setCabinList(cabinSet);
        System.out.println("in addCabin: aircraftType is " + AirType.getType());
        System.out.println("No. of cabin class in this type is " + AirType.getCabinList().size());

        em.merge(AirType);

    }

    @Override
    public boolean checkDuplicate(String type) {
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            return false;    //not exist,no duplicate
        } else {
            return true;
        }
    }

    @Override
    public void addAircraftType(String type, String manufacturer, Double maxDistance, Double leaseCost, Double fuelCost, Double aircraftLength, Double wingspan, String minAirspace,
            Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) throws Exception {
        System.out.println("get in addAircraftType");
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType != null) {
            throw new Exception("AircraftType exists.");
        }
        aircraftType = new AircraftType();
        aircraftType.create(type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo);
        em.persist(aircraftType);
        em.flush();
        System.out.println("Aircrat Type is added!");
        addCabin(aircraftType, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo);
    }

    @Override
    public void editAircraftType(String type, String manufacturer, Double maxDistance, Double leaseCost, Double fuelCost, Double aircraftLength, Double wingspan, String minAirspace,
            Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) throws Exception {
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist..");
        }
        aircraftType.setManufacturer(manufacturer);
        aircraftType.setMaxDistance(maxDistance);
        aircraftType.setLeaseCost(leaseCost);
        aircraftType.setFuelCost(fuelCost);
        aircraftType.setAircraftLength(aircraftLength);
        aircraftType.setWingspan(wingspan);
        aircraftType.setMinAirspace(minAirspace);
        aircraftType.setSuiteNo(suiteNo);
        aircraftType.setFcSeatNo(fcSeatNo);
        aircraftType.setBcSeatNo(bcSeatNo);
        aircraftType.setPecSeatNo(pecSeatNo);
        aircraftType.setEcSeatNo(ecSeatNo);
        em.merge(aircraftType);
        em.flush();
        System.out.println("Aircrat Type is edited!");

    }
//    
//    @Override
//    public void deleteAircraftType(String type)throws Exception{
//        aircraftType = em.find(AircraftType.class, type);
//        if (aircraftType == null) {
//            throw new Exception("AircraftType does not exist.");
//        }
//        if(aircraftType.getAircraft().isEmpty())
//            em.remove(aircraftType);
//        else
//            throw new Exception("Cannot delete.This aircraft type is linked with an aircraft.");
//        em.flush();
//    }

    @Override
    public void tryDeleteAircraftType(AircraftType aircraftType) throws Exception {
        Query q = em.createQuery("SELECT a FROM Aircraft a where a.aircraftType =:aircraftType");
        q.setParameter("aircraftType", aircraftType);
        if (!q.getResultList().isEmpty()) {
            throw new Exception("Cannot delete. This aircraft type is linked with an aircraft.");
        } else {
            System.out.println("This aircraft type " + aircraftType.getType() + " can be deleted.");
        }
    }

    @Override
    public boolean tryDeleteTypeList(List<AircraftType> typeList) throws Exception {
        for (AircraftType a : typeList) {
            tryDeleteAircraftType(a);
        }
        return true;
    }

    @Override
    public void deleteAircraftType(AircraftType aircraftType) throws Exception {
        Query q = em.createQuery("SELECT a FROM Aircraft a where a.aircraftType =:aircraftType");
        q.setParameter("aircraftType", aircraftType);
        if (!q.getResultList().isEmpty()) {
            throw new Exception("Cannot delete. The aircraft type " + aircraftType.getType() + " is linked with a aircraft.");
        } else {
            aircraftType = em.merge(aircraftType);
            em.remove(aircraftType);
            em.flush();
            System.out.println("This aircraft type " + aircraftType.getType() + " is deleted!");
        }
    }

    @Override
    public void deleteAircraftTypeList(List<AircraftType> typeList) throws Exception {
        for (AircraftType at : typeList) {
            deleteAircraftType(at);
        }
    }

    @Override
    public List<AircraftType> canDeleteTypeList() {
        Query q = em.createQuery("SELECT at FROM AircraftType at");
        List<AircraftType> typeList = q.getResultList();
        List<AircraftType> typeList2 = q.getResultList();
        AircraftType at = new AircraftType();
        for (int i = 0; i < typeList.size(); i++) {
            at = typeList.get(i);
            Query q2 = em.createQuery("SELECT a FROM Aircraft a where a.aircraftType =:aircraftType").setParameter("aircraftType", at);
            if (!q2.getResultList().isEmpty()) {
                typeList2.remove(at);
            }
        }
        return typeList2;
    }

    @Override
    public List<AircraftType> cannotDeleteTypeList() {
        Query q = em.createQuery("SELECT at FROM AircraftType at");
        List<AircraftType> typeList = (List<AircraftType>) q.getResultList();
        List<AircraftType> typeList2 = new ArrayList<>();
        for (AircraftType at : typeList) {
            Query q2 = em.createQuery("SELECT a FROM Aircraft a where a.aircraftType =:aircraftType");
            q2.setParameter("aircraftType", at);
            if (!q2.getResultList().isEmpty()) {
                typeList2.add(at);
            }
        }
        return typeList2;
    }

    @Override
    public List<AircraftType> getAllAircraftType() {
        Query query = em.createQuery("SELECT at FROM AircraftType at ");
        List<AircraftType> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("getAllType List is empty");
        } else {
            System.out.println("getAllType List data exists");
        }

        return resultList;
    }

    @Override
    public AircraftType getAircraftType(String type) {
        aircraftType = em.find(AircraftType.class, type);
        System.out.println("getAircraftType: "+aircraftType.getType());
        return aircraftType;
    }

    @Override
    public Integer getTypeSize(String type) {
        aircraftType = em.find(AircraftType.class, type);
        Integer size=aircraftType.getAircraft().size();
        System.out.println("getTypeSize:"+ size);
        return size;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addAircraft(String type, String registrationNo, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate,
            Long flightLogId, Long maintenanceLogId) throws Exception {
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft != null) {
            throw new Exception("Aircraft has already existed.");
        }
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist.");
        }
        aircraft = new Aircraft();
        aircraft.create(registrationNo, serialNo, status, firstFlyDate, deliveryDate, retireDate, flightLogId, maintenanceLogId);
        aircraft.setAircraftType(aircraftType);
        em.persist(aircraft);
        em.flush();
        aircraftType.getAircraft().add(aircraft);
        em.merge(aircraftType);
        em.flush();
    }

    @Override
    public void editAircraft(String type, String registrationNo, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate,
            Long flightLogId, Long maintenanceLogId) throws Exception {
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft == null) {
            throw new Exception("Aircraft does not exist.");
        }
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist.");
        }
        System.out.println("Fleet Planning Bean is editing Aircraft...");
        aircraft.setSerialNo(serialNo);
        aircraft.setStatus(status);
        aircraft.setFirstFlyDate(firstFlyDate);
        aircraft.setDeliveryDate(deliveryDate);
        aircraft.setRetireDate(retireDate);
        aircraft.setFlightLogId(flightLogId);
        aircraft.setMaintenanceLogId(maintenanceLogId);
        aircraft.setAircraftType(aircraftType);
        em.merge(aircraft);
        em.flush();
    }

    @Override
    public void deleteAircraft(ArrayList<Aircraft> selectedList) throws Exception {
        if (selectedList.size() > 0) {
            for (int i = 0; i < selectedList.size(); i++) {
                String registrationNo = selectedList.get(i).getRegistrationNo();
                String type = selectedList.get(i).getAircraftType().getType();
                aircraft = em.find(Aircraft.class, registrationNo);
                aircraftType = em.find(AircraftType.class, type);
                aircraftType.getAircraft().remove(aircraft);
                em.remove(aircraft);
                em.flush();
            }
        } else {
            throw new Exception("No Aircraft Type is selected.");
        }
    }

    @Override
    public List<Aircraft> getAllAircraft() {
        Query query = em.createQuery("SELECT a FROM Aircraft a ");
        List<Aircraft> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("List data exists");
        }
        return resultList;
    }

    @Override
    public Aircraft getAircraft(String registrationNo) {
        aircraft = em.find(Aircraft.class, registrationNo);
        return aircraft;
    }
}
