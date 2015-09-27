INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo) values("A380","Airbus",100,100,100,100,100,100,100,100,100,100,100)
INSERT IGNORE INTO AircraftType (type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo,bcSeatNo,pecSeatNo,ecSeatNo) values("B737-300","Boeing",200,200,200,200,200,200,200,200,200,200,200)

INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace) values("SIN","Changi","Singapore","Singapore","spec1","UTC+08:00","opstatus1","strlevel1","airspace1")
INSERT IGNORE INTO Airport (IATA, airportName,cityName, countryName,spec, timeZone,opStatus,strategicLevel, airspace) values("XIA","Xianyang","Xi'an","China","spec2","UTC+08:00","opstatus2","strlevel2","airspace2")

INSERT IGNORE INTO Aircraft (registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,flightLogId,maintenanceLogId) values("A001","0000001","In Service","2015/09/01","2013/09/01","2025/08/30",0000001,0000001)
INSERT IGNORE INTO Aircraft (registrationNo,serialNo,status,firstFlyDate,deliveryDate,retireDate,flightLogId,maintenanceLogId) values("A002","0000002","In Service","2015/09/01","2013/09/01","2025/08/30",0000002,0000002)

INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata) values(2,10000,12,2000,2500,1500,1000,"A380","SIN","XIA")
INSERT IGNORE INTO Route (id,distance,blockhour,basicFcFare,basicBcFare, basicPecFare, basicEcFare,acType_type,dest_iata,origin_iata) values(3,15000,15,2000,2500,1500,1000,"B737-300","XIA","SIN")



