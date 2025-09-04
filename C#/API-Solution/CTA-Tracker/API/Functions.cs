using CTA_Tracker.Models;
using Newtonsoft.Json.Linq;

namespace CTA_Tracker.API
{
    public static class Functions
    {
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
    }
}