package engine;

import java.util.HashMap;

public class MenuItem {
	public boolean highlighted;
	public String text;
	public int x;
	public int y;
	public String goesTo;
	public HashMap<String, String> otherInformation;
	
	public MenuItem(String text, int x, int y, String goesTo) {
		this.highlighted = false;
		this.text = text;
		this.x = x;
		this.y = y;
		this.goesTo = goesTo;
		this.otherInformation = new HashMap<String, String>();
	}
	
	public String Option() {
		return this.goesTo;
	}
	
	public int GetX() {
		return this.x;
	}
	
	public int GetY() {
		return this.y;
	}
	
	public String GetText() {
		return this.text;
	}
	
	public boolean IsHighlighted() {
		return this.highlighted;
	}
	
	public void Highlight() {
		this.highlighted = true;
	}
	
	public void UnHighlight() {
		this.highlighted = false;
	}
	
	public void DeHighlight() {
		this.highlighted = false;
	}
}
