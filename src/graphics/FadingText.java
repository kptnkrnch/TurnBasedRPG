package graphics;

import org.newdawn.slick.Color;

public class FadingText {
	public String text;
	public Color text_color;
	public int fade_time;
	public int cur_fade_time;
	
	public FadingText(String text, int fade_time) {
		this.fade_time = fade_time * 1000;
		this.cur_fade_time = 0;
		this.text = text;
		this.text_color = new Color(255, 255, 255, 255);
	}
	
	public FadingText(String text, Color color, int fade_time) {
		this.fade_time = fade_time * 1000;
		this.text = text;
		this.text_color = new Color(color.r, color.g, color.b, 255);
	}
	
	public void SetFadeTime(int fade_time) {
		this.fade_time = fade_time;
	}
	
	public void Fade(int fps_scaler) {
		if (this.text_color != null && this.text_color.a > 0) {
			this.cur_fade_time += fps_scaler;
			float alpha = (float)(this.fade_time - this.cur_fade_time) / this.fade_time;
			if (alpha <= 0.0001) {
				this.text_color.a = 0;
				this.cur_fade_time = 0;
			} else {
				this.text_color.a = (alpha);
			}
		}
	}
	
	public boolean IsFaded() {
		if (this.text_color != null) {
			if (this.text_color.getAlpha() < 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
