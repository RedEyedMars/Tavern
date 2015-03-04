package game;

public class Timer {
	private static Time time = new Time();
	
	public void tick(){
		time.increment("ticks");
		time.increment("seconds");
		if(time.seconds()>=60){
			time.reset("seconds");
			time.increment("minutes");
		}
		if(time.minutes()>=60){
			time.reset("minutes");
			time.increment("hours");
		}
		if(time.hours()>=24){
			time.reset("hours");
			time.increment("days");
			switch(time.days()){
			case 0:time.set("day","Moonsday"); break;
			case 1:time.set("day","Starsday"); break;
			case 2:time.set("day","Sunsday"); break;
			case 3:time.set("day","Peddlesday"); break;
			case 4:time.set("day","Faunsday"); break;
			}
		}
		if(time.days()>=30){
			time.reset("days");
			time.increment("months");
			switch(time.months()){
			case 0:time.set("month","Deador"); break;
			case 1:time.set("month","Languinas"); break;
			case 2:time.set("month","Moonbane"); break;
			case 3:time.set("month","Spurnin"); break;
			case 4:time.set("month","Delfish"); break;
			case 5:time.set("month","Midsun"); break;
			case 6:time.set("month","Drover'lem"); break;
			case 7:time.set("month","Felling'lem"); break;
			case 8:time.set("month","Grild'lem"); break;
			case 9:time.set("month","Alstiporl"); break;
			case 10:time.set("month","Uurl"); break;
			}
			switch(time.months()){
			case 0:time.set("season","Summer"); break;
			case 3:time.set("season","Autumn"); break;
			case 5:time.set("season","Winter"); break;
			case 8:time.set("season","Spring"); break;
			}
		}
		if(time.months()>=11){
			time.reset("months");
			time.increment("years");
		}
	}
	
	@Override
	public String toString(){
		return time.toString();
	}

	public static long getTicks() {
		return time.ticks();
	}

	public static String getSeason() {
		return time.season();
	}

	public static Time getTime() {
		return time.clone();
	}
}
