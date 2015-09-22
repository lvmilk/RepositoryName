/*
 *  Copyright 2001-2012 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.time;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.FixedDateTimeZone;

/**
 * Test testing.
 */
public class TempTest {

    private static final DateTimeZone SYDNEY = DateTimeZone.forID("Australia/Sydney");
    private static final DateTimeZone PARIS = DateTimeZone.forID("Europe/Paris");
    private static final DateTimeZone GMT = DateTimeZone.forID("GMT");

    private static void setTimeZone(String timeZoneID) {
        setTimeZone(TimeZone.getTimeZone(timeZoneID), DateTimeZone.forID(timeZoneID));
    }

    private static void setTimeZone(TimeZone timeZone, DateTimeZone dateTimeZone) {
        System.setProperty("user.timezone", timeZone.getID());
        TimeZone.setDefault(timeZone);
        DateTimeZone.setDefault(dateTimeZone);
    }

    public static void main(String[] args) throws Exception {
        
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss z").withLocale(Locale.US)
                        .withZone(GMT);

        String date = formatter.print(new Date().getTime());
        System.out.println("date = " + date);
        
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss z yyyy");
//        SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//        
//        setTimeZone("GMT");
//        System.out.println(formatter.print(new DateTime(2001, 12, 22, 23, 59, 59, 0)));;
//        Date time = new GregorianCalendar(2001, 11, 22, 23, 59, 59).getTime();
//        f.setTimeZone(TimeZone.getTimeZone("GMT"));
//        System.out.println(f.format(time));
//        setTimeZone("Europe/London");
//        System.out.println(formatter.print(new DateTime(2001, 12, 22, 23, 59, 59, 0)));;
//        System.out.println(formatter.print(new DateTime(2001, 6, 22, 23, 59, 59, 0)));;
    
//        DateTimeZone asm = DateTimeZone.forID("Africa/Asmara");
//        System.out.println(asm);
//        long last = Long.MIN_VALUE;
//        long next = asm.nextTransition(last);
//        while (next != last) {
//            System.out.println(next);
//            last = next;
//            next = asm.nextTransition(last);
//        }
//        DateTimeZone nai = DateTimeZone.forID("Africa/Nairobi");
//        long last2 = Long.MIN_VALUE;
//        long next2 = nai.nextTransition(last2);
//        while (next2 != last2) {
//            System.out.println(next2);
//            last2 = next2;
//            next2 = asm.nextTransition(last2);
//        }
//        System.out.println(nai);
//        System.out.println(DateTimeZone.forID("Africa/Asmera"));
        
        
//        DateTime dt = new DateTime(SYDNEY);
//        
//        Set<String> availableIds = DateTimeZone.getAvailableIDs();
//        for (String availableId : availableIds) {
////            if (availableId.startsWith("Austr")) {
//                DateTimeZone zone = DateTimeZone.forID(availableId);
//                String shortName = zone.getShortName(dt.getMillis());
////                if (shortName.startsWith("-") || shortName.startsWith("+") || shortName.length() < 3) {
////                    System.out.println(shortName + " " + zone.getID() + " " + zone.isStandardOffset(dt.getMillis()));
////                }
////            }
//        }
//        
        
//        Years years = Years.yearsBetween(new LocalDate(1996, 2, 28), new LocalDate(2014, 2, 27));
//        System.out.println(years.getYears());
//        Years years2 = Years.yearsBetween(new LocalDate(1996, 2, 29), new LocalDate(2014, 2, 28));
//        System.out.println(years2.getYears());
        
//        DateTimeZone zone = new DateTime().getZone();
//        DateTime mar1 = new DateTime(2014, 3, 1, 0, 0, 0, 0, zone);
//        DateTime feb28 = new DateTime(2014, 2, 28, 0, 0, 0, 0, zone);
//        
//        System.out.println(new Period(new DateTime(1996, 2, 27, 0, 0, 0, 0, zone), mar1));
//        System.out.println(new Period(new LocalDate(1996, 2, 27), mar1.toLocalDate()));
//        System.out.println(new LocalDate(1996, 2, 27).plusYears(18).plusDays(2));
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 2, 28, 0, 0, 0, 0, zone), mar1));
//        System.out.println(new Period(new LocalDate(1996, 2, 28), mar1.toLocalDate()));
//        System.out.println(new LocalDate(1996, 2, 28).plusYears(18).plusDays(1));
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 2, 29, 0, 0, 0, 0, zone), mar1));
//        System.out.println(new Period(new LocalDate(1996, 2, 29), mar1.toLocalDate()));
//        System.out.println(new LocalDate(1996, 2, 29).plusYears(18).plusDays(1));
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 3, 1, 0, 0, 0, 0, zone), mar1));
//        System.out.println(new Period(new LocalDate(1996, 3, 1), mar1.toLocalDate()));
//        System.out.println(new LocalDate(1996, 3, 1).plusYears(18).plusDays(0));
//        
//        System.out.println("----");
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 2, 27, 0, 0, 0, 0, feb28.getZone()), feb28));
//        System.out.println(new Period(new LocalDate(1996, 2, 27), feb28.toLocalDate()));
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 2, 28, 0, 0, 0, 0, feb28.getZone()), feb28));
//        System.out.println(new Period(new LocalDate(1996, 2, 28), feb28.toLocalDate()));
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 2, 29, 0, 0, 0, 0, feb28.getZone()), feb28));
//        System.out.println(new Period(new LocalDate(1996, 2, 29), feb28.toLocalDate()));
//        System.out.println("----");
//        System.out.println(new Period(new DateTime(1996, 3, 1, 0, 0, 0, 0, feb28.getZone()), feb28));
//        System.out.println(new Period(new LocalDate(1996, 3, 1), feb28.toLocalDate()));
        
