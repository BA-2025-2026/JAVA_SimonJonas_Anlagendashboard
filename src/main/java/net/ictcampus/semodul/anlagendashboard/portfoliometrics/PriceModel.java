package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDateTime;

/**
 * Data model representing the price of an asset at a specific point in time.
 * Holds price data received from database: id of the db entry (primary key), id of the asset, timestamp of the price,
 * price itself.
 */
public class PriceModel {
	private int idPrice;
	private int assetId;
	private LocalDateTime timestamp;
	private double price;

	public PriceModel(int idPrice, int assetId, LocalDateTime timestamp, double price) {
		this.idPrice = idPrice;
		this.assetId = assetId;
		this.timestamp = timestamp;
		this.price = price;
	}

	public int getIdPrice() {
		return idPrice;
	}

	public int getAssetId() {
		return assetId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public double getPrice() {
		return price;
	}
}
