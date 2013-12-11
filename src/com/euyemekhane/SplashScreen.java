package com.euyemekhane;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class SplashScreen extends SherlockActivity {

	private int SPLASH_DISPLAY_TIME;

	private static String url = "http://ferhatezizi.com/yemekhane/ege/";

	private static final String TAG_YEMEKLER = "yemekler";
	private static final String TAG_TARIH = "tarih";
	private static final String TAG_YEMEK = "yemek";

	private JSONArray yemekler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean splashKontrol = sharedPreferences.getBoolean("splashKontrol", false);

		if (!splashKontrol) {
			setContentView(R.layout.activity_splash_screen);

			ImageView logo = (ImageView) findViewById(R.id.splashLogo);
			Animation slide = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
			slide.setDuration(2000);
			logo.startAnimation(slide);

			TextView text = (TextView) findViewById(R.id.splashText);
			Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
			fadeIn.setDuration(2000);
			text.startAnimation(fadeIn);

			SPLASH_DISPLAY_TIME = 2000;
		} else {
			SPLASH_DISPLAY_TIME = 0;
		}

		new JSONParse(url).execute();

	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;
		private String url;
		private String tur;
		private MenuDAL dalMenu;
		private SevilmeyenYemekDAL dalSevilmeyen;
		private Menu entMenu;
		private String yemek;
		private String yemekTarihi;
		private final Calendar takvim;
		private int hafta;

		public JSONParse (String url) {
			this.url = url;
			dalMenu = new MenuDAL(getApplicationContext());
			dalSevilmeyen = new SevilmeyenYemekDAL(getApplicationContext());
			entMenu = null;
			takvim = Calendar.getInstance();
			hafta = 0;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			boolean splashKontrol = sharedPreferences.getBoolean("splashKontrol", false);
			if (splashKontrol) {
				pDialog = new ProgressDialog(SplashScreen.this);
				pDialog.setMessage("Güncel liste kontrol ediliyor...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			dalMenu.GunlukEskiKayitlariSil();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(url);

			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			
			if (json == null) {
				Toast.makeText(getApplicationContext(), "Baðlantý hatasý", Toast.LENGTH_SHORT).show();
			} else {

				try {
					yemekler = json.getJSONArray(TAG_YEMEKLER);
					String[] kayitliListeTarih = null;
					String[] tarih;
					int kayitliListeAy;

					entMenu = dalMenu.SonOgleYemekGetir();
					if (entMenu != null)
						kayitliListeTarih = entMenu.getTarih().split("[\\.\\s*]");

					if (kayitliListeTarih != null)
						kayitliListeAy = Integer.parseInt(kayitliListeTarih[1]);
					else
						kayitliListeAy = -1;


					String[] guncelListeTarih = yemekler.getJSONObject(0).getString(TAG_TARIH).split("\\s+");
					guncelListeTarih = tarihFormatDegistir(guncelListeTarih);

					if (Integer.parseInt(guncelListeTarih[1]) > kayitliListeAy) {

						for (int i = 0; i < yemekler.length(); i++) {
							JSONObject c = yemekler.getJSONObject(i);
							yemekTarihi = c.getString(TAG_TARIH);
							yemek = c.getString(TAG_YEMEK).replaceAll("\\s+", " ");

							tarih = tarihFormatDegistir(yemekTarihi.split("\\s+"));
							yemekTarihi = tarih[0] + "." + tarih[1] + "." + tarih[2] + " " + tarih[3];

							takvim.set(Integer.parseInt(tarih[2]), Integer.parseInt(tarih[1])-1, Integer.parseInt(tarih[0]));
							hafta = takvim.get(Calendar.WEEK_OF_MONTH);

							if ((i % 2) == 0)
								tur = "ogle";
							else
								tur = "aksam";

							entMenu = new Menu();
							entMenu.setGun(0);
							if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pa.*"))
								entMenu.setGun(1);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Sa.*"))
								entMenu.setGun(2);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Ça.*"))
								entMenu.setGun(3);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pe.*"))
								entMenu.setGun(4);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Cu.*"))
								entMenu.setGun(5);

							entMenu.setSevilmeyen(0);
							entMenu.setTur(tur);
							entMenu.setTarih(yemekTarihi);
							entMenu.setHafta(hafta);
							entMenu.setMenu(yemek);
							entMenu.setSelected(false);
							dalMenu.MenuKaydet(entMenu);
						}

						dalSevilmeyen.TumSevilmeyenGuncelle(0);
						Toast.makeText(getApplicationContext(), "Yemek listesi güncellendi", Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getApplicationContext(), "Yeni liste yok", Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent i;

					if (sharedPreferences.getBoolean("firstRun", true)) {
						i = new Intent(SplashScreen.this, Tutorial.class);
						sharedPreferences.edit().putBoolean("firstRun", false).commit();
					} else {
						i = new Intent(SplashScreen.this, MainActivity.class);
					}

					startActivity(i);
					SplashScreen.this.finish();
				}
			}, SPLASH_DISPLAY_TIME);

		}

	}

	private String[] tarihFormatDegistir(String[] s) {

		if (s[1].equals("Ocak"))
			s[1] = "01";
		else if (s[1].equals("Þubat"))
			s[1] = "02";
		else if (s[1].equals("Mart"))
			s[1] = "03";
		else if (s[1].equals("Nisan"))
			s[1] = "04";
		else if (s[1].equals("Mayýs"))
			s[1] = "05";
		else if (s[1].equals("Haziran"))
			s[1] = "06";
		else if (s[1].equals("Temmuz"))
			s[1] = "07";
		else if (s[1].equals("Aðustos"))
			s[1] = "08";
		else if (s[1].equals("Eylül"))
			s[1] = "09";
		else if (s[1].equals("Ekim"))
			s[1] = "10";
		else if (s[1].equals("Kasým"))
			s[1] = "11";
		else if (s[1].equals("Aralýk"))
			s[1] = "12";

		return s;
	}

}
