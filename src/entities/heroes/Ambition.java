package entities.heroes;

import entities.heroes.quests.Quest;

public abstract class Ambition {

	private String name;
	
	public Ambition(String name){
		this.name = name;
	}
	
	public int getEvaluation(Quest quest) {
		int score = 0;
		for(Reward reward: quest.getReward()){
			score+=this.getEvaluation(reward);
		}
		return score;
	}
	
	public int getEvaluation(Reward reward){
		float score = getMonetaryCare()*reward.getMonetaryValue();
		      score+= getMoralityCare()*reward.getMoralityValue();
		return (int)score;
	}

    public abstract int getMoralityCare();

	public abstract int getMonetaryCare();
	
	public String getName(){
		return name;
	}

}
