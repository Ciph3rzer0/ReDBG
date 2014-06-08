package net.umc.ludumdare.tools;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Button {

	private Image _image;
	private String _text;
	private float _x, _y, _width, _height;
	private boolean _hovering, _clicked;
	
	public Button (float x, float y, float width, float height) {
		_x = x;
		_y = y;
		_width = width;
		_height = height;
	}
	
	public Button(float x, float y, float width, float height, Image image, String text) {
		this(x, y, width, height);
		_image = image;
		_text = text;
	}
	
	public Button(float x, float y, float width, float height, Image image) {
		this(x, y, width, height, image, "");
	}
	
	public Button(float x, float y, float width, float height, String text) {
		this(x, y, width, height, null, text);
	}
	
	public void update(float x, float y, boolean clicked) {
		_hovering = isHovering(x, y);
		_clicked = isClicked(clicked);
	}
	
	public boolean isHovering(float x, float y) {
		return !(x < _x || x > _x + _width || y < _y || y > _y + _height);
	}
	
	private boolean isClicked(boolean clicked) {
		return _hovering && clicked;
	}
	
	public boolean isClicked() {
		return _clicked;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (_image != null)
		{
			if (_clicked) {
				g.drawImage(_image, _x, _y, Color.red);
			}
			else if (_hovering) {
				g.drawImage(_image, _x, _y, Color.blue);
			}
			else {
				g.drawImage(_image, _x, _y);
			}
		}
		
		if (_text == null || _text.isEmpty()) return;
		int height = g.getFont().getHeight("S");
		int width = g.getFont().getWidth(_text);
		g.drawString(_text, _x + ((_width - width) / 2), _y + ((_height - height) / 2));
	}
	
	public void setImage(Image image) { _image = image; }
}
