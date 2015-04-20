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
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		
		Coordinates o2 = (Coordinates) o;
		
		return row == o2.row && column == o2.column;
	}
}
