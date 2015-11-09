INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, purchaseCost, fuelCost, mtCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo, cabinCrew, cabinLeader, captain, pilot, acInH, acInC, acDu, acMH, bcInH, bcInC, bcDu, bcMH, ccInH, ccInC, ccDu, ccMH, dcInH, dcInC, dcDu, dcMH) values("A380","AIRBUS",15200,200000000,12000,80,75,2000,"4E",6,10,20,50,300,0.02,0.005,2,2, 300,60,24,240,2000,400,48,600,8000,1600,336,3000,36000,6100,1440,50000)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, purchaseCost, fuelCost, mtCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo, cabinCrew, cabinLeader, captain, pilot, acInH, acInC, acDu, acMH, bcInH, bcInC, bcDu, bcMH, ccInH, ccInC, ccDu, ccMH, dcInH, dcInC, dcDu, dcMH) values("777-300","BOEING",11400,150000000,10000,70,67,1500,"4F",3,20,50,100,200,0.02,0.005,1,2, 300,36,20,200,1800,360,40,500,7500,1500,240,2500,30000,6000,1200,45000)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, purchaseCost, fuelCost, mtCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo, cabinCrew, cabinLeader, captain, pilot) values("A330-200","AIRBUS",10800,50000000,6000,60,55,800,"4F",2,10,50,80,200,0.02,0.005,2,2)

INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName, spec, lang, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("SIN","Singapore Changi Airport","Singapore","Singapore","Changi Intl", "English", "UTC+08:00","Normal","Hub","4F", 1.364420, 103.991531)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName, spec, lang, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("XIA","Xi'an Xianyang International Airport","Xi'an","China","Xianyang", "Mandarin", "UTC+08:00","Normal","Normal","4F", 34.441983, 108.771426)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName, spec, lang, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("LHR","Heathrow Airport","London","United Kingdom","Heathrow", "English", "UTC+00:00","Normal","Normal","4F", 51.470022, -0.454295)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName, spec, lang, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("DXB","Dubai International Airport","Dubai","United Arab Emirates","Dubai Intl", "Arabic", "UTC+04:00","Normal","Hub","4F", 25.253175, 55.365673)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName, spec, lang, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("LAX","Los Angeles International Airport","Los Angeles","United States","Los Angeles Intl", "English", "UTC-08:00","Normal","Normal","4F", 33.941589, -118.408530)

INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(10000,"Suite",6,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(20000,"First Class",10,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(30000,"Business Class",20,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(40000,"Premium Economy Class",50,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(50000,"Economy Class",300,"A380")

INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(60000,"Suite",3,"A330-200")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(70000,"First Class",20,"A330-200")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(80000,"Business Class",50,"A330-200")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(90000,"Premium Economy Class",100,"A330-200")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(100000,"Economy Class",200,"777-300")

INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,currentAirport,yearDiff) values("A380","9V-ABC","0000001","In Service","2015-02-04","2015-10-12","2025-01-03","SIN",10)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,currentAirport,yearDiff) values("A380","9V-XYZ","0000004","In Service","2015-01-04","2015-10-12","2025-01-03","SIN",10)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,currentAirport,yearDiff) values("777-300","9V-AAA","0000002","In Service","2015-03-05","2015-10-10","2025-01-03","SIN",10)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,currentAirport,yearDiff) values("777-300","9V-BBB","0000003","In Service","2015-04-05","2015-10-10","2025-01-03","SIN",10)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,currentAirport,yearDiff) values("777-300","9V-CCC","0000005","In Service","2015-05-05","2015-10-10","2025-01-03","SIN",10)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,currentAirport,yearDiff) values("A330-200","9V-000","9999999","Testing","2015-02-30","2015-10-09","2050-12-25","SIN",10)


INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(10001,0,0,0,0,0,0,"777-300","SIN","SIN","Pending",0)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(10002,0,0,0,0,0,0,"A380","SIN","SIN","Pending",0)

INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(10001,0,0,0,0,0,0,"777-300","SIN","SIN","Pending",0)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(10002,0,0,0,0,0,0,"A380","SIN","SIN","Pending",0)

INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(10000,3900,5,600,500,400,300,"777-300","XIA","SIN","Pending",800)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(20000,3900,5,600,500,400,300,"777-300","SIN","XIA","Pending",800)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(30000,6500,4,800,700,600,500,"A380","DXB","SIN","Pending",1000)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(40000,6500,4,800,700,600,500,"A380","SIN","DXB","Pending",1000)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(50000,6900,4,1000,800,700,600,"A380","LHR","DXB","Pending",1500)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(60000,6900,4,1000,800,700,600,"A380","DXB","LHR","Pending",1500)

