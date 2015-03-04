package entities;

public class Direction {
	public static Direction[] directions;
	public static Direction right = new Direction( 1, 0,1);
	public static Direction left  = new Direction(-1, 0,3);
	public static Direction up    = new Direction( 0, 1,0);
	public static Direction down  = new Direction( 0,-1,2);

	static
	{
		directions = new Direction[]{up,right,down,left};
	}

	private int x;
	private int y;
	private int index;
	public Direction(int x, int y,int index)
	{
		this.x = x;
		this.y = y;
		this.index = index;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public Direction getPositivePerpendicular() {
		for(Direction d:directions){
			if(d.x==y&&d.y==x){
				return d;
			}
		}
		return null;
	}
	public Direction getNegativePerpendicular() {
		for(Direction d:directions){
			if(d.x==-y&&d.y==-x){
				return d;
			}
		}
		return null;
	}
	public Direction getInverse() {
		for(Direction d:directions){
			if(d.x==-x&&d.y==-y){
				return d;
			}
		}
		return null;
	}
	@Override
	public boolean equals(Object o){
		return ((Direction)o).x==x&&((Direction)o).y==y;
	}
	@Override
	public String toString(){
		if(x==0&&y==-1){
			return "down";
		}
		if(x==1&&y==0){
			return "right";
		}
		if(x==0&&y==1){
			return "up";
		}
		if(x==-1&&y==0){
			return "left";
		}
		return "unknown:"+x+","+y;
	}
	public static Direction random() {
		return directions[(int) (directions.length*Math.random())];
	}
	public Direction getRight() {
		int i = index+1;
		if(i>3){
			i=0;
		}
		return directions[i];
	}
	public Direction getLeft() {
		int i = index-1;
		if(i<0){
			i=3;
		}
		return directions[i];
	}
}
