package SessionBean.APS;

import Entity.AAS.Expense;
import Entity.AAS.Revenue;
import Entity.AFOS.Maintenance;
import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Entity.AIS.CabinClass;
import Entity.APS.FlightInstance;
import SessionBean.AIS.SeatPlanBeanLocal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
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
    Expense expense;
    Revenue revenue;

    @EJB
    SeatPlanBeanLocal sp;

    private Map<String, Integer> allSize = new HashMap<String, Integer>();
    private Map<String, String> allManufacturer = new HashMap<String, String>();
    private Map<String, List<String>> allNum = new HashMap<String, List<String>>();

    private Map<String, String> allStatus = new HashMap<String, String>();

    public FleetPlanningBean() {
    }

    public void addCabin(AircraftType aircraftType, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) {
        AircraftType AirType = em.find(AircraftType.class, aircraftType.getType());
        System.out.println("Aircraft Type found is " + aircraftType.getType());
        List<CabinClass> cabinSet = new ArrayList<CabinClass>();
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
            Pecon.setCabinName("Premium Economy Class");
            Pecon.setSeatCount(pecSeatNo);
            Pecon.setAircraftType(AirType);
//            em.persist(Pecon);
            cabinSet.add(Pecon);
        }

        if (ecSeatNo > 0) {
            CabinClass econ = new CabinClass();
            econ.setCabinName("Economy Class");
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
    public Map<String, List<String>> getAllNum(String type) {
        aircraftType = em.find(AircraftType.class, type);
        List<String> registrationNo = new ArrayList<String>();
        for (int i = 0; i < aircraftType.getAircraft().size(); i++) {

            registrationNo.add(aircraftType.getAircraft().get(i).getRegistrationNo());
            System.out.println("getRegistrationNum:" + registrationNo);

        }
        allNum.put(type, registrationNo);
        System.out.println("this allNum" + allNum.entrySet().toString());
        for (Map.Entry<String, List<String>> entry : allNum.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("Registration Map Key = " + key);
            System.out.println("Registration Map Values = " + values);
        }
        return allNum;
    }

    @Override
    public Map<String, Integer> getAllSize(String type) {
        aircraftType = em.find(AircraftType.class, type);
        Integer size = aircraftType.getAircraft().size();
        System.out.println("getTypeSize:" + size);
        allSize.put(type, size);
        return allSize;
    }

    @Override
    public Map<String, String> getAllManufacturer(String type) {
        aircraftType = em.find(AircraftType.class, type);
        String manufacturer = aircraftType.getManufacturer();
        System.out.println("getManufacturerMap:" + manufacturer);
        allManufacturer.put(type, manufacturer);
        return allManufacturer;
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
    public void addAircraftType(String type, String manufacturer, Double maxDistance, Double fuelCost, Double mtCost, Double aircraftLength, Double wingspan, String minAirspace,
            Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo, Double cabinCrew, Double purser, Integer captain, Integer pilot) throws Exception {
        System.out.println("get in addAircraftType");
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType != null) {
            throw new Exception("Aircraft Type has existed.");
        }
        if ((suiteNo + fcSeatNo + bcSeatNo + pecSeatNo + ecSeatNo) > 800) {
            throw new Exception("Total number of cabin classes exceeds the max capacity (800)!");
        }
        if ((cabinCrew + purser + captain + pilot) > 25) {
            throw new Exception("Total number of crews exceeds the max capacity (25)!");
        }
        aircraftType = new AircraftType();
        aircraftType.create(type, manufacturer, maxDistance, fuelCost, mtCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo, cabinCrew, purser, captain, pilot);
        em.persist(aircraftType);
        em.flush();
        ////////////
        expense = new Expense();
        expense.setCategory("Fuel Cost");
        expense.setType("Sunk Cost");
        expense.setPayable(fuelCost);
        expense.setCostSource(type);
        em.persist(expense);
        em.flush();
        expense = new Expense();
        expense.setCategory("Maintenance Cost");
        expense.setType("Sunk Cost");
        expense.setPayable(mtCost);
        expense.setCostSource(type);
        em.persist(expense);
        em.flush();
        System.out.println("FPB: add aircraft type: Expense setting");
        ////////////
        System.out.println("Aircrat Type is added!");
        addCabin(aircraftType, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo);
    }

    @Override
    public void editAircraftType(String type, String manufacturer, Double maxDistance, Double fuelCost, Double mtCost, Double aircraftLength, Double wingspan, String minAirspace,
            Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo, Double cabinCrew, Double purser, Integer captain, Integer pilot) throws Exception {
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist.");
        }
        if ((suiteNo + fcSeatNo + bcSeatNo + pecSeatNo + ecSeatNo) > 800) {
            throw new Exception("Total number of cabin classes exceeds the max capacity (800)!");
        }
        if ((cabinCrew + purser + captain + pilot) > 25) {
            throw new Exception("Total number of crews exceeds the max capacity (25)!");
        }
        aircraftType.setManufacturer(manufacturer);
        aircraftType.setMaxDistance(maxDistance);
        aircraftType.setFuelCost(fuelCost);
        aircraftType.setMtCost(mtCost);
        aircraftType.setAircraftLength(aircraftLength);
        aircraftType.setWingspan(wingspan);
        aircraftType.setMinAirspace(minAirspace);
        aircraftType.setSuiteNo(suiteNo);
        aircraftType.setFcSeatNo(fcSeatNo);
        aircraftType.setBcSeatNo(bcSeatNo);
        aircraftType.setPecSeatNo(pecSeatNo);
        aircraftType.setEcSeatNo(ecSeatNo);
        aircraftType.setCabinCrew(cabinCrew);
        aircraftType.setCabinLeader(purser);
        aircraftType.setCaptain(captain);
        aircraftType.setPilot(pilot);
        em.merge(aircraftType);
        em.flush();
        Query q = em.createQuery("SELECT e FROM Expense e where e.costSource=:type");
        q.setParameter("type", type);
        if (q.getResultList().isEmpty()) {
           System.out.println("No aircraft type related to this expense.");
        } else {
            for (int i = 0; i < q.getResultList().size(); i++) {
                expense = (Expense) q.getResultList().get(i);
                if (expense.getCategory().equals("Fuel Cost")) {
                    expense.setPayable(fuelCost);
                    expense.setCostSource(type);
                }
                if (expense.getCategory().equals("Maintenance Cost")) {
                    expense.setPayable(mtCost);
                    expense.setCostSource(type);
                }
                em.merge(expense);
                em.flush();
            }
        }
        System.out.println("Aircrat Type is edited!");

    }

    @Override
    public void tryDeleteAircraftType(AircraftType aircraftType) throws Exception {
        Query q = em.createQuery("SELECT a FROM Aircraft a where a.aircraftType =:aircraftType");
        q.setParameter("aircraftType", aircraftType);
        if (!q.getResultList().isEmpty()) {
            throw new Exception("Cannot delete. This aircraft type " + aircraftType.getType() + " is linked with an aircraft.");
        } else {
            System.out.println("This aircraft type " + aircraftType.getType() + " can be deleted.");
        }

        Query q2 = em.createQuery("SELECT r FROM Route r where r.acType =:aircraftType").setParameter("aircraftType", aircraftType);
        if (!q2.getResultList().isEmpty()) {
            throw new Exception("Cannot delete. This aircraft type " + aircraftType.getType() + " is linked with a route.");
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
        Query q2 = em.createQuery("SELECT r FROM Route r where r.acType =:aircraftType").setParameter("aircraftType", aircraftType);
        if (!q.getResultList().isEmpty()) {
            throw new Exception("Cannot delete. The aircraft type " + aircraftType.getType() + " is linked with an aircraft.");
        } else if (!q2.getResultList().isEmpty()) {
            throw new Exception("Cannot delete. This aircraft type " + aircraftType.getType() + " is linked with a route.");
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
        List<AircraftType> typeList3 = q.getResultList();
        AircraftType at = new AircraftType();
        AircraftType at2 = new AircraftType();
        for (int i = 0; i < typeList.size(); i++) {
            at = typeList.get(i);
            Query q2 = em.createQuery("SELECT a FROM Aircraft a where a.aircraftType =:aircraftType").setParameter("aircraftType", at);
            if (!q2.getResultList().isEmpty()) {
                typeList2.remove(at);
                typeList3.remove(at);
            }
        }
        System.out.println("fleetPlanningBean: canDeleteTypeList: check 1: type list 2: " + typeList2 + " and type List 3: " + typeList3);
        for (int j = 0; j < typeList2.size(); j++) {
            at2 = typeList2.get(j);
            Query q3 = em.createQuery("SELECT r FROM Route r where r.acType =:aircraftType").setParameter("aircraftType", at2);
            if (!q3.getResultList().isEmpty()) {
                typeList3.remove(at2);
            }
        }
        System.out.println("fleetPlanningBean: canDeleteTypeList: check 2: type list 3: " + typeList3);
        return typeList3;
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
                Query q3 = em.createQuery("SELECT r FROM Route r where r.acType =:aircraftType").setParameter("aircraftType", at);
                if (!q3.getResultList().isEmpty()) {
                    typeList2.add(at);
                }
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
        System.out.println(
                "getAircraftType: " + aircraftType.getType());
        return aircraftType;
    }

    @Override
    public List<Aircraft> getThisTypeAircraft(String type) {
        aircraftType = em.find(AircraftType.class, type);
        List<Aircraft> aircraftList = aircraftType.getAircraft();
        return aircraftList;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addAircraft(String type, String registrationNo, String status, String firstFlyDate, String deliveryDate, String retireDate, Double purchaseCost) throws Exception {
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft != null) {
            throw new Exception("Aircraft has already existed.");
        }
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist.");
        }
        aircraft = new Aircraft();
        aircraft.create(registrationNo, status, firstFlyDate, deliveryDate, retireDate, purchaseCost);
        aircraft.setAircraftType(aircraftType);
        aircraft.setCurrentAirport("SIN");
        em.persist(aircraft);
        em.flush();
        aircraftType.getAircraft().add(aircraft);
        em.merge(aircraftType);
        em.flush();
        ////////////
        String string = deliveryDate;
        Integer yearDiff = aircraft.getYearDiff();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date purchaseDate = format.parse(string);
        expense = new Expense();
        expense.setCategory("Purchase Aircraft");
        expense.setType("Sunk Cost");
        expense.setPaymentDate(purchaseDate);
        expense.setPayable(purchaseCost);
        expense.setCostSource(registrationNo);
        em.persist(expense);
        em.flush();
        expense = new Expense();
        expense.setCategory("Depreciation");
        expense.setType("Sunk Cost");
        Double depreciation = 0.7 * purchaseCost / yearDiff;         //depreciation per annum... residual value=0.3*purchase cost
        expense.setPayable(depreciation);
        expense.setCostSource(registrationNo);
        em.persist(expense);
        em.flush();
        System.out.println("FPB: add aircraft type: Expense setting");
        ////////////
    }

    @Override
    public void editAircraft(String type, String registrationNo, String status, String firstFlyDate, String deliveryDate, String retireDate, Double purchaseCost) throws Exception {
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft == null) {
            throw new Exception("Aircraft does not exist.");
        }
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist.");
        }

        if ((status.equals("Retired")) && (!aircraft.getFlightInstance().isEmpty())) {
            //     System.out.println("fleetPlanningBean: editAircraft: reired with flight instance ??!! "+aircraft.getStatus() + status + aircraft.getFlightInstance());
            throw new Exception("Aircraft status cannot be Retired. This aircraft " + aircraft.getRegistrationNo() + " has already linked with a flight instance.");
        }
         if (aircraft.getStatus().equals("Retired") && !status.equals("Retired")) {
            throw new Exception("Aircraft status cannot be changed. This aircraft " + aircraft.getRegistrationNo() + " has already been retired.");
        }

        System.out.println("fleetPlanningBean: editAircraft: reired with flight instance ??!! " + aircraft.getStatus() + aircraft.getFlightInstance());
        System.out.println("Fleet Planning Bean is editing Aircraft...");
        aircraft.setStatus(status);
        aircraft.setRetireDate(retireDate);
        aircraft.setPurchaseCost(purchaseCost);
        aircraft.setAircraftType(aircraftType);
        em.merge(aircraft);
        em.flush();
        Integer start = Integer.parseInt(deliveryDate.substring(0, 4));
        Integer end = Integer.parseInt(retireDate.substring(0, 4));
        Integer yearDiff = Math.abs(end - start);
        Query q = em.createQuery("SELECT e FROM Expense e where e.costSource=:registrationNo");
        q.setParameter("registrationNo", registrationNo);
        if (q.getResultList().isEmpty()) {
            throw new Exception("No aircraft related to this expense.");
        } else {
            for (int i = 0; i < q.getResultList().size(); i++) {
                expense = (Expense) q.getResultList().get(i);
                if (expense.getCategory().equals("Purchase Aircraft")) {
                    expense.setPayable(purchaseCost);
                    expense.setCostSource(registrationNo);
                }
                if (expense.getCategory().equals("Depreciation")) {
                    Double depreciation = 0.7 * purchaseCost / yearDiff;         //depreciation per annum, residual value = 0.3*purchase cost
                    expense.setPayable(depreciation);
                    expense.setCostSource(registrationNo);
                }
                em.merge(expense);
                em.flush();
            }
        }
        Query q2 = em.createQuery("SELECT r FROM Revenue r where r.payer=:registrationNo");
        q2.setParameter("registrationNo", registrationNo);
        if (!q2.getResultList().isEmpty()) {
            System.out.println("There is an existing sale revenue related to this aircraft "+registrationNo);
        } else {
            if (status.equals("Retired")) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date retire = format.parse(retireDate);
                revenue = new Revenue();
                revenue.setReceivable(0.3 * purchaseCost);
                revenue.setChannel("OTHER");
                revenue.setType("Aircraft Sale");
                revenue.setPayer(registrationNo);
                revenue.setPaymentDate(retire);
                em.persist(expense);
                em.flush();
            } else {
                System.out.println("This aircraft " + registrationNo + " is not retired");
            }
        }
    }

    @Override
    public void deleteAircraft(ArrayList<Aircraft> selectedList) throws Exception {
        if (selectedList.size() > 0) {
            for (int i = 0; i < selectedList.size(); i++) {
                String registrationNo = selectedList.get(i).getRegistrationNo();
                aircraft = em.find(Aircraft.class, registrationNo);
                String status = aircraft.getStatus();
                if (!status.equals("Retired")) {
                    throw new Exception("Cannot delete! This aircraft is not retired");
                }
                Query q = em.createQuery("SELECT fi FROM FlightInstance fi where fi.aircraft.registrationNo =:registrationNo");
                q.setParameter("registrationNo", registrationNo);
                if (!q.getResultList().isEmpty()) {
                    throw new Exception("Cannot delete. The aircraft " + registrationNo + " is linked with a flight instance.");
                } else {
                    String type = selectedList.get(i).getAircraftType().getType();
                    aircraft = em.find(Aircraft.class, registrationNo);
                    aircraftType = em.find(AircraftType.class, type);
                    aircraftType.getAircraft().remove(aircraft);
                    em.remove(aircraft);
                    em.flush();
                }
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

    @Override
    public List<FlightInstance> getThisFlightInstance(String registrationNo) throws Exception {
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft == null) {
            throw new Exception("Aircraft does not exist.");
        }
        Query q = em.createQuery("SELECT fi FROM FlightInstance fi where fi.aircraft=:aircraft").setParameter("aircraft", aircraft);
        if (q.getResultList().isEmpty()) {
            System.out.println("This flight instance linked with this aircraft does not exist.");
        }
        return q.getResultList();
    }

    @Override
    public void editMtStandard(AircraftType acType, Integer acInH, Integer acInD, Integer acDu, Integer acMH, Integer bcInH, Integer bcInD, Integer bcDu, Integer bcMH, Integer ccInH, Integer ccInD, Integer ccDu, Integer ccMH, Integer dcInH, Integer dcInD, Integer dcDu, Integer dcMH) {
        AircraftType act = em.find(AircraftType.class, acType.getType());
//        act.setDailycDu(dailycDu);
//        act.setDailycMH(dailycMH);
//        act.setWeeklycDu(weeklycDu);
//        act.setWeeklycMH(weeklycMH);
        act.setAcInH(acInH);
        act.setAcInC(acInD);
        act.setAcDu(acDu);
        act.setAcMH(acMH);
        act.setBcInH(bcInH);
        act.setBcInC(bcInD);
        act.setBcDu(bcDu);
        act.setBcMH(bcMH);
        act.setCcInH(ccInH);
        act.setCcInC(ccInD);
        act.setCcDu(ccDu);
        act.setCcMH(ccMH);
        act.setDcInH(dcInH);
        act.setDcInC(dcInD);
        act.setDcDu(dcDu);
        act.setDcMH(dcMH);
        em.merge(act);
        em.flush();
        System.out.println("Update mt standard for " + act.getType());
    }

}