INSERT IGNORE INTO Adminstaff(ID,ADMNAME,ADMPASSWORD,STFTYPE) values(10000,"admin", "admin", "administrator")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("O777777","777@777.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("O888888","888@888.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("G777777","111@111.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("G888888","222@222.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CB777777","333@333.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CB888888","444@444.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CP777777","555@555.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CP888888","666@666.com")

INSERT IGNORE INTO AGENCY(AGENCYID,AGENPWD,EMAIL,PTYPE)values("AG777777","16deddbdc8449f8e47ff8dc6d49e716c","ag777@777.com","agency")

INSERT IGNORE INTO Officestaff(OFFNAME,EMAIL,OFFPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("O777777","o777777@mas.com","72ebb4d2f49358f09e1811874bb487d1","officeStaff",0,0,"O777777","HAO","LI","Normal",5000,0)
INSERT IGNORE INTO Officestaff(OFFNAME,EMAIL,OFFPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("0888888","o888888@mas.com","0e267f2d45cd249730ca941395119dec","officeStaff",0,0,"O888888","LI","HAO","Normal",5000,0)

INSERT IGNORE INTO Groundstaff(GRDNAME,EMAIL,GRDPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("G777777","g777777@mas.com","de581c8f145c1fb4114c34405b17a479","groundStaff",0,0,"G777777","HAO","LI","Normal",5000,0)
INSERT IGNORE INTO Groundstaff(GRDNAME,EMAIL,GRDPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("G888888","g888888@mas.com","d3e68babfccc6540105b8a6e8c2f017a","groundStaff",0,0,"G888888","LI","HAO","Normal",5000,0)

INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888881","a9e82ba9d07b46fb5ae7ad1bb15bcc26","cb888881@mas.com","cabin",0,0,"CB777777","HAO","LI","Cabin Leader",10000,20)
INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888882","d77c897797e7edf03bf5cd8509493314","cb888882@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin Crew",8000,15)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888883","d77c897797e7edf03bf5cd8509493314","CB888883@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888884","d77c897797e7edf03bf5cd8509493314","CB888884@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888885","d77c897797e7edf03bf5cd8509493314","CB888885@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888886","d77c897797e7edf03bf5cd8509493314","CB888886@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888887","d77c897797e7edf03bf5cd8509493314","CB888887@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888888","d77c897797e7edf03bf5cd8509493314","CB888888@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888889","d77c897797e7edf03bf5cd8509493314","CB888889@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)
-- INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CB888890","d77c897797e7edf03bf5cd8509493314","CB888889@mas.com","cabin",0,0,"CB888888","LI","HAO","Cabin",8000,0)


INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777771","829d214cbc0fd94b7e16a52e526d3a56","cp777771@mas.com","cockpit","L12345",0,0,"CP777777","HAO","LI","Captain",20000,35)
INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777772","91da5b4c6cb97677b5b66f0f07ef993b","cp777772@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,30)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777773","91da5b4c6cb97677b5b66f0f07ef993b","CP777773@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777774","91da5b4c6cb97677b5b66f0f07ef993b","CP777774@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777775","91da5b4c6cb97677b5b66f0f07ef993b","CP777775@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777776","91da5b4c6cb97677b5b66f0f07ef993b","CP777776@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777777","91da5b4c6cb97677b5b66f0f07ef993b","CP777777@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777778","91da5b4c6cb97677b5b66f0f07ef993b","CP777778@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777779","91da5b4c6cb97677b5b66f0f07ef993b","CP777779@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)
-- INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME,FIRSTNAME,LASTNAME,STFLEVEL,SALARY,HOURPAY) values("CP777780","91da5b4c6cb97677b5b66f0f07ef993b","CP777780@mas.com","cockpit","L12345",0,0,"CP888888","XIAOMING","HUANG","Pilot",15000,0)


INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo, startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (10000,10000,'MR001',"2015-01-01","2025-01-01","10:00","15:00",0,True,False,True,False,True,False,True)
INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo,  startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (20000,20000,'MR002',"2012-10-08","2022-10-08","18:00","23:00",0,True,False,True,False,True,False,True)
INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo, startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (30000,30000,'MR003',"2015-01-01","2025-01-01","08:00","12:00",0,False,True,False,True,False,True,False)
INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo,  startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (40000,40000,'MR004',"2012-10-08","2022-10-08","03:00","07:00",0,True,False,False,True,False,True,False)
INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo, startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (50000,50000,'MR005',"2015-01-01","2025-01-01","15:00","21:00",0,False,True,False,True,False,True,False)
INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo,  startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (60000,60000,'MR006',"2012-10-08","2022-10-08","16:00","22:00",0,False,False,True,False,True,False,True)


INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(30000,30000,"2015-12-08","08:00","12:00","08:00","12:00","Testing",0,0,"2015-12-08 08:00","2015-12-08 12:00","9V-000",'2015-12-08 08:00','2015-12-08 12:00')
INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(50000,50000,"2015-12-08","15:00","21:00","15:00","21:00","Testing",0,0,"2015-12-08 15:00","2015-12-08 21:00","9V-000",'2015-12-08 15:00','2015-12-08 21:00')
INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(60000,60000,"2015-12-23","16:00","22:00","16:00","22:00","Testing",0,0,"2015-12-23 16:00","2015-12-23 22:00","9V-000",'2015-12-23 16:00','2015-12-23 22:00')
INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(40000,40000,"2015-12-24","03:00","07:00","03:00","07:00","Testing",0,0,"2015-12-24 03:00","2015-12-24 07:00","9V-000",'2015-12-24 03:00','2015-12-24 07:00')

-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(1000000,10000,"2015-10-13","10:00","15:00","10:00","15:00","Testing",0,0,"2015-10-13 10:00","2015-10-13 15:00","9V-000",'2015-10-13 10:00','2015-10-13 15:00')
-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(1000001,20000,"2015-10-13","18:00","23:00","18:00","23:00","Testing",0,0,"2015-10-13 18:00","2015-10-13 23:00","9V-000",'2015-10-13 18:00','2015-10-13 23:00')

-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(10000,10000,"2015-11-01","10:00","15:00","10:00","15:00","Testing",0,0,"2015-11-01 10:00","2015-11-01 15:00","9V-000",'2015-11-01 10:00','2015-11-01 15:00')
-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(20000,20000,"2015-11-03","10:00","15:00","10:00","15:00","Testing",0,0,"2015-11-03 10:00","2015-11-03 15:00","9V-000",'2015-11-03 10:00','2015-11-03 15:00')
-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(70000,30000,"2015-12-19","10:00","15:00","10:00","15:00","Testing",0,0,"2015-12-19 10:00","2015-12-19 15:00","9V-000",'2015-12-19 10:00','2015-12-19 15:00')

-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(80000,50000,"2015-12-15","17:00","20:00","17:00","20:00","Testing",0,0,"2015-12-15 17:00","2015-12-15 20:00","9V-000",'2015-12-15 17:00','2015-12-15 20:00')
-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(90000,50000,"2015-12-17","17:00","20:00","17:00","20:00","Testing",0,0,"2015-12-17 17:00","2015-12-17 20:00","9V-000",'2015-12-17 17:00','2015-12-17 20:00')
-- INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime,aircraft_registrationNo,standardDepTimeDateType,standardArrTimeDateType) values(100000,50000,"2015-12-19","17:00","20:00","17:00","20:00","Testing",0,0,"2015-12-19 17:00","2015-12-19 20:00","9V-000",'2015-12-19 17:00','2015-12-19 20:00')




INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (10000,30000,10000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (20000,30000,20000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (30000,30000,30000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (40000,30000,40000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (50000,30000,50000,0)

INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (60000,40000,10000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (70000,40000,20000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (80000,40000,30000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (90000,40000,40000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (100000,40000,50000,0)

INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (110000,50000,10000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (120000,50000,20000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (130000,50000,30000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (140000,50000,40000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (150000,50000,50000,0)

INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (160000,60000,10000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (170000,60000,20000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (180000,60000,30000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (190000,60000,40000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (200000,60000,50000,0)



INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(10000,'S','Suite',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(20000,'F','First Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(30000,'J','Business Class',1.111)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(40000,'C','Business Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(50000,'D','Premium Economy Class',1.25)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(60000,'P','Premium Economy Class',1.125)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(70000,'Z','Premium Economy Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(80000,'Y','Economy Class',2)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(90000,'B','Economy Class',1.8)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(100000,'M','Economy Class',1.6)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(110000,'H','Economy Class',1.4)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(120000,'Q','Economy Class',1.2)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(130000,'N','Economy Class',1)


INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(10000,10000,10000,3000,6,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(20000,20000,20000,2000,10,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(30000,30000,30000,1777,5,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(40000,40000,30000,1600,15,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(50000,50000,40000,1500,5,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(60000,60000,40000,1350,15,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(70000,70000,40000,1200,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(80000,80000,50000,1000,10,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(90000,90000,50000,900,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(100000,100000,50000,800,50,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(110000,110000,50000,700,60,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(120000,120000,50000,600,70,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(130000,130000,50000,500,80,0)

INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(140000,10000,60000,3000,3,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(150000,20000,70000,2000,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(160000,30000,80000,1777.6,15,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(170000,40000,80000,1600,35,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(180000,50000,90000,1500,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(190000,60000,90000,1350,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(200000,70000,90000,1200,50,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(210000,80000,100000,1000,10,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(220000,90000,100000,900,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(230000,100000,100000,800,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(240000,110000,100000,700,40,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(250000,120000,100000,600,45,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(260000,130000,100000,500,55,0)

INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(270000,10000,110000,3000,3,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(280000,20000,120000,2500,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(290000,30000,130000,1999.8,15,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(300000,40000,130000,1800,35,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(310000,50000,140000,1750,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(320000,60000,140000,1575,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(330000,70000,140000,1400,50,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(340000,80000,150000,1200,10,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(350000,90000,150000,1080,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(360000,100000,150000,960,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(370000,110000,150000,840,40,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(380000,120000,150000,720,45,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(390000,130000,150000,600,55,0)

INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(400000,10000,160000,3000,3,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(410000,20000,170000,2500,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(420000,30000,180000,1999,15,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(430000,40000,180000,1800,35,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(440000,50000,190000,1750,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(450000,60000,190000,1575,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(460000,70000,190000,1400,50,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(470000,80000,200000,1200,10,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(480000,90000,200000,1080,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(500000,100000,200000,960,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(510000,110000,200000,840,40,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(520000,120000,200000,720,45,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(530000,130000,200000,600,55,0)



INSERT IGNORE INTO Booker(id,address,contact,dob,email,firstname,lastname,memberstatus,miles,passport,title) values(99,"Strathmore Ave", 7788414,"22/05/1989","hahaha@gmail.com","hao","li",true,0,"G12345678","Dr")
INSERT IGNORE INTO Booker(id,address,contact,dob,email,firstname,lastname,memberstatus,miles,passport,title) values(999,"Strathmore Ave", 66666666,"22/05/1989","hehehe@gmail.com","li","hao",true,0,"G87654321","Mr")


INSERT IGNORE INTO Passenger(id,passport,firstName,lastName,ffpName,ffpNo) values(9999,"G1234589","Hao","Li","TFP","A123")
INSERT IGNORE INTO Passenger(id,passport,firstName,lastName,ffpName,ffpNo) values(8888,"G4567890","Yuqi","Liu","TFP","A456")

INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(111111,8888, 1500.00,"ARS","2015-08-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(222222,9999, 6000.00,"ARS","2015-09-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(333333,8888, 3000.00,"ARS","2015-10-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(444444,9999, 4000.00,"ARS","2015-11-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(555555,8888, 2200.00,"ARS","2015-11-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(666666,8888, 1700.00,"ADS","2015-08-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(777777,9999, 1900.00,"ADS","2015-09-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(888888,8888, 6000.00,"ADS","2015-11-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(999999,9999, 2500.00,"ADS","2015-11-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(1111,8888, 1500.00,"GDS","2015-03-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(2222,9999, 6000.00,"GDS","2015-04-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(3333,8888, 3000.00,"GDS","2015-08-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(4444,9999, 4000.00,"GDS","2015-11-10")
INSERT IGNORE INTO Ticket(ticketID,passenger_id,price,bookSystem,bookDate) values(5555,8888, 2200.00,"GDS","2015-11-10")

INSERT IGNORE INTO Hotel(hotelName)values("Marina Bay Sands")
INSERT IGNORE INTO Hotel(hotelName)values("Hilton")

INSERT IGNORE INTO CarRental(companyName)values("Grab Taxi")

INSERT IGNORE INTO Railway(railwayName)values("Japan Railway")


INSERT IGNORE INTO HotelPayment(Hotel_hotelName,id,payment,paymentDate)values("Marina Bay Sands",1111,1000.0,"2015-10-12")
INSERT IGNORE INTO HotelPayment(Hotel_hotelName,id,payment,paymentDate)values("Marina Bay Sands",2222,1500.0,"2015-05-10")
INSERT IGNORE INTO HotelPayment(Hotel_hotelName,id,payment,paymentDate)values("Hilton",1111,2000.0,"2015-01-10")

INSERT IGNORE INTO CarPayment(CarRental_companyName,id,payment,paymentDate)values("Grab Taxi",1111,1200.0,"2015-10-12")
INSERT IGNORE INTO CarPayment(CarRental_companyName,id,payment,paymentDate)values("Grab Taxi",2222,1800.0,"2015-05-10")
INSERT IGNORE INTO CarPayment(CarRental_companyName,id,payment,paymentDate)values("Grab Taxi",1111,1000.0,"2015-01-10")

INSERT IGNORE INTO RailwayPayment(Railway_railwayName,id,payment,paymentDate)values("Japan Railway",1111,3000.0,"2015-10-12")
INSERT IGNORE INTO RailwayPayment(Railway_railwayName,id,payment,paymentDate)values("Japan Railway",2222,2200.0,"2015-05-10")
INSERT IGNORE INTO RailwayPayment(Railway_railwayName,id,payment,paymentDate)values("Japan Railway",1111,2500.0,"2015-01-10")