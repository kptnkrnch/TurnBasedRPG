package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EffectsController {
	public static ArrayList<Animation> effects;
	public static final int DUSK = 0;
	
	public static void Init() {
		effects = new ArrayList<Animation>();
		Image[] frames = new Image[1];
		int[] durations = new int[1];
		try {
			frames[0] = new Image("res/effects/dusk.png");
			frames[0].setAlpha(0.4f);
			durations[0] = 1000;
			Animation temp = new Animation(frames, durations);
			effects.add(temp);
		} catch (SlickException e) {
		}
	}
	
	public static Animation GetEffect(int effect) {
		if (effects != null && effect >= 0 && effect < effects.size()) {
			return effects.get(effect);
		} else {
			return null;
		}
	}
	
	public static void UpdateEffects(int fps_scaler) {
		if (effects != null) {
			for (int i = 0; i < effects.size(); i++) {
				Animation temp = effects.get(i);
				temp.update(fps_scaler);
				effects.set(i, temp);
			}
		}
	}
}
