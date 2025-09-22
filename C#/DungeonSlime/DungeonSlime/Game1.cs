﻿using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using MonoGameLibrary;
using MonoGameLibrary.Graphics;
using MonoGameLibrary.Input;

namespace DungeonSlime
{
    public class Game1 : Core
    {
        private AnimatedSprite _slime;
        private AnimatedSprite _bat;

        private Vector2 _slimePosition;

        private const float MOVEMENT_SPEED = 5.0f;

        private Vector2 _batPosition;
        private Vector2 _batVelocity;
        
        private Tilemap _tilemap;

        private Rectangle _roomBounds;

        public Game1() : base("Dungeon Slime", 1280, 720, false)
        {

        }

        protected override void Initialize()
        {
            base.Initialize();

            Rectangle screenBounds = GraphicsDevice.PresentationParameters.Bounds;

            _roomBounds = new Rectangle(
                (int)_tilemap.TileWidth,
                (int)_tilemap.TileHeight,
                screenBounds.Width - (int)_tilemap.TileWidth * 2,
                screenBounds.Height - (int)_tilemap.TileHeight * 2
            );
            
            int centerRow = _tilemap.Rows / 2;
            int centerColumn = _tilemap.Columns / 2;
            _slimePosition = new Vector2(centerColumn * _tilemap.TileWidth, centerRow * _tilemap.TileHeight);
            
            _batPosition = new Vector2(_roomBounds.Left, _roomBounds.Top);
            
            AssignRandomBatVelocity();
        }

        protected override void LoadContent()
        {
            TextureAtlas atlas = TextureAtlas.FromFile(Content, "Images/Atlas-Definition.xml");

            _slime = atlas.CreateAnimatedSprite("Slime-Animation");
            _slime.Scale = new Vector2(4.0f, 4.0f);

            _bat = atlas.CreateAnimatedSprite("Bat-Animation");
            _bat.Scale = new Vector2(4.0f, 4.0f);
            
            _tilemap = Tilemap.FromFile(Content, "Images/Tilemap-Definition.xml");
            _tilemap.Scale = new Vector2(4.0f, 4.0f);
        }

        protected override void Update(GameTime gameTime)
        {
            _slime.Update(gameTime);
            _bat.Update(gameTime);

            CheckKeyboardInput();
            CheckGamePadInput();

            Circle slimeBounds = new Circle(
                (int)(_slimePosition.X + (_slime.Width * 0.5f)),
                (int)(_slimePosition.Y + (_slime.Height * 0.5f)),
                (int)(_slime.Width * 0.5f)
            );
            
            if (slimeBounds.Left < _roomBounds.Left)
            {
                _slimePosition.X = _roomBounds.Left;
            }
            else if (slimeBounds.Right > _roomBounds.Right)
            {
                _slimePosition.X = _roomBounds.Right - _slime.Width;
            }

            if (slimeBounds.Top < _roomBounds.Top)
            {
                _slimePosition.Y = _roomBounds.Top;
            }
            else if (slimeBounds.Bottom > _roomBounds.Bottom)
            {
                _slimePosition.Y = _roomBounds.Bottom - _slime.Height;
            }
            
            Vector2 newBatPosition = _batPosition + _batVelocity;

            Circle batBounds = new Circle(
                (int)(newBatPosition.X + (_bat.Width * 0.5f)),
                (int)(newBatPosition.Y + (_bat.Height * 0.5f)),
                (int)(_bat.Width * 0.5f)
            );

            Vector2 normal = Vector2.Zero;
            
            if (batBounds.Left < _roomBounds.Left)
            {
                normal.X = Vector2.UnitX.X;
                newBatPosition.X = _roomBounds.Left;
            }
            else if (batBounds.Right > _roomBounds.Right)
            {
                normal.X = -Vector2.UnitX.X;
                newBatPosition.X = _roomBounds.Right - _bat.Width;
            }

            if (batBounds.Top < _roomBounds.Top)
            {
                normal.Y = Vector2.UnitY.Y;
                newBatPosition.Y = _roomBounds.Top;
            }
            else if (batBounds.Bottom > _roomBounds.Bottom)
            {
                normal.Y = -Vector2.UnitY.Y;
                newBatPosition.Y = _roomBounds.Bottom - _bat.Height;
            }
            
            if (normal != Vector2.Zero)
            {
                normal.Normalize();
                _batVelocity = Vector2.Reflect(_batVelocity, normal);
            }

            _batPosition = newBatPosition;

            if (slimeBounds.Intersects(batBounds))
            {
                int totalColumns = GraphicsDevice.PresentationParameters.BackBufferWidth / (int)_bat.Width;
                int totalRows = GraphicsDevice.PresentationParameters.BackBufferHeight / (int)_bat.Height;
                
                int column = Random.Shared.Next(0, totalColumns);
                int row = Random.Shared.Next(0, totalRows);
                
                _batPosition = new Vector2(column * _bat.Width, row * _bat.Height);
                
                AssignRandomBatVelocity();
            }

            base.Update(gameTime);
        }