//        LocalDate ld = new LocalDate(-2422054408000L);
//        System.out.println(ld);
//        System.out.println(ld.year().isLeap());
//        System.out.println(ld.monthOfYear().isLeap());
//        System.out.println(ld.dayOfMonth().isLeap());
//        System.out.println(ld.dayOfYear().isLeap());
        
//        new Partial(new DateTimeFieldType[] { DateTimeFieldType.yearOfEra(), DateTimeFieldType.yearOfCentury() }, new int [] { 1, 1});
        
//        new Partial(DateTimeFieldType.weekyear(),  1).with(DateTimeFieldType.yearOfCentury(), 1);
//        new Partial(DateTimeFieldType.year(),  1).with(DateTimeFieldType.yearOfCentury(), 1);
//        new Partial(DateTimeFieldType.year(),  1).with(DateTimeFieldType.yearOfEra(), 1);
//        new Partial(DateTimeFieldType.year(),  1).with(DateTimeFieldType.weekyearOfCentury(), 1);
        
//        DurationField base1 = ISOChronology.getInstance().hourOfDay().getDurationField();
//        DurationField range1 = ISOChronology.getInstance().hourOfDay().getRangeDurationField();
//        DurationField base2 = ISOChronology.getInstance().clockhourOfDay().getDurationField();
//        DurationField range2 = ISOChronology.getInstance().clockhourOfDay().getRangeDurationField();
//        assert base1.equals(base2);
//        assert range1.equals(range2);
//        
//        
//        new Partial(new DateTimeFieldType[] { DateTimeFieldType.dayOfYear(), DateTimeFieldType.dayOfMonth() }, new int [] { 1, 1});
//        new Partial(new DateTimeFieldType[] { DateTimeFieldType.yearOfCentury(), DateTimeFieldType.yearOfEra() }, new int [] { 1, 1});
//        new Partial(new DateTimeFieldType[] { DateTimeFieldType.year(), DateTimeFieldType.yearOfEra() }, new int [] { 1, 1});
////        new Partial(new DateTimeFieldType[] { DateTimeFieldType.yearOfEra(), DateTimeFieldType.year() }, new int [] { 1, 1});
//        new Partial(new DateTimeFieldType[] { DateTimeFieldType.secondOfDay(), DateTimeFieldType.secondOfMinute() }, new int [] { 1, 1});
//        
//        DateTimeFormatterBuilder a = new DateTimeFormatterBuilder();
//        a.append(DateTimeFormat.forPattern("HH:mm"));
//        a.appendOptional(DateTimeFormat.forPattern(":ss").getParser());
//        a.appendOptional(DateTimeFormat.forPattern(".SSS").getParser());
//        a.append(DateTimeFormat.forPattern(":ss.SSS").getPrinter());
//        DateTimeFormatter f = a.toFormatter(); 
//        DateTime c = f.parseDateTime("05:04:59");
//        System.out.println(f.print(c));
        
//        Chronology chronology = GJChronology.getInstance();
//
//        LocalDate start = new LocalDate(2013, 5, 31, chronology);
//        LocalDate expectedEnd = new LocalDate(-1, 5, 31, chronology); // 1 BC
//        System.out.println(start.minusYears(2012));
//        System.out.println(start.minusYears(2013));
//        System.out.println(expectedEnd);
//        System.out.println("-----");
//        System.out.println(start.plus(Period.years(-2013)));
//        System.out.println(expectedEnd);
        
//        DateTime dateTimeBefore = new DateTime(2012, 10, 28, 2, 59, 0, 0, DateTimeZone.forID("+02:00"));
//        System.out.println(dateTimeBefore);
//        DateTime dateTimeAfter = dateTimeBefore.withSecondOfMinute(0);
//        System.out.println(dateTimeAfter);
//        System.out.println(dateTimeBefore.equals(dateTimeAfter));
//
//        DateTime dateTimeBefore2 = new DateTime(2012, 10, 28, 2, 59, 0, 0, DateTimeZone.forID("Europe/Berlin"));
//        System.out.println(dateTimeBefore2);
//        DateTime dateTimeAfter2 = dateTimeBefore2.withSecondOfMinute(0);
//        System.out.println(dateTimeAfter2);
//        System.out.println(dateTimeBefore2.equals(dateTimeAfter2));
//
//        DateTime dateTimeBefore3 = new DateTime(2012, 10, 28, 1, 59, 0, 0, DateTimeZone.forID("Europe/Berlin"));
//        dateTimeBefore3 = dateTimeBefore3.plusMinutes(60); // 2:59 +02:00 with Europe/Berlin
//        System.out.println(dateTimeBefore3);
//        DateTime dateTimeAfter3 = dateTimeBefore2.withSecondOfMinute(0);
//        System.out.println(dateTimeAfter3);
//        // DateTimeZone changed from +02:00 to +01:00
//        System.out.println(dateTimeBefore3.equals(dateTimeAfter3));


