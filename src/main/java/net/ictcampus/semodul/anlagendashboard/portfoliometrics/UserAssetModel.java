/**
 * Represents a entry in the table user_asset with details such as ID, user ID, asset ID, broker ID, quantity, purchase and sale timestamps.
 */
package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDateTime;

public class UserAssetModel {
	private int idUserAsset;
	private int userId;
	private int assetId;
	private int brokerId;
	private int quantity;
	private LocalDateTime purchasedAt;
	private LocalDateTime soldAt;

	public UserAssetModel(int idUserAsset, int userId, int assetId, int brokerId, int quantity, LocalDateTime purchasedAt, LocalDateTime soldAt) {
		this.idUserAsset = idUserAsset;
		this.userId = userId;
		this.assetId = assetId;
		this.brokerId = brokerId;
		this.quantity = quantity;
		this.purchasedAt = purchasedAt;
		this.soldAt = soldAt;
	}

	public int getIdUserAsset() {
		return idUserAsset;
	}

	public int getUserId() {
		return userId;
	}

	public int getAssetId() {
		return assetId;
	}

	public int getBrokerId() {
		return brokerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public LocalDateTime getPurchasedAt() {
		return purchasedAt;
	}

	public LocalDateTime getSoldAt() {
		return soldAt;
	}
}
