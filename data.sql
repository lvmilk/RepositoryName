INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo) values("A380","Airbus",100,100,100,100,100,"4F",5,20,80,150,100)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo) values("777-300","Boeing",200,200,200,200,200,"4F",3,20,50,100,200)

INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryCode,spec, timeZone,opStatus,strategicLevel, airspace) values("SIN","Changi","Singapore","Singapore","spec1","UTC+08:00","opstatus1","strlevel1","airspace1")
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryCode,spec, timeZone,opStatus,strategicLevel, airspace) values("XIA","Xianyang","Xi'an","China","spec2","UTC+08:00","opstatus2","strlevel2","airspace2")

INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,flightLogId,maintenanceLogId) values("A380","9V-ABC","0000001","In Service","09/01/2015","09/01/2015","08/30/2025",0000001,0000001)
INSERT IGNORE INTO Aircraft (aircraftType_type,registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,flightLogId,maintenanceLogId) values("777-300","9V-AAA","0000002","In Service","09/01/2015","09/01/2013","08/30/2025",0000002,0000002)

INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status) values(2,10000,12,2000,2500,1500,1000,"A380","SIN","XIA","Pending")
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata,status) values(3,15000,15,2000,2500,1500,1000,"777-300","XIA","SIN","Pending")



