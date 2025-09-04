namespace CTA_Tracker.Models
{
    public class TrainModel
    {
        public string? StationName { get; init; }
        public string? StationDescription { get; init; }
        public string? DestinationName { get; init; }
        public DateTime? EstimatedArrival { get; init; }
        public string? Approaching { get; init; }
        public string? Delayed { get; init; }
    } 
}