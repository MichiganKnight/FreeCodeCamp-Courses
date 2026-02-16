namespace Train_Tracker.Areas.CTATracker.Models
{
    public class RouteModel
    {
        public string? RouteNumber { get; set; }
        public string? Destination { get; set; }
        public string? NextStationName { get; set; }
        
        public string? Route { get; set; }
        public double? Latitude { get; set; }
        public double? Longitude { get; set; }
        public int? Heading { get; set; }
    }
}