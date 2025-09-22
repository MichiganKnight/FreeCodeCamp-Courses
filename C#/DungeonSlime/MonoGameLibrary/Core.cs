using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using MonoGameLibrary.Input;

namespace MonoGameLibrary
{
    public class Core : Game
    {
        internal static Core s_instance;
        
        /// <summary>
        /// Gets a Reference to the Core Instance
        /// </summary>
        public static Core Instance => s_instance;
        
        /// <summary>
        /// Gets the Graphics Device Manager to Control the Presentation of Graphics
        /// </summary>
        public static GraphicsDeviceManager Graphics { get; private set; }
        
        /// <summary>
        /// Gets the Graphics Device to Render Graphics to the Screen
        /// </summary>
        public static new GraphicsDevice GraphicsDevice { get; private set; }
        
        /// <summary>
        /// Gets the Sprite Batch Used for all 2D Rendering
        /// </summary>
        public static SpriteBatch SpriteBatch { get; private set; }
        
        /// <summary>
        /// Gets the Content Manager to Load Global Assets
        /// </summary>
        public static new ContentManager Content { get; private set; }
        
        /// <summary>
        /// Gets a Reference to the Input Manager
        /// </summary>
        public static InputManager Input { get; private set; }
        
        /// <summary>
        /// Gets or Sets if the Game Should Exit when the Escape Key is Pressed.
        /// </summary>
        public static bool ExitOnEscape { get; set; }

        /// <summary>
        /// Creates a new Core Instance
        /// </summary>
        /// <param name="title">Title to Display in the Title Bar of the Game Window</param>
        /// <param name="width">The Initial Width, in Pixels, of the Game Window</param>
        /// <param name="height">The Initial Height, in Pixels, of the Game Window</param>
        /// <param name="fullScreen">Indicates if the Game Should Start in Fullscreen mode</param>
        public Core(string title, int width, int height, bool fullScreen)
        {
            // Ensure Multiple Cores Are Not Created
            if (s_instance != null)
            {
                throw new InvalidOperationException($"Only a Single Core Instance Can be Created");
            }

            // Store Reference to Engine for Global Member Access
            s_instance = this;
            
            // Creates a new Graphics Device Manager
            Graphics = new GraphicsDeviceManager(this);

            // Set the Graphics Defaults
            Graphics.PreferredBackBufferWidth = width;
            Graphics.PreferredBackBufferHeight = height;
            Graphics.IsFullScreen = fullScreen;
            
            // Apply the Graphic Presentation Changes
            Graphics.ApplyChanges();
            
            // Set the Window Title
            Window.Title = title;
            
            // Set the Core's Content Manager to a Reference of the Base Game's Content Manager
            Content = base.Content;
            
            // Set the Root Directory for Content
            Content.RootDirectory = "Content";
            
            // Mouse is Visible by Default
            IsMouseVisible = true;
            
            // Exit on Escape by Default
            ExitOnEscape = true;
        }

        protected override void Initialize()
        {
            base.Initialize();

            // Set the Core's Graphics Device to a Reference of the Base Game's Graphics Device'
            GraphicsDevice = base.GraphicsDevice;
            
            // Creates the Sprite Batch Instance
            SpriteBatch = new SpriteBatch(GraphicsDevice);
            
            // Creates the Input Manager Instance
            Input = new InputManager();
        }

        protected override void Update(GameTime gameTime)
        {
            Input.Update(gameTime);

            if (ExitOnEscape && Input.Keyboard.IsKeyDown(Keys.Escape))
            {
                Exit();
            }
            
            base.Update(gameTime);
        }
    }
}