package com.zhongdan.games.chinesechess;

import com.zhongdan.games.chinesechess.GameConstants.GameSettings;

public class PosNode {

	private int row;
	private int col;

	public PosNode(PosNode pos) {
		this.row = pos.getRow();
		this.col = pos.getCol();
	}

	public PosNode(String pos) {
		this.row = pos.charAt(1) - '0';
		this.col = pos.charAt(0) - 'a';
	}

	public PosNode(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public String getPos() {
		String pos = "";
		pos += (char) (col + 'a');
		pos += (char) (row + '0');
		return pos;
	}

	public int getPieceX() {
		return GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * col;
	}

	public int getPieceY() {
		return GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT * (9 - row);
	}

	public int getCursorX() {
		return GameSettings.CUR_START_X + GameSettings.CELL_WIDTH * col;
	}

	public int getCursorY() {
		return GameSettings.CUR_START_Y + GameSettings.CELL_HEIGHT * (9 - row);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PosNode) {
			PosNode pos = (PosNode) obj;
			return this.row == pos.getRow() && this.col == pos.getCol();
		}
		return false;
	}

}
