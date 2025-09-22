using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using MonoGameLibrary;
using MonoGameLibrary.Graphics;

namespace DungeonSlime
{
    public class Game1 : Core
    {
        private AnimatedSprite _slime;
        private AnimatedSprite _bat;

        public Game1() : base("Dungeon Slime", 1280, 720, false)
        {
        }

        protected override void Initialize()
        {
            // TODO: Add your initialization logic here

            base.Initialize();
        }

        protected override void LoadContent()
        {
            TextureAtlas atlas = TextureAtlas.FromFile(Content, "Images/Atlas-Definition.xml");

            _slime = atlas.CreateAnimatedSprite("Slime-Animation");
            _slime.Scale = new Vector2(4.0f, 4.0f);
            
            _bat = atlas.CreateAnimatedSprite("Bat-Animation");
            _bat.Scale = new Vector2(4.0f, 4.0f);
        }

        protected override void Update(GameTime gameTime)
        {
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed ||
                Keyboard.GetState().IsKeyDown(Keys.Escape))
                Exit();

            _slime.Update(gameTime);
            _bat.Update(gameTime);

            base.Update(gameTime);
        }

        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);
            
            SpriteBatch.Begin(samplerState: SamplerState.PointClamp);
            
            _slime.Draw(SpriteBatch, Vector2.One);
            _bat.Draw(SpriteBatch, new Vector2(_slime.Width + 10, 0));

            // Always End the Sprite Batch When Finished
            SpriteBatch.End();

            base.Draw(gameTime);
        }
    }
}