package com.euyemekhane;

import java.util.ArrayList;

public class SevilmeyenYemek {

	private int kullaniciID;
	private ArrayList<String> yemekler;

	public int getKullaniciId() {
		return kullaniciID;
	}

	public void setKullaniciId(int kulaniciId) {
		this.kullaniciID = kulaniciId;
	}

	public ArrayList<String> getYemekler() {
		return yemekler;
	}

	public void setYemekler(ArrayList<String> yemekler) {
		this.yemekler = yemekler;
	}

}