//        final DateTime start = new LocalDateTime(2012, 11, 4, 1, 30).toDateTime(DateTimeZone.forID("America/New_York"));
//        final DateTime end = new LocalDateTime(2012, 11, 4, 2, 0).toDateTime(DateTimeZone.forID("America/New_York"));
//        System.out.println(start);
//        System.out.println(end);
//
//        final MutableInterval interval = new MutableInterval(start, end);
//        System.out.println(interval);
//        System.out.println(new Period(interval));
//        System.out.println(Hours.hoursIn(interval));
//        System.out.println(Hours.hoursIn(interval).getHours());
//        
//        // Period is correctly PT1H30, 1 == 1
//        Assert.assertEquals(interval.toPeriod().getHours(), Hours.hoursIn(interval).getHours());
//
//        interval.setStart(interval.getStart().plusHours(1));
//        System.out.println("-----");
//        System.out.println(interval);
//        System.out.println(new Period(interval));
//        System.out.println(Hours.hoursIn(interval));
//        System.out.println(Hours.hoursIn(interval).getHours());
//        // Period is incorrectly PT1H30, 1 == 0
//        Assert.assertEquals(Hours.hoursIn(interval).getHours(), interval.toPeriod().getHours());
        
//        DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime("00.10.2010");
        
//        DateTime dateTime = new DateTime("2010-10-10T04:00:00",
//        DateTimeZone.forID("America/Caracas"));
//        // time zone is -04:30 -- UTC date time is 2010-10-09T23:30
//
//        System.out.println(dateTime + " " + dateTime.getChronology());
//        MutableDateTime mutableDateTime = dateTime.toMutableDateTime();
//        mutableDateTime.setDate(dateTime); // is essentially a no-op
//        System.out.println(mutableDateTime + " " + mutableDateTime.getChronology());

//        Expected is: 2010-10-10T04:00:00.000-04:30
//        Actual result is: 2010-10-09T04:00:00.000-04:30        
        
        
//      DateTime dt = new DateTime(1, 1, 1, 0, 0, 0, ISOChronology.getInstanceUTC());
//      System.out.println(dt + " " + dt.toString("yyyy YYYY GG"));
//      dt = dt.minusDays(1);
//      System.out.println(dt + " " + dt.toString("yyyy YYYY GG"));
//      
//      dt = new DateTime(1, 1, 1, 0, 0, 0, GregorianChronology.getInstanceUTC());
//      System.out.println(dt + " " + dt.toString("yyyy YYYY GG"));
//      dt = dt.minusDays(1);
//      System.out.println(dt + " " + dt.toString("yyyy YYYY GG"));
//      
//      dt = new DateTime(100000, 1, 1, 0, 0, 0, GregorianChronology.getInstanceUTC());
//      System.out.println(dt + " " + dt.toString("yyyy YYYY GG"));
//      
//      dt = new DateTime(-100000, 1, 1, 0, 0, 0, GregorianChronology.getInstanceUTC());
//      System.out.println(dt + " " + dt.toString("yyyy YYYY GG"));
      
//      DateTime birth = new DateTime(2012, 03, 31, 02, 28, 0, 0).minus(Years.years(27));
//      DateTime now = new DateTime(2012, 03, 31, 02, 28, 33, 0);
//      System.out.println(birth);
//      System.out.println(now);
//      System.out.println(new Period(birth, now));
      
//      // From the first test 
//      // Comment out (in) the following call in order to make the test pass (fail). 
//      DateTimeZone p1 = DateTimeZone.forID("Europe/Paris");
//      System.out.println(p1 + " " + System.identityHashCode(p1));
//      DateTimeZone.setDefault(p1); 
//      new DateMidnight(2004, 6, 9); 
//      // From the first test 
//      
//      // From the second test 
//      DateTimeZone.setProvider(null); 
//      // From the second test 
//      
//      // From the third test 
//      DateTimeZone p2 = DateTimeZone.forID("Europe/Paris");
//      System.out.println(p2 + " " + System.identityHashCode(p2));
//      DateTimeZone.setDefault(p2); 
//      
//      DateTime test = new DateTime(0); 
//      DateTime result = test.withZoneRetainFields(p2); 
//      if (test != result) {
//        throw new IllegalArgumentException();
//      }
    }

}
