package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.utility.FinanceMathUtil;
import net.ictcampus.semodul.anlagendashboard.utility.NoDataFoundException;
import net.ictcampus.semodul.anlagendashboard.utility.TimeUtil;

import java.rmi.ServerError;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for calculating portfolio performance metrics.
 */
public class PortfolioMetricsService {
    PriceDao priceDao;
    UserAssetDao userAssetDao;

    /**
     * Constructs a new PortfolioMetricsService with required DAOs.
     * @param priceDao the data access object for price information
     * @param userAssetDao the data access object for user asset information
     */
    public PortfolioMetricsService(PriceDao priceDao, UserAssetDao userAssetDao) {
        this.priceDao = priceDao;
        this.userAssetDao = userAssetDao;
    }

    /**
     * Calculates and returns portfolio metrics for a specific user.
     * @param userId the ID of the user
     * @return a DTO containing all calculated metrics
     * @throws IllegalArgumentException if the user ID is invalid
     */
    public PortfolioMetricsDto getPortfolioMetricsByUserId(int userId) {
        // Validate user id
        if (userId < 0) throw new IllegalArgumentException("Invalid user ID: " + userId);

        // Get open positions for user
        List<UserAssetModel> openPositions = userAssetDao.getOpenPositionsByUserId(userId);

        // If user doesn't have any open positions, return MetricsDto with 0
        if (openPositions == null || openPositions.isEmpty()) {
            throw new NoDataFoundException("No open positions found for user with id " + userId);
        }

        // CALCULATE METRICS
        // Get positions current value
        double totalPortfolioValue = getPositionsTotalValue(openPositions);
        // Get positions value when bought
        double investedTotal = getPositionsTotalInvested(openPositions);
        // Calculate performances
        double absolutePerformance = FinanceMathUtil.calculateAbsolutePerformance(investedTotal, totalPortfolioValue);
        double relativePerformance = FinanceMathUtil.calculateRelativePerformance(investedTotal, totalPortfolioValue);

        // Return metrics DTO
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
    private double getPriceByTimeAndAssetId(LocalDateTime timestampTarget, int assetId) {
        // Get priceModel of the asset right before provided timestamp
        PriceModel priceBefore = priceDao.getPriceBeforeTimestampByAssetID(timestampTarget, assetId);
        // Get priceModel of the asset right after provided timestamp
        PriceModel priceAfter = priceDao.getPriceAfterTimestampByAssetID(timestampTarget, assetId);

        // No prices found at all
        if (priceBefore == null && priceAfter == null) {
            throw new NoDataFoundException("Internal server error: No price data found for asset id: " + assetId);
        }

        // Only one price found
        if (priceBefore == null) return priceAfter.getPrice();
        if (priceAfter == null) return priceBefore.getPrice();

        // Found two prices
        // Calculate time delta in milliseconds to see which price is closest to provided timestamp
        if (TimeUtil.deltaMillis(timestampTarget, priceBefore.getTimestamp())
                <= TimeUtil.deltaMillis(timestampTarget, priceAfter.getTimestamp())) {
            // Price right before is closest to timestamp
            return priceBefore.getPrice();
        }

        // Price right after is closest to timestamp
        return priceAfter.getPrice();
    }

    /**
     * Calculates the current total value of the portfolio based on the latest available prices.
     * <p>
     * This method iterates over all open positions and determines the price closest
     * to the current time for each asset.
     * </p>
     * @param openPositions A list of the user's currently held assets.
     * @return The cumulative market value of all positions in the standard currency.
     */
    private double getPositionsTotalValue(List<UserAssetModel> openPositions) {

        // Sum of current value of each position
        double portfolioValue = 0.0;

        // Sum up current price for each open position
        for (UserAssetModel position : openPositions) {
            // get latest price information available in db for the asset
            double valueAsOfLatestPriceData = getPriceByTimeAndAssetId(LocalDateTime.now(), position.getAssetId());
            // get quantity: how much of that asset does the open position have
            double quantity = position.getQuantity();

            // Add to the sum
            portfolioValue += valueAsOfLatestPriceData * quantity;
        }

        // Return sum
        return portfolioValue;
    }

    /**
     * Calculates the sum of the originally invested capital for all open positions.
     * <p>
     * For each position, the historical price at the exact time of purchase
     * is determined and multiplied by the quantity held.
     * </p>
     * @param openPositions A list of the user's currently held assets.
     * @return The sum of acquisition costs (total investment) for the given positions.
     */
    private double getPositionsTotalInvested(List<UserAssetModel> openPositions) {
        double totalInvested = 0.0;

        // Sum of how much the user
        for (UserAssetModel position : openPositions) {
            // get historic price of the asset at the moment the position was bought
            double priceWhenBought = getPriceByTimeAndAssetId(position.getPurchasedAt(), position.getAssetId());
            // get quantity: how much of that asset does the open position have
            double quantity = position.getQuantity();

            // Add to the sum
            totalInvested += priceWhenBought * quantity;
        }

        // Return sum
        return totalInvested;
    }
}
