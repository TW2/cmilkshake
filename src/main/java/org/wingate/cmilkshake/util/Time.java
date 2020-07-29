/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wingate.cmilkshake.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a tool for time events.
 * @author The Wingate 2940
 */
    public class Time{
        
        private int hours = 0;
        private int minutes = 0;
        private int seconds = 0;
        private int milliseconds = 0;
        
        public Time(){
            
        }
        
        public static Time create(String s){
            Pattern p = Pattern.compile("(\\d+).{1}(\\d+).{1}(\\d+).{1}(\\d+)");
            Matcher m = p.matcher(s);
            
            if(m.matches()){
                int h = Integer.parseInt(m.group(1));
                int min = Integer.parseInt(m.group(2));
                int sec = Integer.parseInt(m.group(3));
                int ms = Integer.parseInt(m.group(4)) * 10;
                
                return create(h, min, sec, ms);
            }else{
                return create(0L);
            }
        }
        
        public static Time create(long milliseconds){
            return fromMillisecondsTime(milliseconds);
        }
        
        public static Time create(int hours, int minutes, int seconds, int milliseconds){
            Time t = new Time();
            
            t.hours = hours;
            t.minutes = minutes;
            t.seconds = seconds;
            t.milliseconds = milliseconds;
            
            return t;
        }
        
        // <editor-fold defaultstate="collapsed" desc=" get/set Time components ">
        
        /** <p>Get the hours.<br />Obtient les heures.</p> */
        public int getHours(){
            return hours;
        }
        
        /** <p>Get the minutes.<br />Obtient les minutes.</p> */
        public int getMinutes(){
            return minutes;
        }
        
        /** <p>Get the seconds.<br />Obtient les secondes.</p> */
        public int getSeconds(){
            return seconds;
        }
        
        /** <p>Get the milliseconds.<br />Obtient les millisecondes.</p> */
        public int getMilliseconds(){
            return milliseconds;
        }
        
        /** <p>Set the hours.<br />Définit les heures.</p> */
        public void setHours(int hours){
            this.hours = hours;
        }
        
        /** <p>Set the minutes.<br />Définit les minutes.</p> */
        public void setMinutes(int minutes){
            this.minutes = minutes;
        }
        
        /** <p>Set the seconds.<br />Définit les secondes.</p> */
        public void setSeconds(int seconds){
            this.seconds = seconds;
        }
        
        /** <p>Set the milliseconds.<br />Définit les millisecondes.</p> */
        public void setMilliseconds(int milliseconds){
            this.milliseconds = milliseconds;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Addition / Substraction ">
        
        /** <p>Add Time t1 to Time t2.<br />Ajoute le temps t1 au temps t2.<br />
         * <tt>t = t1 + t2</tt>.</p> */
        public static Time addition(Time t1, Time t2){
            Time t;
            
            long lt1 = toMillisecondsTime(t1);
            long lt2 = toMillisecondsTime(t2);
            
            long lt = lt1 + lt2;
            
            t = fromMillisecondsTime(lt);
            
            return t;
        }
        
        /** <p>Substract Time t2 to Time t1.<br />Soustrait le temps t2 au temps t1.<br />
         * <tt>t = t2 - t1</tt>. * </p> */
        public static Time substract(Time t1, Time t2){
            Time t;
            
            long lt1 = toMillisecondsTime(t1);
            long lt2 = toMillisecondsTime(t2);
            
            long lt = Math.max(lt1, lt2) - Math.min(lt1, lt2);
            
            t = fromMillisecondsTime(lt);
            
            return t;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Conversion Time <> milliseconds ">
        
        /** <p>Convert Time object to milliseconds.<br />
         * Convertit un objet Time en millisecondes.</p> */
        public static long toMillisecondsTime(Time t){
            long mst;
            
            mst = t.getHours()*3600000
                    + t.getMinutes()*60000
                    + t.getSeconds()*1000
                    + t.getMilliseconds();
            
            return mst;
        }
        
        /** <p>Convert milliseconds to Time object.<br />
         * Convertit des millisecondes en un objet Time.</p> */
        public static Time fromMillisecondsTime(long mst){
            Time t = new Time();
            
            int hour = (int)(mst / 3600000);
            int min = (int)((mst - 3600000 * hour) / 60000);
            int sec = (int)((mst - 3600000 * hour - 60000 * min) / 1000);
            int mSec = (int)(mst - 3600000 * hour - 60000 * min - 1000 * sec);
            
            t.setHours(hour);
            t.setMinutes(min);
            t.setSeconds(sec);
            t.setMilliseconds(mSec);
            
            return t;
        }
        
        // </editor-fold>
        
        /** <p>Get Time.<br />Obtient le temps.</p> */
        public Time getTime(){
            return this;
        }
        
        /** <p>Set Time.<br />Définit le temps.</p> */
        public void setTime(Time t){
            this.hours = t.getHours();
            this.minutes = t.getMinutes();
            this.seconds = t.getSeconds();
            this.milliseconds = t.getMilliseconds();
        }
        
        // <editor-fold defaultstate="collapsed" desc=" Import / export ">
        
        /** <p>Get Time in ASS format.<br />
         * Obtient le temps au format ASS.</p> */
        public String toASSTime(){
            String Smin, Ssec, Scent;
            
            int hour = getHours();
            int min = getMinutes();
            int sec = getSeconds();
            int cSec = getMilliseconds()/10;
            
            if (min<10){Smin = "0"+min;}else{Smin = String.valueOf(min);}
            if (sec<10){Ssec = "0"+sec;}else{Ssec = String.valueOf(sec);}
            if (cSec<10){Scent = "0"+cSec;}else{Scent = String.valueOf(cSec);}

            return hour + ":" + Smin + ":" + Ssec + "." + Scent;
        }
        
        /** <p>Get Time in Program based ASS format.<br />
         * Obtient le temps au format du programme pour de l'ASS.</p> */
        public String toProgramBasedASSTime(){
            String Smin, Ssec, Scent;
            
            int hour = getHours();
            int min = getMinutes();
            int sec = getSeconds();
            int cSec = getMilliseconds()/10;
            
            if (min<10){Smin = "0"+min;}else{Smin = String.valueOf(min);}
            if (sec<10){Ssec = "0"+sec;}else{Ssec = String.valueOf(sec);}
            if (cSec<10){Scent = "0"+cSec;}else{Scent = String.valueOf(cSec);}

            return hour + "." + Smin + "." + Ssec + "." + Scent;
        }
        
        /** <p>Get Time in Program extended format.<br />
         * Obtient le temps au format étendu du programme.</p> */
        public String toProgramExtendedTime(){
            String Smin, Ssec, Smilli;
            
            int hour = getHours();
            int min = getMinutes();
            int sec = getSeconds();
            int mSec = getMilliseconds();
            
            if (min<10){Smin = "0"+min;}else{Smin = String.valueOf(min);}
            if (sec<10){Ssec = "0"+sec;}else{Ssec = String.valueOf(sec);}
            if (mSec<10){
                Smilli = "00"+mSec;
            }else if (mSec<100){
                Smilli = "0"+mSec;
            }else{
                Smilli = String.valueOf(mSec);
            }

            return hour + "." + Smin + "." + Ssec + "." + Smilli;
        }
        
        // </editor-fold>
        
        public static boolean isTimeBetween(Time current, Time start, Time end){
            long msCurrent = Time.toMillisecondsTime(current);
            long msStart = Time.toMillisecondsTime(start);
            long msEnd = Time.toMillisecondsTime(end);
            
            return msStart <= msCurrent && msCurrent < msEnd;
        }
        
        public static boolean isTimeBetween(Time from, Time to, Time start, Time end){
            long msFrom = Time.toMillisecondsTime(from);
            long msTo = Time.toMillisecondsTime(to);
            long msCurrent = (msTo - msFrom) / 2L;
            long msStart = Time.toMillisecondsTime(start);
            long msEnd = Time.toMillisecondsTime(end);
            
            return msStart <= msFrom && msTo < msEnd 
                    | msStart <= msCurrent && msCurrent < msEnd
                    | msFrom <= msStart && msEnd < msTo;
        }
        
        // <editor-fold defaultstate="collapsed" desc=" Logical ">
        
        // Same time = start_A == start_B & end_A == end_B
        public static boolean isSameTime(Time start_A, Time end_A, Time start_B, Time end_B){
            long sa = Time.toMillisecondsTime(start_A);
            long ea = Time.toMillisecondsTime(end_A);
            long sb = Time.toMillisecondsTime(start_B);
            long eb = Time.toMillisecondsTime(end_B);
            return sa == sb & ea == eb;
        }
        
        // Shift time = start_A != start_B & total_A == total_B
        public static boolean isShiftTime(Time start_A, Time end_A, Time start_B, Time end_B){
            Time total_A = Time.substract(start_A, end_A);
            Time total_B = Time.substract(start_B, end_B);
            long sa = Time.toMillisecondsTime(start_A);
            long sb = Time.toMillisecondsTime(start_B);
            long ta = Time.toMillisecondsTime(total_A);
            long tb = Time.toMillisecondsTime(total_B);
            return sa != sb & ta == tb;
        }
        
        // Enlarged = total_A < total_B
        public static boolean isEnlargedTime(Time start_A, Time end_A, Time start_B, Time end_B){
            Time total_A = Time.substract(start_A, end_A);
            Time total_B = Time.substract(start_B, end_B);
            long ta = Time.toMillisecondsTime(total_A);
            long tb = Time.toMillisecondsTime(total_B);
            return ta < tb;
        }
        
        // Reduced = total_A > total_B
        public static boolean isReducedTime(Time start_A, Time end_A, Time start_B, Time end_B){
            Time total_A = Time.substract(start_A, end_A);
            Time total_B = Time.substract(start_B, end_B);
            long ta = Time.toMillisecondsTime(total_A);
            long tb = Time.toMillisecondsTime(total_B);
            return ta > tb;
        }

        // Same start time = start_A == start_B
        public static boolean isSameStartTime(Time start_A, Time start_B){
            long sa = Time.toMillisecondsTime(start_A);
            long sb = Time.toMillisecondsTime(start_B);
            return sa == sb;
        }
        // Same end time = end_A == end_B
        public static boolean isSameEndTime(Time end_A, Time end_B){
            long ea = Time.toMillisecondsTime(end_A);
            long eb = Time.toMillisecondsTime(end_B);
            return ea == eb;
        }
        // Same duration time = dur_A == dur_B
        public static boolean isSameDurationTime(Time start_A, Time end_A, Time start_B, Time end_B){
            long sa = Time.toMillisecondsTime(start_A);
            long sb = Time.toMillisecondsTime(start_B);
            long ea = Time.toMillisecondsTime(end_A);
            long eb = Time.toMillisecondsTime(end_B);
            return ea - sa == eb - sb;
        }
        
        // </editor-fold>
    }
