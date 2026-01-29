package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.user.UserController;
import net.ictcampus.semodul.anlagendashboard.user.UserDao;
import net.ictcampus.semodul.anlagendashboard.user.UserJdbcDao;
import net.ictcampus.semodul.anlagendashboard.user.UserService;

public class TestClassST08 {
	public static void main(String[] args) {
		PriceDao priceDao = new PriceJdbcDao();
		UserAssetDao userAssetDao = new UserAssetJdbcDao();
		PortfolioMetricsService portfolioMetricsService = new PortfolioMetricsService(priceDao, userAssetDao);
		PortfolioMetricsController portfolioMetricsController = new PortfolioMetricsController(portfolioMetricsService);
		portfolioMetricsController.getPortfolioMetricsByUserIdEndpoint(-1);
	}
}
