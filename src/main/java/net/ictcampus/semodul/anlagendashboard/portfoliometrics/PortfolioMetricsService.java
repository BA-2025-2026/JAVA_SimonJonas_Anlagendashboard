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
        // Validate user id
        if (userId < 0) throw new IllegalArgumentException("Invalid user ID: " + userId);

        // Get open positions for user
        List<UserAssetModel> openPositions = userAssetDao.getOpenPositionsByUserId(userId);

        // If user doesn't have any open positions, return MetricsDto with 0
        if (openPositions == null || openPositions.isEmpty()) {
            return new PortfolioMetricsDto(userId, 0, 0, 0, 0);
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
        if (priceBefore == null && priceAfter == null) return 0.0;

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
     * Berechnet den aktuellen Gesamtwert des Portfolios basierend auf den neuesten verfügbaren Preisen.
     * <p>
     * Diese Methode iteriert über alle offenen Positionen und ermittelt für jedes Asset
     * den zeitlich am nächsten liegenden Preis zum aktuellen Zeitpunkt.
     * </p>
     * @param openPositions Eine Liste der aktuell gehaltenen Assets des Benutzers.
     * @return Der kumulierte Marktwert aller Positionen in der Standardwährung.
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
     * Berechnet die Summe des ursprünglich investierten Kapitals für alle offenen Positionen.
     * <p>
     * Für jede Position wird der historische Preis zum exakten Zeitpunkt des Kaufs
     * ermittelt und mit der gehaltenen Menge multipliziert.
     * </p>
     * @param openPositions Eine Liste der aktuell gehaltenen Assets des Benutzers.
     * @return Die Summe der Anschaffungskosten (Investitionssumme) für die gegebenen Positionen.
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
