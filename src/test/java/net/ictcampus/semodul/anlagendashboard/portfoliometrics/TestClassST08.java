package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.user.UserController;
import net.ictcampus.semodul.anlagendashboard.user.UserDao;
import net.ictcampus.semodul.anlagendashboard.user.UserJdbcDao;
import net.ictcampus.semodul.anlagendashboard.user.UserService;

/**
 * Manual integration test class for the Portfolio Metrics endpoint.
 * <p>
 * This class initializes the required data access objects (DAO) for prices and
 * user assets, gives them into the {@link PortfolioMetricsService}, and
 * executes a request via the {@link PortfolioMetricsController}. It tests
 * how the system handles metric requests for a specific user ID.
 */
public class TestClassST08 {
	public static void main(String[] args) {
		PriceDao priceDao = new PriceJdbcDao();
		UserAssetDao userAssetDao = new UserAssetJdbcDao();
		PortfolioMetricsService portfolioMetricsService = new PortfolioMetricsService(priceDao, userAssetDao);
		PortfolioMetricsController portfolioMetricsController = new PortfolioMetricsController(portfolioMetricsService);
		portfolioMetricsController.getPortfolioMetricsByUserIdEndpoint(-1);
	}
}
