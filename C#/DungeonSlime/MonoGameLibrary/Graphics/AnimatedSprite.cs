using System;
using Microsoft.Xna.Framework;

namespace MonoGameLibrary.Graphics
{
    public class AnimatedSprite : Sprite
    {
        private int _currentFrame;
        private TimeSpan _elapsed;
        private Animation _animation;

        public Animation Animation
        {
            get =>  _animation;
            set
            {
                _animation = value;
                Region = _animation.Frames[0];
            }
        }

        /// <summary>
        /// Creates New Animated Sprite
        /// </summary>
        public AnimatedSprite()
        {
            
        }

        /// <summary>
        /// Creates New Animated Sprite with the Given Animation
        /// </summary>
        /// <param name="animation">Animation for this Animated Sprite</param>
        public AnimatedSprite(Animation animation)
        {
            Animation = animation;
        }

        /// <summary>
        /// Updates the Animated Sprite
        /// </summary>
        /// <param name="gameTime">Snapshot of the Game Timing Values</param>
        public void Update(GameTime gameTime)
        {
            _elapsed += gameTime.ElapsedGameTime;

            if (_elapsed >= _animation.Delay)
            {
                _elapsed -= _animation.Delay;
                _currentFrame++;
                
                if (_currentFrame >= _animation.Frames.Count)
                {
                    _currentFrame = 0;
                }
                
                Region = _animation.Frames[_currentFrame];
            }
        }
    }
}