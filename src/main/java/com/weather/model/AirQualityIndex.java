package com.weather.model;

public class AirQualityIndex {
	// default pressure is 1000hpa
	public int pm2dot5;
	public int pm10;
	public int no2;
	public int o3;
	public int so2;
	public int co;

	public AirQualityIndex() {
		this.so2 = -1;
		this.no2 = -1;
		this.co = -1;
		this.o3 = -1;
		this.pm2dot5 = -1;
		this.pm10 = -1;
	}

	public AirQualityIndex(int pm25, int pm10, int o3, int no2, int so2, int co) {
		this.so2 = so2;
		this.no2 = no2;
		this.co = co;
		this.o3 = o3;
		this.pm2dot5 = pm25;
		this.pm10 = pm10;
	}

	public boolean IsValid() {
		return !(this.pm2dot5 < 0 || this.pm10 < 0 || this.no2 < 0 || this.o3 < 0 || this.so2 < 0 || this.co < 0);
	}
}
