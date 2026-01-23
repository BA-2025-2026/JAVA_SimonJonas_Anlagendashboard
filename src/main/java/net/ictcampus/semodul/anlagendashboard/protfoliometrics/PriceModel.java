/**
 * Data model representing the price of an asset at a specific point in time.
 */
package net.ictcampus.semodul.anlagendashboard.protfoliometrics;

import java.time.LocalDateTime;

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
