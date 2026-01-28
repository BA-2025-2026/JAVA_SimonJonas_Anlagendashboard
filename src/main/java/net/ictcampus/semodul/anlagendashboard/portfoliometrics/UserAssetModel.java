/**
 */
package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDateTime;

/**
 * Holds information about the assets a user is holding (or has held, if soldAt is not null).
 */
public class UserAssetModel {
	/** primary key */
	private int idUserAsset;
	/** foreign key pointing to the user */
	private int userId;
	/** foreign key pointing to the asset */
	private int assetId;
	/** foreign key pointing to the broker */
	private int brokerId;
	/** quantity indicating how much of the asset the user bought */
	private double quantity;
	/** time the purchase was made */
	private LocalDateTime purchasedAt;
	/** time the assed was sold, can be null in case the asset is still held by the user */
	private LocalDateTime soldAt;

	public UserAssetModel(int idUserAsset, int userId, int assetId, int brokerId, double quantity,
						  LocalDateTime purchasedAt, LocalDateTime soldAt) {
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

	public double getQuantity() {
		return quantity;
	}

	public LocalDateTime getPurchasedAt() {
		return purchasedAt;
	}

	public LocalDateTime getSoldAt() {
		return soldAt;
	}
}
