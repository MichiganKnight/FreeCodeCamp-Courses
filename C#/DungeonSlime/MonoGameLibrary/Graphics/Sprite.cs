using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace MonoGameLibrary.Graphics
{
    public class Sprite
    {
        /// <summary>
        /// Gets or Sets the Texture Region for this Sprite
        /// </summary>
        public TextureRegion Region { get; set; }
        
        /// <summary>
        /// Gets or Sets the Color of this Sprite
        /// </summary>
        /// <remarks>
        /// Defaults to Color.White
        /// </remarks>
        public Color Color { get; set; } = Color.White;

        /// <summary>
        /// Gets or Sets the Rotation of this Sprite
        /// </summary>
        /// <remarks>
        /// Defaults to 0.0f
        /// </remarks>
        public float Rotation { get; set; } = 0.0f;
        
        /// <summary>
        /// Gets or Sets the Scale of this Sprite
        /// </summary>
        /// <remarks>
        /// Defaults to Vector2.One
        /// </remarks>
        public Vector2 Scale { get; set; } = Vector2.One;
        
        /// <summary>
        /// Gets or Sets the XY-Coordinates of the Origin of this Sprite
        /// </summary>
        /// <remarks>
        /// Defaults to Vector2.Zero
        /// </remarks>
        public Vector2 Origin { get; set; } = Vector2.Zero;
        
        /// <summary>
        /// Gets or Sets the Effects of this Sprite
        /// </summary>
        /// <remarks>
        /// Defaults to SpriteEffects.None
        /// </remarks>
        public SpriteEffects Effects { get; set; } = SpriteEffects.None;
        
        /// <summary>
        /// Gets or Sets the Layer Depth of this Sprite
        /// </summary>
        /// <remarks>
        /// Defaults to 0.0f
        /// </remarks>
        public float LayerDepth { get; set; } = 0.0f;

        /// <summary>
        /// Gets the Width of this Sprite
        /// </summary>
        /// <remarks>
        /// Calculated by multiplying the Width of the Region by the X-Scale
        /// </remarks>
        public float Width => Region.Width * Scale.X;
        
        /// <summary>
        /// Gets the Height of this Sprite
        /// </summary>
        /// <remarks>
        /// Calculated by multiplying the Height of the Region by the Y-Scale
        /// </remarks>
        public float Height => Region.Height * Scale.Y;

        /// <summary>
        /// Creates a new Sprite
        /// </summary>
        public Sprite()
        {
            
        }
        
        /// <summary>
        /// Creates a new Sprite Using the Specified Texture Region
        /// </summary>
        /// <param name="region">Texture Region to Use as the Source Texture Region for this Sprite</param>
        public Sprite(TextureRegion region)
        {
            Region = region;
        }

        /// <summary>
        /// Sets the Origin of this Sprite to the Center
        /// </summary>
        public void CenterOrigin()
        {
            Origin = new Vector2(Region.Width, Region.Height) * 0.5f;
        }
        
        /// <summary>
        /// Submit this Sprite for Drawing in the Current Batch
        /// </summary>
        /// <param name="spriteBatch">Sprite Batch Instance Used for Batching Draw Calls</param>
        /// <param name="position">XY-Coordinate Position to Render this Sprite at</param>
        public void Draw(SpriteBatch spriteBatch, Vector2 position)
        {
            Region.Draw(spriteBatch, position, Color, Rotation, Origin, Scale, Effects, LayerDepth);
        }
    }
}