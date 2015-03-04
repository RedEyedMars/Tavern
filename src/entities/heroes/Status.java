package entities.heroes;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Status implements Comparable<Status>{

	public static Map<String, Status> statuses = new HashMap<String,Status>();
	public static final Status Wounded = new Status("Wounded","Healthy");
	public static final Status Healthy = new Status("Healthy","ALL");

	private String name;
	private Set<Status> excludes;
	private List<Status> status;
	private int id;
	public Status(){
		name = "Healthy";
		status = new ArrayList<Status>();
		status.add(Healthy);
		excludes = Healthy.excludes;
		id = 1;
	}

	private Status(String name,String... excludes){
		this.id = statuses.size();
		this.name = name;
		this.excludes = new TreeSet<Status>();
		for(String exclude:excludes){
			if(statuses.containsKey(exclude)){
				this.excludes.add(statuses.get(exclude));
			}
			else if(exclude.equals("ALL")){
				for(String stat:statuses.keySet()){
					if(!name.equals(stat)){
						this.excludes.add(statuses.get(stat));
					}
				}
			}
		}
	}

	public void change(Status status) {
		this.status.removeAll(status.excludes);
		Set<Status> oldStatuses = new TreeSet<Status>();
		oldStatuses.addAll(this.status);
		for(Status stat:oldStatuses){
			if(stat.excludes.contains(status)){
				this.status.remove(stat);
			}
		}
		this.status.add(status);
	}

	public String toString(){
		if(status!=null&&!status.isEmpty()){
			StringBuilder builder = new StringBuilder();
			String slash = "";
			for(Status status:this.status){
				builder.append(slash);
				builder.append(status.toString());
				slash = " / ";
			}
			return builder.toString();
		}
		else return name;
	}

	public void remove(Status wounded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Status o) {
		return o.id-id;
	}

}
