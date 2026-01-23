/**
 * Interface for accessing price data of assets.
 */
package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDateTime;

public interface PriceDao {
	/**
	 * Retrieves the price of an asset that was recorded before the specified timestamp.
	 *
	 * @param timestamp The point in time before which the price should be retrieved.
	 * @param assetId The unique identifier of the asset for which the price is sought.
	 * @return The price of the asset as a data structure or object, if found, before the given timestamp.
	 */
	public PriceModel getPriceBeforeTimestampByAssetID(LocalDateTime timestamp, int assetId);
	/**
	 * Retrieves the price of an asset that was recorded after the specified timestamp.
	 *
	 * @param timestamp The point in time after which the price should be retrieved.
	 * @param assetId The unique identifier of the asset for which the price is sought.
	 * @return The price of the asset as a data structure or object, if found, after the given timestamp.
	 */
	public PriceModel getPriceAfterTimestampByAssetID(LocalDateTime timestamp, int assetId);
}
