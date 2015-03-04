package game;

public class Time implements Cloneable{

	private long ticks = 0;
	private byte seconds = 0;
	private byte minutes = 0;
	private byte hours = 0;
	private byte days = 0;
	private byte months = 0;
	private byte years = 0;
	private String day = "Moonday";
	private String month = "Midsun";
	private String season = "Summer";
	
	public long ticks(){ return ticks; }
	public byte seconds(){ return seconds; }
	public byte minutes(){ return minutes; }
	public byte hours(){ return hours; }
	public byte days(){ return days;}
	public byte months(){ return months; }
	public byte years(){ return years; }
	public String day(){ return day; }
	public String month(){ return month; }
	public String season(){ return season; }
	
	public void increment(String name){
		if("ticks".equals(name)){
			++ticks;
		}
		else if("seconds".equals(name)){
			++seconds;
		}
		else if("minutes".equals(name)){
			++minutes;
		}
		else if("hours".equals(name)){
			++hours;
		}
		else if("days".equals(name)){
			++days;
		}
		else if("months".equals(name)){
			++months;
		}
		else if("years".equals(name)){
			++years;
		}
	}
	
	public void reset(String name){
		if("ticks".equals(name)){
			ticks=0;
		}
		else if("seconds".equals(name)){
			seconds=0;
		}
		else if("minutes".equals(name)){
			minutes=0;
		}
		else if("hours".equals(name)){
			hours=0;
		}
		else if("days".equals(name)){
			days=0;
		}
		else if("months".equals(name)){
			months=0;
		}
		else if("years".equals(name)){
			years=0;
		}
	}
	
	public void set(String name, String value){
		if("day".equals(name)){
			day = value;
		}
		else if("month".equals(name)){
			month = value;
		}
		else if("season".equals(name)){
			season = value;
		}
		
	}

	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(years());
		builder.append(" ");
		builder.append(month());
		builder.append(" ");
		builder.append(day());
		builder.append(" ");
		builder.append(hours());
		builder.append(":");
		builder.append(minutes());
		return builder.toString();
	}

	@Override
	public Time clone(){
		Time time = new Time();
		time.ticks = ticks;
		time.seconds = seconds;
		time.minutes = minutes;
		time.hours = hours;
		time.days = days;
		time.months = months;
		time.years = years;
		time.day = day;
		time.month = month;
		time.season = season;
		return time;
	}
	
	public int difference(Time time){
		return difference(time,"ticks");
	}
	
	public int difference(Time time,String name){
		if("ticks".equals(name)){
			return (int) (time.ticks-ticks);
		}
		else if("seconds".equals(name)){
			return (int) (time.seconds-seconds);
		}
		else if("minutes".equals(name)){
			return (int) (time.minutes-minutes);
		}
		else if("hours".equals(name)){
			return (int) (time.hours-hours);
		}
		else if("days".equals(name)){
			return (int) (time.days-days);
		}
		else if("months".equals(name)){
			return (int) (time.months-months);
		}
		else if("years".equals(name)){
			return (int) (time.years-years);
		}
		else return -1;
	}
}
