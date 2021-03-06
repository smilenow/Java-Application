package miniCAD;

import java.awt.Image;

public class shape {
	// type
	static final int _line = 0;
	static final int _circle = 1;
	static final int _rectangle = 2;
	static final int _text = 3;
	static final int _image = 4;
	// color
	static final int _black = 0;
	static final int _blue = 1;
	static final int _green = 2;
	static final int _red = 3;
	static final int _yellow = 4;
	//
	int type = 0; // default line
	int color = 0; // default black
	int st_x = 0, st_y = 0, ed_x = 0, ed_y = 0; // position
	Image image;
	String text = null;
	
	public shape(){}
	
	public shape(shape that){
		this.type = that.type;
		this.color = that.color;
		this.st_x = that.st_x;
		this.st_y = that.st_y;
		this.ed_x = that.ed_x;
		this.ed_y = that.ed_y;
		this.text = that.text;
		this.image = that.image;
	}
}