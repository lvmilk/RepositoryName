INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo, stewardess, steward, purser, captain, pilot) values("A380","Airbus",15200,100000,100,100,100,"4E",6,10,20,50,300,16,4,2,2,2)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo, stewardess, steward, purser, captain, pilot) values("777-300","Boeing",11400,200000,200,200,200,"4F",3,20,50,100,200,14,2,2,2,2)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo, stewardess, steward, purser, captain, pilot) values("A330-200","Airbus",10800,100000,100,200,200,"4F",2,10,50,80,200,10,2,2,2,2)

INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("SIN","Singapore Changi Airport","Singapore","Singapore","Changi Intl","UTC+08:00","Normal","Hub","4F", 1.364420, 103.991531)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("XIA","Xi'an Xianyang International Airport","Xi'an","China","Xianyang","UTC+08:00","Normal","Normal","4F", 34.441983, 108.771426)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("LHR","Heathrow Airport","London","United Kingdom","Heathrow","UTC+00:00","Normal","Normal","4F", 51.470022, -0.454295)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("DXB","Dubai International Airport","Dubai Intl","United Arab Emirates","Dubai Intl","UTC+04:00","Normal","Hub","4F", 25.253175, 55.365673)
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace, lat, lon) values("LAX","Los Angeles International Airport","Los Angeles Intl","the United States","Dubai Intl","UTC-08:00","Normal","Normal","4F", 33.941589, -118.408530)

INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(10000,"Suite",6,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(20000,"First Class",10,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(30000,"Business Class",20,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(40000,"Premium Economy Class",50,"A380")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(50000,"Economy Class",300,"A380")

INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(60000,"Suite",3,"777-300")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(70000,"First Class",20,"777-300")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(80000,"Business Class",50,"777-300")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(90000,"Premium Economy Class",100,"777-300")
INSERT IGNORE INTO CabinClass(cabinID,cabinName,seatCount,aircraftType_type) values(100000,"Economy Class",200,"777-300")

INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate) values("A380","9V-ABC","0000001","In Service","2012-01-09","2014-01-03","2025-01-03")
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate) values("A380","9V-XYZ","0000004","Pending","2014-10-09","2015-12-03","2020-01-03")
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate) values("777-300","9V-AAA","0000002","In Service","2010-01-09","2011-01-03","2020-01-03")
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate) values("777-300","9V-BBB","0000003","In Service","2009-01-09","2011-01-03","2022-01-03")
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate) values("777-300","9V-CCC","0000005","In Service","2012-01-09","2013-01-03","2022-01-03")
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate) values("777-300","9V-XXX","9999999","Testing","2012-01-09","2013-01-03","2050-12-25")

INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(10000,10000,12,0,0,0,0,"A380","SIN","XIA","Pending",0)
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status,basicScFare) values(20000,15000,15,0,0,0,0,"777-300","XIA","SIN","Pending",0)

INSERT IGNORE INTO Adminstaff(ID,ADMNAME,ADMPASSWORD,STFTYPE) values(10000,"admin", "admin", "administrator")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("O777777","777@777.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("O888888","888@888.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("G777777","111@111.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("G888888","222@222.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CB777777","333@333.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CB888888","444@444.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CP777777","555@555.com")
INSERT IGNORE INTO UserEntity(USERNAME,COMEMAIL)values("CP888888","666@666.com")

INSERT IGNORE INTO Officestaff(OFFNAME,EMAIL,OFFPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME) values("O777777","777@777.com","72ebb4d2f49358f09e1811874bb487d1","officeStaff",0,0,"O777777")
INSERT IGNORE INTO Officestaff(OFFNAME,EMAIL,OFFPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME) values("O888888","888@888.com","0e267f2d45cd249730ca941395119dec","officeStaff",0,0,"O888888")

INSERT IGNORE INTO Groundstaff(GRDNAME,EMAIL,GRDPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME) values("G777777","111@111.com","de581c8f145c1fb4114c34405b17a479","groundStaff",0,0,"G777777")
INSERT IGNORE INTO Groundstaff(GRDNAME,EMAIL,GRDPASSWORD,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME) values("G888888","222@222.com","d3e68babfccc6540105b8a6e8c2f017a","groundStaff",0,0,"G888888")

INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME) values("CB777777","a9e82ba9d07b46fb5ae7ad1bb15bcc26","333@333.com","cabin",0,0,"CB777777")
INSERT IGNORE INTO Cabincrew(CBNAME,CBPASSWORD,EMAIL,STFTYPE,ATTEMPT,LOCKED,USER_USERNAME) values("CB888888","d77c897797e7edf03bf5cd8509493314","444@444.com","cabin",0,0,"CB888888")

INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME) values("CP777777","829d214cbc0fd94b7e16a52e526d3a56","555@555.com","cockpit","L12345",0,0,"CP777777")
INSERT IGNORE INTO Cockpitcrew(CPNAME,CPPASSWORD,EMAIL,STFTYPE,LICENCE,ATTEMPT,LOCKED,USER_USERNAME) values("CP888888","91da5b4c6cb97677b5b66f0f07ef993b","666@666.com","cockpit","L12345",0,0,"CP888888")

INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo, startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (10000,10000,'MR001',"2015-01-01","2025-01-01","10:00","15:00",0,True,False,True,False,True,False,True)
INSERT IGNORE INTO FlightFrequency(id,route_id, flightNo,  startDate,endDate,scheduleDepTime,scheduleArrTime, dateAdjust, onMon,  onTue, onWed, onThu,  onFri, onSat, onSun) values (20000,20000,'MR002',"2012-10-08","2022-10-08","16:00","21:00",0,True,False,True,False,True,False,True)

INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime) values(10000,10000,"2016-10-01","10:00","15:00","10:00","15:00","Testing",0,0,"2016-10-01 10:00","2016-10-01 15:00")
INSERT IGNORE INTO FlightInstance(id,flightFrequency_id,date,estimatedDepTime,estimatedArrTime,actualDepTime,actualArrTime,flightStatus,estimatedDateAdjust,actualDateAdjust,standardDepTime,standardArrTime) values(20000,20000,"2016-10-03","10:00","15:00","10:00","15:00","Testing",0,0,"2016-10-01 10:00","2016-10-01 15:00")

INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (10000,10000,10000,1)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (20000,10000,20000,2)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (30000,10000,30000,3)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (40000,10000,40000,6)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (50000,10000,50000,21)


INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (60000,20000,60000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (70000,20000,70000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (80000,20000,80000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (90000,20000,90000,0)
INSERT IGNORE INTO FlightCabin(id,flightInstance_id,cabinClass_cabinID,bookedSeat) values (100000,20000,100000,0)


INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(10000,'S','Suite',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(20000,'F','First Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(30000,'J','Business Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(40000,'C','Business Class',0.9)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(50000,'D','Premium Economy Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(60000,'P','Premium Economy Class',0.9)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(70000,'Z','Premium Economy Class',0.8)

INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(80000,'Y','Economy Class',1)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(90000,'B','Economy Class',0.9)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(100000,'M','Economy Class',0.8)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(110000,'H','Economy Class',0.7)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(120000,'Q','Economy Class',0.6)
INSERT IGNORE INTO BookingClass(id,annotation,cabinName,price_percentage) values(130000,'N','Economy Class',0.5)


INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(10000,10000,10000,0,6,1)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(20000,20000,20000,0,10,2)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(30000,30000,30000,0,5,1)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(40000,40000,30000,0,15,2)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(50000,50000,40000,0,5,1)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(60000,60000,40000,0,15,2)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(70000,70000,40000,0,30,3)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(80000,80000,50000,0,10,1)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(90000,90000,50000,0,30,2)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(100000,100000,50000,0,50,3)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(110000,110000,50000,0,60,4)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(120000,120000,50000,0,70,5)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(130000,130000,50000,0,80,6)


INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(140000,10000,60000,0,3,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(150000,20000,70000,0,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(160000,30000,80000,0,15,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(170000,40000,80000,0,35,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(180000,50000,90000,0,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(190000,60000,90000,0,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(200000,70000,90000,0,50,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(210000,80000,100000,0,10,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(220000,90000,100000,0,20,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(230000,100000,100000,0,30,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(240000,110000,100000,0,40,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(250000,120000,100000,0,45,0)
INSERT IGNORE INTO BookingClassInstance(id, bookingClass_id,flightCabin_id,price,seatNo,bookedSeatNo) values(260000,130000,100000,0,55,0)

