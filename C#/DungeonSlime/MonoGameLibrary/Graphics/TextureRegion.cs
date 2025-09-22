using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace MonoGameLibrary.Graphics
{
    /// <summary>
    /// Represents a Rectangular Region Within a Texture
    /// </summary>
    public class TextureRegion
    {
        /// <summary>
        /// Gets or Sets the Source Texture for this Region
        /// </summary>
        public Texture2D Texture { get; set; }
        
        /// <summary>
        /// Gets or Sets the Source Rectangle Boundary for this Region
        /// </summary>
        public Rectangle SourceRectangle { get; set; }
        
        /// <summary>
        /// Gets the Width of this Region
        /// </summary>
        public int Width => SourceRectangle.Width;
        
        /// <summary>
        /// Gets the Height of this Region
        /// </summary>
        public int Height => SourceRectangle.Height;

        /// <summary>
        /// Creates a new Texture Region
        /// </summary>
        public TextureRegion()
        {
            
        }

        /// <summary>
        /// Creates a New Texture Region Using the Specified Source Texture
        /// </summary>
        /// <param name="texture">Texture to Use as the Source Texture</param>
        /// <param name="x">X-Coordinate Position of the Upper-Left Corner of this Texture Region Relative to the Upper-Left Corner of the Source Texture</param>
        /// <param name="y">Y-Coordinate Position of the Upper-Left Corner of this Texture Region Relative to the Upper-Left Corner of the Source Texture</param>
        /// <param name="width">Width, in Pixels, of this Texture Region</param>
        /// <param name="height">Height, in Pixels, of this Texture Region</param>
        public TextureRegion(Texture2D texture, int x, int y, int width, int height)
        {
            Texture = texture;
            SourceRectangle = new Rectangle(x, y, width, height);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="spriteBatch"></param>
        /// <param name="position"></param>
        /// <param name="color"></param>
        public void Draw(SpriteBatch spriteBatch, Vector2 position, Color color)
        {
            Draw(spriteBatch, position, color, 0.0f, Vector2.Zero, Vector2.One, SpriteEffects.None, 0.0f);
        }

        /// <summary>
        /// Submit this Texture Region for Drawing in the Current Batch
        /// </summary>
        /// <param name="spriteBatch">SpriteBatch Instance Used for Batching Draw Calls</param>
        /// <param name="position">XY-Coordinate Location to Draw this Texture Region on the Screen</param>
        /// <param name="color">Color Mask to Apply When Drawing this Texture Region on Screen</param>
        /// <param name="rotation">Amount of Rotation, in Radians, to Apply When Drawing this Texture on Screen</param>
        /// <param name="origin">Center of Rotation, Scaling, and Position When Drawing this Texture on Screen</param>
        /// <param name="scale">Scale Factor to Apply When Drawing this Texture on Screen</param>
        /// <param name="effects">Specifies if this Texture Region Should be Flipped Horizontally, Vertically, or Both When Drawing this Texture on Screen</param>
        /// <param name="layerDepth">Depth of the Layer to Use When Drawing this Texture on Screen</param>
        public void Draw(SpriteBatch spriteBatch, Vector2 position, Color color, float rotation, Vector2 origin, float scale, SpriteEffects effects, float layerDepth)
        {
            Draw(spriteBatch, position, color, rotation, origin, new Vector2(scale, scale), effects, layerDepth);
        }

        /// <summary>
        /// Submit this Texture Region for Drawing in the Current Batch
        /// </summary>
        /// <param name="spriteBatch">SpriteBatch Instance Used for Batching Draw Calls</param>
        /// <param name="position">XY-Coordinate Location to Draw this Texture Region on the Screen</param>
        /// <param name="color">Color Mask to Apply When Drawing this Texture Region on Screen</param>
        /// <param name="rotation">Amount of Rotation, in Radians, to Apply When Drawing this Texture on Screen</param>
        /// <param name="origin">Center of Rotation, Scaling, and Position When Drawing this Texture on Screen</param>
        /// <param name="scale">Amount of Scaling to Apply to the X- and Y-Axes When Drawing this Texture Region On Screen</param>
        /// <param name="effects">Specifies if this Texture Region Should be Flipped Horizontally, Vertically, or Both When Drawing this Texture on Screen</param>
        /// <param name="layerDepth">Depth of the Layer to Use When Drawing this Texture on Screen</param>
        public void Draw(SpriteBatch spriteBatch, Vector2 position, Color color, float rotation, Vector2 origin, Vector2 scale, SpriteEffects effects, float layerDepth)
        {
            spriteBatch.Draw(Texture, position, SourceRectangle, color, rotation, origin, scale, effects, layerDepth);
        }
    }
}