package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PortfolioMetricsService {

    // Inject daos

    // constructor

    /**
     * Returns the asset price closest to the given timestamp.
     * @param timestampProvided The target timestamp.
     * @param assetId The unique identifier of the asset.
     * @return PriceModel containing the closest available price data.
     */
    public PriceModel getPriceByTimeAndAssetId(LocalDate timestampProvided, int assetId) {

        // Get priceModel of the asset right before provided timestamp
        PriceModel priceBefore =;
        // Get priceModel of the asset right after provided timestamp
        PriceModel priceAfter =;

        // Calculate time delta to see which price is closest to provided timestamp
        if (timestampProvided - priceBefore.getTimestamp() <= priceAfter.getTimestamp() - timestampProvided) {
            // Price right before is closest to timestamp
            return priceBefore;
        } else {
            // Price right after is closest to timestamp
            return priceAfter;
        }
    }


    //TODO: das ist ohne Preis?
    /**
     * Returns list of assets which have been bought (and not yet sold) by a given user.
     * @param userId id of the user
     * @return List of asset objects
     */
    public List<AssetObjects> getOpenPositionsByUserId(int userId) {
        if (userId < 0) throw new IllegalArgumentException("Invalid user ID: " + userId);

        List<String> assetsS = new ArrayList<>();
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

    public double getAbsolutePerformanceByUserId(int userId) {
        // Get current portfolio value
        double currentPortfolioValue = getTotalPortfolioValueByUserId(userId);

        // Get amount the user originally invested
        double investedTotal = getInvestedTotalByUserId(userId);

        // Calculate absolute performance (win / loss)
        return currentPortfolioValue - investedTotal;
    }

    // TODO: Sollte man hier nicht eher ein objekt schrittweise befüllen? sonst muss man ja immer alle daten nochmal
    // neu fetchen und berechnen!!???


}
