package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.time.LocalDateTime;

/**
 * Stores portfolio metrics for a given user: user id, timestamp of when this information was calculated,
 * total portfolio value, absolute performance (win / loss), relative performance (win / loss as a percentage)
 * and the currency.
 */
public class PortfolioMetricsDto {

    private int userId;
    /**
     * ISO-8601 timestamp as String, e.g. "2026-01-23T15:30:12.345678900"
     */
    private String timestamp;
    private double totalPortfolioValue;
    private double investedTotalValue;
    private double absolutePerformance;
    private double relativePerformance;
    private final String currency = "CHF";

    /**
     * Constructs a DTO with all calculated metrics.
     * @param userId user identifier
     * @param totalPortfolioValue current market value
     * @param investedTotalValue original cost basis
     * @param absolutePerformance profit/loss in absolute terms
     * @param relativePerformance profit/loss in percentage
     */
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

    /** @return the user ID */
    public int getUserId() {
        return userId;
    }

    /** @return the calculation timestamp */
    public String getTimestamp() {
        return timestamp;
    }

    /** @return total portfolio value */
    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    /** @return total invested value */
    public double getInvestedTotalValue() {
        return investedTotalValue;
    }

    /** @return absolute performance value */
    public double getAbsolutePerformance() {
        return absolutePerformance;
    }

    /** @return relative performance as a decimal/percentage */
    public double getRelativePerformance() {
        return relativePerformance;
    }

    /** @return the currency code */
    public String getCurrency() {
        return currency;
    }
}
