package base;

public enum Direction {
	west, northwest, north, northeast, east, southeast, south, southwest;
	
	public static Direction[] all() {
		return new Direction[] {west, northwest, north, northeast, east, southeast, south, southwest};
	}
	
	public Direction getDirection(int x, int y) {
		int dx = Test.clamp(x, -1, 1);
		int dy = Test.clamp(y, -1, 1);
		if(dx == -1) {
			if(dy == -1)
				return northwest;
			if(dy == 0)
				return west;
			if(dy == 1)
				return southwest;
		} else if(dx == 0) {
			if(dy == -1)
				return north;
			if(dy == 1)
				return south;
		} else if(dx == 1) {
			if(dy == -1)
				return northeast;
			if(dy == 0)
				return east;
			if(dy == 1)
				return southeast;
		}
		
		return south;
	}
	
	public int getX() {
		switch(this) {
			case west:
			case northwest:
			case southwest:
				return -1;
			case north:
			case south:
				return 0;
			case northeast:
			case southeast:
			case east:
				return 1;
			default:
				return 0;
		}
	}
	
	public int getY() {
		switch(this) {
			case northwest:
			case north:
			case northeast:
				return -1;
			case east:
			case west:
				return 0;
			case southwest:
			case southeast:
			case south:
				return 1;
			default:
				return 0;
		}
	}

}
