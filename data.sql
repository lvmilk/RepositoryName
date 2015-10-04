INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo) values("A380","Airbus",100,100,100,100,100,"4F",5,20,80,150,100)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo) values("777-300","Boeing",200,200,200,200,200,"4F",3,20,50,100,200)

INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace) values("SIN","Changi","Singapore","Singapore","spec1","UTC+08:00","opstatus1","strlevel1","airspace1")
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace) values("XIA","Xianyang","Xi'an","China","spec2","UTC+08:00","opstatus2","strlevel2","airspace2")

INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,flightLogId,maintenanceLogId) values("A380","9V-ABC","0000001","In Service","09/01/2015","09/01/2015","08/30/2025",0000001,0000001)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,flightLogId,maintenanceLogId) values("777-300","9V-AAA","0000002","In Service","09/01/2015","09/01/2013","08/30/2025",0000002,0000002)

INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status) values(2,10000,12,2000,2500,1500,1000,"A380","SIN","XIA","Pending")
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status) values(3,15000,15,2000,2500,1500,1000,"777-300","XIA","SIN","Pending")

INSERT IGNORE INTO Adminstaff(ID,ADMNAME,ADMPASSWORD,STFTYPE) values(1,"admin", "admin", "administrator")
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

