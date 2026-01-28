package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.utility.ErrorDto;
import net.ictcampus.semodul.anlagendashboard.utility.JsonUtil;

/**
 * Controller for handling portfolio metrics related endpoints.
 */
public class PortfolioMetricsController {
	private PortfolioMetricsService portfolioMetricsService;

	public PortfolioMetricsController(PortfolioMetricsService portfolioMetricsService) {
		this.portfolioMetricsService = portfolioMetricsService;
	}

	/**
	 * Endpoint for getting portfolio metrics by user ID.
	 * @param userId The ID of the user for whom to retrieve portfolio metrics.
	 */

	public void getPortfolioMetricsByUserIdEndpoint(int userId) {
		System.out.println("==== RESPONSE of GET /portfoliometrics/{id} ====");
		try {
			if (userId <= 0) throw new IllegalArgumentException("User id cannot be 0 or lower.");

			PortfolioMetricsDto portfolioMetricsDto = portfolioMetricsService.getPortfolioMetricsByUserId(userId);

			if (portfolioMetricsDto == null) {
				ErrorDto errorDto = new ErrorDto("Could not find a user with id " + userId);
				System.out.println(JsonUtil.toJson(errorDto));
			} else {
				System.out.println(JsonUtil.toJson(portfolioMetricsDto));
			}
		} catch (IllegalArgumentException e) {
			ErrorDto errorDto = new ErrorDto("Invalid argument in request with user ID: " + userId + ". " + e.getMessage());
			System.out.println(JsonUtil.toJson(errorDto));
		} catch (RuntimeException e) {
			ErrorDto errorDto = new ErrorDto(e.getMessage());
			System.out.println(JsonUtil.toJson(errorDto));
		}
	}
}
