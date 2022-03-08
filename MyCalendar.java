/**
 * Name: Nathan Morales
 * ID: A17073905
 * Email: njmorales@ucsd.edu
 * Sources used: zybooks
 * 
 * This file contains the class MyCalendar. MyCalendar uses the adapter method
 * to contin a MyTreeMap which extends MyBST, allowing various events and 
 * times to be stored in the tree. 
 */

 /**
 * MyCalendar allows the booking of events based on their start and end time.
 * Double-booking is not allowed, so the start and end times of different
 * events are not allowed to collide with each other. The key of a node in 
 * MyCalendar represents an event's start time, and the value of that node
 * represents the event's end time. 
 */
public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;
    
    /**
     * Initializes the calendar object.
     */
    public MyCalendar() {
        this.calendar = new MyTreeMap<>();
    }
    
    /**
     * Attempt to book an event from time start to time end. 
     * If adding the event does not cause a double booking, 
     * add the event and return true.
     * @param start - start time of the event (key)
     * @param end - end time of the event (value)
     * @return true if the event was successfully added without collision
     */
    public boolean book(int start, int end) {
        if (start < 0 || start >= end) { //invalid time input
            throw new IllegalArgumentException();
        }
        Integer prevStart = calendar.floorKey(start);
        Integer prevEnd = calendar.get(prevStart);
        Integer nextStart = calendar.ceilingKey(start);
        if (prevStart == null && nextStart == null) { //empty calendar
            calendar.put(start, end);
            return true;
        }
        //no preceding event
        else if (prevStart == null) {
            //end of this event is before the start of the next event
            if (end <= nextStart) {
                calendar.put(start, end);
                return true;
            }
        }
        //no next event
        else if (nextStart == null) {
            //start of this event is after the end of the previous event
            if (start >= prevEnd) {
                calendar.put(start, end);
                return true;
            }
        }
        /**
         * start of this event is after the end of the previous event
         * AND end of this event is before the start of the next event
         */
        else if (start >= prevEnd && end <= nextStart) {
            calendar.put(start, end);
            return true;
        }
        //start and end are not within an available timeslot
        return false;
    }

    /**
     * @return this calendar of type MyTreeMap
     */
    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}