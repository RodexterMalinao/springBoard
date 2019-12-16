package com.bomwebportal.ims.dto.ui;

public class GenreUI {
	private String genreId;
	private String genreCd;
	private String genreDesc;
	private String genreClass;
	private String genreImagePath;
	
	public GenreUI(){}
	
	public GenreUI(GenreUI g){
		genreId = g.getGenreId();
		genreCd = g.getGenreCd();
		genreDesc = g.getGenreDesc();	
		genreImagePath = g.getGenreImagePath();
	}
	
	

	public String getGenreImagePath() {
		return genreImagePath;
	}

	public void setGenreImagePath(String genreImagePath) {
		this.genreImagePath = genreImagePath;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
	public String getGenreId() {
		return genreId;
	}
	public void setGenreCd(String genreCd) {
		this.genreCd = genreCd;
	}
	public String getGenreCd() {
		return genreCd;
	}
	public void setGenreDesc(String genreDesc) {
		this.genreDesc = genreDesc;
	}
	public String getGenreDesc() {
		return genreDesc;
	}
	public boolean equals(GenreUI genre){
		if(this.getGenreId()!=null && this.getGenreId().equals(genre.getGenreId())) return true;
		else return false;
	}
	public void setGenreClass(String genreClass) {
		this.genreClass = genreClass;
	}
	public String getGenreClass() {
		return genreClass;
	}
}
