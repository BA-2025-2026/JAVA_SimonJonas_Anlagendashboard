package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDateTime;

/**
 * Stores portfolio metrics for a given user: user id, timestamp of when this information was calculated,
 * total portfolio value, absolute performance (win / loss), relative performance (win / loss as a percentage)
 * and the currency.
 */
public class PortfolioMetricsDto {

    public int userId;
    /**
     * ISO-8601 timestamp as String, e.g. "2026-01-23T15:30:12.345678900"
     */
    public String timestamp;
    public double totalPortfolioValue;
    public double investedTotalValue;
    public double absolutePerformance;
    public double relativePerformance;
    public final String currency = "CHF";

    public PortfolioMetricsDto(int userId,
                               double totalPortfolioValue,
                               double investedTotalValue,
                               double absolutePerformance,
                               double relativePerformance) {

        this.userId = userId;
        this.timestamp = LocalDateTime.now().toString();
        this.totalPortfolioValue = totalPortfolioValue;
        this.investedTotalValue = investedTotalValue;
        this.absolutePerformance = absolutePerformance;
        this.relativePerformance = relativePerformance;
    }

    public int getUserId() {
        return userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public double getInvestedTotalValue() {
        return investedTotalValue;
    }

    public double getAbsolutePerformance() {
        return absolutePerformance;
    }

    public double getRelativePerformance() {
        return relativePerformance;
    }

    public String getCurrency() {
        return currency;
    }
}
