package entities.heroes.histories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class History implements Iterable<HistoryEvent>{
	private List<HistoryEvent> events;
	
	public History(){
		events = new ArrayList<HistoryEvent>();
	}

	public void append(HistoryEvent event) {
		events.add(event);
	}
	
	public void deappend(HistoryEvent event) {
		for(int i=events.size()-1;i>=0;--i){
			if(events.get(i).equals(event)){
				events.remove(i);
			}
		}
	}

	@Override
	public Iterator<HistoryEvent> iterator() {
		return events.iterator();
	}

	public int length() {
		return events.size();
	}
}
