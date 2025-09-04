using CTA_Tracker.Models;
using Newtonsoft.Json.Linq;

namespace CTA_Tracker.API
{
    /// <summary>
    /// Helper Utility Functions Class
    /// </summary>
    public static class Functions
    {
        private static readonly HashSet<string> ValidRoutes =
        [
            "red", "blue", "brn", "g", "org", "pnk", "p", "y"
        ];
        
        private static readonly Dictionary<string, (string Display, string Css, string BtnCss)> RouteStyles = new ()
        {
            ["red"] = ("Red", "redLine", "btn-red-line"),
            ["blue"] = ("Blue", "blueLine", "btn-blue-line"),
            ["brn"] = ("Brown", "brownLine", "btn-brown-line"),
            ["g"] = ("Green", "greenLine", "btn-green-line"),
            ["org"] = ("Orange", "orangeLine", "btn-orange-line"),
            ["pnk"] = ("Pink", "pinkLine", "btn-pink-line"),
            ["p"] = ("Purple", "purpleLine", "btn-purple-line"),
            ["y"] = ("Yellow", "yellowLine", "btn-yellow-line")
        };

        private static string NormalizeRoute(string? route) => (route ?? string.Empty).ToLowerInvariant();
        public static bool IsValidRoute(string? route) => ValidRoutes.Contains(NormalizeRoute(route));
        
        public static string GetDisplayName(string? route) => RouteStyles.TryGetValue(NormalizeRoute(route), out (string Display, string Css, string BtnCss) style) ? style.Display : "Selected";
        public static string GetLineCss(string? route) => RouteStyles.TryGetValue(NormalizeRoute(route), out (string Display, string Css, string BtnCss) style) ? style.Css : "redLine";
        public static string GetButtonCss(string? route) => RouteStyles.TryGetValue(NormalizeRoute(route), out (string Display, string Css, string BtnCss) style) ? style.BtnCss : "btn-red-line";

        /// <summary>
        /// Build the CTA URL
        /// </summary>
        /// <param name="apiKey">API Key</param>
        /// <param name="route">CTA Route</param>
        /// <returns><see cref="string"/> as the CTA URL</returns>
        public static string BuildCtaUrl(string apiKey, string route)
        {
            string r = NormalizeRoute(route);
            
            return $"https://lapi.transitchicago.com/api/1.0/ttpositions.aspx?key={Uri.EscapeDataString(apiKey)}&rt={Uri.EscapeDataString(r)}&outputType=json";
        }
        
        /// <summary>
        /// Extract Train Items from JSON
        /// </summary>
        /// <param name="json">The JSON to Parse</param>
        /// <returns>Result as a <see cref="List{T}"/></returns>
        public static List<RouteModel> ExtractTrainItems(string json)
        {
            List<RouteModel> result = [];

            try
            {
                JObject obj = JObject.Parse(json);

                if (obj["ctatt"] is not JObject ctaTrainTracker)
                {
                    return result;
                }

                JToken? routeToken = ctaTrainTracker?["route"];
                JArray routes = routeToken switch
                {
                    JArray arr => arr,
                    JObject singleObject => new JArray(singleObject),
                    _ => []
                };

                foreach (JToken route in routes)
                {
                    if (route is not JObject routeObj)
                    {
                        continue;
                    }

                    JToken? trainsToken = routeObj["train"];
                    JArray trains = trainsToken switch
                    {
                        JArray trainArray => trainArray,
                        JObject trainObject => new JArray(trainObject),
                        _ => []
                    };

                    foreach (JToken train in trains)
                    {
                        if (train is not JObject trainObj)
                        {
                            continue;
                        }

                        result.Add(new RouteModel
                        {
                            RouteNumber = trainObj.Value<string>("rn"),
                            Destination = trainObj.Value<string>("destNm"),
                            NextStationName = trainObj.Value<string>("nextStaNm")
                        });
                    }
                }
            }
            catch
            {
                // Ignore
            }
            
            return result;
        }

        /// <summary>
        /// Use Try / Catch to get the Timestamp from the JSON
        /// </summary>
        /// <param name="json">JSON String</param>
        /// <returns><see cref="string"/> as a Timestamp</returns>
        public static string? TryGetTimestamp(string json)
        {
            try
            {
                JObject obj = JObject.Parse(json);

                return obj.SelectToken("ctatt.tmst")?.Value<string>();
            }
            catch
            {
                return null;
            }
        }
    }
}