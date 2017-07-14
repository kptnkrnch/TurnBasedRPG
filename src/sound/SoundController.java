package sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class SoundController {
	private static int current_song = 0;
	private static Music[] song = new Music[2];
	
	public static boolean LoadMusic(String song_location) {
		try {
			song[current_song] = new Music(song_location);
			return true;
		} catch (SlickException e) {
			return false;
		}
	}
	
	public static boolean LoadNextSong(String song_location) {
		int nextsong = 0;
		if (current_song == 1) {
			nextsong = 0;
		} else {
			nextsong = 1;
		}
		try {
			song[nextsong] = new Music(song_location);
			return true;
		} catch (SlickException e) {
			return false;
		}
	}
	
	public static void PlayMusic() {
		if (song[current_song] != null) {
			song[current_song].play();
		}
	}
	
	public static void PlayMusic(boolean loop) {
		if (song[current_song] != null) {
			if (loop) {
				song[current_song].loop();
			} else {
				song[current_song].play();
			}
		}
	}
	
	public static void StopMusic() {
		if (song[current_song] != null) {
			song[current_song].stop();
		}
	}
	
	public static void SetVolume(float volume) {
		if (song[current_song] != null) {
			song[current_song].setVolume(volume);
		}
	}
	
	public static boolean IsPlaying() {
		if (song[current_song] != null) {
			return song[current_song].playing();
		} else {
			return false;
		}
	}
	
	public static void SetNext() {
		int nextsong = 0;
		if (current_song == 1) {
			nextsong = 0;
		} else {
			nextsong = 1;
		}
		if (song[nextsong] != null) {
			current_song = nextsong;
		}
	}
	
	public static void PlayNext() {
		StopMusic();
		SetNext();
		PlayMusic();
	}
	
	public static void PlayNext(boolean loop) {
		StopMusic();
		SetNext();
		PlayMusic(loop);
	}
}
