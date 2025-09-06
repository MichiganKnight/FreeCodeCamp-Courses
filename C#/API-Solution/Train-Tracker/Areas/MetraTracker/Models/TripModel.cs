namespace Train_Tracker.Areas.MetraTracker.Models
{
    public class TripModel
    {
        public string? RouteID { get; init; }
        public string? TripID { get; init; }
        public string? TripHeadsign { get; init; }
        public string? ShapeID { get; init; }
        public string? DirectionID { get; init; }
    }
}