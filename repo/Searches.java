package repo;

import java.util.List;

public class Searches {
	// The query. 
	private String query;

	// The time taken. 
	private double timeTaken;

	// The results. 
	private List<Rank> results;

	/** Gets the query.
	 * @return the query */
	public String get_Query() {
		return query;
	}

	/** Sets the query.
	 * @param query the new query */
	public void set_Query(String query) {
		this.query = query;
	}

	/** Gets the results.
	 * @return the results */
	public List<Rank> get_Results() {
		return results;
	}

	/** Sets the results.
	 * @param results the new results */
	public void set_Results(List<Rank> results) {
		this.results = results;
	}

	/** Gets the time taken.
	 * @return the time taken */
	public double get_Time_Taken() {
		return timeTaken;
	}

	/** Sets the time taken.
	 * @param timeTaken the new time taken */
	public void set_Time_Taken(double timeTaken) {
		this.timeTaken = timeTaken;
	}
}
