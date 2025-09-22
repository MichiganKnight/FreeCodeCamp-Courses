using System.Collections.Generic;
using System.IO;
using System.Xml;
using System.Xml.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace MonoGameLibrary.Graphics
{
    public class TextureAtlas
    {
        private Dictionary<string, TextureRegion> _regions;
        
        /// <summary>
        /// Gets or Sets the Source Rectangle Boundary for this Region
        /// </summary>
        public Texture2D Texture { get; set; }
        
        /// <summary>
        /// Creates a new Texture Atlas
        /// </summary>
        public TextureAtlas()
        {
            _regions = new Dictionary<string, TextureRegion>();
        }

        /// <summary>
        /// Creates a New Texture Atlas Instance Using the Given Texture
        /// </summary>
        /// <param name="texture"></param>
        public TextureAtlas(Texture2D texture)
        {
            Texture = texture;
            _regions = new Dictionary<string, TextureRegion>();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="name"></param>
        /// <param name="x"></param>
        /// <param name="y"></param>
        /// <param name="width"></param>
        /// <param name="height"></param>
        public void AddRegion(string name, int x, int y, int width, int height)
        {
            TextureRegion region = new TextureRegion(Texture, x, y, width, height);
            _regions.Add(name, region);
        }
        
        public TextureRegion GetRegion(string name)
        {
            return _regions[name];
        }
        
        /// <summary>
        /// Removes Region with the Given Name from this Texture Atlas
        /// </summary>
        /// <param name="name">Name of the Region to Remove</param>
        /// <returns></returns>
        public bool RemoveRegion(string name)
        {
            return _regions.Remove(name);
        }

        /// <summary>
        /// Removes All Regions from this Texture Atlas
        /// </summary>
        public void Clear()
        {
            _regions.Clear();
        }

        public static TextureAtlas FromFile(ContentManager content, string fileName)
        {
            TextureAtlas atlas = new TextureAtlas();

            string filePath = Path.Combine(content.RootDirectory, fileName);

            using (Stream stream = TitleContainer.OpenStream(filePath))
            {
                using (XmlReader reader = XmlReader.Create(stream))
                {
                    XDocument doc = XDocument.Load(reader);
                    XElement root = doc.Root;

                    string texturePath = root.Element("Texture").Value;
                    atlas.Texture = content.Load<Texture2D>(texturePath);
                    
                    IEnumerable<XElement> regions = root.Element("Regions")?.Elements("Region");

                    if (regions != null)
                    {
                        foreach (XElement region in regions)
                        {
                            string name = region.Attribute("name")?.Value;
                            int x = int.Parse(region.Attribute("x")?.Value ?? "0");
                            int y = int.Parse(region.Attribute("y")?.Value ?? "0");
                            int width = int.Parse(region.Attribute("width")?.Value ?? "0");
                            int height = int.Parse(region.Attribute("height")?.Value ?? "0");

                            if (!string.IsNullOrEmpty(name))
                            {
                                atlas.AddRegion(name, x, y, width, height);
                            }
                        }
                    }
                    
                    return atlas;
                }
            }
        }

        /// <summary>
        /// Creates a New Sprite Using the Region from this Texture Atlas with the Given Name
        /// </summary>
        /// <param name="regionName">Name of the Region to Create the Sprite With</param>
        /// <returns>New Sprite Using the Texture Region with the Given Name</returns>
        public Sprite CreateSprite(string regionName)
        {
            TextureRegion region = GetRegion(regionName);
            return new Sprite(region);
        }
    }
}