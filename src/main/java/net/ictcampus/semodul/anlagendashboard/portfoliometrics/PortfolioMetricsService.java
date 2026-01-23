package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.utility.FinanceMathUtil;
import net.ictcampus.semodul.anlagendashboard.utility.TimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PortfolioMetricsService {
    PriceDao priceDao;
    UserAssetDao userAssetDao;

    public PortfolioMetricsService(PriceDao priceDao, UserAssetDao userAssetDao) {
        this.priceDao = priceDao;
        this.userAssetDao = userAssetDao;
    }

    public PortfolioMetricsDto getPortfolioMetricsByUserId(int userId) {
        // Get metrics
        double totalPortfolioValue = getTotalPortfolioValueByUserId(userId);
        double investedTotal = getInvestedTotalByUserId(userId);
        double absolutePerformance = FinanceMathUtil.calculateAbsolutePerformance(investedTotal, totalPortfolioValue);
        double relativePerformance = FinanceMathUtil.calculateRelativePerformance(investedTotal, totalPortfolioValue);

        // Return DTO
        return new PortfolioMetricsDto(
                userId,
                totalPortfolioValue,
                investedTotal,
                absolutePerformance,
                relativePerformance
        );
    }

    /**
     * Returns the asset price closest to the given timestamp.
     * @param timestampTarget The target timestamp.
     * @param assetId The unique identifier of the asset.
     * @return PriceModel containing the closest available price data.
     */
    public PriceModel getPriceByTimeAndAssetId(LocalDateTime timestampTarget, int assetId) {

        // Get priceModel of the asset right before provided timestamp
        PriceModel priceBefore = priceDao.getPriceBeforeTimestampByAssetID(timestampTarget, assetId);
        // Get priceModel of the asset right after provided timestamp
        PriceModel priceAfter = priceDao.getPriceAfterTimestampByAssetID(timestampTarget, assetId);

        // Calculate time delta in milliseconds to see which price is closest to provided timestamp
        if (TimeUtil.deltaMillis(timestampTarget, priceBefore.getTimestamp())
                <= TimeUtil.deltaMillis(timestampTarget, priceAfter.getTimestamp())) {
            // Price right before is closest to timestamp
            return priceBefore;
        }

        // Price right after is closest to timestamp
        return priceAfter;
    }


    //TODO: das ist ohne Preis?
    /**
     * Returns list of assets which have been bought (and not yet sold) by a given user.
     * @param userId id of the user
     * @return List of asset objects
     */
    public List<UserAssetModel> getOpenPositionsByUserId(int userId) {
        if (userId < 0) throw new IllegalArgumentException("Invalid user ID: " + userId);

        List<UserAssetModel> assets = new ArrayList<UserAssetModel>();

        // Call doa and get back a list of hold asset objects
        List<AssetObjects> assets =


        // If result is null, return null
        // TODO: Is this even necessary? Or shall we just return the empty list?
        if (assets.isEmpty()) return null;

        return assets;
    }

    // TODO: Das ist mit Preis?
    public double getTotalPortfolioValueByUserId(int userId) {

        // Get all open positions

        // Get current price for each open position

        // Multiply current price by amount held

        // Sum all prices


        // Return sum
    }

    /**
     * only open positions!
     * @param userId
     * @return
     */
    public double getInvestedTotalByUserId(int userId) {
        // Get all open positions

        // For each open position:
        // Get price of that asset at the buy timestamp

        // Multiply price by amount bought

        // Sum prices

        // Return sum
    }


}
