public class Review {
    final String reviewId;
    final int numericScore;
    final String feedback;
    final String[] tags;

    public Review(String reviewId, int numericScore, String feedback, String tags) {
        this.reviewId = reviewId;
        this.numericScore = numericScore;
        this.feedback = feedback;
        this.tags = tags.split(":");
    }
}