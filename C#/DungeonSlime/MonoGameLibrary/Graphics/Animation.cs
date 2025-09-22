using System;
using System.Collections.Generic;

namespace MonoGameLibrary.Graphics
{
    public class Animation
    {
        /// <summary>
        /// Texture Regions that Make Up the Frames of the Animation
        /// Order of the Region Within the Collection are the Order that the Frames are Played
        /// </summary>
        public List<TextureRegion> Frames { get; set; }
        
        /// <summary>
        /// Amount of Time to Delay Between Frames
        /// </summary>
        public TimeSpan Delay { get; set; }

        /// <summary>
        /// Creates a new Animation
        /// </summary>
        public Animation()
        {
            Frames = [];
            Delay = TimeSpan.FromMilliseconds(100);
        }
        
        /// <summary>
        /// Creates a new Animation with the Specified Frames and Delay
        /// </summary>
        /// <param name="frames">Ordered Collection of the Frames for this Animation</param>
        /// <param name="delay">Amount of Time to Delay Each Frame of this Animation</param>
        public Animation(List<TextureRegion> frames, TimeSpan delay)
        {
            Frames = frames;
            Delay = delay;
        }
    }
}