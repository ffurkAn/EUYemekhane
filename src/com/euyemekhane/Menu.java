package com.euyemekhane;

public class Menu {

	private int id;
	private String ay;
	private String tur;
	private String tarih;
	private int gun;
	private String menu;
	private int sevilmeyen;
	private boolean selected;

	public Menu() {
		setSelected(false);
	}
	
	public String getTarih() {
		return tarih;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getTur() {
		return tur;
	}

	public void setTur(String tur) {
		this.tur = tur;
	}

	public String getAy() {
		return ay;
	}

	public void setAy(String ay) {
		this.ay = ay;
	}

	public int getGun() {
		return gun;
	}

	public void setGun(int gun) {
		this.gun = gun;
	}

	public int getSevilmeyen() {
		return sevilmeyen;
	}

	public void setSevilmeyen(int sevilmeyen) {
		this.sevilmeyen = sevilmeyen;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}