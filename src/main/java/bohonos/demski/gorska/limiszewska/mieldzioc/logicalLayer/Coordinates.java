package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

/**
 * 
 * @author Dominik Demski
 *
 */
public class Coordinates {
	
	private int row;
	private int column;
	
	public Coordinates(int row, int column) {
		this.row = row;
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
        
        //poni¿sze metody zwracaj¹ s¹siednie pola (o ile mieszcz¹ siê na planszy)
        public Coordinates getTop() {
            if (row>0) return new Coordinates(row-1, column);
            return null;
        }
	
        public Coordinates getBottom() {
            if (row<16) return new Coordinates(row+1, column);
            return null;
        }
	
        public Coordinates getLeft() {
            if (column>0) return new Coordinates(row, column-1);
            return null;
        }
	
        public Coordinates getRight() {
            if (column<20) return new Coordinates(row, column+1);
            return null;
        }

	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		
		Coordinates o2 = (Coordinates) o;
		
		return row == o2.row && column == o2.column;
	}
}
