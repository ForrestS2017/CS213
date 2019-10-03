/**
 * @author Forrest Smith
 * @author Jim Tang
 */

package Model;

import java.util.UUID;

public class Song {
    
	/**
	 * Required fields for a song
	 */
	private String name, artist, album, year;
	
	public Song(String name, String artist, String album, String year)
	{
		this.name = name.trim();
		this.artist = artist.trim();
		this.album = album.trim();
		this.year = year.trim();
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public boolean setArtist(String artist) {
		if (artist.isBlank()) return false;
		this.artist = artist;
		return true;
	}

	public boolean setName(String name) {
		if (name.isBlank()) return false;
		this.name = name;
		return true;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAlbum() {
		return this.album;
	}

	public String getArtist() {
		return this.artist;
	}
	
	public String getName() {
		return this.name;
	}

	public String getYear() {
		return this.year;
	}
}
