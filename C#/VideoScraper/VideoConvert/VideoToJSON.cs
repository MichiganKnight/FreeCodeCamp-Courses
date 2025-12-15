using VideoConvert.Sites;

namespace VideoConvert
{
    public abstract class VideoToJSON
    {
        public static async Task GetVideos()
        {
            List<(string, string)> tnaUrls =
            [
                ("https://www.tnaflix.com/anal-porn/Hot-college-sluts-go-wild-in-the-country/video9255852", "Hot College Sluts go Wild in the Country - Part 1"),
                ("https://www.tnaflix.com/amateur-porn/Hot-college-sluts-go-wild-in-the-country%2C-part-3/video9360228", "Hot College Sluts Go Wild in the Country - Part 3"),
                ("https://www.tnaflix.com/amateur-porn/Theartporn-Wtfpass-Agnessa-Juan-Away-From-The-Whole-World-Sons-Screw/video11741701", "Agnessa & Juan Away from the Whole World"),
                ("https://www.tnaflix.com/amateur-porn/CollegeFuckParties-Student-sex-friends-sinspire-a-good-girl/video1352238", "Student Sex Friends"),
                ("https://www.tnaflix.com/amateur-porn/LE-BAISER-%282015%29/video9114050", "LE Baiser (2015)"),
                ("https://www.tnaflix.com/es/videos-hd/Party-Hardcore-gone-Crazy-Vol.-23/video4046197", "Party Hardcore Gone Crazy Vol. 23"),
                ("https://www.tnaflix.com/amateur-porn/Czech-couples-30/video10360204", "Czech Couples 30"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-1/video10733222", "The Whores of Wall Street - Part 1"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-2/video10733195", "The Whores of Wall Street - Part 2"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-3/video10733206", "The Whores of Wall Street - Part 3"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-4/video10733180", "The Whores of Wall Street - Part 4"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-5/video10733182", "The Whores of Wall Street - Part 5"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-6/video10733183", "The Whores of Wall Street - Part 6"),
                ("https://www.tnaflix.com/amateur-porn/DSO-The-Whores-Of-Wall-Street-Part-7/video10733184", "The Whores of Wall Street - Part 7")
            ];

            List<(string, string)> spankBangUrls =
            [
                ("https://spankbang.com/12ru4/video/filthy+college+sluts+turn+an+outdoor+party+into+wild+fuck+fest", "Filthy College Sluts Turn an Outdoor Party into Wild Fuck Fest"),
                ("https://spankbang.com/5ms2q/video/debbie+does+dallas", "Debbie Does Dallas"),
                ("https://spankbang.com/5zm4b/video/0livia+d3vine+pickup", "Olivia Devine Pickup")
            ];

            TNA.GetVideos(tnaUrls);
            SpankBang.GetVideos(spankBangUrls);

            Logger.LogMessage(LogType.Success, "Non-M3U8 Web Scraping Finished!\n");

            List<(string, string)> hdefUrls =
            [
                ("https://hdefporn.com/i/90103/real-sex-party-on-the-sunny-beach-part-3", "Real Sex Party on the Sunny Beach - Part 3"),
                ("https://hdefporn.com/i/86727/game-hanger", "Game Hanger")

            ];

            List<(string, string)> xVideosUrls =
            [
                ("https://www.xvideos.com/video.ufbiivdae4e/excogi_-_new_to_porn_blonde_gets_face_fucked_and_facialized_by_two_big_cocks", "New to Porn Blonde Gets  Face Fucked and Facialized by Two Big Cocks"),
                ("https://www.xvideos.com/video.udlbdho1c4f/besties_having_group_fuck_after_college_-_amateur_foursome_-_full", "Besties Having Group Fuck After College"),
                ("https://www.xvideos.com/video.hbuiocmbd74/rosenbergporn014_01", "Rosenberg 14-01"),
                ("https://www.xvideos.com/video.hbuiivo42a3/rosenbergporn014_02", "Rosenberg 14-02"),
                ("https://www.xvideos.com/video.hbuiiimf790/rosenbergporn014_03", "Rosenberg 14-03"),
                ("https://www.xvideos.com/video.hbuikphac60/rosenbergporn014_04", "Rosenberg 14-04"),
                ("https://www.xvideos.com/video.allbft4de7/the_wizard_of_oz_full_porn_parody_movie", "The Wizard of Oz Porn Parody")
            ];

            List<(string, string)> xHamsterUrls =
            [
                ("https://xhamster.com/videos/yacht-party-sex-orgie-lake-balaton-movie-xheqHTm", "Yacht Party Sex Orgy Lake Balaton Movie"),
                ("https://xhamster.com/videos/sex-island-tournament-semifinal-i-hot-frisbee-xhKFzNE", "Beach Group Sex Game"),
                ("https://xhamster.com/videos/the-cruise-of-pleasure-xhKIcTq", "The Cruise of Pleasure")
            ];

            await LocalNSFW.GetVideos(hdefUrls);
            await LocalNSFW.GetVideos(xVideosUrls);
            await LocalNSFW.GetVideos(xHamsterUrls);

            Logger.LogMessage(LogType.Success, "M3U8 Web Scraping Finished!\n");
        }
    }
}