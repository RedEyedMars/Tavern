package entities.heroes.classes;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

	private static List<String> sylables = new ArrayList<String>();
	private static Character[] consanents = new Character[]{'b','c','d','f','g','h','j','k','l','m','n','p','q','s','t','v','w','x','y','z'};
	private static Character[] vowels = new Character[]{'a','e','i','o','u','y'};
	
	public static void init(){
		for(double chance=0.01f;Math.random()>chance;chance+=0.0015f){
			String syl = (Math.random()>0.25f?consanents[(int) (consanents.length*Math.random())].toString():"")+
					(Math.random()>0.25f?vowels[(int) (vowels.length*Math.random())]:"")+
					(Math.random()>0.85f?consanents[(int) (consanents.length*Math.random())].toString():"")+
					(Math.random()>0.85f?vowels[(int) (vowels.length*Math.random())]:"");
			if(syl.length()>1){
				sylables.add(syl);
			}
		}
	}

	public static String getName() {
		StringBuilder builder = new StringBuilder();
		for(double chance=0.1f;Math.random()>chance;chance+=0.075f){
			builder.append(sylables .get((int) (sylables.size()*Math.random())));
		}
		String name = builder.toString();
		return name.length()>2&&name.length()<10?name.substring(0,1).toUpperCase()+name.substring(1):getName();
	}

}