        private void AssignRandomBatVelocity()
        {
            float angle = (float)(Random.Shared.NextDouble() * Math.PI * 2);
            
            float x = (float)Math.Cos(angle);
            float y = (float)Math.Sin(angle);
            Vector2 direction = new Vector2(x, y);
            
            _batVelocity = direction * MOVEMENT_SPEED;
        }

        private void CheckKeyboardInput()
        {
            float speed = MOVEMENT_SPEED;
            if (Input.Keyboard.IsKeyDown(Keys.Space))
            {
                speed *= 1.5f;
            }

            if (Input.Keyboard.IsKeyDown(Keys.W) || Input.Keyboard.IsKeyDown(Keys.Up))
            {
                _slimePosition.Y -= speed;
            }

            if (Input.Keyboard.IsKeyDown(Keys.S) || Input.Keyboard.IsKeyDown(Keys.Down))
            {
                _slimePosition.Y += speed;
            }

            if (Input.Keyboard.IsKeyDown(Keys.A) || Input.Keyboard.IsKeyDown(Keys.Left))
            {
                _slimePosition.X -= speed;
            }
            
            if (Input.Keyboard.IsKeyDown(Keys.D) || Input.Keyboard.IsKeyDown(Keys.Right))
            {
                _slimePosition.X += speed;
            }
        }

        private void CheckGamePadInput()
        {
            GamePadInfo gamePadOne = Input.GamePads[(int)PlayerIndex.One];
            
            float speed = MOVEMENT_SPEED;
            if (gamePadOne.IsButtonDown(Buttons.A))
            {
                speed *= 1.5f;
                GamePad.SetVibration(PlayerIndex.One, 1.0f, 1.0f);
            }
            else
            {
                GamePad.SetVibration(PlayerIndex.One, 0.0f, 0.0f);
            }
            
            if (gamePadOne.LeftThumbStick != Vector2.Zero)
            {
                _slimePosition.X += gamePadOne.LeftThumbStick.X * speed;
                _slimePosition.Y -= gamePadOne.LeftThumbStick.Y * speed;
            }
            else
            {
                if (gamePadOne.IsButtonDown(Buttons.DPadUp))
                {
                    _slimePosition.Y -= speed;
                }
                
                if (gamePadOne.IsButtonDown(Buttons.DPadDown))
                {
                    _slimePosition.Y += speed;
                }
                
                if (gamePadOne.IsButtonDown(Buttons.DPadLeft))
                {
                    _slimePosition.X -= speed;
                }
                
                if (gamePadOne.IsButtonDown(Buttons.DPadRight))
                {
                    _slimePosition.X += speed;
                }
            }
        }

        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);

            SpriteBatch.Begin(samplerState: SamplerState.PointClamp);
            
            _tilemap.Draw(SpriteBatch);
            _slime.Draw(SpriteBatch, _slimePosition);
            _bat.Draw(SpriteBatch, _batPosition);
            
            SpriteBatch.End();

            base.Draw(gameTime);
        }
    }
}